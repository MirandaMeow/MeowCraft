package cn.miranda.MeowCraft.Listeners.Skills.Misc;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Iterator;

import static cn.miranda.MeowCraft.Utils.SkillLib.summons;

public class PlayerUnavailableEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void PlayerDeadKillSummons(PlayerDeathEvent event) {
        Player player = event.getEntity();
        killSummon(player);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    private void PlayerDeadKillSummons(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        killSummon(player);
    }

    private void killSummon(Player player) {
        if (summons.get(player) == null) {
            return;
        }
        Iterator<Entity> killList = summons.get(player).iterator();
        while (killList.hasNext()) {
            ((LivingEntity) killList.next()).setHealth(0);
        }
        summons.remove(player);
    }
}