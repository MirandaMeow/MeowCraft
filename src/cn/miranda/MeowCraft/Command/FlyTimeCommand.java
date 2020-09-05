package cn.miranda.MeowCraft.Command;

import cn.miranda.MeowCraft.Manager.ConfigMaganer;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Task.FlyTimeCoolDownTask;
import cn.miranda.MeowCraft.Utils.Misc;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;


import java.util.*;

import static cn.miranda.MeowCraft.Manager.ConfigMaganer.playerData;

public final class FlyTimeCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            MessageManager.Messager(sender, "§e用法: §6/flytime §b<set|check|abort> <player> <time>");
            return true;
        }
        switch (args[0]) {
            case "set":
                if (!sender.hasPermission("flytime.admin")) {
                    MessageManager.Messager(sender, "§c你没有权限！");
                    return true;
                }
                if (args.length != 3) {
                    MessageManager.Messager(sender, "§c参数数量不正确");
                    return true;
                }
                Player target = Misc.player(args[1]);
                if (target == null) {
                    MessageManager.Messager(sender, "§c指定玩家不在线");
                    return true;
                }
                String targetName = target.getName();
                if (playerData.get(String.format("%s.flytime", targetName)) != null) {
                    MessageManager.Messager(sender, "§e已经开启限时飞行");
                    return true;
                }
                if (!Misc.isInt(args[2])) {
                    MessageManager.Messager(sender, "§e参数不正确");
                    return true;
                }
                int setTime = Integer.parseInt(args[2]);
                target.setAllowFlight(true);
                MessageManager.Messager(sender, String.format("§e为玩家 §b%s §e开启了时长 §b%d §e秒的限时飞行", targetName, setTime));
                MessageManager.Messager(target, String.format("§e已开启时长为 §b%d §e秒的限时飞行", setTime));
                playerData.set(String.format("%s.flytime.time", targetName), setTime);
                playerData.set(String.format("%s.flytime.cancel", targetName), false);
                new FlyTimeCoolDownTask().initFlyTime(target);
                ConfigMaganer.saveConfigs();
                return true;
            case "check":
                if (args.length > 2) {
                    MessageManager.Messager(sender, "§e参数过多");
                    return true;
                }
                if (args.length == 1) {
                    if (!(sender instanceof Player)) {
                        MessageManager.Messager(sender, "§c无法在控制台使用该命令");
                        return true;
                    }
                    String senderName = sender.getName();
                    if (playerData.get(String.format("%s.flytime", senderName)) == null) {
                        MessageManager.Messager(sender, "§e未开启限时飞行");
                        return true;
                    }
                    int getTime = playerData.getInt(String.format("%s.flytime.time", senderName), 0);
                    MessageManager.Messager(sender, String.format("§e剩余飞行时间为 §b%d §e秒", getTime));
                    return true;
                }
                if (!sender.hasPermission("flytime.admin")) {
                    MessageManager.Messager(sender, "§c你没有权限！");
                    return true;
                }
                Player targetCheck = Misc.player(args[1]);
                if (targetCheck == null) {
                    MessageManager.Messager(sender, "§c指定玩家不在线");
                    return true;
                }
                String targetCheckName = targetCheck.getName();
                if (playerData.get(String.format("%s.flytime", targetCheckName)) == null) {
                    MessageManager.Messager(sender, String.format("§e玩家 §b%s §e未开启限时飞行", targetCheckName));
                    return true;
                }
                int getTime = playerData.getInt(String.format("%s.flytime.time", targetCheckName), 0);
                MessageManager.Messager(sender, String.format("§e玩家 §b%s §e剩余飞行时间为 §b%d §e秒", targetCheckName, getTime));
                return true;
            case "abort":
                if (!sender.hasPermission("flytime.admin")) {
                    MessageManager.Messager(sender, "§c你没有权限！");
                    return true;
                }
                if (args.length != 2) {
                    MessageManager.Messager(sender, "§e参数过少");
                    return true;
                }
                Player targetAbort = Misc.player(args[1]);
                if (targetAbort == null) {
                    MessageManager.Messager(sender, "§c指定玩家不在线");
                    return true;
                }
                String targetAbortName = targetAbort.getName();
                if (playerData.get(String.format("%s.flytime", targetAbortName)) == null) {
                    MessageManager.Messager(sender, String.format("§e玩家 §b%s §e未开启限时飞行", targetAbortName));
                    return true;
                }
                playerData.set(String.format("%s.flytime.cancel", targetAbortName), true);
                ConfigMaganer.saveConfigs();
                MessageManager.Messager(sender, String.format("§e终止了玩家 §b%s §e的限时飞行", targetAbortName));
                return true;
            default:
                MessageManager.Messager(sender, "§e用法: §6/flytime §b<set|check|abort> [player] <time>");
                return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            return new ArrayList<>(Arrays.asList("set", "check", "abort"));
        }
        if (strings.length == 2) {
            return Misc.getOnlinePlayerMames();
        }
        return new ArrayList();
    }
}
