package wtf.taksa.usual.utils.chat;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static wtf.taksa.usual.utils.minecraft.ContextWrapper.mc;

/**
 * @author Kenny1337
 * @since 28.06.2025
 */
public class ChatUtil {

    public static void sendMessage(String message) {
        if (mc.inGameHud != null && mc.inGameHud.getChatHud() != null) {
            mc.inGameHud.getChatHud().addMessage(Text.literal(message));
        }
    }

    public static void clientMessage(String message) {
        sendMessage("§7[§fTAKSA§7] §f" + message);
    }

    public static void clientError(String message) {
        sendMessage("§7[§cОшибка§7] §f" + message);
    }

    public static void clientWarning(String message) {
        sendMessage("§7[§eПредупреждение§7] §f" + message);
    }

    public static void clientSuccess(String message) {
        sendMessage("§7[§aУспех§7] §f" + message);
    }

    public static void clientCommand(String message) {
        sendMessage("§7[§6Команда§7] §f" + message);
    }
}