package cn.miranda.MeowCraft.Command;

import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Utils.Misc;
import cn.miranda.MeowCraft.Utils.Occ;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static cn.miranda.MeowCraft.Manager.ConfigMaganer.skills;

public class EsotericaScrollCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("esotericascroll.admin")) {
            MessageManager.Messager(sender, "§c你没有权限");
            return true;
        }
        if (args.length != 2) {
            MessageManager.Messager(sender, "§e用法: §6/esotericascroll §b<player> <skill>");
            return true;
        }
        Player target = Misc.player(args[0]);
        if (target == null) {
            MessageManager.Messager(sender, "§c指定玩家不在线");
            return true;
        }
        String targetName = target.getName();
        String skillChineseName = args[1];
        List skillList = Occ.skillList();
        if (!skillList.contains(skillChineseName)) {
            MessageManager.Messager(sender, "§c技能不存在");
            return true;
        }
        if (Misc.isInventoryFull(target)) {
            MessageManager.Messager(sender, "§c你的背包满了");
            return true;
        }
        getEsotericaScroll(target, Occ.getSkillID(skillChineseName), Occ.getSkillLore(skillChineseName));
        return true;
    }

    private static void getEsotericaScroll(Player player, String type, List lore) {
        ItemStack esotericaScroll = new ItemStack(Material.PAPER, 1);
        ItemMeta scrollMeta = esotericaScroll.getItemMeta();
        scrollMeta.setDisplayName("§9秘传之书");
        ArrayList<String> loreList = new ArrayList<>();
        loreList.add(String.format("§3可习得技能 §c%s", skills.getString(String.format("%s.name", type))));
        loreList.add("");
        for (Object s : lore) {
            loreList.add(s.toString());
        }
        scrollMeta.setLore(loreList);
        esotericaScroll.setItemMeta(scrollMeta);
        player.getInventory().addItem(esotericaScroll);
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1)
            return Misc.getOnlinePlayerMames();
        if (strings.length == 2) {
            return Occ.skillList();
        }
        return new ArrayList();
    }
}
