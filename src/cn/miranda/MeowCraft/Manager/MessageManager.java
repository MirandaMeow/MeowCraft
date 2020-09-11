package cn.miranda.MeowCraft.Manager;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

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
}
