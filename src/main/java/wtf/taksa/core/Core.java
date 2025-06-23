package wtf.taksa.core;

import com.google.common.eventbus.EventBus;
import wtf.taksa.usual.utils.minecraft.ContextWrapper;

/**
 * Автор: NoCap
 * Дата создания: 23.06.2025
 */
public class Core implements ContextWrapper {
    public static EventBus EVENT_BUS = new EventBus();

    public void inCore() {
        EVENT_BUS.register(this);
    }
}
