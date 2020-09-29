package cn.miranda.MeowCraft.Listeners;

import cn.miranda.MeowCraft.MeowCraft;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static cn.miranda.MeowCraft.Manager.ConfigManager.config;

public class BookToItemEvent implements Listener {
    private static int getCount(AnvilInventory inventory) {
        int count = 0;
        if (inventory.getItem(0) != null) {
            count += 1;
        }
        if (inventory.getItem(1) != null) {
            count += 1;
        }
        return count;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    private void BookToItem(PrepareAnvilEvent event) {
        AnvilInventory anvilInventory = event.getInventory();
        ItemStack preItem = anvilInventory.getItem(0);
        ItemStack bookItem = anvilInventory.getItem(1);
        if (getCount(anvilInventory) < 2) {
            return;
        }
        if (preItem.getType().equals(Material.WRITABLE_BOOK)) {
            return;
        }
        if (!bookItem.getType().equals(Material.WRITABLE_BOOK)) {
            return;
        }
        if (preItem.getItemMeta() == null) {
            return;
        } else if (preItem.getItemMeta().hasLore()) {
            return;
        }
        BookMeta bookMeta = (BookMeta) bookItem.getItemMeta();
        if (!bookMeta.hasPages()) {
            return;
        }
        int cost = config.getInt("AnvilLore.cost", 5);
        List<String> loreList_temp = bookMeta.getPages();
        List<String> loreList = new ArrayList<>();
        for (String s : loreList_temp) {
            loreList.add("§7" + s.replace("\n", "").replace("&", "§"));
        }
        ItemStack resultItem = preItem.clone();
        ItemMeta itemMeta = resultItem.getItemMeta();
        itemMeta.setLore(loreList);
        resultItem.setItemMeta(itemMeta);
        event.setResult(resultItem);
        Bukkit.getServer().getScheduler().runTask(MeowCraft.plugin, () -> {
            anvilInventory.setRepairCost(cost);
            for (HumanEntity humanEntity : event.getInventory().getViewers()) {
                if (humanEntity instanceof Player) {
                    humanEntity.setWindowProperty(InventoryView.Property.REPAIR_COST, cost);
                }
            }
        });
    }
}

