package wtf.taksa;

import lombok.Getter;
import net.fabricmc.api.ModInitializer;
import wtf.taksa.engine.Engine;
import wtf.taksa.engine.manager.FunctionManager;

public class Taksa implements ModInitializer {

        Engine engine = new Engine();

    @Override
    public void onInitialize() {
        engine.onInitialize();
    }
}
