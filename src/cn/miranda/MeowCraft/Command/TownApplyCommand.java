package cn.miranda.MeowCraft.Command;

import cn.miranda.MeowCraft.Manager.ConfigMaganer;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Utils.Town;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static cn.miranda.MeowCraft.Manager.ConfigMaganer.temp;

public class TownApplyCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            MessageManager.Messager(sender, "§c该命令不能在控制台运行");
            return true;
        }
        Player player = (Player) sender;
        String playerName = player.getName();
        if (args.length != 1) {
            MessageManager.Messager(player, "§e用法: §6/townapply §b<town>");
            return true;
        }
        if (!Town.getTownList().contains(args[0])) {
            MessageManager.Messager(player, "§c小镇不存在");
            return true;
        }
        String TownChinese = Town.getPlayerTownChinese(player);
        if (TownChinese != "undefined") {
            MessageManager.Messager(player, String.format("§e你已经加入了 §b%s", TownChinese));
            return true;
        }
        if (temp.get(String.format("TownApply.%s", playerName)) != null) {
            MessageManager.Messager(player, "§c请勿重复加入");
            return true;
        }
        temp.set(String.format("TownApply.%s", playerName), args[0]);
        ConfigMaganer.saveConfigs();
        MessageManager.Messager(player, String.format("§e已经申请加入 §b%s§e, 请等待镇长回应", args[0]));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            return Town.getTownList();
        }
        return new ArrayList<String>();
    }
}