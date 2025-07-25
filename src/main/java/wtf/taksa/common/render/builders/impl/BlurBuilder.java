package wtf.taksa.common.render.builders.impl;

import wtf.taksa.common.render.builders.*;
import wtf.taksa.common.render.builders.states.*;
import wtf.taksa.common.render.renderers.impl.*;

public final class BlurBuilder extends AbstractBuilder<BuiltBlur> {

    private SizeState size;
    private QuadRadiusState radius;
    private QuadColorState color;
    private float smoothness;
    private float blurRadius;
    private float brightness;

    public BlurBuilder size(SizeState size) {
        this.size = size;
        return this;
    }

    public BlurBuilder radius(QuadRadiusState radius) {
        this.radius = radius;
        return this;
    }

    public BlurBuilder color(QuadColorState color) {
        this.color = color;
        return this;
    }

    public BlurBuilder smoothness(float smoothness) {
        this.smoothness = smoothness;
        return this;
    }

    public BlurBuilder blurRadius(float blurRadius) {
        this.blurRadius = blurRadius;
        return this;
    }

    public BlurBuilder brightness(float brightness) {
        this.brightness = brightness;
        return this;
    }

    @Override
    protected BuiltBlur _build() {
        return new BuiltBlur(
                this.size,
                this.radius,
                this.color,
                this.smoothness,
                this.blurRadius,
                this.brightness
        );
    }

    @Override
    protected void reset() {
        this.size = SizeState.NONE;
        this.radius = QuadRadiusState.NO_ROUND;
        this.color = QuadColorState.WHITE;
        this.smoothness = 1.0f;
        this.blurRadius = 0.0f;
        this.brightness = 1.0f;
    }

}