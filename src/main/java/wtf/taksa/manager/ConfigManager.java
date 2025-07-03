package wtf.taksa.manager;

import com.google.gson.*;
import net.minecraft.client.MinecraftClient;
import wtf.taksa.module.Module;
import wtf.taksa.module.ModuleBinding;
import wtf.taksa.module.ModuleHolder;
import wtf.taksa.module.setting.*;
import wtf.taksa.usual.utils.chat.ChatUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * @author Kenny1337
 * @since 03.07.2025
 */
public class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final Path configDir;
    private final Path defaultConfig;
    private final ModuleHolder moduleHolder;

    private boolean disclaimerAccepted = false;

    public ConfigManager(ModuleHolder moduleHolder) {
        this.moduleHolder = moduleHolder;
        this.configDir = Paths.get(MinecraftClient.getInstance().runDirectory.getPath(), "taksa", "configs");
        this.defaultConfig = this.configDir.resolve("default.json");

        try {
            if (!Files.exists(configDir)) {
                Files.createDirectories(configDir);
            }
        } catch (IOException e) {
            ChatUtil.clientError("Не удалось создать директорию конфигов: " + e.getMessage());
        }
    }
    public void saveConfig(String configName) {
        if (configName == null || configName.isEmpty()) {
            configName = "default";
        }

        Path configFile = configDir.resolve(configName + ".json");

        try {
            if (!Files.exists(configFile.getParent())) {
                Files.createDirectories(configFile.getParent());
            }

            JsonObject root = new JsonObject();

            root.addProperty("disclaimerAccepted", disclaimerAccepted);

            JsonArray modulesArray = new JsonArray();

            for (Module module : moduleHolder.getModules()) {
                JsonObject moduleObj = new JsonObject();
                moduleObj.addProperty("name", module.getName());
                moduleObj.addProperty("enabled", module.isEnabled());
                moduleObj.addProperty("bind", module.getBind());
                moduleObj.addProperty("binding", module.getBinding().name());

                if (!module.getSettings().isEmpty()) {
                    JsonArray settingsArray = new JsonArray();

                    for (Setting<?> setting : module.getSettings()) {
                        JsonObject settingObj = new JsonObject();
                        settingObj.addProperty("name", setting.getName());

                        if (setting instanceof BooleanSetting) {
                            settingObj.addProperty("type", "boolean");
                            settingObj.addProperty("value", ((BooleanSetting) setting).getValue());
                        } else if (setting instanceof DoubleSetting) {
                            settingObj.addProperty("type", "double");
                            settingObj.addProperty("value", ((DoubleSetting) setting).getValue());
                        } else if (setting instanceof ListSetting) {
                            settingObj.addProperty("type", "list");
                            JsonObject listValues = new JsonObject();
                            for (Map.Entry<String, Boolean> entry : ((ListSetting) setting).getValue().entrySet()) {
                                listValues.addProperty(entry.getKey(), entry.getValue());
                            }
                            settingObj.add("value", listValues);
                        } else if (setting instanceof ModeSetting) {
                            settingObj.addProperty("type", "mode");
                            settingObj.addProperty("value", ((ModeSetting) setting).getValue());
                        } else if (setting instanceof BindSetting) {
                            settingObj.addProperty("type", "bind");
                            settingObj.addProperty("value", ((BindSetting) setting).getValue());
                        }

                        settingsArray.add(settingObj);
                    }

                    moduleObj.add("settings", settingsArray);
                }

                modulesArray.add(moduleObj);
            }

            root.add("modules", modulesArray);

            try (Writer writer = Files.newBufferedWriter(configFile, StandardCharsets.UTF_8)) {
                GSON.toJson(root, writer);
                ChatUtil.clientMessage("§aКонфигурация §f" + configName + " §aуспешно сохранена");
            }
        } catch (Exception e) {
            ChatUtil.clientError("Ошибка при сохранении конфигурации: " + e.getMessage());
        }
    }
    public void loadConfig(String configName) {
        if (configName == null || configName.isEmpty()) {
            configName = "default";
        }

        Path configFile = configDir.resolve(configName + ".json");

        if (!Files.exists(configFile)) {
            ChatUtil.clientError("Конфигурация не найдена: " + configName);
            return;
        }

        try (Reader reader = Files.newBufferedReader(configFile, StandardCharsets.UTF_8)) {
            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();

            if (root.has("disclaimerAccepted")) {
                disclaimerAccepted = root.get("disclaimerAccepted").getAsBoolean();
            } else {
                disclaimerAccepted = false;
            }

            JsonArray modulesArray = root.getAsJsonArray("modules");

            for (JsonElement moduleElement : modulesArray) {
                JsonObject moduleObj = moduleElement.getAsJsonObject();
                String name = moduleObj.get("name").getAsString();

                Module module = moduleHolder.getModule(name);
                if (module == null) {
                    continue;
                }

                boolean enabled = moduleObj.get("enabled").getAsBoolean();
                int bind = moduleObj.get("bind").getAsInt();
                String binding = moduleObj.has("binding") ?
                        moduleObj.get("binding").getAsString() : ModuleBinding.TOGGLE.name();

                module.setBind(bind);
                module.setBinding(ModuleBinding.valueOf(binding));

                if (moduleObj.has("settings")) {
                    JsonArray settingsArray = moduleObj.getAsJsonArray("settings");

                    for (JsonElement settingElement : settingsArray) {
                        JsonObject settingObj = settingElement.getAsJsonObject();
                        String settingName = settingObj.get("name").getAsString();
                        String type = settingObj.get("type").getAsString();

                        for (Setting<?> setting : module.getSettings()) {
                            if (setting.getName().equals(settingName)) {
                                switch (type) {
                                    case "boolean":
                                        ((BooleanSetting) setting).setValue(settingObj.get("value").getAsBoolean());
                                        break;
                                    case "double":
                                        ((DoubleSetting) setting).setValue(settingObj.get("value").getAsDouble());
                                        break;
                                    case "list":
                                        JsonObject listValues = settingObj.getAsJsonObject("value");
                                        ListSetting listSetting = (ListSetting) setting;
                                        for (Map.Entry<String, JsonElement> entry : listValues.entrySet()) {
                                            listSetting.setValue(entry.getKey(), entry.getValue().getAsBoolean());
                                        }
                                        break;
                                    case "mode":
                                        ((ModeSetting) setting).setValue(settingObj.get("value").getAsString());
                                        break;
                                    case "bind":
                                        ((BindSetting) setting).setValue(settingObj.get("value").getAsInt());
                                        break;
                                }
                                break;
                            }
                        }
                    }
                }

                if (enabled != module.isEnabled()) {
                    module.toggle();
                }
            }

            ChatUtil.clientMessage("§aКонфигурация §f" + configName + " §aуспешно загружена");
        } catch (Exception e) {
            ChatUtil.clientError("Ошибка при загрузке конфигурации: " + e.getMessage());
        }
    }
    public List<String> getAvailableConfigs() {
        List<String> configNames = new ArrayList<>();

        try {
            Files.list(configDir)
                    .filter(path -> path.toString().endsWith(".json"))
                    .forEach(path -> {
                        String fileName = path.getFileName().toString();
                        configNames.add(fileName.substring(0, fileName.length() - 5));
                    });
        } catch (IOException e) {
            ChatUtil.clientError("Ошибка при получении списка конфигураций: " + e.getMessage());
        }

        return configNames;
    }

    public void deleteConfig(String configName) {
        if (configName == null || configName.isEmpty() || configName.equals("default")) {
            ChatUtil.clientError("Невозможно удалить конфигурацию по умолчанию");
            return;
        }

        Path configFile = configDir.resolve(configName + ".json");

        try {
            if (Files.deleteIfExists(configFile)) {
                ChatUtil.clientMessage("§aКонфигурация §f" + configName + " §aуспешно удалена");
            } else {
                ChatUtil.clientError("Конфигурация не найдена: " + configName);
            }
        } catch (IOException e) {
            ChatUtil.clientError("Ошибка при удалении конфигурации: " + e.getMessage());
        }
    }
    public void loadDefaultConfig() {
        if (!Files.exists(defaultConfig)) {
            saveConfig("default");
        }
        loadConfig("default");
    }
    public void setDisclaimerAccepted(boolean accepted) {
        this.disclaimerAccepted = accepted;
        saveConfig("default");
    }
    public boolean isDisclaimerAccepted() {
        return disclaimerAccepted;
    }
}
