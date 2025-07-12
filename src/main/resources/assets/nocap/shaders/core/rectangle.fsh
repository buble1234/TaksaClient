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

uniform vec2 Size;
uniform vec4 Radius;
uniform float Smoothness;

uniform vec4 color1;
uniform vec4 color2;
uniform vec4 color3;
uniform vec4 color4;

uniform float globalAlpha;
uniform float brightness;

out vec4 fragColor;

void main() {
    float alpha = ralpha(Size, FragCoord, Radius, Smoothness);

    vec2 normalizedCoord = FragCoord;

    vec4 colorTop = mix(color1, color2, normalizedCoord.x);
    vec4 colorBottom = mix(color3, color4, normalizedCoord.x);
    vec4 finalColor = mix(colorTop, colorBottom, normalizedCoord.y);

    finalColor.rgb *= brightness;
    finalColor.a *= globalAlpha * alpha;

    if (finalColor.a <= 0.001) {
        discard;
    }

    fragColor = finalColor;
}