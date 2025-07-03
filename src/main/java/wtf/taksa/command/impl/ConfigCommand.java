package wtf.taksa.command.impl;

import wtf.taksa.Taksa;
import wtf.taksa.command.Command;
import wtf.taksa.manager.ConfigManager;

import java.util.List;
/**
 * @author Kenny1337
 * @since 03.07.2025
 */
public class ConfigCommand extends Command {

    public ConfigCommand() {
        super("cfg", "Управление конфигурациями", ".cfg <save|load|list|delete> [имя]", "config");
    }

    @Override
    public void execute(String[] args) {
        ConfigManager configManager = Taksa.getConfigManager();
        
        if (configManager == null) {
            sendError("Менеджер конфигураций не найден!");
            return;
        }
        
        if (args.length == 0) {
            sendSyntax();
            return;
        }
        
        String action = args[0].toLowerCase();
        
        switch (action) {
            case "save":
                if (args.length < 2) {
                    configManager.saveConfig("default");
                } else {
                    configManager.saveConfig(args[1]);
                }
                break;
                
            case "load":
                if (args.length < 2) {
                    configManager.loadConfig("default");
                } else {
                    configManager.loadConfig(args[1]);
                }
                break;
                
            case "list":
                List<String> configs = configManager.getAvailableConfigs();
                if (configs.isEmpty()) {
                    sendMessage("§7Конфигурации не найдены");
                } else {
                    sendMessage("§7------------- [§fДоступные конфигурации§7] -------------");
                    for (String config : configs) {
                        sendMessage("§f- " + config);
                    }
                    sendMessage("§7------------------------------------");
                }
                break;
                
            case "delete":
                if (args.length < 2) {
                    sendError("Укажите имя конфигурации для удаления");
                } else {
                    configManager.deleteConfig(args[1]);
                }
                break;
                
            default:
                sendSyntax();
                break;
        }
    }
} 