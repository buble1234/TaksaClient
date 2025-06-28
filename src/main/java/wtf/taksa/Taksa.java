package wtf.taksa;
import lombok.Getter;
import net.fabricmc.api.ModInitializer;
import wtf.taksa.core.Core;
import wtf.taksa.manager.CommandManager;
import wtf.taksa.manager.ModuleManager;


public class Taksa implements ModInitializer {

    private static Taksa instance;
    @Getter
    private final ModuleManager moduleManager = new ModuleManager();
    private static CommandManager commandManager;


    Core core = new Core();

    @Override
    public void onInitialize() {
        instance = this;
        commandManager = new CommandManager();
        moduleManager.init();
        core.inCore();
    }
    public static Taksa getInstance() {
        return instance;
    }
    public static CommandManager getCommandManager() {
        return commandManager;
    }
}
