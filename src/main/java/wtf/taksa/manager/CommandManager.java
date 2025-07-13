package wtf.taksa.manager;


import lombok.Getter;
import wtf.taksa.command.Command;
import wtf.taksa.command.impl.FriendCommand;
import wtf.taksa.command.impl.HelpCommand;
import wtf.taksa.usual.utils.chat.ChatUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Kenny1337
 * @since 28.06.2025
 */
@Getter
public class CommandManager {
    private static final String PREFIX = ".";
    private final List<Command> commands = new ArrayList<>();

    public CommandManager() {
        addCommand(new HelpCommand());
        addCommand(new FriendCommand());
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

    public static String getPrefix() {
        return PREFIX;
    }
} 