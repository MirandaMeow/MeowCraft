package cn.miranda.MeowCraft.Command;

import cn.miranda.MeowCraft.Enum.Notify;
import cn.miranda.MeowCraft.Manager.ConfigManager;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Utils.Misc;
import cn.miranda.MeowCraft.Utils.Town;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static cn.miranda.MeowCraft.Manager.ConfigManager.cache;

public class TownApplyCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            MessageManager.Message(sender, Notify.No_Console.getString());
            return true;
        }
        Player player = (Player) sender;
        String playerName = player.getName();
        if (args.length != 1) {
            MessageManager.Message(player, "§e用法: §6/townapply §b<town>");
            return true;
        }
        if (!Town.getTownList().contains(args[0])) {
            MessageManager.Message(player, Notify.No_Town.getString());
            return true;
        }
        String TownChinese = Town.getPlayerTownChinese(player);
        if (!Objects.equals(TownChinese, "undefined")) {
            MessageManager.Message(player, String.format("§e你已经加入了 §b%s", TownChinese));
            return true;
        }
        if (cache.get(String.format("TownApply.%s", playerName)) != null) {
            MessageManager.Message(player, "§c请勿重复加入");
            return true;
        }
        cache.set(String.format("TownApply.%s", playerName), args[0]);
        ConfigManager.saveConfigs();
        MessageManager.Message(player, String.format("§e已经申请加入 §b%s§e, 请等待镇长回应", args[0]));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            return Misc.returnSelectList(Town.getTownList(), strings[0]);
        }
        return new ArrayList<>();
    }
}
