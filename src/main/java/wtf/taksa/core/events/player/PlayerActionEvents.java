package wtf.taksa.core.events.player;

import net.minecraft.entity.Entity;
import wtf.taksa.core.events.Event;

/**
 * Автор: NoCap
 * Дата создания: 04.07.2025
 */
public abstract class PlayerActionEvents extends Event {

    private PlayerActionEvents() {
    }

    public static class Attack extends PlayerActionEvents {
        private final Entity entity;
        private final boolean pre;

        public Attack(Entity entity, boolean pre) {
            super();
            this.entity = entity;
            this.pre = pre;
        }

        public Entity getEntity() {
            return entity;
        }

        public boolean isPre() {
            return pre;
        }
    }

    public static class Jump extends PlayerActionEvents {
        public Jump() {
            super();
        }
    }
}