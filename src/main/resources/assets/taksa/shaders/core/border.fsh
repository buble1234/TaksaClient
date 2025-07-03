#version 150

float rdist(vec2 pos, vec2 size, vec4 radius) {
    radius.xy = (pos.x > 0.0) ? radius.xy : radius.wz;
    radius.x  = (pos.y > 0.0) ? radius.x : radius.y;

    vec2 v = abs(pos) - size + radius.x;
    return min(max(v.x, v.y), 0.0) + length(max(v, 0.0)) - radius.x;
}

in vec2 FragCoord;
in vec2 TexCoord;

uniform vec2 Size;
uniform vec4 Radius;
uniform float Thickness;
uniform vec2 Smoothness;

uniform vec4 fillColor;
uniform vec4 outlineColor;
uniform vec4 outlineColor2;

uniform float gradientFlag;

out vec4 fragColor;

void main() {
    vec2 center = Size * 0.5;
    float dist = rdist(center - (FragCoord.xy * Size), center - 1.0, Radius);

    float alpha = smoothstep(1.0 - Thickness - Smoothness.x - Smoothness.y, 1.0 - Thickness - Smoothness.y, dist);
    alpha *= 1.0 - smoothstep(1.0 - Smoothness.y, 1.0, dist);

    float fillAlpha = 1.0 - smoothstep(1.0 - Smoothness.x, 1.0, dist);

    vec4 borderColor = outlineColor;
    if (gradientFlag > 0.5) {
        float t = FragCoord.y;
        borderColor = mix(outlineColor, outlineColor2, t);
    }

    vec4 finalColor = mix(fillColor * fillAlpha, borderColor, alpha);
    finalColor.a *= (fillAlpha + alpha - fillAlpha * alpha);

    if (finalColor.a <= 0.001) {
        discard;
    }

    fragColor = finalColor;
}