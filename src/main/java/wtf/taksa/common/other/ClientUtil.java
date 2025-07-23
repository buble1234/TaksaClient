/*
 * @author Sarkolsss
 * @date 23.07.2025, 14:14
 */

package wtf.taksa.common.other;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static wtf.taksa.unclassified.interfaces.ContextWrapper.mc;

public class ClientUtil {
    public static void print(String msg) {
        mc.inGameHud.getChatHud().addMessage(Text.of(Formatting.DARK_RED + "[" + Formatting.RESET + "Taksa" + Formatting.DARK_RED + "]" + Formatting.GRAY + " -> " + Formatting.RESET + msg));
    }
}