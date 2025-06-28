package wtf.taksa.mixin.chat;

import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import net.minecraft.client.gui.screen.ChatInputSuggestor;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.taksa.Taksa;
import wtf.taksa.command.Command;
import wtf.taksa.manager.CommandManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
/**
 * @author Kenny1337
 * @since 28.06.2025
 */
@Mixin(ChatInputSuggestor.class)
public class ChatInputSuggestorMixin {

    @Final @Shadow private TextFieldWidget textField;
    @Shadow private CompletableFuture<Suggestions> pendingSuggestions;

    @Shadow
    protected native void show(boolean narrateFirstSuggestion);

    @Inject(method = "refresh", at = @At("HEAD"), cancellable = true)
    private void onRefresh(CallbackInfo ci) {
        String text = textField != null ? textField.getText() : "";
        String prefix = CommandManager.getPrefix();

        if (text.startsWith(prefix)) {
            List<Suggestion> suggestions = new ArrayList<>();
            int commandStartPos = prefix.length();
            StringRange range = StringRange.between(commandStartPos, text.length());

            String commandInput = text.substring(commandStartPos).trim().toLowerCase();
            List<Command> commands = Taksa.getInstance().getCommandManager().getCommands();

            if (commandInput.isEmpty()) {
                for (Command command : commands) {
                    suggestions.add(new Suggestion(range, command.getName()));
                }
            } else {
                String[] parts = commandInput.split(" ");
                String firstPart = parts[0];

                if (parts.length == 1) {
                    for (Command command : commands) {
                        if (command.getName().toLowerCase().startsWith(firstPart)) {
                            suggestions.add(new Suggestion(range, command.getName()));
                        }

                        for (String alias : command.getAliases()) {
                            if (alias.toLowerCase().startsWith(firstPart)) {
                                suggestions.add(new Suggestion(range, alias));
                            }
                        }
                    }
                } else {
                    Command matchedCommand = findCommandByName(commands, firstPart);
                    if (matchedCommand != null) {
                        if (matchedCommand.getName().equalsIgnoreCase("config") || matchedCommand.getName().equalsIgnoreCase("cfg")) {
                            if (parts.length == 2) {
                                String secondPart = parts[1];
                                List<String> configActions = List.of("save", "load", "list", "delete");

                                for (String action : configActions) {
                                    if (action.startsWith(secondPart)) {
                                        StringRange actionRange = StringRange.between(prefix.length() + firstPart.length() + 1, text.length());
                                        suggestions.add(new Suggestion(actionRange, action));
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (!suggestions.isEmpty()) {
                this.pendingSuggestions = CompletableFuture.completedFuture(
                        new Suggestions(range, suggestions)
                );
                this.show(true);
                ci.cancel();
            }
        }
    }

    private Command findCommandByName(List<Command> commands, String name) {
        for (Command command : commands) {
            if (command.getName().equalsIgnoreCase(name)) {
                return command;
            }

            for (String alias : command.getAliases()) {
                if (alias.equalsIgnoreCase(name)) {
                    return command;
                }
            }
        }

        return null;
    }
}