package wtf.taksa.module.impl.visuals;

import wtf.taksa.module.Category;
import wtf.taksa.module.Module;
import wtf.taksa.module.ModuleRegistry;
import wtf.taksa.module.setting.DoubleSetting;
/**
 * @author Kenny1337
 * @since 02.07.2025
 */
@ModuleRegistry(name = "AspectRatio", category = Category.VISUALS, description = "Изменяет матрицу экрана")
public class AspectRatio extends Module {
    public DoubleSetting алерадиодада = new DoubleSetting("Радио", 1.78f, 0.1f, 5f);

    public AspectRatio() {
        addSetting(алерадиодада);
    }
}