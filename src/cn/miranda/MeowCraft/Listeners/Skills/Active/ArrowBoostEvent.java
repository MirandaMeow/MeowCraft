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

public class ArrowBoostEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void ArrowBoost(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        String skillName = "Ranger_ArrowBoost";
        if (!player.hasPermission("occ.bypass")) {
            if (!Occ.isFitOcc(player, skillName) || !playerData.getBoolean(String.format("%s.occConfig.enabled", playerName), true)) {
                return;
            }
        }
        if (player.isSneaking() && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && player.getInventory().getItemInMainHand().getType() == Material.BOW && event.getHand() == EquipmentSlot.HAND) {
            if (playerData.get(String.format("%s.occConfig.occSkills.%s", playerName, skillName)) == null) {
                return;
            }
            if (cache.get(String.format("OccSkillCoolDown.%s.%s", playerName, skillName)) != null) {
                MessageManager.ActionBarMessage(player, String.format("§c§l箭矢爆发§r§e冷却尚未结束  §e剩余 §b%s §e秒", cache.getInt(String.format("OccSkillCoolDown.%s.%s", playerName, skillName))));
                return;
            }
            if (!Occ.requireItem(player, Material.ARROW, skills.getInt(String.format("%s.cost", skillName), 25))) {
                MessageManager.ActionBarMessage(player, "§c箭不足");
                return;
            }
            int settings_range = skills.getInt(String.format("%s.range", skillName), 25);
            int settings_per_wave_amount = skills.getInt(String.format("%s.per_wave_amount", skillName), 5);
            int settings_wave = skills.getInt(String.format("%s.wave", skillName), 5);
            long settings_interval = skills.getLong(String.format("%s.interval", skillName), 5);
            MessageManager.ActionBarMessage(player, "§c§l箭矢爆发§r§e发动!");
            SkillLib.ArrowBoost(player, settings_range, settings_per_wave_amount, settings_wave, settings_interval);
            Effect.activeSkillEffect(player);
            int coolDown = skills.getInt(String.format("%s.cooldown", skillName), 30);
            cache.set(String.format("OccSkillCoolDown.%s.%s", playerName, skillName), coolDown);
            new OccSkillsCoolDownTask().OccSkillsCoolDown(player, skillName);
            ConfigManager.saveConfigs();
        }
    }
}
