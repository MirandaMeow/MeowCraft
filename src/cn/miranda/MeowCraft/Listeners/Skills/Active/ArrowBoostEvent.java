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
        if (!player.hasPermission("occ.bypass")) {
            if (!Occ.isFitOcc(player, "Ranger_ArrowBoost") || !playerData.getBoolean(String.format("%s.occConfig.enabled", playerName), true)) {
                return;
            }
        }
        if (player.isSneaking() && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && player.getInventory().getItemInMainHand().getType() == Material.BOW && event.getHand() == EquipmentSlot.HAND) {
            if (playerData.get(String.format("%s.occConfig.occSkills.Ranger_ArrowBoost", playerName)) == null) {
                return;
            }
            if (temp.get(String.format("OccSkillCoolDown.%s.Ranger_ArrowBoost", playerName)) != null) {
                MessageManager.ActionBarMessage(player, String.format("§c§l箭矢爆发§r§e冷却尚未结束  §e剩余 §b%s §e秒", temp.getInt(String.format("OccSkillCoolDown.%s.Ranger_ArrowBoost", playerName))));
                return;
            }
            if (!Occ.requireItem(player, Material.ARROW, skills.getInt("Ranger_ArrowBoost.cost", 25))) {
                MessageManager.ActionBarMessage(player, "§c箭不足");
                return;
            }
            MessageManager.ActionBarMessage(player, "§c§l箭矢爆发§r§e发动!");
            SkillLib.ArrowBoost(player);
            Effect.activeSkillEffect(player);
            int coolDown = skills.getInt("Ranger_ArrowBoost.cooldown", 30);
            temp.set(String.format("OccSkillCoolDown.%s.Ranger_ArrowBoost", playerName), coolDown);
            new OccSkillsCoolDownTask().OccSkillsCoolDown(player, "Ranger_ArrowBoost");
            ConfigManager.saveConfigs();
        }
    }
}
