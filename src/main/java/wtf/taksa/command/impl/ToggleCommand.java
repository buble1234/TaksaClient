package wtf.taksa.command.impl;

import wtf.taksa.command.Command;
import wtf.taksa.manager.ModuleManager;
import wtf.taksa.module.Module;

public class ToggleCommand extends Command {
    private final ModuleManager moduleManager;

    public ToggleCommand(ModuleManager moduleManager) {
        super("toggle", "Включает или выключает модуль.", ".toggle", "t");
        this.moduleManager = moduleManager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            sendMessage("§cИспользование: .toggle <имя_модуля>");
            return;
        }

        String moduleName = args[0].toLowerCase();

        Module module = moduleManager.getModuleByName(moduleName);

        if (module == null) {
            sendMessage("§cМодуль '" + moduleName + "' не найден.");
            return;
        }

        module.toggle();

        sendMessage("§aМодуль '" + module.getName() + "' " + (module.isEnabled() ? "включен" : "выключен") + ".");
    }
}