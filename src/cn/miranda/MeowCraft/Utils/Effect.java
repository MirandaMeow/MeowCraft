package cn.miranda.MeowCraft.Utils;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Effect {
    public static void activeSkillEffect(Player player) {
        player.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, player.getLocation(), 1);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 10, 10);
    }

    public static void useEsotericaScroll(Player player) {
        player.getWorld().spawnParticle(Particle.WATER_DROP, player.getLocation().add(0, 2, 0), 1);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 10, 10);
    }

    public static void eggCatchDropItem(Entity target) {
        World playerWorld = target.getWorld();
        Location targetLocation = target.getLocation();
        playerWorld.spawnParticle(Particle.EXPLOSION_LARGE, targetLocation, 1);
        playerWorld.playSound(targetLocation, Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST, 10, 10);
    }
}
