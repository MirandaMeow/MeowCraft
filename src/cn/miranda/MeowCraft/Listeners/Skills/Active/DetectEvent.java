package cn.miranda.MeowCraft.Listeners.Skills.Active;

import cn.miranda.MeowCraft.Manager.ConfigManager;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Task.OccSkillsCoolDownTask;
import cn.miranda.MeowCraft.Utils.Effect;
import cn.miranda.MeowCraft.Utils.Occ;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import static cn.miranda.MeowCraft.Manager.ConfigManager.*;

public class DetectEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void DetectEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        if (!player.hasPermission("occ.bypass")) {
            if (!Occ.isFitOcc(player, "Artisan_Detect") || !playerData.getBoolean(String.format("%s.occConfig.enabled", playerName), true)) {
                return;
            }
        }
        if (player.isSneaking() && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && player.getInventory().getItemInMainHand().getType() == Material.COMPASS && event.getHand() == EquipmentSlot.HAND) {
            if (playerData.get(String.format("%s.occConfig.occSkills.Artisan_Detect", playerName)) == null) {
                return;
            }
            if (temp.get(String.format("OccSkillCoolDown.%s.Artisan_Detect", playerName)) != null) {
                MessageManager.Messager(player, String.format("§c§l地质勘测§r§e冷却尚未结束, §e剩余 §b%s §e秒", temp.getInt(String.format("OccSkillCoolDown.%s.Artisan_Detect", playerName))));
                return;
            }
            MessageManager.Messager(player, "§c§l地质勘测§r§e发动!");
            activeDetect(player);
            Effect.activeSkillEffect(player);
            int coolDown = skills.getInt("Artisan_Detect.cooldown", 30);
            temp.set(String.format("OccSkillCoolDown.%s.Artisan_Detect", playerName), coolDown);
            new OccSkillsCoolDownTask().OccSkillsCoolDown(player, "Artisan_Detect");
            ConfigManager.saveConfigs();
        }
    }

    private void activeDetect(Player player) {
        int settings_radius = skills.getInt("Artisan_Detect.radius", 20);
        Location playerLocation = player.getLocation();
        int playerX = (int) Math.round(playerLocation.getX());
        int playerY = (int) Math.round(playerLocation.getY());
        int playerZ = (int) Math.round(playerLocation.getZ());
        for (int currentRadius = 1; currentRadius <= settings_radius; currentRadius++) {
            for (int x = -(2 * currentRadius - 1); x <= (2 * currentRadius - 1); x++) {
                for (int z = -(2 * currentRadius - 1); z <= (2 * currentRadius - 1); z++) {
                    int currentX = playerX + x;
                    int currentZ = playerZ + z;
                    Location currentLocation = new Location(player.getWorld(), currentX, playerY, currentZ);
                    if (currentLocation.getBlock().getType().equals(Material.DIAMOND_ORE)) {
                        player.setCompassTarget(currentLocation);
                        MessageManager.Messager(player, String.format("§e当前层离你最近的钻石矿位于 §b(%d %d %d)", (int) currentLocation.getX(), (int) currentLocation.getY(), (int) currentLocation.getZ()));
                        return;
                    }
                }
            }
        }
        MessageManager.Messager(player, String.format("§e当前层 §b%d §e范围内没有钻石矿", settings_radius));
    }
}
