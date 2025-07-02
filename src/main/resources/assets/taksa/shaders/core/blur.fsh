#version 150

float rdist(vec2 pos, vec2 size, vec4 radius) {
    radius.xy = (pos.x > 0.0) ? radius.xy : radius.wz;
    radius.x  = (pos.y > 0.0) ? radius.x : radius.y;

    vec2 v = abs(pos) - size + radius.x;
    return min(max(v.x, v.y), 0.0) + length(max(v, 0.0)) - radius.x;
}

float ralpha(vec2 size, vec2 coord, vec4 radius, float smoothness) {
    vec2 center = size * 0.5;
    float dist = rdist(center - (coord * size), center - 1.0, radius);
    return 1.0 - smoothstep(1.0 - smoothness, 1.0, dist);
}

in vec2 FragCoord;
in vec2 TexCoord;

uniform sampler2D InputSampler;
uniform vec2 InputResolution;
uniform vec2 Size;
uniform vec4 Radius;
uniform float BlurRadius;
uniform float Smoothness;
uniform float Brightness;
uniform vec4 color1;

out vec4 fragColor;

const float DPI = 6.28318530718;
const float STEP = DPI / 16.0;

vec3 gaussianBlur() {
    vec2 multiplier = BlurRadius / InputResolution;

    vec3 average = texture(InputSampler, TexCoord).rgb;

    for (float d = 0.0; d < DPI; d += STEP) {
        for (float i = 0.2; i <= 1.0; i += 0.2) {
            vec2 offset = vec2(cos(d), sin(d)) * multiplier * i;
            average += texture(InputSampler, TexCoord + offset).rgb;
        }
    }

    return average / 80.0;
}

void main() {
    vec3 blurredColor = gaussianBlur();

    vec4 finalColor = vec4(blurredColor, 1.0) * color1 * Brightness;

    finalColor.a *= ralpha(Size, FragCoord, Radius, Smoothness);

    if (finalColor.a <= 0.001) {
        discard;
    }

    fragColor = finalColor;
}