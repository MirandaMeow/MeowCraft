package cn.miranda.MeowCraft.Listeners;

import cn.miranda.MeowCraft.Enum.EntityDrop;
import cn.miranda.MeowCraft.Manager.ConfigManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class EntityDropItemViewEvent implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    private void EntityDropItemView(InventoryClickEvent event) {
        String title = event.getView().getTitle();
        if (title.contains("§9查看掉落物品 - §4")) {
            event.setCancelled(true);
            return;
        }
        if (title.contains("§9删除掉落物品 - §4")) {
            ItemStack clicked = event.getCurrentItem();
            EntityDrop entityDrop = EntityDrop.getByName(title.split("§4")[1]);
            if (entityDrop == null) {
                return;
            }
            entityDrop.getItemDropTable().remove(clicked);
            ConfigManager.saveDropTable();
            ConfigManager.saveConfigs();
            event.getClickedInventory().setContents(entityDrop.getItems());
            event.setCancelled(true);
        }
    }
}
