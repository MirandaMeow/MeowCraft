package cn.miranda.MeowCraft.Listeners.Skills.Misc;

import cn.miranda.MeowCraft.Listeners.Skills.Active.ArrowBoostEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ArrowBoostCancelNoDamageTicksEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void ArrowBoostCancelNoDamageTicks(ProjectileHitEvent event) {
        if (ArrowBoostEvent.arrowIDs == null) {
            return;
        }
        Entity entity = event.getEntity();
        if (entity.getType() != EntityType.ARROW) {
            return;
        }
        if (!ArrowBoostEvent.arrowIDs.contains(entity.getEntityId())) {
            return;
        }
        Entity target = event.getHitEntity();
        if (!(target instanceof LivingEntity)) {
            return;
        }
        ((LivingEntity) target).setNoDamageTicks(0);
    }
}
