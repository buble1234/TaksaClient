/*
 * @author Sarkolsss
 * @date 23.07.2025, 14:08
 */

package wtf.taksa.common.commands.storage;

import wtf.taksa.common.commands.Command;
import wtf.taksa.common.other.ClientUtil;

public class GCCommand extends Command {
    public GCCommand() {
        super("GC", "Запускает сборщик мусора.", ".gc", ".gc");
    }

    @Override
    public void execute(String[] args) {
        if (args[1].equalsIgnoreCase("gc")) {
            System.gc();
            ClientUtil.print("Успешно собрал мусор!");
        }
    }
}