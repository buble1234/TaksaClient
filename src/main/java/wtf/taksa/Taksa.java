package wtf.taksa;

import net.fabricmc.api.ModInitializer;
import wtf.taksa.engine.Engine;

public class Taksa implements ModInitializer {
    Engine engine = new Engine();

    @Override
    public void onInitialize() {
        engine.onInitialize();
    }
}
