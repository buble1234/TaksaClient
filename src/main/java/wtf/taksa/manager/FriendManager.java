package wtf.taksa.manager;

import wtf.taksa.usual.utils.chat.ChatUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FriendManager {
    private final List<String> friends = new ArrayList<>();

    public void add(String name) {
        if (friends.contains(name)) {
            ChatUtil.clientCommand("Друг " + name + " уже существует!");
            return;
        }

        friends.add(name);
        ChatUtil.clientCommand("Друг " + name + " успешно добавлен");
    }

    public void del(String name) {
        if (!friends.contains(name)) {
            ChatUtil.clientCommand("Нету друга с ником " + name);
            return;
        }

        friends.remove(name);
        ChatUtil.clientCommand("Друг " + name + " успешно удален");
    }

    public void clear() {
        friends.clear();
        ChatUtil.clientCommand("Все друзья очищены");
    }

    public List<String> friendsList() {
        return Collections.unmodifiableList(friends);
    }
}
