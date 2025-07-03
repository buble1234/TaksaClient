package wtf.taksa.manager;


import wtf.taksa.command.Command;
import wtf.taksa.command.impl.HelpCommand;
import wtf.taksa.command.impl.ToggleCommand;
import wtf.taksa.usual.utils.chat.ChatUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Kenny1337
 * @since 28.06.2025
 */
public class CommandManager {
    private static final String PREFIX = ".";
    private final List<Command> commands = new ArrayList<>();
    private final ModuleManager moduleManager;

    public CommandManager(ModuleManager moduleManager) {
        this.moduleManager = moduleManager;
        addCommand(new HelpCommand());
        addCommand(new ToggleCommand(moduleManager));
    }

    public void addCommand(Command command) {
        commands.add(command);
    }

    public boolean processCommand(String message) {
        if (!message.startsWith(PREFIX)) {
            return false;
        }

        String[] split = message.substring(PREFIX.length()).split(" ");
        String commandName = split[0].toLowerCase();
        String[] args = Arrays.copyOfRange(split, 1, split.length);

        for (Command command : commands) {
            if (command.matches(commandName)) {
                try {
                    command.execute(args);
                } catch (Exception e) {
                    ChatUtil.clientError("Ошибка при выполнении команды: " + e.getMessage());
                }
                return true;
            }
        }

        ChatUtil.clientError("Команда не найдена: " + commandName);
        return true;
    }

    public List<Command> getCommands() {
        return commands;
    }

    public static String getPrefix() {
        return PREFIX;
    }
} 