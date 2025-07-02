package wtf.taksa.render.font;

import java.awt.Font;
/**
 * @author Kenny1337
 * @since 28.06.2025
 */
public class FontManager {

    private static FontRenderer textRenderer;
    private static FontRenderer titleRenderer;

    private static void lazyInit() {
        if (textRenderer == null) {
            Font placeholderFont = new Font("Arial", Font.PLAIN, 9);
            textRenderer = new FontRenderer(new Font[]{placeholderFont}, 9);
            titleRenderer = new FontRenderer(new Font[]{placeholderFont}, 12);
        }
    }

    public static FontRenderer getTextRenderer() {
        lazyInit();
        return textRenderer;
    }

    public static FontRenderer getTitleRenderer() {
        lazyInit();
        return titleRenderer;
    }
} 