/*
 * @author Sarkolsss
 * @date 23.07.2025, 10:59
 */

package wtf.taksa.common.functions.settings.impl;

import wtf.taksa.common.functions.settings.api.Setting;

public class BindSetting extends Setting<Integer> {
    public BindSetting(final String name, final Integer value) {
        super(name, value);
    }
}