package wtf.taksa.usual.utils.discord.callbacks;

import com.sun.jna.Callback;
/**
 * @author Kenny1337
 * @since 03.07.2025
 */
public interface ErroredCallback extends Callback {
    void apply(final int p0, final String p1);
}
