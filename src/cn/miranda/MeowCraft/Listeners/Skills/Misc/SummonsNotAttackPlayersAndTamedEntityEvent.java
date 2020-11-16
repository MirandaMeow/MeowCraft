package cn.miranda.MeowCraft.Listeners.Skills.Misc;

import cn.miranda.MeowCraft.Utils.Misc;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static cn.miranda.MeowCraft.Utils.SkillLib.summons;

public class SummonsNotAttackPlayersAndTamedEntityEvent implements Listener {
    public static LivingEntity summonTarget(Entity entity, Entity player) {
        List<Entity> entityList = player.getNearbyEntities(20, 20, 20);
        TreeMap<Double, LivingEntity> livingEntity = new TreeMap<>();
        for (Entity e : entityList) {
            if (!(e instanceof Monster)) {
                continue;
            }
            if (!e.equals(entity) && (!(e instanceof Player)) && (!Misc.getAllSummons().contains(e))) {
                livingEntity.put(entity.getLocation().distance(e.getLocation()), ((LivingEntity) e));
            }
        }
        if (livingEntity.size() == 0) {
            return null;
        }
        return livingEntity.firstEntry().getValue();
    }

    @EventHandler(priority = EventPriority.NORMAL)
    private void SummonsNotAttackPlayersAndTamedEntity(EntityTargetLivingEntityEvent event) {
        Entity entity = event.getEntity();
        Entity player = event.getTarget();
        if (player == null) {
            return;
        }
        if (!Misc.getAllSummons().contains(entity)) {
            return;
        }
        for (Map.Entry<Player, List<Entity>> entry : summons.entrySet()) {
            if (entry.getValue().contains(entity)) {
                player = entry.getKey();
                break;
            }
        }
        LivingEntity target = summonTarget(entity, player);
        if (target == null) {
            event.setCancelled(true);
            return;
        }
        event.setTarget(target);
        ((LivingEntity) entity).attack(target);
    }
}
