package cn.miranda.MeowCraft.Listeners.Skills.Misc;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.List;
import java.util.Map;

import static cn.miranda.MeowCraft.Utils.SkillLib.summons;

public class SummonDeadEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void SummonDead(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        for (Map.Entry<Player, List<Entity>> entry : summons.entrySet()) {
            if (entry.getValue().contains(entity)) {
                entry.getValue().remove(entity);
                event.setDroppedExp(0);
                event.getDrops().clear();
            }
            if (entry.getValue().size() == 0) {
                summons.remove(entry.getKey());
            }
        }
    }
}
