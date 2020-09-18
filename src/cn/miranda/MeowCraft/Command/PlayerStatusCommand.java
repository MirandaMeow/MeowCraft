package cn.miranda.MeowCraft.Command;

import cn.miranda.MeowCraft.Enum.Notify;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Manager.PlayerStatusManager;
import cn.miranda.MeowCraft.Utils.Misc;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerStatusCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            MessageManager.Message(sender, Notify.No_Console.getString());
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("playerstatus.admin")) {
            MessageManager.Message(player, Notify.No_Permission.getString());
            return true;
        }
        if (args.length != 1) {
            MessageManager.Message(sender, "§e用法: §6/playerstatus §b<setdefault|setrestore>");
            return true;
        }
        switch (args[0]) {
            case "setdefault":
                PlayerStatusManager.setDefault(player);
                return true;
            case "setrestore":
                PlayerStatusManager.setRestore(player);
                return true;
            default:
                MessageManager.Message(sender, "§e用法: §6/playerstatus §b<setdefault|setrestore>");
                return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            return Misc.returnSelectList(new ArrayList<>(Arrays.asList("setdefault", "setrestore")), strings[0]);
        }
        return new ArrayList<>();
    }
}
