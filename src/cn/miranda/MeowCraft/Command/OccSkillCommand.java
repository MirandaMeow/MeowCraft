package cn.miranda.MeowCraft.Command;

import cn.miranda.MeowCraft.Manager.ConfigManager;
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

import static cn.miranda.MeowCraft.Manager.ConfigManager.skills;
import static cn.miranda.MeowCraft.Manager.ConfigManager.temp;

public class OccSkillCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            MessageManager.Message(sender, "§c无法在控制台使用该命令");
            return true;
        }
        Player player = (Player) sender;
        String playerName = player.getName();
        if (args.length > 2 || args.length == 0) {
            MessageManager.Message(player, "§e用法: §6/occskill §b<list|cooldown> [player]");
            return true;
        }
        if (Objects.equals(args[0], "list")) {
            if (args.length == 1) {
                MessageManager.Message(player, "§e已习得技能:");
                return displaySkillList(player, player);
            }
            if (!player.hasPermission("occskill.admin")) {
                MessageManager.Message(sender, "§c你没有权限");
                return true;
            }
            Player target = Misc.player(args[1]);
            if (target == null) {
                MessageManager.Message(sender, "§c指定玩家不在线");
                return true;
            }
            String targetName = target.getName();
            MessageManager.Message(player, String.format("§e玩家§b %s §e已习得技能:", targetName));
            return displaySkillList(player, target);
        }
        if (Objects.equals(args[0], "cooldown")) {
            if (!player.hasPermission("occskill.admin")) {
                MessageManager.Message(sender, "§c你没有权限");
                return true;
            }
            if (args.length == 1) {
                temp.set(String.format("OccSkillCoolDown.%s", playerName), null);
                ConfigManager.saveConfigs();
                MessageManager.Message(player, "§e成功冷却所有职业技能");
                return true;
            }
            Player target = Misc.player(args[1]);
            if (target == null) {
                MessageManager.Message(sender, "§c指定玩家不在线");
                return true;
            }
            String targetName = target.getName();
            temp.set(String.format("OccSkillCoolDown.%s", targetName), null);
            ConfigManager.saveConfigs();
            MessageManager.Message(player, String.format("§e成功冷却玩家 §b%s §e的所有职业技能", targetName));
            MessageManager.Message(target, "§e你的所有职业技能已冷却");
            return true;
        }
        MessageManager.Message(player, "§e用法: §6/occskill §b<list|cooldown> [player]");
        return true;
    }

    public boolean displaySkillList(Player player, Player target) {
        List<String> skillIDs = Occ.getPlayerSkills(target);
        List<String> active = new ArrayList<>();
        List<String> passive = new ArrayList<>();
        for (String skill : skillIDs) {
            if (skills.getBoolean(String.format("%s.isPassive", skill))) {
                passive.add(skill);
                continue;
            }
            active.add(skill);
        }
        MessageManager.Message(player, "§e    主动技能:");
        showSkills(player, active);
        MessageManager.Message(player, "§e    被动技能:");
        showSkills(player, passive);
        return true;
    }

    public static void showSkills(Player player, List<String> skillList) {
        if (skillList.size() == 0) {
            MessageManager.Message(player, "§e        无");
        } else {
            for (Object i : skillList) {
                String currentSkill = Occ.getSkillChineseById(i.toString());
                List<String> hoverLore = Occ.getHoverLore(i.toString());
                MessageManager.HoverMessage(player, String.format("§c        %s", currentSkill), hoverLore);
            }
        }
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
