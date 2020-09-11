package cn.miranda.MeowCraft.Listeners;

import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Manager.TempleManager;
import cn.miranda.MeowCraft.Task.TempleAccessTask;
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

import static cn.miranda.MeowCraft.Manager.ConfigManager.playerData;
import static cn.miranda.MeowCraft.Manager.ConfigManager.temples;


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
                MessageManager.Message(player, temples.getString(String.format("%s.denyMessage", templeName)));
                return;
            }
            player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, fixedLocation, 100, 0, 2, 0);
            player.getWorld().playSound(fixedLocation, Sound.ENTITY_WITHER_SPAWN, 10, 10);
            new TempleAccessTask().TempleAccess(player, templeName);
        }
    }
}
