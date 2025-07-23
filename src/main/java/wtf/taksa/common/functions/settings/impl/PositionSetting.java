/*
 * @author Sarkolsss
 * @date 23.07.2025, 10:59
 */

package wtf.taksa.common.functions.settings.impl;

import net.minecraft.util.math.Vec2f;
import wtf.taksa.common.functions.settings.api.Setting;

public class PositionSetting extends Setting<Vec2f> {
    public PositionSetting(final String name, final Vec2f value) {
        super(name, value);
    }
}
