package cn.miranda.MeowCraft.Command;

import cn.miranda.MeowCraft.Manager.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import cn.miranda.MeowCraft.Utils.Misc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class HealthPointSetCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("hpset.set")) {
            MessageManager.Message(sender, "§c你没有权限");
            return false;
        }
        if (args.length > 2 || args.length == 0) {
            MessageManager.Message(sender, "§e用法: §6/hpset §b[player] <value|reset>");
            return true;
        }
        if (args.length == 1) {
            if (!(sender instanceof Player)) {
                MessageManager.Message(sender, "§c不能对控制台使用");
                return true;
            }
            Player player = (Player) sender;
            if (Objects.equals(args[0], "reset")) {
                Misc.resetHP(player);
                MessageManager.Message(player, "§e你的生命上限被重置了");
                return true;
            }
            if (!Misc.isInt(args[0])) {
                MessageManager.Message(player, "§c输入错误");
                return true;
            }
            int setValue = Integer.parseInt(args[0]);
            if (setValue <= 0 || setValue > 2048) {
                MessageManager.Message(player, "§e设定值必须介于 §b1 - 2048 §e之间");
                return true;
            }
            Misc.setMaxHP(player, setValue);
            MessageManager.Message(player, String.format("§e你的生命上限被设置为 §b%d", setValue));
            return true;
        }
        Player target = Misc.player(args[0]);
        if (target == null) {
            MessageManager.Message(sender, "§c指定玩家不在线");
            return true;
        }
        if (Objects.equals(args[1], "reset")) {
            Misc.resetHP(target);
            MessageManager.Message(sender, String.format("§e你重置了 §b%s §e的生命上限", target.getName()));
            MessageManager.Message(target, "§e你的生命上限被重置了");
            return true;
        }
        if (!Misc.isInt(args[1])) {
            MessageManager.Message(sender, "§c输入不正确");
            return true;
        }
        int setValue = Integer.parseInt(args[1]);
        if (setValue <= 0 || setValue > 2048) {
            MessageManager.Message(sender, "§e设定值必须介于 §b1 - 2048 §e之间");
            return true;
        }
        Misc.setMaxHP(target, setValue);
        MessageManager.Message(sender, String.format("§e你将 §b%s §e的生命上限设置为 §b%d", target.getName(), setValue));
        MessageManager.Message(target, String.format("§e你的生命上限被设置为 §b%d", setValue));
        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            return Misc.getOnlinePlayerNames();
        }
        return new ArrayList<>();
    }
}
