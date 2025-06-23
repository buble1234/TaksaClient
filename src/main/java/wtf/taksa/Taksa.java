package wtf.taksa;

import net.fabricmc.api.ModInitializer;
import wtf.taksa.core.Core;

public class Taksa implements ModInitializer {

    Core core = new Core();

    @Override
    public void onInitialize() {
        core.inCore();
    }
}
