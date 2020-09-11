package cn.miranda.MeowCraft.Command;

import cn.miranda.MeowCraft.Manager.ConfigManager;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Utils.Misc;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static cn.miranda.MeowCraft.Manager.ConfigManager.playerData;

public class CommandMonitorCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        if (!player.hasPermission("cmdlogger.admin")) {
            MessageManager.Message(sender, "§c你没有权限");
            return true;
        }
        if (args.length != 2) {
            MessageManager.Message(sender, "§e用法: §6/cmdlogger §b<player> <on|off>");
            return true;
        }
        Player target = Misc.player(args[0]);
        if (target == null) {
            MessageManager.Message(sender, "§c指定玩家不在线");
            return true;
        }
        String targetName = target.getName();
        switch (args[1]) {
            case "on":
                playerData.set(String.format("%s.cmdlogger", targetName), true);
                MessageManager.Message(player, String.format("§e玩家§b %s §e已§c启用§e指令监视", targetName));
                ConfigManager.saveConfigs();
                return true;
            case "off":
                playerData.set(String.format("%s.cmdlogger", targetName), false);
                MessageManager.Message(player, String.format("§e玩家§b %s §e已§c禁用§e指令监视", targetName));
                ConfigManager.saveConfigs();
                return true;
            default:
                MessageManager.Message(sender, "§e用法: §6/cmdlogger §b<player> <on|off>");
                return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length==1) {
            return Misc.getOnlinePlayerNames();
        }
        if (strings.length ==2) {
            return new ArrayList<>(Arrays.asList("on", "off"));
        }
        return new ArrayList<>();
    }
}
