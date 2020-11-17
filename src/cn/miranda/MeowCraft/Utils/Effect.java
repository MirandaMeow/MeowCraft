package cn.miranda.MeowCraft.Utils;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Effect {
    public static void activeSkillEffect(Player player) {
        World playerWorld = player.getWorld();
        Location playerLocation = player.getLocation();
        playerWorld.spawnParticle(Particle.EXPLOSION_LARGE, playerLocation, 1);
        playerWorld.playSound(playerLocation, Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 10, 10);
    }

    public static void useEsotericaScroll(Player player) {
        World playerWorld = player.getWorld();
        Location playerLocation = player.getLocation();
        playerWorld.spawnParticle(Particle.WATER_DROP, playerLocation.add(0, 2, 0), 1);
        playerWorld.playSound(playerLocation, Sound.ENTITY_ITEM_PICKUP, 10, 10);
    }

    public static void eggCatchDropItem(Entity target) {
        World targetWorld = target.getWorld();
        Location targetLocation = target.getLocation();
        targetWorld.spawnParticle(Particle.EXPLOSION_LARGE, targetLocation, 1);
        targetWorld.playSound(targetLocation, Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST, 10, 10);
    }

    //TODO: 增加怪物卡片效果
    public static void collectMonster(Entity target) {
        World targetWorld = target.getWorld();
        Location targetLocation = target.getLocation();
        targetWorld.spawnParticle(Particle.VILLAGER_ANGRY, targetLocation.add(0, 2, 0), 1);
        targetWorld.playSound(targetLocation, Sound.BLOCK_GRASS_BREAK, 10, 10);
    }

    public static void finishedCollect(Player player) {
        World playerWorld = player.getWorld();
        Location playerLocation = player.getLocation();
        playerWorld.spawnParticle(Particle.VILLAGER_HAPPY, playerLocation.add(0, 2, 0), 5);
        playerWorld.playSound(playerLocation, Sound.ENTITY_ENDERMAN_TELEPORT, 10, 10);
    }

    public static void useMonsterCard(Player player) {
        World playerWorld = player.getWorld();
        Location playerLocation = player.getLocation();
        playerWorld.spawnParticle(Particle.EXPLOSION_LARGE, playerLocation.add(0,1,0), 1);
        playerWorld.playSound(playerLocation, Sound.BLOCK_END_PORTAL_SPAWN, 10, 10);
    }

    public static void monsterCardTimeUp(Player player) {
        World playerWorld = player.getWorld();
        Location playerLocation = player.getLocation();
        playerWorld.spawnParticle(Particle.EXPLOSION_LARGE, playerLocation.add(0,1,0), 1);
        playerWorld.playSound(playerLocation, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 10, 10);
    }
}
