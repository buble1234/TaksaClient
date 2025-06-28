package wtf.taksa.usual.utils.math;

import lombok.Getter;
/**
 * @author Kenny1337
 * @since 28.06.2025
 */
public class Timer {
    private long time;
    @Getter
    public long lastMS = System.currentTimeMillis();
    public void reset() {
        lastMS = System.currentTimeMillis();
    }
    public boolean isReached(long time) {
        return System.currentTimeMillis() - lastMS > time;
    }
    public boolean every(float ms) {
        long currentTime = System.currentTimeMillis();
        boolean passed = currentTime - lastMS >= ms;
        if (passed) {
            reset();
        }
        return passed;
    }

    public boolean finished(final double delay) {
        return System.currentTimeMillis() - delay >= lastMS;
    }

    public void setLastMS(long newValue) {
        lastMS = System.currentTimeMillis() + newValue;
    }
    public void setTime(long time) {
        lastMS = time;
    }

    public long getTime() {
        return System.currentTimeMillis() - lastMS;
    }
    public boolean isRunning() {
        return System.currentTimeMillis() - lastMS <= 0;
    }
    public boolean hasTimeElapsed(float v) {
        return lastMS < System.currentTimeMillis();
    }

    public boolean hasTimeElapsed(int duration) {
        return false;
    }
    public boolean passedMs(long ms) {
        return getMs(System.nanoTime() - time) >= ms;
    }
    public long getMs(long time) {
        return time / 1000000L;
    }

    public long getPassedTimeMs() {
        return getMs(System.nanoTime() - time);
    }

    public boolean timeElapsed(long ms) {
        return System.currentTimeMillis() - lastMS >= ms;
    }
}