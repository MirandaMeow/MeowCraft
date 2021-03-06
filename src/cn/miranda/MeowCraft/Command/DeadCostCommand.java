package cn.miranda.MeowCraft.Command;

import cn.miranda.MeowCraft.Enum.Notify;
import cn.miranda.MeowCraft.Manager.ConfigManager;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Utils.Misc;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static cn.miranda.MeowCraft.Manager.ConfigManager.config;
import static cn.miranda.MeowCraft.Manager.ConfigManager.playerData;
import static org.bukkit.Bukkit.getServer;

public final class DeadCostCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 1) {
            MessageManager.Message(sender, "§e用法: §6/deadcost §b[value]");
            return true;
        }
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                MessageManager.Message(sender, Notify.No_Console.getString());
                return true;
            }
            Player player = (Player) sender;
            String playerName = player.getName();
            if (playerData.get(String.format("%s.deadcost", playerName)) == null || !playerData.getBoolean(String.format("%s.deadcost", playerName))) {
                playerData.set(String.format("%s.deadcost", playerName), true);
                ConfigManager.saveConfigs();
                MessageManager.Message(player, "§e启用死亡不掉落");
                return true;
            }
            playerData.set(String.format("%s.deadcost", player.getName()), false);
            ConfigManager.saveConfigs();
            MessageManager.Message(player, "§e禁用死亡不掉落");
            return true;

        }
        if (!sender.hasPermission("deadcost.admin")) {
            MessageManager.Message(sender, Notify.No_Permission.getString());
            return true;
        }
        if (!Misc.isInt(args[0])) {
            MessageManager.Message(sender, Notify.Invalid_Input.getString());
            return true;
        }

        int setValue = Integer.parseInt(args[0]);
        if (setValue < 0 || setValue > 100) {
            MessageManager.Message(sender, "§e请输入一个介于 §b0 - 100 §e的正整数");
            return true;
        }
        getServer().broadcastMessage(String.format("§e死亡不掉落的费率变更为 §b%d%%", setValue));
        config.set("DeadCost.ratio", setValue);
        ConfigManager.saveConfigs();
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return new ArrayList<>();
    }
}
