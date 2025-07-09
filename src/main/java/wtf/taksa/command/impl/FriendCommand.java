package wtf.taksa.command.impl;

import wtf.taksa.Taksa;
import wtf.taksa.command.Command;
import wtf.taksa.manager.FriendManager;
import wtf.taksa.usual.utils.chat.ChatUtil;

import java.util.List;

public class FriendCommand extends Command {

    public FriendCommand() {
        super("friend", "Взаимодействие с друзьями", ".friend", "friends");
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            sendSyntax();
            return;
        }
        FriendManager friendManager = Taksa.getInstance().getFriendManager();
        String action = args[0].toLowerCase();

        switch (action) {
            case "add":
                friendManager.add(args[1]);
                break;

            case "del":
                friendManager.del(args[1]);
                break;

            case "clear":
                friendManager.clear();
                break;

            case "list":
                listFriends();
                break;
        }
    }

    private void listFriends() {
        final List<String> friends = Taksa.getInstance().getFriendManager().friendsList();

        if (friends.isEmpty()) {
            ChatUtil.clientCommand("У тебя друзей столько сколько у меня!");
            return;
        }

        ChatUtil.clientCommand("Список друзей: ");

        for (int i = 0; i < friends.size(); i++) {
            ChatUtil.clientCommand("[" + (i + 1) + "] " + friends.get(i));
        }
    }
}
