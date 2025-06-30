package wtf.taksa.module.setting;

import net.minecraft.util.math.MathHelper;

/**
 * Автор: NoCap
 * Дата создания: 30.06.2025
 */
public class DoubleSetting extends Setting<Double> {
    private final double min;
    private final double max;

    public DoubleSetting(String name, double defaultValue, double min, double max) {
        super(name, defaultValue);
        this.min = min;
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    @Override
    public void setValue(Double value) {
        super.setValue(MathHelper.clamp(value, this.min, this.max));
    }
}