/*
 * @author Sarkolsss
 * @date 23.07.2025, 12:48
 */

package wtf.taksa.common.other;

import lombok.SneakyThrows;
import org.lwjgl.glfw.GLFW;

import java.lang.reflect.Field;
import java.util.HashMap;

public class KeyStorage {
    public static HashMap<String, Integer> map = new HashMap<>();
    public static HashMap<Integer, String> map2 = new HashMap<>();

    @SneakyThrows
    public void init() {
        Field[] fields = GLFW.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().startsWith("GLFW_KEY_")) {
                map.put(field.getName().replace("GLFW_KEY_", ""), field.getInt(null));
                map2.put(field.getInt(null), field.getName().replace("GLFW_KEY_", ""));
            }
        }
    }

    public String get(int key) {
        return map2.get(key);
    }

    public int get(String key) {
        return map.get(key);
    }
}