package wtf.taksa.command;

import wtf.taksa.usual.utils.chat.ChatUtil;

/**
 * @author Kenny1337
 * @since 28.06.2025
 */
public abstract class Command {
    private final String name;
    private final String[] aliases;
    private final String description;
    private final String syntax;

    public Command(String name, String description, String syntax, String... aliases) {
        this.name = name;
        this.description = description;
        this.syntax = syntax;
        this.aliases = aliases;
    }

    public abstract void execute(String[] args);

    public boolean matches(String name) {
        if (this.name.equalsIgnoreCase(name)) {
            return true;
        }

        for (String alias : aliases) {
            if (alias.equalsIgnoreCase(name)) {
                return true;
            }
        }

        return false;
    }
    protected void sendMessage(String message) {
        ChatUtil.clientMessage(message);
    }
    protected void sendError(String message) {
        ChatUtil.clientError(message);
    }
    protected void sendSyntax() {
        sendError("Использование: " + syntax);
    }


    public String getName() {
        return name;
    }

    public String[] getAliases() {
        return aliases;
    }

    public String getDescription() {
        return description;
    }

    public String getSyntax() {
        return syntax;
    }
} 