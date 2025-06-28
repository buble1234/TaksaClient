package wtf.taksa.usual.utils.render;

import java.util.Stack;
/**
 * @author Kenny1337
 * @since 28.06.2025
 */

public class AlphaOverride {
    private static final Stack<Float> alphaMultipliers = new Stack<>();

    public static void pushAlphaMul(float val) {
        alphaMultipliers.push(val);
    }

    public static void popAlphaMul() {
        alphaMultipliers.pop();
    }


    public static float compute(float initialAlpha) {
        float alpha = initialAlpha;
        for (Float alphaMultiplier : alphaMultipliers) {
            alpha *= alphaMultiplier;
        }
        return alpha;
    }
}