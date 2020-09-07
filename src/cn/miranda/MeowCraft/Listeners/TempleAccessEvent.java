package cn.miranda.MeowCraft.Listeners;

import cn.miranda.MeowCraft.Manager.ConfigMaganer;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Manager.TempleManager;
import cn.miranda.MeowCraft.Utils.Misc;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import static cn.miranda.MeowCraft.Manager.ConfigMaganer.playerData;
import static cn.miranda.MeowCraft.Manager.ConfigMaganer.temples;


public class TempleAccessEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void AccessTempleEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getHand() == EquipmentSlot.HAND) {
            Location blockLocation = event.getClickedBlock().getLocation();
            Location fixedLocation = Misc.getFixedLocation(blockLocation);
            if (!TempleManager.TempleList.contains(fixedLocation)) {
                return;
            }
            String templeName = TempleManager.giveTempleName(fixedLocation);
            if (playerData.getBoolean(String.format("%s.temples.%s", playerName, TempleManager.giveTempleName(fixedLocation)))) {
                MessageManager.Messager(player, String.format("§e%s", temples.getString(String.format("%s.denyMessage", templeName))));
                return;
            }
            String title = temples.getString(String.format("%s.title", templeName));
            executeTempleEffect(player, title);
            player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, fixedLocation, 100, 0, 2, 0);
            playerData.set(String.format("%s.temples.%s", playerName, templeName), true);
            ConfigMaganer.saveConfigs();
            MessageManager.Messager(player, String.format("§e%s", temples.getString(String.format("%s.accessMessage", templeName))));
        }
    }

    private void executeTempleEffect(Player player, String title) {
        int setPlayerMaxHealth = (int) player.getMaxHealth() + 1;
        player.setMaxHealth(setPlayerMaxHealth);
        player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 40, 0, 2, 0);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 10);
        player.sendTitle(title, "", 10, 70, 20);
    }

}
