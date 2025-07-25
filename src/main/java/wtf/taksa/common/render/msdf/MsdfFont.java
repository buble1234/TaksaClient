package wtf.taksa.common.render.msdf;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;
import wtf.taksa.common.resource.ResourceUtility;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class MsdfFont {

	private final String name;
	private final AbstractTexture texture;
	private final FontData.AtlasData atlas;
	private final FontData.MetricsData metrics;
	private final Map<Integer, MsdfGlyph> glyphs;
	private final Map<Integer, Map<Integer, Float>> kernings;

	private MsdfFont(String name, AbstractTexture texture, FontData.AtlasData atlas, FontData.MetricsData metrics, Map<Integer, MsdfGlyph> glyphs, Map<Integer, Map<Integer, Float>> kernings) {
		this.name = name;
		this.texture = texture;
		this.atlas = atlas;
		this.metrics = metrics;
		this.glyphs = glyphs;
		this.kernings = kernings;
	}

	public int getTextureId() {
		return this.texture.getGlId();
	}
	
	public void applyGlyphs(Matrix4f matrix, VertexConsumer consumer, String text, float size, float thickness, float spacing, float x, float y, float z, int color) {
		int prevChar = -1;
		for (int i = 0; i < text.length(); i++) {
			int _char = (int) text.charAt(i);
			MsdfGlyph glyph = this.glyphs.get(_char);
			
			if (glyph == null) continue;

			Map<Integer, Float> kerning = this.kernings.get(prevChar);
			if (kerning != null) {
				x += kerning.getOrDefault(_char, 0.0f) * size;
			}

			x += glyph.apply(matrix, consumer, size, x, y, z, color) + thickness + spacing;
			prevChar = _char;
		}
	}
	
	public float getWidth(String text, float size) {
		int prevChar = -1;
		float width = 0.0f;
		for (int i = 0; i < text.length(); i++) {
			int _char = (int) text.charAt(i);
			MsdfGlyph glyph = this.glyphs.get(_char);
			
			if (glyph == null)
				continue;
			
			Map<Integer, Float> kerning = this.kernings.get(prevChar);
			if (kerning != null) {
				width += kerning.getOrDefault(_char, 0.0f) * size;
			}
			
			width += glyph.getWidth(size);
			prevChar = _char;
		}
		
		return width;
	}

	public  float getFontHeight(MsdfFont font, float size) {
		if (font == null) {
			return 0.0f;
		}
		return font.getMetrics().lineHeight() * size;
	}
	
	public String getName() {
		return this.name;
	}
	
	public FontData.AtlasData getAtlas() {
		return this.atlas;
	}
	
	public FontData.MetricsData getMetrics() {
		return this.metrics;
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder {
		
		private String name = "?";
		private Identifier dataIdentifer;
		private Identifier atlasIdentifier;
		
		private Builder() {}
		
		public Builder name(String name) {
			this.name = name;
			return this;
		}
		
		public Builder data(String dataFileName) {
			this.dataIdentifer = Identifier.of("taksa", "fonts/" + dataFileName + ".json");
			return this;
		}
		
		public Builder atlas(String atlasFileName) {
			this.atlasIdentifier = Identifier.of("taksa", "fonts/" + atlasFileName + ".png");
			return this;
		}
		
		public MsdfFont build() {
			FontData data = ResourceUtility.fromJsonToInstance(this.dataIdentifer, FontData.class);
			AbstractTexture texture = MinecraftClient.getInstance().getTextureManager().getTexture(this.atlasIdentifier);
			
			if (data == null) {
				throw new RuntimeException("Failed to read font data file: " + this.dataIdentifer.toString() + 
						"; Are you sure this is json file? Try to check the correctness of its syntax.");
			}
			
			RenderSystem.recordRenderCall(() -> texture.setFilter(true, false));
			
			float aWidth = data.atlas().width();
			float aHeight = data.atlas().height();
			Map<Integer, MsdfGlyph> glyphs = data.glyphs().stream()
					.collect(Collectors.<FontData.GlyphData, Integer, MsdfGlyph>toMap(
							(glyphData) -> glyphData.unicode(),
							(glyphData) -> new MsdfGlyph(glyphData, aWidth, aHeight)
					));
	
			Map<Integer, Map<Integer, Float>> kernings = new HashMap<>();
			data.kernings().forEach((kerning) -> {
				Map<Integer, Float> map = kernings.get(kerning.leftChar());
				if (map == null) {
					map = new HashMap<>();
					kernings.put(kerning.leftChar(), map);
				}

				map.put(kerning.rightChar(), kerning.advance());
			});

			return new MsdfFont(this.name, texture, data.atlas(), data.metrics(), glyphs, kernings);
		}

	}

}