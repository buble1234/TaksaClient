package wtf.taksa.usual.utils.minecraft;

import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;

/**
 * Автор: NoCap
 * Дата создания: 04.07.2025
 */
public class KeyUtils {

    public static final int MW_UP = -2;
    public static final int MW_DOWN = -3;
    private static final Map<Integer, String> keyNameMap = new HashMap<>();

    static {
        keyNameMap.put(GLFW.GLFW_KEY_UNKNOWN, "NONE");
        keyNameMap.put(GLFW.GLFW_KEY_SPACE, "SPACE");
        keyNameMap.put(GLFW.GLFW_KEY_APOSTROPHE, "'");
        keyNameMap.put(GLFW.GLFW_KEY_COMMA, ",");
        keyNameMap.put(GLFW.GLFW_KEY_MINUS, "-");
        keyNameMap.put(GLFW.GLFW_KEY_PERIOD, ".");
        keyNameMap.put(GLFW.GLFW_KEY_SLASH, "/");
        keyNameMap.put(GLFW.GLFW_KEY_0, "0");
        keyNameMap.put(GLFW.GLFW_KEY_1, "1");
        keyNameMap.put(GLFW.GLFW_KEY_2, "2");
        keyNameMap.put(GLFW.GLFW_KEY_3, "3");
        keyNameMap.put(GLFW.GLFW_KEY_4, "4");
        keyNameMap.put(GLFW.GLFW_KEY_5, "5");
        keyNameMap.put(GLFW.GLFW_KEY_6, "6");
        keyNameMap.put(GLFW.GLFW_KEY_7, "7");
        keyNameMap.put(GLFW.GLFW_KEY_8, "8");
        keyNameMap.put(GLFW.GLFW_KEY_9, "9");
        keyNameMap.put(GLFW.GLFW_KEY_SEMICOLON, ";");
        keyNameMap.put(GLFW.GLFW_KEY_EQUAL, "=");
        keyNameMap.put(GLFW.GLFW_KEY_A, "A");
        keyNameMap.put(GLFW.GLFW_KEY_B, "B");
        keyNameMap.put(GLFW.GLFW_KEY_C, "C");
        keyNameMap.put(GLFW.GLFW_KEY_D, "D");
        keyNameMap.put(GLFW.GLFW_KEY_E, "E");
        keyNameMap.put(GLFW.GLFW_KEY_F, "F");
        keyNameMap.put(GLFW.GLFW_KEY_G, "G");
        keyNameMap.put(GLFW.GLFW_KEY_H, "H");
        keyNameMap.put(GLFW.GLFW_KEY_I, "I");
        keyNameMap.put(GLFW.GLFW_KEY_J, "J");
        keyNameMap.put(GLFW.GLFW_KEY_K, "K");
        keyNameMap.put(GLFW.GLFW_KEY_L, "L");
        keyNameMap.put(GLFW.GLFW_KEY_M, "M");
        keyNameMap.put(GLFW.GLFW_KEY_N, "N");
        keyNameMap.put(GLFW.GLFW_KEY_O, "O");
        keyNameMap.put(GLFW.GLFW_KEY_P, "P");
        keyNameMap.put(GLFW.GLFW_KEY_Q, "Q");
        keyNameMap.put(GLFW.GLFW_KEY_R, "R");
        keyNameMap.put(GLFW.GLFW_KEY_S, "S");
        keyNameMap.put(GLFW.GLFW_KEY_T, "T");
        keyNameMap.put(GLFW.GLFW_KEY_U, "U");
        keyNameMap.put(GLFW.GLFW_KEY_V, "V");
        keyNameMap.put(GLFW.GLFW_KEY_W, "W");
        keyNameMap.put(GLFW.GLFW_KEY_X, "X");
        keyNameMap.put(GLFW.GLFW_KEY_Y, "Y");
        keyNameMap.put(GLFW.GLFW_KEY_Z, "Z");
        keyNameMap.put(GLFW.GLFW_KEY_LEFT_BRACKET, "[");
        keyNameMap.put(GLFW.GLFW_KEY_BACKSLASH, "\\");
        keyNameMap.put(GLFW.GLFW_KEY_RIGHT_BRACKET, "]");
        keyNameMap.put(GLFW.GLFW_KEY_GRAVE_ACCENT, "`");
        keyNameMap.put(GLFW.GLFW_KEY_ESCAPE, "ESC");
        keyNameMap.put(GLFW.GLFW_KEY_ENTER, "ENTER");
        keyNameMap.put(GLFW.GLFW_KEY_TAB, "TAB");
        keyNameMap.put(GLFW.GLFW_KEY_BACKSPACE, "BACKSPACE");
        keyNameMap.put(GLFW.GLFW_KEY_INSERT, "INSERT");
        keyNameMap.put(GLFW.GLFW_KEY_DELETE, "DELETE");
        keyNameMap.put(GLFW.GLFW_KEY_RIGHT, "RIGHT");
        keyNameMap.put(GLFW.GLFW_KEY_LEFT, "LEFT");
        keyNameMap.put(GLFW.GLFW_KEY_DOWN, "DOWN");
        keyNameMap.put(GLFW.GLFW_KEY_UP, "UP");
        keyNameMap.put(GLFW.GLFW_KEY_PAGE_UP, "PAGE UP");
        keyNameMap.put(GLFW.GLFW_KEY_PAGE_DOWN, "PAGE DOWN");
        keyNameMap.put(GLFW.GLFW_KEY_HOME, "HOME");
        keyNameMap.put(GLFW.GLFW_KEY_END, "END");
        keyNameMap.put(GLFW.GLFW_KEY_CAPS_LOCK, "CAPS LOCK");
        keyNameMap.put(GLFW.GLFW_KEY_SCROLL_LOCK, "SCROLL LOCK");
        keyNameMap.put(GLFW.GLFW_KEY_NUM_LOCK, "NUM LOCK");
        keyNameMap.put(GLFW.GLFW_KEY_PRINT_SCREEN, "PRINT SCREEN");
        keyNameMap.put(GLFW.GLFW_KEY_PAUSE, "PAUSE");
        keyNameMap.put(GLFW.GLFW_KEY_F1, "F1");
        keyNameMap.put(GLFW.GLFW_KEY_F2, "F2");
        keyNameMap.put(GLFW.GLFW_KEY_F3, "F3");
        keyNameMap.put(GLFW.GLFW_KEY_F4, "F4");
        keyNameMap.put(GLFW.GLFW_KEY_F5, "F5");
        keyNameMap.put(GLFW.GLFW_KEY_F6, "F6");
        keyNameMap.put(GLFW.GLFW_KEY_F7, "F7");
        keyNameMap.put(GLFW.GLFW_KEY_F8, "F8");
        keyNameMap.put(GLFW.GLFW_KEY_F9, "F9");
        keyNameMap.put(GLFW.GLFW_KEY_F10, "F10");
        keyNameMap.put(GLFW.GLFW_KEY_F11, "F11");
        keyNameMap.put(GLFW.GLFW_KEY_F12, "F12");
        keyNameMap.put(GLFW.GLFW_KEY_KP_0, "NUM 0");
        keyNameMap.put(GLFW.GLFW_KEY_KP_1, "NUM 1");
        keyNameMap.put(GLFW.GLFW_KEY_KP_2, "NUM 2");
        keyNameMap.put(GLFW.GLFW_KEY_KP_3, "NUM 3");
        keyNameMap.put(GLFW.GLFW_KEY_KP_4, "NUM 4");
        keyNameMap.put(GLFW.GLFW_KEY_KP_5, "NUM 5");
        keyNameMap.put(GLFW.GLFW_KEY_KP_6, "NUM 6");
        keyNameMap.put(GLFW.GLFW_KEY_KP_7, "NUM 7");
        keyNameMap.put(GLFW.GLFW_KEY_KP_8, "NUM 8");
        keyNameMap.put(GLFW.GLFW_KEY_KP_9, "NUM 9");
        keyNameMap.put(GLFW.GLFW_KEY_KP_DECIMAL, "NUM .");
        keyNameMap.put(GLFW.GLFW_KEY_KP_DIVIDE, "NUM /");
        keyNameMap.put(GLFW.GLFW_KEY_KP_MULTIPLY, "NUM *");
        keyNameMap.put(GLFW.GLFW_KEY_KP_SUBTRACT, "NUM -");
        keyNameMap.put(GLFW.GLFW_KEY_KP_ADD, "NUM +");
        keyNameMap.put(GLFW.GLFW_KEY_KP_ENTER, "NUM ENTER");
        keyNameMap.put(GLFW.GLFW_KEY_KP_EQUAL, "NUM =");
        keyNameMap.put(GLFW.GLFW_KEY_LEFT_SHIFT, "LSHIFT");
        keyNameMap.put(GLFW.GLFW_KEY_LEFT_CONTROL, "LCTRL");
        keyNameMap.put(GLFW.GLFW_KEY_LEFT_ALT, "LALT");
        keyNameMap.put(GLFW.GLFW_KEY_RIGHT_SHIFT, "RSHIFT");
        keyNameMap.put(GLFW.GLFW_KEY_RIGHT_CONTROL, "RCTRL");
        keyNameMap.put(GLFW.GLFW_KEY_RIGHT_ALT, "RALT");
        keyNameMap.put(getMouseButtonCode(0), "M1");
        keyNameMap.put(getMouseButtonCode(1), "M2");
        keyNameMap.put(getMouseButtonCode(2), "M3");
        keyNameMap.put(getMouseButtonCode(3), "M4");
        keyNameMap.put(getMouseButtonCode(4), "M5");
        keyNameMap.put(MW_UP, "MW Up");
        keyNameMap.put(MW_DOWN, "MW Down");
    }

    public static String getKeyName(int keyCode) {
        return keyNameMap.getOrDefault(keyCode, "UNKNOWN");
    }

    public static int getMouseButtonCode(int button) {
        return -100 - button;
    }
}