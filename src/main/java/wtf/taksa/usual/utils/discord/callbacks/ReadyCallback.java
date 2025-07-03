package wtf.taksa.usual.utils.discord.callbacks;

import com.sun.jna.Callback;
import  wtf.taksa.usual.utils.discord.DiscordUser;
/**
 * @author Kenny1337
 * @since 03.07.2025
 */
public interface ReadyCallback extends Callback {
    void apply(final DiscordUser p0);
}
