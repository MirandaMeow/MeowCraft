package cn.miranda.MeowCraft.Command;

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

public final class ImprintCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        int amount;
        Player target;
        if (!sender.hasPermission("imprint.admin")) {
            MessageManager.Message(sender, "§c你没有权限");
            return true;
        }
        if (args.length > 2 || args.length == 0) {
            MessageManager.Message(sender, "§e用法 §6/imprint §b[player] <amount>");
            return true;
        }
        if (args.length == 1) {
            if (!(sender instanceof Player)) {
                MessageManager.Message(sender, "§c不能对控制台使用");
                return true;
            }
            target = (Player) sender;
            if (!Misc.isInt(args[0])) {
                MessageManager.Message(sender, "§c参数不正确");
                return true;
            }
            amount = Integer.parseInt(args[0]);
        } else {
            String playerInputPlayerName = args[0];
            target = Misc.player(playerInputPlayerName);
            if (target == null) {
                MessageManager.Message(sender, "§c玩家不存在");
                return true;
            }
            if (!Misc.isInt(args[1])) {
                MessageManager.Message(sender, "§c参数不正确");
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
        scrollMeta.setDisplayName("§9烙印卷轴");
        ArrayList<String> loreList = new ArrayList<>();
        loreList.add("§3空白的烙印卷轴");
        loreList.add("");
        loreList.add("§6shift + 右击地面以缔结烙印");
        loreList.add("");
        loreList.add("§7这种卷轴能够依据使用者对当时所在地的");
        loreList.add("§7强烈印象留下烙印, 当再次打开它时, ");
        loreList.add("§7使用者将被吸入裂隙回到印记之处, ");
        loreList.add("§7但偶尔会有人使用后不知所踪");
        scrollMeta.setLore(loreList);
        scroll.setItemMeta(scrollMeta);
        target.getInventory().addItem(scroll);
        MessageManager.Message(target, String.format("§e收到 §b%d §e个烙印卷轴", amount));
        return true;
    }
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            return Misc.getOnlinePlayerNames();
        }
        return new ArrayList<>();
    }
}
