package cn.miranda.MeowCraft.Command;

import cn.miranda.MeowCraft.Manager.ConfigMaganer;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Utils.Misc;
import cn.miranda.MeowCraft.Utils.Occ;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static cn.miranda.MeowCraft.Manager.ConfigMaganer.playerData;

public class OccSkillCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            MessageManager.Messager(sender, "§c无法在控制台使用该命令");
            return true;
        }
        Player player = (Player) sender;
        String playerName = player.getName();
        if (args.length > 2 || args.length == 0) {
            MessageManager.Messager(player, "§e用法: §6/occskill §b<list|cooldown> [player]");
            return true;
        }
        if (Objects.equals(args[0], "list")) {
            if (args.length == 1) {
                MessageManager.Messager(player, "§e已习得技能:");
                List skillIDs = Occ.getPlayerSkills(player);
                if (skillIDs.size() == 0) {
                    MessageManager.Messager(player, "§e无");
                    return true;
                }
                for (Object i : skillIDs) {
                    MessageManager.Messager(player, String.format("§c%s", Occ.getSkillChineseById(i.toString())));
                }
                return true;
            }
            if (!player.hasPermission("occskill.admin")) {
                MessageManager.Messager(sender, "§c你没有权限");
                return true;
            }
            Player target = Misc.player(args[1]);
            if (target == null) {
                MessageManager.Messager(sender, "§c指定玩家不在线");
                return true;
            }
            String targetName = target.getName();
            MessageManager.Messager(player, String.format("§e玩家§b %s §e已习得技能:", targetName));
            List skillIDs = Occ.getPlayerSkills(target);
            if (skillIDs.size() == 0) {
                MessageManager.Messager(player, "§e无");
                return true;
            }
            for (Object i : skillIDs) {
                MessageManager.Messager(player, String.format("§c%s", Occ.getSkillChineseById(i.toString())));
            }
            return true;
        }
        if (Objects.equals(args[0], "cooldown")) {
            if (!player.hasPermission("occskill.admin")) {
                MessageManager.Messager(sender, "§c你没有权限");
                return true;
            }
            if (args.length == 1) {
                playerData.set(String.format("%s.occConfig.occSkills.occCoolDown", playerName), null);
                playerData.set(String.format("%s.temp", playerName), null);
                ConfigMaganer.saveConfigs();
                MessageManager.Messager(player, "§e成功冷却所有职业技能");
                return true;
            }
            Player target = Misc.player(args[1]);
            if (target == null) {
                MessageManager.Messager(sender, "§c指定玩家不在线");
                return true;
            }
            String targetName = target.getName();
            playerData.set(String.format("%s.occConfig.occSkills.occCoolDown", targetName), null);
            playerData.set(String.format("%s.temp", targetName), null);
            ConfigMaganer.saveConfigs();
            MessageManager.Messager(player, String.format("§e成功冷却玩家 §b%s §e的所有职业技能", targetName));
            MessageManager.Messager(target, "§e你的所有职业技能已冷却");
            return true;
        }
        MessageManager.Messager(player, "§e用法: §6/occskill §b<list|cooldown> [player]");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            return new ArrayList<>(Arrays.asList("list", "cooldown"));
        }
        if (strings.length == 2) {
            return Misc.getOnlinePlayerNames();
        }
        return new ArrayList<>();
    }
}
