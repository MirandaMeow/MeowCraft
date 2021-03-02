package cn.miranda.MeowCraft.Command;

import cn.miranda.MeowCraft.Cores.Treasure;
import cn.miranda.MeowCraft.Cores.TreasureSet;
import cn.miranda.MeowCraft.Enum.Notify;
import cn.miranda.MeowCraft.Manager.ConfigManager;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Utils.IO;
import cn.miranda.MeowCraft.Utils.Misc;
import cn.miranda.MeowCraft.Utils.Occ;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.*;

import static cn.miranda.MeowCraft.Manager.ConfigManager.playerData;

public class TreasureCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length < 2) {
            MessageManager.Message(sender, "§e用法: §6/treasure §b<create|set|delete|show|reset|clear> <displayName>");
            return true;
        }
        String option = args[0];
        String displayName = args[1];
        TreasureSet treasureSet;
        try {
            treasureSet = new TreasureSet();
        } catch (IOException | ClassNotFoundException e) {
            MessageManager.Message(sender, "§c文件初始化失败");
            e.printStackTrace();
            return true;
        }
        ArrayList<String> adminList = new ArrayList<>(Arrays.asList("create", "set", "delete", "reset", "clear"));
        if (adminList.contains(option)) {
            if (!sender.hasPermission("treasure.admin")) {
                MessageManager.Message(sender, Notify.No_Permission.getString());
                return true;
            }
            if (Objects.equals(option, "create")) {
                if (args.length != 3) {
                    MessageManager.Message(sender, "§c需要权限标识符");
                    return true;
                }
                String perm = args[2];
                Treasure treasure = new Treasure(displayName, perm);
                try {
                    treasureSet.addTreasure(displayName, treasure);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MessageManager.Message(sender, String.format("§e奖励箱 §b%s §e已创建", displayName));
                return true;
            }
            if (Objects.equals(option, "delete")) {
                if (!treasureSet.getList().contains(displayName)) {
                    MessageManager.Message(sender, String.format("§c奖励箱 §b%s §c不存在", displayName));
                    return true;
                }
                try {
                    treasureSet.removeTreasure(displayName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MessageManager.Message(sender, String.format("§e奖励箱 §b%s §e已删除", displayName));
                return true;
            }
            if (Objects.equals(option, "reset")) {
                String playerName = args[1];
                if (args.length != 3) {
                    MessageManager.Message(sender, "§c需要奖励箱名称");
                    return true;
                }
                String targetDisplayName = args[2];
                String removePerm = "-" + treasureSet.getTreasure(targetDisplayName).getPermission();
                Occ.removePermissionFromPlayerName(playerName, removePerm);
                MessageManager.Message(sender, String.format("§e已清除玩家 §b%s §e禁止打开该箱子的权限", playerName));
                ConfigurationSection targetConfig = playerData.getConfigurationSection(String.format("%s.treasures", playerName));
                if (targetConfig == null) {
                    MessageManager.Message(sender, String.format("§c没有玩家 §b%s §c打开奖励箱的记录", playerName));
                    return true;
                }
                Set<String> targetList = targetConfig.getValues(false).keySet();
                if (targetList.size() == 0) {
                    MessageManager.Message(sender, String.format("§c没有玩家 §b%s §c打开奖励箱的记录", playerName));
                    return true;
                }
                if (!targetList.contains(targetDisplayName)) {
                    MessageManager.Message(sender, String.format("§c没有玩家 §b%s §c打开过 §b%s §c的记录", playerName, targetDisplayName));
                    return true;
                }
                playerData.set(String.format("%s.treasures.%s", playerName, targetDisplayName), null);
                if (playerData.getConfigurationSection(String.format("%s.treasures", playerName)).getValues(false).isEmpty()) {
                    playerData.set(String.format("%s.treasures", playerName), null);
                }
                MessageManager.Message(sender, String.format("§e清除了玩家 §b%s §e的 §b%s §e奖励箱的记录", playerName, targetDisplayName));
                ConfigManager.saveConfigs();
                return true;
            }
            if (Objects.equals(option, "set")) {
                if (!(sender instanceof Player)) {
                    MessageManager.Message(sender, Notify.No_Console.getString());
                    return true;
                }
                Player player = (Player) sender;
                if (!sender.hasPermission("treasure.admin")) {
                    MessageManager.Message(sender, Notify.No_Permission.getString());
                    return true;
                }
                Treasure setTreasure = treasureSet.getTreasure(displayName);
                if (setTreasure == null) {
                    MessageManager.Message(sender, String.format("§c奖励箱 §b%s §c不存在", displayName));
                    return true;
                }
                setTreasure.show(player, true);
                return true;
            }
            if (Objects.equals(option, "clear")) {
                if (!sender.hasPermission("treasure.admin")) {
                    MessageManager.Message(sender, Notify.No_Permission.getString());
                    return true;
                }
                ArrayList<String> players = new ArrayList<>(playerData.getKeys(false));
                if (!treasureSet.getList().contains(displayName)) {
                    MessageManager.Message(sender, String.format("§c奖励箱 §b%s §c不存在", displayName));
                    return true;
                }
                String removePerm = "-" + treasureSet.getTreasure(displayName).getPermission();
                for (String player : players) {
                    Occ.removePermissionFromPlayerName(player, removePerm);
                    ConfigurationSection playerConfig = playerData.getConfigurationSection(String.format("%s.treasures", player));
                    if (playerConfig == null) {
                        continue;
                    }
                    playerConfig.set(displayName, null);
                    if (playerConfig.getValues(false).isEmpty()) {
                        playerData.set(String.format("%s.treasures", player), null);
                    }
                }
                MessageManager.Message(sender, String.format("§e已经清除配置文件中所有的 §b%s §e奖励箱", displayName));
                ConfigManager.saveConfigs();
            }
        }
        if (Objects.equals(option, "show")) {
            if (!(sender instanceof Player)) {
                MessageManager.Message(sender, Notify.No_Console.getString());
                return true;
            }
            Player showPlayer = (Player) sender;
            String savedData = playerData.getString(String.format("%s.treasures.%s", sender.getName(), displayName));
            if (savedData == null) {
                Treasure showTreasure = treasureSet.getTreasure(displayName);
                if (showTreasure == null) {
                    MessageManager.Message(sender, String.format("§c奖励箱 §b%s §c不存在", displayName));
                    return true;
                }
                String permission = showTreasure.getPermission();
                if (!sender.hasPermission(permission)) {
                    ArrayList<String> list = new ArrayList<>(Collections.singletonList("§b" + permission));
                    MessageManager.HoverMessage((Player) sender, "§c你无法打开此奖励箱", list);
                    return true;
                }
                showTreasure.show(showPlayer, false);
            } else {
                try {
                    Treasure savedTreasure = (Treasure) IO.decodeData(savedData);
                    assert savedTreasure != null;
                    savedTreasure.show(showPlayer, false);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            return Misc.returnSelectList(new ArrayList<>(Arrays.asList("create", "set", "delete", "show", "reset", "clear")), strings[0]);
        }
        if (strings.length == 2) {
            ArrayList<String> list = new ArrayList<>(Arrays.asList("set", "delete", "show", "clear"));
            if (list.contains(strings[0])) {
                try {
                    TreasureSet treasureSet = new TreasureSet();
                    return Misc.returnSelectList(treasureSet.getList(), strings[1]);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            if (Objects.equals(strings[0], "reset")) {
                return Misc.returnSelectList(Misc.getOnlinePlayerNames(), strings[1]);
            }
        }
        if (strings.length == 3) {
            if (Objects.equals(strings[0], "reset")) {
                ConfigurationSection playerConfig = playerData.getConfigurationSection(String.format("%s.treasures", commandSender.getName()));
                if (playerConfig == null) {
                    return new ArrayList<>();
                } else {
                    ArrayList<String> list = new ArrayList<>(playerConfig.getKeys(false));
                    return Misc.returnSelectList(list, strings[2]);
                }
            }
        }
        return new ArrayList<>();
    }
}
