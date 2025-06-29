package wtf.taksa;
import lombok.Getter;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wtf.taksa.core.Core;
import wtf.taksa.manager.CommandManager;
import wtf.taksa.manager.ModuleManager;
import wtf.taksa.usual.utils.shader.ShaderManager;

import static wtf.taksa.core.Core.EVENT_BUS;


public class Taksa implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Renderer");

    private static Taksa instance;
    @Getter
    private final ModuleManager moduleManager = new ModuleManager();
    private static CommandManager commandManager;


    Core core = new Core();

    @Override
    public void onInitialize() {
        core.inCore();
        ShaderManager.doInit();
        LOGGER.info("Initialized renderer library");
        instance = this;
        commandManager = new CommandManager();
        moduleManager.init();
    }
    public static Taksa getInstance() {
        return instance;
    }
    public static CommandManager getCommandManager() {
        return commandManager;
    }
}
