/*
 * @author Sarkolsss
 * @date 23.07.2025, 12:48
 */

package wtf.taksa.common.other;

public class MathUtil {
    public static double interpolate(double current, double old, double scale) {
        return old + (current - old) * scale;
    }
}