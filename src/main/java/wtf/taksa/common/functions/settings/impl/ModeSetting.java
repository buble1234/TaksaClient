/*
 * @author Sarkolsss
 * @date 23.07.2025, 10:59
 */

package wtf.taksa.common.functions.settings.impl;

import wtf.taksa.common.functions.settings.api.Setting;

public class ModeSetting extends Setting<String[]> {
    public ModeSetting(String name, String... value) {
        super(name, value);
    }
}
