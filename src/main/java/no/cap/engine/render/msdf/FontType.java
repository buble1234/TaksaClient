package no.cap.engine.render.msdf;

import com.google.common.base.Suppliers;

import java.util.function.Supplier;

/**
 * Автор NoCap
 * Дата создания: 11.06.2025, в 13:05
 */
public class FontType {
    public static final Supplier<MsdfFont> sf_regular = Suppliers.memoize(() -> MsdfFont.builder().atlas("sf_regular").data("sf_regular").build());
}
