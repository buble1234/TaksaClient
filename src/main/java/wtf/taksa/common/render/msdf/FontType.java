package wtf.taksa.common.render.msdf;

import com.google.common.base.Suppliers;

import java.util.function.Supplier;

public class FontType {
    public static final Supplier<MsdfFont> sf_regular = Suppliers.memoize(() -> MsdfFont.builder().atlas("sfregular").data("sfregular").build());
}
