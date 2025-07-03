package wtf.taksa.usual.utils.discord.helpers;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
/**
 * @author Kenny1337
 * @since 03.07.2025
 */
public class RPCButton implements Serializable {
    private final String url;
    private final String label;

    public String getLabel() {
        return this.label;
    }

    public String getUrl() {
        return this.url;
    }

    public static @NotNull RPCButton create(String substring, final String s) {
        substring = substring.substring(0, Math.min(substring.length(), 31));
        return new RPCButton(substring, s);
    }

    protected RPCButton(final String label, final String url) {
        this.label = label;
        this.url = url;
    }
}
