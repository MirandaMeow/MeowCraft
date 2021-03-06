package cn.miranda.MeowCraft.Command;

import cn.miranda.MeowCraft.Enum.Notify;
import cn.miranda.MeowCraft.Manager.ConfigManager;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Utils.Misc;
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
            MessageManager.Message(sender, Notify.No_Console.getString());
            return true;
        }
        Player player = (Player) sender;
        String playerName = player.getName();
        if (!player.hasPermission("occ.use")) {
            MessageManager.Message(player, Notify.No_Permission.getString());
            return true;
        }
        if (args.length != 1) {
            MessageManager.Message(player, "§e用法: §6/occ §b<on|off|reset>");
            return true;
        }
        switch (args[0]) {
            case "on":
                if (!player.hasPermission("occ.change")) {
                    MessageManager.Message(player, "§c你无法变更自己的职业状态");
                    return true;
                }
                if (playerData.get(String.format("%s.occConfig", playerName)) == null) {
                    MessageManager.Message(player, Notify.No_Occ.getString());
                    return true;
                }
                String occ = playerData.getString(String.format("%s.occConfig.name", playerName), "");
                playerData.set(String.format("%s.occConfig.enabled", playerName), true);
                ConfigManager.saveConfigs();
                Occ.removeALLOccGroups(player);
                Occ.removeGroup(player, "Essential");
                Occ.addGroup(player, occ);
                MessageManager.Message(player, "§e已经恢复职业");
                return true;
            case "off":
                if (!player.hasPermission("occ.change")) {
                    MessageManager.Message(player, "§c你无法变更自己的职业状态");
                    return true;
                }
                if (playerData.get(String.format("%s.occConfig", playerName)) == null) {
                    MessageManager.Message(player, Notify.No_Occ.getString());
                    return true;
                }
                playerData.set(String.format("%s.occConfig.enabled", playerName), false);
                ConfigManager.saveConfigs();
                Occ.removeALLOccGroups(player);
                Occ.addGroup(player, "Essential");
                MessageManager.Message(player, "§e已经临时禁用职业");
                return true;
            case "reset":
                if (!player.hasPermission("occ.reset")) {
                    MessageManager.Message(player, "§c你无法重置自己的职业");
                    return true;
                }
                if (playerData.get(String.format("%s.occConfig", playerName)) == null) {
                    MessageManager.Message(player, Notify.No_Occ.getString());
                    return true;
                }
                Occ.removeALLOccGroups(player);
                Occ.addGroup(player, "Essential");
                Occ.mcMMOSkillsReset(player);
                Occ.removePermission(player, "occ.reset");
                playerData.set(String.format("%s.occConfig", playerName), null);
                ConfigManager.saveConfigs();
                MessageManager.Message(player, "§emcMMO 技能等级已重置");
                MessageManager.Message(player, "§e已经重置职业");
                return true;
            default:
                MessageManager.Message(player, "§e用法: §6/occ §b<on|off|reset>");
                return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            return Misc.returnSelectList(new ArrayList<>(Arrays.asList("on", "off", "reset")), strings[0]);
        }
        return new ArrayList<>();
    }
}
