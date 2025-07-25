package wtf.taksa.common.functions.settings.impl;

import wtf.taksa.common.functions.settings.api.Setting;

public class BooleanSetting extends Setting<Boolean> {
    public BooleanSetting(String name, boolean value) {
        super(name, value);
    }

    public void toggle() {
        setValue(!getValue());
    }
}
