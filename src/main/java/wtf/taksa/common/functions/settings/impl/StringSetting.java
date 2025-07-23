/*
 * @author Sarkolsss
 * @date 23.07.2025, 10:59
 */

package wtf.taksa.common.functions.settings.impl;

import wtf.taksa.common.functions.settings.api.Setting;

public class StringSetting extends Setting<String> {
    public StringSetting(final String name, final String value) {
        super(name, value);
    }
}