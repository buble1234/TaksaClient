package wtf.taksa.usual.utils.discord;

import com.sun.jna.Structure;
import  wtf.taksa.usual.utils.discord.callbacks.*;

import java.util.Arrays;
import java.util.List;
/**
 * @author Kenny1337
 * @since 03.07.2025
 */
public class DiscordEventHandlers extends Structure {
    public DisconnectedCallback disconnected;
    public JoinRequestCallback joinRequest;
    public SpectateGameCallback spectateGame;
    public ReadyCallback ready;
    public ErroredCallback errored;
    public JoinGameCallback joinGame;
    
    protected List<String> getFieldOrder() {
        return Arrays.asList("ready", "disconnected", "errored", "joinGame", "spectateGame", "joinRequest");
    }
}