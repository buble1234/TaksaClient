package wtf.taksa.usual.utils.animation.impl;

/**
 * @author Kenny1337
 * @since 28.06.2025
 */
import wtf.taksa.usual.utils.animation.Animation;

public class ZoomAnim extends Animation {
    @Override
    public double calculation(double value) {
        return 1 - Math.abs((value / ms) - 1);
    }
}