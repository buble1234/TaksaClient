package wtf.taksa.common.ui.clickgui.component;

import wtf.taksa.common.ui.clickgui.component.background.BackgroundComponent;
import wtf.taksa.common.ui.clickgui.component.background.RightPanelComponent;

/**
 * Автор: NoCap
 * Дата создания: 23.07.2025
 */
public class ComponentManager {

    public static BackgroundComponent createBackgroundComponent(int x, int y, int w, int h) {
        return new BackgroundComponent(x, y, w, h);
    }

    public static RightPanelComponent createRightPanelComponent(int x, int y, int w, int h) {
        return new RightPanelComponent(x, y, w, h);
    }
}