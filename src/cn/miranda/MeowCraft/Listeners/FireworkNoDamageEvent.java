package cn.miranda.MeowCraft.Listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class FireworkNoDamageEvent implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    private void FireworkNoDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager().getType() == EntityType.FIREWORK) {
            event.setCancelled(true);
        }
    }
}
