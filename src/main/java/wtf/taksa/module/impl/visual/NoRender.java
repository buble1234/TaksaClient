package wtf.taksa.module.impl.visual;

import lombok.Getter;
import wtf.taksa.module.Category;
import wtf.taksa.module.Module;
import wtf.taksa.module.ModuleRegistry;
import wtf.taksa.module.setting.ListSetting;

//говнокод любимый
@ModuleRegistry(name = "NoRender", category = Category.VISUALS)
@Getter
public class NoRender extends Module {
    private final ListSetting noRender = new ListSetting("Убирать", "Таблицу", "Огонь", "Тряску камеры");

    public NoRender() {
        noRender.setValue("Таблицу", true);
        noRender.setValue("Огонь", true);
        noRender.setValue("Тряску камеры", true);
        addSetting(noRender);
    }

    public boolean canRemoveScoreBoard() {
        return noRender.isToggled("Таблицу");
    }

    public boolean canRemoveFireOverlay() {
        return noRender.isToggled("Огонь");
    }

    public boolean canRemoveHurtCam() {
        return noRender.isToggled("Тряску камеры");
    }
}