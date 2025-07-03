package wtf.taksa;

import lombok.Getter;
import net.fabricmc.api.ModInitializer;
import wtf.taksa.core.Core;
import wtf.taksa.manager.CommandManager;
import wtf.taksa.manager.ConfigManager;
import wtf.taksa.manager.ModuleManager;
import wtf.taksa.module.ModuleHolder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Taksa implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("LOGER SYKA");

    private static Taksa instance;
    @Getter
    private final ModuleManager moduleManager = new ModuleManager();
    private static CommandManager commandManager;
    private static ConfigManager configManager;
    private static boolean configInitialized = false;

    Core core = new Core();

    @Override
    public void onInitialize() {
        core.inCore();
        instance = this;

        commandManager = new CommandManager();
        moduleManager.init();

        configManager = new ConfigManager(ModuleHolder.getInstance());
    }
    public static void initConfig() {
        if (!configInitialized && configManager != null) {
            configManager.loadDefaultConfig();
            configInitialized = true;
        }
    }

    public static Taksa getInstance() {
        return instance;
    }

    public static CommandManager getCommandManager() {
        return commandManager;
    }

    public static ConfigManager getConfigManager() {
        return configManager;
    }
}
