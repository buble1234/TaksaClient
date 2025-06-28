package wtf.taksa.command.impl;

import wtf.taksa.Taksa;
import wtf.taksa.command.Command;
import wtf.taksa.manager.CommandManager;
/**
 * @author Kenny1337
 * @since 28.06.2025
 */
public class HelpCommand extends Command {

    public HelpCommand() {
        super("help", "Показывает список всех команд.", ".help", "h");
    }

    @Override
    public void execute(String[] args) {
        sendMessage("§7------------- [§fСписок команд§7] -------------");
        for (Command command : Taksa.getCommandManager().getCommands()) {
            sendMessage(String.format("§f%s%s §7- %s", CommandManager.getPrefix(), command.getName(), command.getDescription()));
        }
        sendMessage("§7------------------------------------");
    }
} 