package cn.miranda.MeowCraft.Command;

import cn.miranda.MeowCraft.Manager.ChestManager;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Utils.Misc;
import cn.miranda.MeowCraft.Utils.Town;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TownBankCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            MessageManager.Messager(sender, "§c该命令不能在控制台运行");
            return true;
        }
        Player player = (Player) sender;
        boolean pass = false;
        for (Object p : Town.getTownsPermission()) {
            String perm = p.toString();
            if (player.hasPermission(perm)) {
                pass = true;
                break;
            }
        }
        if (!pass) {
            MessageManager.Messager(sender, "§c你没有加入小镇");
            return true;
        }
        if (args.length != 0) {
            MessageManager.Messager(player, "§e用法: §6/bank");
            return true;
        }
        player.openInventory(ChestManager.mainPanel(player));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return new ArrayList<String>();
    }
}
