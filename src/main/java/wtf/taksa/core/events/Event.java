package wtf.taksa.core.events;

/**
 * Автор: NoCap
 * Дата создания: 23.06.2025
 */

public class Event {
    private boolean cancelled = false;

    public boolean isCancelled() {
        return cancelled;
    }

    public void cancel() {
        cancelled = true;
    }
}