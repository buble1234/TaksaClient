package wtf.taksa.usual.utils.animation.impl;


import wtf.taksa.usual.utils.animation.Animation;
/**
 * @author Kenny1337
 * @since 28.06.2025
 */
public class AlphaAnim extends Animation {
    @Override
    public double calculation(double value) {
        double x = value / ms;

        if (x < 0.5) {
            return 4 * x * x;
        } else {
            double bounce = x - 1;
            return -4 * bounce * bounce + 1;
        }
    }
}
