package wtf.taksa.module.setting;

import java.util.Arrays;
import java.util.List;

/**
 * Автор: NoCap
 * Дата создания: 02.07.2025
 */
public class ModeSetting extends Setting<String> {
    private final List<String> modes;
    
    public ModeSetting(String name, String defaultMode, String... modes) {
        super(name, defaultMode);
        this.modes = Arrays.asList(modes);
    }
    
    public List<String> getModes() {
        return modes;
    }

    public void cycle() {
        int currentIndex = modes.indexOf(getValue());
        int nextIndex = (currentIndex + 1) % modes.size();
        setValue(modes.get(nextIndex));
    }
}