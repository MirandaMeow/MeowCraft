package cn.miranda.MeowCraft.Command;

import cn.miranda.MeowCraft.Manager.ConfigManager;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Utils.Occ;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static cn.miranda.MeowCraft.Manager.ConfigManager.playerData;

public final class OccCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            MessageManager.Messager(sender, "§c无法在控制台使用该命令");
            return true;
        }
        Player player = (Player) sender;
        String playerName = player.getName();
        if (!player.hasPermission("occ.use")) {
            MessageManager.Messager(player, "§c你无法使用这个命令");
            return true;
        }
        if (args.length != 1) {
            MessageManager.Messager(player, "§e用法: §6/occ §b<on|off|reset>");
            return true;
        }
        switch (args[0]) {
            case "on":
                if (!player.hasPermission("occ.change")) {
                    MessageManager.Messager(player, "§c你无法变更自己的职业状态");
                    return true;
                }
                if (playerData.get(String.format("%s", playerName)) == null) {
                    MessageManager.Messager(player, "§c你没有职业");
                    return true;
                }
                String occ = playerData.getString(String.format("%s.occConfig.name", playerName), "");
                playerData.set(String.format("%s.occConfig.enabled", playerName), true);
                ConfigManager.saveConfigs();
                Occ.removeALLOccGroups(player);
                Occ.removeGroup(player, "Essential");
                Occ.addGroup(player, occ);
                MessageManager.Messager(player, "§e已经恢复职业");
                return true;
            case "off":
                if (!player.hasPermission("occ.change")) {
                    MessageManager.Messager(player, "§c你无法变更自己的职业状态");
                    return true;
                }
                if (playerData.get(String.format("%s", playerName)) == null) {
                    MessageManager.Messager(player, "§c你没有职业");
                    return true;
                }
                playerData.set(String.format("%s.occConfig.enabled", playerName), false);
                ConfigManager.saveConfigs();
                Occ.removeALLOccGroups(player);
                Occ.addGroup(player, "Essential");
                MessageManager.Messager(player, "§e已经临时禁用职业");
                return true;
            case "reset":
                if (!player.hasPermission("occ.reset")) {
                    MessageManager.Messager(player, "§c你无法重置自己的职业");
                    return true;
                }
                if (playerData.get(String.format("%s", playerName)) == null) {
                    MessageManager.Messager(player, "§c你没有职业");
                    return true;
                }
                Occ.removeALLOccGroups(player);
                Occ.addGroup(player, "Essential");
                Occ.mcMMOSkillsReset(player);
                Occ.removePermission(player, "occ.reset");
                playerData.set(String.format("%s.occConfig", playerName), null);
                ConfigManager.saveConfigs();
                MessageManager.Messager(player, "§emcMMO 技能等级已重置");
                MessageManager.Messager(player, "§e已经重置职业");
                return true;
            default:
                MessageManager.Messager(player, "§e用法: §6/occ §b<on|off|reset>");
                return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            return new ArrayList<>(Arrays.asList("on", "off", "reset"));
        }
        return new ArrayList<String>();
    }
}
