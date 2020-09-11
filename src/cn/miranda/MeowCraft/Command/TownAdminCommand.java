package cn.miranda.MeowCraft.Command;

import cn.miranda.MeowCraft.Manager.ConfigMaganer;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Utils.Misc;
import cn.miranda.MeowCraft.Utils.Occ;
import cn.miranda.MeowCraft.Utils.Town;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static cn.miranda.MeowCraft.Manager.ConfigMaganer.temp;

public class TownAdminCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            MessageManager.Messager(sender, "§c该命令不能在控制台运行");
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("town.admin")) {
            MessageManager.Messager(player, "§c你不是镇长");
            return true;
        }
        if (args.length > 2 || args.length == 0) {
            MessageManager.Messager(player, "§e用法: §6/townadmin §b<list|apply|deny> <player>");
            return true;
        }
        String playerTown = Town.getPlayerTownChinese(player);
        switch (args[0]) {
            case "apply":
                String targetNameApply = args[1];
                if (!Town.getApplyList(playerTown).contains(targetNameApply)) {
                    MessageManager.Messager(player, "§c列表中没有此玩家");
                    return true;
                }
                PermissionUser pexUser = PermissionsEx.getUser(targetNameApply);
                pexUser.addGroup(Town.getTownPermissionGroup(playerTown));
                Occ.reversePlayerGroup(targetNameApply);
                MessageManager.Messager(player, String.format("§e成功接纳玩家 §b%s", targetNameApply));
                temp.set(String.format("TownApply.%s", targetNameApply), null);
                ConfigMaganer.saveConfigs();
                return true;
            case "deny":
                String targetNameDeny = args[1];
                if (!Town.getApplyList(playerTown).contains(targetNameDeny)) {
                    MessageManager.Messager(player, "§c列表中没有此玩家");
                    return true;
                }
                MessageManager.Messager(player, String.format("§e拒绝接纳玩家 §b%s", targetNameDeny));
                temp.set(String.format("TownApply.%s", targetNameDeny), null);
                ConfigMaganer.saveConfigs();
                return true;
            case "list":
                List<String> applyList = Town.getApplyList(playerTown);
                if (applyList.size() == 0) {
                    MessageManager.Messager(player, "§e当前没有玩家申请加入小镇");
                    return true;
                }
                MessageManager.Messager(player, String.format("§e当前申请加入小镇的玩家有 §b%d §e人", applyList.size()));
                MessageManager.Messager(player, "§e" + applyList.toString());
                return true;
            default:
                MessageManager.Messager(player, "§e用法: §6/townadmin §b<list|apply|deny> <player>");
                return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            return new ArrayList<>(Arrays.asList("apply", "deny", "list"));
        }
        if (strings.length == 2) {
            return Misc.getOnlinePlayerNames();
        }
        return new ArrayList<>();
    }
}
