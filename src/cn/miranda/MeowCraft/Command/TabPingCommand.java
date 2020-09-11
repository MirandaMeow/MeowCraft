package cn.miranda.MeowCraft.Command;

import cn.miranda.MeowCraft.Manager.ConfigManager;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Task.TabPingTask;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.List;

import static cn.miranda.MeowCraft.Manager.ConfigManager.config;

public class TabPingCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission("tabping.admin")) {
            MessageManager.Messager(sender, "§c你没有权限");
            return true;
        }
        if (args.length != 0) {
            MessageManager.Messager(sender, "§e用法: §6/tabping");
            return true;
        }
        boolean state = config.getBoolean("TabPing.enabled", true);
        if (!state) {
            new TabPingTask().TabPing();
            config.set("TabPing.enabled", true);
            MessageManager.Messager(sender, "§e延迟显示§c已启用");
            ConfigManager.saveConfigs();
            return true;
        }
        config.set("TabPing.enabled", false);
        MessageManager.Messager(sender, "§e延迟显示§c已禁用");
        ConfigManager.saveConfigs();
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return new ArrayList<>();
    }
}
