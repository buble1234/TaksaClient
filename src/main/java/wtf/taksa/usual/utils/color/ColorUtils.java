package wtf.taksa.usual.utils.color;

import java.awt.Color;

/**
 * Автор: NoCap && NickL
 * Дата создания: 03.07.2025
 */
public final class ColorUtils {

    private ColorUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Преобразует HEX-строку в объект {@link Color}.
     * Метод поддерживает следующие форматы:
     * @code RRGGBB}
     * @code #RRGGBB}
     * @code RRGGBBAA}
     * @code #RRGGBBAA}
     * @param hex Строка с цветом в HEX-формате. Не может быть null.
     * @return Объект {@link Color}, соответствующий HEX-строке.
     * @throws IllegalArgumentException если строка имеет неверный формат (неверная длина или недопустимые символы).
     */
    public static Color fromHex(String hex) {
        if (hex == null) {
            throw new IllegalArgumentException("Hex строка не может быть нулевой.");
        }

        if (hex.startsWith("#")) {
            hex = hex.substring(1);
        }

        if (hex.length() != 6 && hex.length() != 8) {
            throw new IllegalArgumentException("Недопустимая длина Hex строки. Должно быть 6 или 8 символов, но была " + hex.length());
        }

        try {
            long longValue = Long.parseLong(hex, 16);

            if (hex.length() == 6) {
                int r = (int) ((longValue >> 16) & 0xFF);
                int g = (int) ((longValue >> 8) & 0xFF);
                int b = (int) (longValue & 0xFF);
                return new Color(r, g, b);
            } else {
                int r = (int) ((longValue >> 24) & 0xFF);
                int g = (int) ((longValue >> 16) & 0xFF);
                int b = (int) ((longValue >> 8) & 0xFF);
                int a = (int) (longValue & 0xFF);
                return new Color(r, g, b, a);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid characters in hex string: \"" + hex + "\"", e);
        }
    }

    /**
     * Создает объект {@link Color} из RGB-компонентов.
     *
     * @param r Красный компонент (0-255).
     * @param g Зеленый компонент (0-255).
     * @param b Синий компонент (0-255).
     * @return Новый объект {@link Color}.
     * @throws IllegalArgumentException если значения выходят за пределы диапазона 0-255.
     */
    public static Color fromRgb(int r, int g, int b) {
        return new Color(r, g, b);
    }

    /**
     * Создает объект {@link Color} из RGBA-компонентов.
     *
     * @param r Красный компонент (0-255).
     * @param g Зеленый компонент (0-255).
     * @param b Синий компонент (0-255).
     * @param a Альфа-компонент (прозрачность) (0-255).
     * @return Новый объект {@link Color}.
     * @throws IllegalArgumentException если значения выходят за пределы диапазона 0-255.
     */
    public static Color fromRgba(int r, int g, int b, int a) {
        return new Color(r, g, b, a);
    }

    /**
     * Создает объект {@link Color} из HSB-компонентов (Hue, Saturation, Brightness)
     *
     * @param hue Цветовой тон (0-360).
     * @param saturation Насыщенность в процентах (0-100).
     * @param brightness Яркость в процентах (0-100).
     * @return Новый объект {@link Color}.
     * @throws IllegalArgumentException если значения выходят за пределы своих диапазонов.
     */
    public static Color fromHsb(int hue, int saturation, int brightness) {
        if (hue < 0 || hue > 360) {
            throw new IllegalArgumentException("Hue (тон) должен быть в диапазоне от 0 до 360.");
        }
        if (saturation < 0 || saturation > 100) {
            throw new IllegalArgumentException("Saturation (насыщенность) должна быть в диапазоне от 0 до 100.");
        }
        if (brightness < 0 || brightness > 100) {
            throw new IllegalArgumentException("Brightness (яркость) должна быть в диапазоне от 0 до 100.");
        }

        float h = hue / 360.0f;
        float s = saturation / 100.0f;
        float b = brightness / 100.0f;

        return Color.getHSBColor(h, s, b);
    }
}