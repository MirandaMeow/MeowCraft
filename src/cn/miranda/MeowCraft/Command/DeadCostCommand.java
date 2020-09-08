package cn.miranda.MeowCraft.Command;

import cn.miranda.MeowCraft.Manager.ConfigMaganer;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Utils.Misc;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static cn.miranda.MeowCraft.Manager.ConfigMaganer.config;
import static cn.miranda.MeowCraft.Manager.ConfigMaganer.playerData;
import static org.bukkit.Bukkit.getServer;

public final class DeadCostCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 1) {
            MessageManager.Messager(sender, "§e用法: §6/deadcost §b[value]");
            return true;
        }
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                MessageManager.Messager(sender, "§c无法在控制台使用该命令");
                return true;
            }
            Player player = (Player) sender;
            String playerName = player.getName();
            if (playerData.get(String.format("%s.deadcost", playerName)) == null || !playerData.getBoolean(String.format("%s.deadcost", playerName))) {
                playerData.set(String.format("%s.deadcost", playerName), true);
                ConfigMaganer.saveConfigs();
                MessageManager.Messager(player, "§e启用死亡不掉落");
                return true;
            }
            playerData.set(String.format("%s.deadcost", player.getName()), false);
            ConfigMaganer.saveConfigs();
            MessageManager.Messager(player, "§e禁用死亡不掉落");
            return true;

        }
        if (!sender.hasPermission("deadcost.admin")) {
            MessageManager.Messager(sender, "§c你没有权限");
            return true;
        }
        if (!Misc.isInt(args[0])) {
            MessageManager.Messager(sender, "§c参数不正确");
            return true;
        }

        int setValue = Integer.parseInt(args[0]);
        if (setValue < 0 || setValue > 100) {
            MessageManager.Messager(sender, "§e请输入一个介于 §b0 - 100 §e的正整数");
            return true;
        }
        getServer().broadcastMessage(String.format("§e死亡不掉落的费率变更为 §b%d%%", setValue));
        config.set("DeadCost.ratio", setValue);
        ConfigMaganer.saveConfigs();
        return true;
    }
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return new ArrayList<String>();
    }
}
