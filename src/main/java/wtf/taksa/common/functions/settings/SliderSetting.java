package wtf.taksa.common.functions.settings;

public class SliderSetting extends Setting<Double> {
    private final double min;
    private final double max;
    private final double step;

    public SliderSetting(String name, double value, double min, double max, double step) {
        super(name, value);
        this.min = min;
        this.max = max;
        this.step = step;
        setValue(value);
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getStep() {
        return step;
    }

    @Override
    public void setValue(Double value) {
        double clampedValue = Math.max(min, Math.min(max, value));
        double steppedValue = Math.round(clampedValue / step) * step;
        steppedValue = Math.max(min, Math.min(max, steppedValue));

        super.setValue(steppedValue);
    }
}