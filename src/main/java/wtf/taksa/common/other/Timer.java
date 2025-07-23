/*
 * @author Sarkolsss
 * @date 23.07.2025, 11:45
 */

package wtf.taksa.common.other;

import lombok.Getter;

@Getter
public class Timer {
    private long startTime;

    public Timer() {
        reset();
    }

    public boolean finished(final double delay) {
        return System.currentTimeMillis() - delay >= startTime;
    }

    public boolean every(final double delay) {
        boolean finished = this.finished(delay);
        if (finished) reset();
        return finished;
    }

    public void reset() {
        this.startTime = System.currentTimeMillis();
    }

    public long elapsedTime() {
        return System.currentTimeMillis() - this.startTime;
    }

    public void setMs(long ms) {
        this.startTime = System.currentTimeMillis() - ms;
    }
}