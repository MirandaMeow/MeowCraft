package cn.miranda.MeowCraft.Listeners.Skills.Misc;

import cn.miranda.MeowCraft.Utils.Misc;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;

public class SummonsNoFireEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void SummonsNoFire(EntityCombustEvent event) {
        if (!Misc.getAllSummons().contains(event.getEntity())) {
            return;
        }
        event.setCancelled(true);
    }
}
