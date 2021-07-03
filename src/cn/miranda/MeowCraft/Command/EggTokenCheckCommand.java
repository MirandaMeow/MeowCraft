package cn.miranda.MeowCraft.Command;

import cn.miranda.MeowCraft.Enum.EggCatcher;
import cn.miranda.MeowCraft.Enum.Notify;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Utils.Misc;
import cn.miranda.MeowCraft.Utils.Occ;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class EggTokenCheckCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 0 && !(sender instanceof Player)) {
            MessageManager.Message(sender, Notify.No_Console.getString());
            return true;
        }
        Player target;
        List<String> permissions;
        if (args.length == 0) {
            target = (Player) sender;
        } else if (args.length == 1) {
            target = Misc.player(args[0]);
            if (target == null) {
                MessageManager.Message(sender, Notify.No_Player.getString());
                return true;
            }
        } else {
            MessageManager.Message(sender, "§e用法: §6/eggtokencheck §b[player]");
            return true;
        }
        permissions = Occ.getPermissionList(target.getName());
        ArrayList<String> eggs = new ArrayList<>();
        for (EggCatcher eggCatcher : EggCatcher.values()) {
            if (permissions.contains(eggCatcher.getPermission())) {
                eggs.add(eggCatcher.getName());
            }
        }
        StringBuilder showText = new StringBuilder();
        for (String egg : eggs) {
            showText.append(egg).append(" ");
        }
        if (sender.getName().equals(target.getName())) {
            if (eggs.size() == 0) {
                MessageManager.Message(target, "§e你可以捕捉的生物: §b无");
            } else {
                MessageManager.Message(target, String.format("§e你可以捕捉的生物: §b%s", showText.toString()));
            }
        } else {
            if (!sender.hasPermission("eggtoken.check")) {
                MessageManager.Message(sender, Notify.No_Permission.getString());
                return true;
            }
            if (eggs.size() == 0) {
                MessageManager.Message(sender, String.format("§e玩家 §b%s §e可以捕捉的生物: §b无", target.getName()));
            } else {
                MessageManager.Message(sender, String.format("§e玩家 §b%s §e可以捕捉的生物: §b%s", target.getName(), showText.toString()));
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            return Misc.returnSelectList(Misc.getOnlinePlayerNames(), strings[0]);
        }
        return new ArrayList<>();
    }
}
