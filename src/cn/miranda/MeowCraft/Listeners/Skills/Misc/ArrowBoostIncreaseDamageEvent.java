package cn.miranda.MeowCraft.Listeners.Skills.Misc;

import cn.miranda.MeowCraft.Utils.SkillLib;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import static cn.miranda.MeowCraft.Manager.ConfigManager.skills;

public class ArrowBoostIncreaseDamageEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void ArrowBoostIncreaseDamage(EntityDamageByEntityEvent event) {
        if (SkillLib.arrowIDs == null) {
            return;
        }
        Entity entity = event.getDamager();
        if (entity.getType() != EntityType.ARROW) {
            return;
        }
        if (!SkillLib.arrowIDs.contains(entity.getEntityId())) {
            return;
        }
        int damage = skills.getInt("Ranger_ArrowBoost.damage", 2);
        event.setDamage(damage);
    }
}
