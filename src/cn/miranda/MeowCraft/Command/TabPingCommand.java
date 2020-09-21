package cn.miranda.MeowCraft.Command;

import cn.miranda.MeowCraft.Enum.Notify;
import cn.miranda.MeowCraft.Manager.ConfigManager;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Task.TabPingTask;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.List;

import static cn.miranda.MeowCraft.Manager.ConfigManager.cache;

public class TabPingCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission("tabping.admin")) {
            MessageManager.Message(sender, Notify.No_Permission.getString());
            return true;
        }
        if (args.length != 0) {
            MessageManager.Message(sender, "§e用法: §6/tabping");
            return true;
        }
        boolean state = cache.getBoolean("TabPing.enabled", true);
        if (!state) {
            new TabPingTask().TabPing();
            cache.set("TabPing.enabled", true);
            MessageManager.Message(sender, "§e延迟显示§c已启用");
            ConfigManager.saveConfigs();
            return true;
        }
        cache.set("TabPing.enabled", false);
        MessageManager.Message(sender, "§e延迟显示§c已禁用");
        ConfigManager.saveConfigs();
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return new ArrayList<>();
    }
}
