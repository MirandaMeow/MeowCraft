package cn.miranda.MeowCraft.Command;

import cn.miranda.MeowCraft.Enum.EggCatcher;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class EggTokenCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            MessageManager.Message(sender, Notify.No_Console.getString());
            return true;
        }

        if (!sender.hasPermission("eggtoken.admin")) {
            MessageManager.Message(sender, Notify.No_Permission.getString());
            return true;
        }
        Player player = (Player) sender;
        if (args.length != 2) {
            MessageManager.Message(player, Notify.Invalid_Input_Length.getString());
            return true;
        }
        Player target = Misc.player(args[0]);
        if (target == null) {
            MessageManager.Message(player, Notify.No_Player.getString());
            return true;
        }
        String type = args[1];
        EggCatcher eggCatcher = EggCatcher.getByName(type);
        if (eggCatcher == null) {
            MessageManager.Message(player, "§c该怪物不存在");
            return true;
        }
        HashMap<Integer, ItemStack> fail = target.getInventory().addItem(getItem(eggCatcher));
        if (fail.isEmpty()) {
            MessageManager.Message(player, String.format("§e将怪物 §b%s §e的捕捉许可证给了 §b%s", eggCatcher.getName(), target.getName()));
            MessageManager.Message(target, String.format("§e收到了怪物 §b%s §e的捕捉许可证", eggCatcher.getName()));
            return true;
        } else {
            MessageManager.Message(player, String.format("§c玩家 §b%s §c的背包满了", target.getName()));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            return Misc.returnSelectList(Misc.getOnlinePlayerNames(), strings[0]);
        }
        if (strings.length == 2) {
            return Misc.returnSelectList(EggCatcher.getList(), strings[1]);
        }
        return new ArrayList<>();
    }

    private ItemStack getItem(EggCatcher eggCatcher) {
        ItemStack itemStack = new ItemStack(Material.PAPER);
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName("§9怪物捕捉许可证");
        ArrayList<String> loreList = new ArrayList<>();
        loreList.add(String.format("§e可以捕捉 §b%s", eggCatcher.getName()));
        loreList.add("");
        loreList.add("§6shift + 右击以使用许可证");
        loreList.add("");
        loreList.add("§7使用后可以使用雪球捕捉指定的怪物");
        itemMeta.setLore(loreList);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
