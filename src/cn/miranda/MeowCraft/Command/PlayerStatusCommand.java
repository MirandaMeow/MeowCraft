package cn.miranda.MeowCraft.Command;

import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Manager.PlayerStatusManager;
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
            MessageManager.Messager(sender, "§c该命令不能在控制台运行");
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("playerstatus.admin")) {
            MessageManager.Messager(player, "§c你没有权限");
            return true;
        }
        if (args.length != 1) {
            MessageManager.Messager(sender, "§e用法: §6/playerstatus §b<setdefault|setrestore>");
            return true;
        }
        switch (args[0]) {
            case "setdefault":
                PlayerStatusManager.setDefault(player);
                MessageManager.Messager(player, "§e玩家数据已保存");
                return true;
            case "setrestore":
                MessageManager.Messager(player, "§e玩家数据已恢复");
                PlayerStatusManager.setRestore(player);
                return true;
            default:
                MessageManager.Messager(sender, "§e用法: §6/playerstatus §b<setdefault|setrestore>");
                return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            return new ArrayList<>(Arrays.asList("setdefault", "setrestore"));
        }
        return new ArrayList<>();
    }
}
