package wtf.taksa.engine.events;

/**
 * Автор: NoCap
 * Дата создания: 17.07.2025
 */
public class CancellableEvent extends Event {

    private boolean cancelled = false;

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}