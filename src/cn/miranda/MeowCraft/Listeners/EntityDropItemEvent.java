package cn.miranda.MeowCraft.Listeners;

import cn.miranda.MeowCraft.Enum.EntityDrop;
import cn.miranda.MeowCraft.Utils.ItemDropTable;
import cn.miranda.MeowCraft.Utils.Misc;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class EntityDropItemEvent implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    private void EntityDropItem(EntityDeathEvent event) {
        Entity victim = event.getEntity();
        Player killer = event.getEntity().getKiller();
        if (killer == null) {
            return;
        }
        ItemDropTable itemDropTable = EntityDrop.valueOf(victim.getType().name()).getItemDropTable();
        for (Map.Entry<ItemStack, Integer> entry : itemDropTable.getData().entrySet()) {
            if (Misc.randomNum(0, 10000) > entry.getValue()) {
                continue;
            }
            event.getDrops().add(entry.getKey());
        }
    }
}
