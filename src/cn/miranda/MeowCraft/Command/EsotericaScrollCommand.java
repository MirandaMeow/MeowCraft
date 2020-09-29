package cn.miranda.MeowCraft.Command;

import cn.miranda.MeowCraft.Enum.Notify;
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

import static cn.miranda.MeowCraft.Manager.ConfigManager.skills;

public class EsotericaScrollCommand implements TabExecutor {
    private static void getEsotericaScroll(Player player, String type, List<String> lore) {
        ItemStack esotericaScroll = new ItemStack(Material.PAPER, 1);
        ItemMeta scrollMeta = esotericaScroll.getItemMeta();
        assert scrollMeta != null;
        scrollMeta.setDisplayName("§9秘传之书");
        ArrayList<String> loreList = new ArrayList<>();
        loreList.add(String.format("§3可习得技能 §c%s", skills.getString(String.format("%s.name", type))));
        loreList.add("");
        loreList.addAll(lore);
        scrollMeta.setLore(loreList);
        esotericaScroll.setItemMeta(scrollMeta);
        player.getInventory().addItem(esotericaScroll);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("esotericascroll.admin")) {
            MessageManager.Message(sender, Notify.No_Permission.getString());
            return true;
        }
        if (args.length != 2) {
            MessageManager.Message(sender, "§e用法: §6/esotericascroll §b<player> <skill>");
            return true;
        }
        Player target = Misc.player(args[0]);
        if (target == null) {
            MessageManager.Message(sender, Notify.No_Player.getString());
            return true;
        }
        String skillChineseName = args[1];
        List<String> skillList = Occ.skillList();
        if (!skillList.contains(skillChineseName)) {
            MessageManager.Message(sender, "§c技能不存在");
            return true;
        }
        if (Misc.isInventoryFull(target)) {
            MessageManager.Message(sender, "§c你的背包满了");
            return true;
        }
        String skillID  = Occ.getSkillID(skillChineseName);
        getEsotericaScroll(target, skillID, Occ.getSkillLore(skillID));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1)
            return Misc.returnSelectList(Misc.getOnlinePlayerNames(), strings[0]);
        if (strings.length == 2) {
            return Misc.returnSelectList(Occ.skillList(), strings[1]);
        }
        return new ArrayList<>();
    }
}
