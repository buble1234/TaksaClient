#version 150

in vec2 TexCoord;
in vec4 FragColor;

uniform sampler2D Sampler0;
uniform float Range;
uniform float Thickness;
uniform float Smoothness;
uniform int Outline;
uniform float OutlineThickness;
uniform vec4 OutlineColor;

out vec4 OutColor;

float median(vec3 color) {
    return max(min(color.r, color.g), min(max(color.r, color.g), color.b));
}

void main() {
    vec3 msd = texture(Sampler0, TexCoord).rgb;
    float dist = median(msd) - 0.5 + Thickness;

    vec2 dxdy = vec2(dFdx(TexCoord.x), dFdy(TexCoord.y));
    vec2 texSize = vec2(textureSize(Sampler0, 0));
    float pixels = Range * inversesqrt(dot(dxdy * texSize, dxdy * texSize));

    float alpha = smoothstep(-Smoothness, Smoothness, dist * pixels);
    vec4 color = vec4(FragColor.rgb, FragColor.a * alpha);

    if (Outline != 0) {
        float outlineAlpha = smoothstep(-Smoothness, Smoothness, (dist + OutlineThickness) * pixels);

        color = mix(OutlineColor, FragColor, alpha);
        color.a = FragColor.a * outlineAlpha;
    }

    OutColor = color;
}