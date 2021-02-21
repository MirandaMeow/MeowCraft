package cn.miranda.MeowCraft.Command;

import cn.miranda.MeowCraft.Enum.EntityDrop;
import cn.miranda.MeowCraft.Enum.Notify;
import cn.miranda.MeowCraft.Manager.ConfigManager;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Utils.ItemDropTable;
import cn.miranda.MeowCraft.Utils.Misc;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class EntityDropCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            MessageManager.Message(sender, Notify.No_Console.getString());
            return true;
        }
        if (!sender.hasPermission("entitydrop.admin")) {
            MessageManager.Message(sender, Notify.No_Permission.getString());
            return true;
        }
        Player player = (Player) sender;
        if (args.length < 2) {
            MessageManager.Message(player, Notify.Invalid_Input_Length.getString());
            return true;
        }
        String type = args[0];
        EntityDrop entityDrop = EntityDrop.getByName(type);
        if (entityDrop == null) {
            MessageManager.Message(player, "§c怪物不存在");
            return true;
        }
        String option = args[1];
        if (!Objects.equals(option, "add") && !Objects.equals(option, "remove") && !Objects.equals(option, "list")) {
            MessageManager.Message(player, Notify.Invalid_Input.getString());
            return true;
        }
        if (Objects.equals(option, "add") && args.length == 3) {
            String chanceString = args[2];
            if (!Misc.isInt(chanceString)) {
                MessageManager.Message(player, Notify.Invalid_Input.getString());
                return true;
            }
            int chance = Integer.parseInt(chanceString);
            if (chance < 0 || chance > 10000) {
                MessageManager.Message(player, "§c掉率范围是 1 - 10000");
                return true;
            }
            if (entityDrop.getItemDropTable().count() == 54) {
                MessageManager.Message(player, "§e掉落列表已满");
                return true;
            }
            ItemDropTable itemDropTable = entityDrop.getItemDropTable();
            ItemStack itemStack = player.getInventory().getItemInMainHand().clone();
            itemDropTable.add(itemStack, chance);
            entityDrop.setData(itemDropTable);
            ConfigManager.saveDropTable();
            ConfigManager.saveConfigs();
            MessageManager.Message(player, String.format("§e生物 §b%s §e的掉落列表中已添加手持物品, §e掉率为 §b%s%%", type, Misc.div(chance, 100)));
            return true;
        }
        if (Objects.equals(option, "list") && args.length == 2) {
            player.openInventory(getView(entityDrop, true));
            return true;
        }
        if (Objects.equals(option, "remove") && args.length == 2) {
            player.openInventory(getView(entityDrop, false));
            return true;
        }
        MessageManager.Message(player, Notify.Invalid_Input.getString());
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            return Misc.returnSelectList(EntityDrop.getList(), strings[0]);
        }
        if (strings.length == 2) {
            return Misc.returnSelectList(new ArrayList<>(Arrays.asList("add", "list", "remove")), strings[1]);
        }
        return new ArrayList<>();
    }

    private Inventory getView(EntityDrop entityDrop, boolean isView) {
        Inventory inventory;
        if (isView) {
            inventory = Bukkit.createInventory(null, 54, String.format("§9查看掉落物品 - §4%s", entityDrop.getName()));
            ItemStack[] itemStacks = entityDrop.getItems();
            for (ItemStack itemStack : itemStacks) {
                ArrayList<String> lores;
                if (itemStack.getItemMeta().hasLore()) {
                    lores = (ArrayList<String>) itemStack.getItemMeta().getLore();
                } else {
                    lores = new ArrayList<>();
                }
                lores.add(String.format("§9掉率: %s%%", Misc.div(entityDrop.getItemDropTable().getData().get(itemStack), 100)));
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setLore(lores);
                itemStack.setItemMeta(itemMeta);
            }
            inventory.setContents(itemStacks);
            ConfigManager.loadDropTable();
        } else {
            inventory = Bukkit.createInventory(null, 54, String.format("§9删除掉落物品 - §4%s", entityDrop.getName()));
            ItemStack[] itemStacks = entityDrop.getItems();
            inventory.setContents(itemStacks);
        }

        return inventory;
    }
}
