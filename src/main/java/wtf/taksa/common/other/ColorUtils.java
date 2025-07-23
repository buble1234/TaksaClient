/*
 * @author Sarkolsss
 * @date 23.07.2025, 12:48
 */

package wtf.taksa.common.other;

import net.minecraft.util.math.MathHelper;

import java.awt.*;

public class ColorUtils {

    public final int green = new Color(64, 255, 64).getRGB();
    public final int yellow = new Color(255, 255, 64).getRGB();
    public final int orange = new Color(255, 128, 32).getRGB();
    public final int red = new Color(255, 64, 64).getRGB();

    public static int rgb(int r, int g, int b) {
        return 255 << 24 | r << 16 | g << 8 | b;
    }

    public static int rgba(int r, int g, int b, int a) {
        return a << 24 | r << 16 | g << 8 | b;
    }

    public static int toColor(String hexColor) {
        int argb = Integer.parseInt(hexColor.substring(1), 16);
        return setAlpha(argb, 255);
    }
    public static int setAlpha(int color, int alpha) {
        return (color & 0x00ffffff) | (alpha << 24);
    }

    public static float[] rgba(final int color) {
        return new float[] {
                (color >> 16 & 0xFF) / 255f,
                (color >> 8 & 0xFF) / 255f,
                (color & 0xFF) / 255f,
                (color >> 24 & 0xFF) / 255f
        };
    }

    public static int gradient(int start, int end, int index, int speed) {
        int angle = (int) ((System.currentTimeMillis() / speed + index) % 360);
        angle = (angle > 180 ? 360 - angle : angle) + 180;
        int color = interpolate(start, end, MathHelper.clamp(angle / 180f - 1, 0, 1));
        float[] hs = rgba(color);
        float[] hsb = Color.RGBtoHSB((int) (hs[0] * 255), (int) (hs[1] * 255), (int) (hs[2] * 255), null);

        hsb[1] *= 1.5F;
        hsb[1] = Math.min(hsb[1], 1.0f);

        return Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
    }

    public static int interpolate(int start, int end, float value) {
        float[] startColor = rgba(start);
        float[] endColor = rgba(end);

        return rgba((int) interpolate(startColor[0] * 255, endColor[0] * 255, value),
                (int) interpolate(startColor[1] * 255, endColor[1] * 255, value),
                (int) interpolate(startColor[2] * 255, endColor[2] * 255, value),
                (int) interpolate(startColor[3] * 255, endColor[3] * 255, value));
    }

    public static double interpolate(double current, double old, double scale) {
        return old + (current - old) * scale;
    }

    public static float[] hsvToRgb(float h, float s, float v) {
        float[] rgb = new float[3];

        if (s == 0) {
            rgb[0] = rgb[1] = rgb[2] = v;
            return rgb;
        }

        float sector = h * 6; // 0-6 кароче
        int i = (int) Math.floor(sector);
        float f = sector - i;

        float p = v * (1 - s);
        float q = v * (1 - s * f);
        float t = v * (1 - s * (1 - f));

        switch (i) {    // Я КРУТОЙ КОДЕР ЭТА ПРАВДА
            case 0:
                rgb[0] = v; rgb[1] = t; rgb[2] = p; break;
            case 1:
                rgb[0] = q; rgb[1] = v; rgb[2] = p; break;
            case 2:
                rgb[0] = p; rgb[1] = v; rgb[2] = t; break;
            case 3:
                rgb[0] = p; rgb[1] = q; rgb[2] = v; break;
            case 4:
                rgb[0] = t; rgb[1] = p; rgb[2] = v; break;
            default:
                rgb[0] = v; rgb[1] = p; rgb[2] = q; break;
        }

        return rgb;
    }
}