package cn.miranda.MeowCraft.Listeners.Skills.Active;

import cn.miranda.MeowCraft.Manager.ConfigManager;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Task.OccSkillsCoolDownTask;
import cn.miranda.MeowCraft.Utils.Effect;
import cn.miranda.MeowCraft.Utils.Occ;
import cn.miranda.MeowCraft.Utils.SkillLib;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import static cn.miranda.MeowCraft.Manager.ConfigManager.*;

public class SelfExplodeEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void SelfExplode(PlayerInteractEvent event) {
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
            if (temp.get(String.format("OccSkillCoolDown.%s.All_SelfExplode", playerName)) != null) {
                MessageManager.Message(player, String.format("§c§l自爆§r§e冷却尚未结束, §e剩余 §b%s §e秒", temp.getInt(String.format("OccSkillCoolDown.%s.All_SelfExplode", playerName))));
                return;
            }
            if (!Occ.requireItem(player, Material.GUNPOWDER, skills.getInt("All_SelfExplode.cost", 64))) {
                MessageManager.Message(player, "§c火药不足");
                return;
            }
            MessageManager.Message(player, "§c§l自爆§r§e发动!");
            SkillLib.SelfExplode(player);
            Effect.activeSkillEffect(player);
            int coolDown = skills.getInt("All_SelfExplode.cooldown", 300);
            temp.set(String.format("OccSkillCoolDown.%s.All_SelfExplode", playerName), coolDown);
            int delay = skills.getInt("All_SelfExplode.delay", 10);
            temp.set(String.format("OccSkillCoolDown.%s.temp.selfExplode", playerName), delay);
            temp.set(String.format("OccSkillCoolDown.%s.temp.selfExplodeCancel", playerName), false);
            new OccSkillsCoolDownTask().OccSkillsCoolDown(player, "All_SelfExplode");
            ConfigManager.saveConfigs();
            event.setCancelled(true);
        }
    }
}
