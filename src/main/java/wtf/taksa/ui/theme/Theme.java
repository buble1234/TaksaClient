package wtf.taksa.ui.theme;

import wtf.taksa.usual.utils.color.ColorUtils;

import java.awt.Color;

/**
 * Автор: NoCap
 * Дата создания: 30.06.2025
 * Стиль: МУЖИКСКИЙ СЛОВНА ТАКСА ЫВФЗХЪВЗЪХФЫЗВХЪЫЗФХ
 */
public class Theme {
    // Основные цвета фона
    public static final Color BACKGROUND = ColorUtils.fromHex("1B1B1B");
    public static final Color PANEL_BACKGROUND = new Color(25, 25, 25, 220);

    // Цвета категорий
    public static final Color CATEGORY_INACTIVE = new Color(40, 40, 40, 240);
    public static final Color CATEGORY_ACTIVE = new Color(255, 255, 255, 255);
    public static final Color CATEGORY_HOVER = new Color(60, 60, 60, 240);

    // Цвета текста
    public static final Color TEXT_LIGHT = Color.WHITE;
    public static final Color TEXT_DARK = Color.BLACK;
    public static final Color TEXT_GRAY = new Color(150, 150, 150);

    // Цвета компонентов
    public static final Color COMPONENT_BACKGROUND = new Color(55, 55, 55, 220);
    public static final Color COMPONENT_HOVER = new Color(75, 75, 75, 220);
    public static final Color ACCENT = Color.WHITE; // Для активных элементов: слайдер, галочка
    public static final Color ACCENT_HOVER = new Color(220, 220, 220);
}