package cn.miranda.MeowCraft.Listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

import static cn.miranda.MeowCraft.Manager.ConfigManager.monsterCard;
import static cn.miranda.MeowCraft.Manager.ConfigManager.playerData;

public class MonsterCardPlayerMilkEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void MonsterCardPlayerMilk(EntityPotionEffectEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof Player)) {
            return;
        }
        if (event.getCause() != EntityPotionEffectEvent.Cause.MILK) {
            return;
        }
        Player player = ((Player) entity);
        String playerName = player.getName();
        if (playerData.get(String.format("%s.monsterCard", playerName)) == null) {
            return;
        }
        String cardType = playerData.getString(String.format("%s.monsterCard.type", playerName));
        List<PotionEffectType> potionEffectTypes = new ArrayList<>();
        for (String current: monsterCard.getConfigurationSection(String.format("%s.effects", cardType)).getValues(false).keySet()) {
            potionEffectTypes.add(PotionEffectType.getByName(current));
        }
        if (potionEffectTypes.contains(event.getOldEffect().getType())) {
            event.setCancelled(true);
        }
    }
}
