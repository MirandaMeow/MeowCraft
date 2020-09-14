package cn.miranda.MeowCraft.Manager;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.ArrayList;
import java.util.List;

import static cn.miranda.MeowCraft.Manager.ConfigManager.config;
import static org.bukkit.Bukkit.getServer;

public class MessageManager {
    public static void Message(@Nullable CommandSender sender, @NotNull String message) {
        if (sender == null) {
            Bukkit.getConsoleSender().sendMessage(message);
        } else {
            if (config.getBoolean("Settings.prefix")) {
                sender.sendMessage("§b[§6猫子组件§b] " + message);
            } else {
                sender.sendMessage(message);
            }
        }
    }

    public static void Broadcast(@NotNull String message) {
        if (config.getBoolean("Settings.prefix")) {
            getServer().broadcastMessage("§b[§6猫子组件§b] " + message);
        } else {
            getServer().broadcastMessage(message);
        }
    }

    public static void ConsoleMessage(@NotNull String message) {
        Bukkit.getConsoleSender().sendMessage(message);
    }

    public static void ActionBarMessage(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }

    public static void HoverMessage(Player player, String title, List<String> lines) {
        TextComponent message = new TextComponent(title);
        List<BaseComponent> list = new ArrayList<>();
        for (String line : lines) {
            list.add(new TextComponent(line));
            if (lines.indexOf(line) != lines.size() - 1) {
                list.add(new TextComponent("\n"));
            }
        }
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, list.toArray(new BaseComponent[list.size()])));
        player.spigot().sendMessage(message);
    }
}
