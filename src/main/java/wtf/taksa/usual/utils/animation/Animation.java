package wtf.taksa.usual.utils.animation;


import lombok.Setter;
import lombok.experimental.Accessors;
import wtf.taksa.usual.utils.math.Timer;

import static wtf.taksa.usual.utils.animation.Direction.FORWARDS;
/**
 * @author Kenny1337
 * @since 28.06.2025
 */

@Setter
@Accessors(chain = true)
public abstract class Animation implements AnimationCalculation {
    private final Timer counter = new Timer();
    protected int ms;
    protected double value;

    protected Direction direction = FORWARDS;

    public void reset() {
        counter.reset();
    }

    public boolean isDone() {
        return counter.isReached(ms);
    }

    public boolean isFinished(Direction direction) {
        return this.direction == direction && isDone();
    }

    public void setDirection(Direction direction) {
        if (this.direction != direction) {
            this.direction = direction;
            adjustTimer();
        }
    }

    private void adjustTimer() {
        counter.setTime(
                System.currentTimeMillis() - ((long) ms - Math.min(ms, counter.getTime()))
        );
    }

    public Double getOutput() {
        double time = (1 - calculation(counter.getTime())) * value;

        return direction == FORWARDS
                ? endValue()
                : isDone() ? 0.0 : time;
    }

    private double endValue() {
        return isDone()
                ? value
                : calculation(counter.getTime()) * value;
    }
}