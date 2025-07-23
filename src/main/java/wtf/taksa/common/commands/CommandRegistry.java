/*
 * @author Sarkolsss
 * @date 23.07.2025, 14:06
 */

package wtf.taksa.common.commands;

import wtf.taksa.common.commands.storage.GCCommand;
import wtf.taksa.engine.Engine;
import wtf.taksa.engine.events.controllers.EventType;
import wtf.taksa.engine.events.controllers.Listen;
import wtf.taksa.engine.events.storage.PlayerEvents;

import java.util.ArrayList;
import java.util.List;

public class CommandRegistry {
    public static List<Command> commands = new ArrayList<Command>();

    public void init() {
        addAll(new GCCommand());
    }

    public void addAll(Command... commands1) {
        Engine.EVENT_BUS.subscribe(this);
        commands.addAll(List.of(commands1));
    }

    @Listen
    public void onChat(PlayerEvents.Chat event) {
        if (event.getType() == EventType.Pre) {
            if (event.getMessage().startsWith(".")) {
                event.cancel();
            }
        }
    }
}
