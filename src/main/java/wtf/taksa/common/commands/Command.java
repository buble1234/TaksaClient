/*
 * @author Sarkolsss
 * @date 23.07.2025, 14:05
 */

package wtf.taksa.common.commands;

import lombok.Getter;
import wtf.taksa.unclassified.interfaces.ContextWrapper;

@Getter
public class Command implements ContextWrapper {
    public String name;
    public String[] aliases;
    public String description;
    public String usage;

    public Command(String name, String description, String usage, String... aliases) {
        this.name = name;
        this.aliases = aliases;
        this.description = description;
    }

    public void execute(String[] args) {

    }
}
