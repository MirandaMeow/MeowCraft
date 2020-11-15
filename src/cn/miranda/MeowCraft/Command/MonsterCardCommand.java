package cn.miranda.MeowCraft.Command;

import cn.miranda.MeowCraft.Enum.Notify;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Utils.Misc;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public final class MonsterCardCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        int amount;
        Player target;
        if (!sender.hasPermission("monstercard.admin")) {
            MessageManager.Message(sender, Notify.No_Permission.getString());
            return true;
        }
        if (args.length > 2 || args.length == 0) {
            MessageManager.Message(sender, "§e用法 §6/monstercard §b[player] <amount>");
            return true;
        }
        if (args.length == 1) {
            if (!(sender instanceof Player)) {
                MessageManager.Message(sender, Notify.Not_For_Console.getString());
                return true;
            }
            target = (Player) sender;
            if (!Misc.isInt(args[0])) {
                MessageManager.Message(sender, Notify.Invalid_Input.getString());
                return true;
            }
            amount = Integer.parseInt(args[0]);
        } else {
            String playerInputPlayerName = args[0];
            target = Misc.player(playerInputPlayerName);
            if (target == null) {
                MessageManager.Message(sender, Notify.No_Player.getString());
                return true;
            }
            if (!Misc.isInt(args[1])) {
                MessageManager.Message(sender, Notify.Invalid_Input.getString());
                return true;
            }
            amount = Integer.parseInt(args[1]);
        }
        if (Misc.isInventoryFull(target)) {
            if (args.length == 1) {
                MessageManager.Message(sender, "§c你的背包已满");
            } else {
                MessageManager.Message(sender, "§c对方的背包已满");
            }
            return true;
        }
        ItemStack scroll = new ItemStack(Material.PAPER, amount);
        ItemMeta scrollMeta = scroll.getItemMeta();
        assert scrollMeta != null;
        scrollMeta.setDisplayName("§9怪物卡片");
        ArrayList<String> loreList = new ArrayList<>();
        loreList.add("§3空白的怪物卡片");
        loreList.add("");
        loreList.add("§6shift + 右击怪物以拓印怪物的灵魂");
        loreList.add("");
        loreList.add("§7这张卡片可以拓印某些怪物的灵魂");
        loreList.add("§7集齐一定数量的怪物灵魂后");
        loreList.add("§7再次使用卡片可以让你在一定时间内");
        loreList.add("§7变成该怪物, 并获得一些它的能力");
        scrollMeta.setLore(loreList);
        scroll.setItemMeta(scrollMeta);
        target.getInventory().addItem(scroll);
        MessageManager.Message(target, String.format("§e收到 §b%d §e个怪物卡片", amount));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            return Misc.returnSelectList(Misc.getOnlinePlayerNames(), strings[0]);
        }
        return new ArrayList<>();
    }
}
