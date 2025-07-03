package wtf.taksa.usual.utils.discord;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;
/**
 * @author Kenny1337
 * @since 03.07.2025
 */
public class DiscordUser extends Structure {
    public String userId;
    public String username;
    @Deprecated
    public String discriminator;
    public String avatar;
    
    protected List<String> getFieldOrder() {
        return Arrays.asList("userId", "username", "discriminator", "avatar");
    }
}
