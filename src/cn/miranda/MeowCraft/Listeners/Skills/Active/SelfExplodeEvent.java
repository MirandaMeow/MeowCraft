package cn.miranda.MeowCraft.Listeners.Skills.Active;

import cn.miranda.MeowCraft.Manager.ConfigMaganer;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Task.OccSkillsCoolDownTask;
import cn.miranda.MeowCraft.Task.SelfExplodeTask;
import cn.miranda.MeowCraft.Utils.Effect;
import cn.miranda.MeowCraft.Utils.Occ;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import static cn.miranda.MeowCraft.Manager.ConfigMaganer.config;
import static cn.miranda.MeowCraft.Manager.ConfigMaganer.playerData;

public class SelfExplodeEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void SelfExplodeEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        if (!player.hasPermission("occ.bypass")) {
            if (!Occ.isFitOcc(player, "All_SelfExplode") || !playerData.getBoolean(String.format("%s.occConfig.enabled", playerName), true)) {
                return;
            }
        }
        if (player.isSneaking() && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && player.getInventory().getItemInOffHand().getType().equals(Material.TORCH) && player.getInventory().getItemInMainHand().getType().equals(Material.GUNPOWDER) && event.getHand() == EquipmentSlot.HAND) {
            if (playerData.get(String.format("%s.occConfig.occSkills.All_SelfExplode", playerName)) == null) {
                return;
            }
            if (playerData.get(String.format("%s.occConfig.occSkills.occCoolDown.All_SelfExplode", playerName)) != null) {
                MessageManager.Messager(player, String.format("§c§l自爆§r§e冷却尚未结束, §e剩余 §b%s §e秒", playerData.getInt(String.format("%s.occConfig.occSkills.occCoolDown.All_SelfExplode", playerName))));
                return;
            }
            if (!Occ.requireItem(player, Material.GUNPOWDER, config.getInt("OccSkillConfig.All_SelfExplode.cost", 64))) {
                MessageManager.Messager(player, "§c火药不足");
                return;
            }
            MessageManager.Messager(player, "§c§l自爆§r§e发动!");
            activeSelfExplode(player);
            Effect.activeSkillEffect(player);
            int coolDown = config.getInt("OccSkillConfig.All_SelfExplode.cooldown", 300);
            playerData.set(String.format("%s.occConfig.occSkills.occCoolDown.All_SelfExplode", playerName), coolDown);
            int delay = config.getInt("OccSkillConfig.All_SelfExplode.delay", 10);
            playerData.set(String.format("%s.temp.selfExplode", playerName), delay);
            playerData.set(String.format("%s.temp.selfExplodeCancel", playerName), false);
            new OccSkillsCoolDownTask().OccSkillsCoolDown(player, "All_SelfExplode");
            ConfigMaganer.saveConfigs();
            event.setCancelled(true);
        }
    }

    private void activeSelfExplode(Player player) {
        int intensity = config.getInt("OccSkillConfig.All_SelfExplode.intensity", 5);
        new SelfExplodeTask().SelfExplode(player, intensity);
    }
}
