package wtf.taksa.common.functions.settings;

public class BooleanSetting extends Setting<Boolean> {
    public BooleanSetting(String name, boolean value) {
        super(name, value);
    }

    public void toggle() {
        setValue(!getValue());
    }
}
