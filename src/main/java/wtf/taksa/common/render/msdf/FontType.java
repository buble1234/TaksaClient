package wtf.taksa.common.render.msdf;

import com.google.common.base.Suppliers;

import java.util.function.Supplier;

public class FontType {
    public static final Supplier<MsdfFont> sf_regular = Suppliers.memoize(() -> MsdfFont.builder().atlas("sf_regular").data("sf_regular").build());
    public static final Supplier<MsdfFont> sf_bold = Suppliers.memoize(() -> MsdfFont.builder().atlas("sf_bold").data("sf_bold").build());
    public static final Supplier<MsdfFont> sf_medium = Suppliers.memoize(() -> MsdfFont.builder().atlas("sf_medium").data("sf_medium").build());
    public static final Supplier<MsdfFont> sf_light = Suppliers.memoize(() -> MsdfFont.builder().atlas("sf_light").data("sf_light").build());
    public static final Supplier<MsdfFont> sf_semibold = Suppliers.memoize(() -> MsdfFont.builder().atlas("sf_semibold").data("sf_semibold").build());
}
