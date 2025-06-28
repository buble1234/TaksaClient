package wtf.taksa.usual.utils.animation.impl;

import wtf.taksa.usual.utils.animation.Animation;
/**
 * @author Kenny1337
 * @since 28.06.2025
 */
public class BounceAnim extends Animation {

    @Override
    public double calculation(double value) {
        double x = value / ms;
        return x * x;
    }
}