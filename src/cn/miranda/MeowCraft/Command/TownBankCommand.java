package cn.miranda.MeowCraft.Command;

import cn.miranda.MeowCraft.Enum.Notify;
import cn.miranda.MeowCraft.Manager.BankManager;
import cn.miranda.MeowCraft.Manager.MessageManager;
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
            MessageManager.Message(sender, Notify.No_Console.getString());
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
            MessageManager.Message(sender, "§c你没有加入小镇");
            return true;
        }
        if (args.length != 0) {
            MessageManager.Message(player, "§e用法: §6/bank");
            return true;
        }
        player.openInventory(BankManager.mainPanel(player));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return new ArrayList<>();
    }
}
