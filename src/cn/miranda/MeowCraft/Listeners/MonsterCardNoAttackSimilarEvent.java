package cn.miranda.MeowCraft.Listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

import static cn.miranda.MeowCraft.Manager.ConfigManager.playerData;

public class MonsterCardNoAttackSimilarEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void MonsterCardNoAttackSimilar(EntityTargetLivingEntityEvent event) {
        Entity entity = event.getEntity();
        Entity target = event.getTarget();
        if (!(target instanceof Player)) {
            return;
        }
        Player player = ((Player) target);
        String playerName = player.getName();
        if (playerData.get(String.format("%s.monsterCard", playerName)) == null) {
            return;
        }
        EntityType entityType = EntityType.valueOf(playerData.getString(String.format("%s.monsterCard.type", playerName)));
        if (!entity.getType().equals(entityType)) {
            return;
        }
        event.setCancelled(true);
    }
}
