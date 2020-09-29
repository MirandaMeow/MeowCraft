package cn.miranda.MeowCraft.Listeners;

import cn.miranda.MeowCraft.Utils.Misc;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Objects;

import static org.bukkit.event.block.Action.RIGHT_CLICK_AIR;
import static org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK;

public class ImprintActivateUseEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void ImprintActivateUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!player.isSneaking() && (event.getAction() == RIGHT_CLICK_BLOCK || event.getAction() == RIGHT_CLICK_AIR) && player.getInventory().getItemInMainHand().getType().equals(Material.PAPER) && event.getHand() == EquipmentSlot.HAND) {
            ItemMeta playerItemMeta = player.getInventory().getItemInMainHand().getItemMeta();
            if (playerItemMeta == null) {
                return;
            }
            if (playerItemMeta.getDisplayName().equals("§9烙印卷轴") && Objects.equals(playerItemMeta.getLore().get(0), "§3附有烙印的卷轴")) {
                player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
                List<String> locData = playerItemMeta.getLore().subList(3, 9);
                World World = Misc.World(locData.get(0).split(": ")[1]);
                double X = Double.parseDouble(locData.get(1).split(": ")[1]);
                double Y = Double.parseDouble(locData.get(2).split(": ")[1]);
                double Z = Double.parseDouble(locData.get(3).split(": ")[1]);
                float Yaw = Float.parseFloat(locData.get(4).split(": ")[1]);
                float Pitch = Float.parseFloat(locData.get(5).split(": ")[1]);
                Location Location = new Location(World, X, Y, Z, Yaw, Pitch);
                player.teleport(Location);
                World.playEffect(Location.add(0, 1, 0), Effect.ENDER_SIGNAL, null);
                World.playSound(Location, Sound.BLOCK_PORTAL_TRAVEL, 10, 10);
                player.sendTitle("§l§c烙 印 归 还", "", 10, 70, 20);
            }
        }
    }
}
