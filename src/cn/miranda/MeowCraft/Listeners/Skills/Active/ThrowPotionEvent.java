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

public class ThrowPotionEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void ThrowPotion(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        String skillName = "Voodoo_ThrowPotion";
        if (!player.hasPermission("occ.bypass")) {
            if (!Occ.isFitOcc(player, skillName) || !playerData.getBoolean(String.format("%s.occConfig.enabled", playerName), true)) {
                return;
            }
        }
        if (player.isSneaking() && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && (player.getInventory().getItemInMainHand().getType() == Material.MAGMA_CREAM || player.getInventory().getItemInMainHand().getType() == Material.GOLDEN_APPLE) && event.getHand() == EquipmentSlot.HAND) {
            if (playerData.get(String.format("%s.occConfig.occSkills.%s", playerName, skillName)) == null) {
                return;
            }
            if (cache.get(String.format("OccSkillCoolDown.%s.%s", playerName, skillName)) != null) {
                MessageManager.ActionBarMessage(player, String.format("§c§l魔药喷洒§r§e冷却尚未结束  §e剩余 §b%s §e秒", cache.getInt(String.format("OccSkillCoolDown.%s.%s", playerName, skillName))));
                return;
            }
            Material material = player.getInventory().getItemInMainHand().getType();
            switch (material) {
                case MAGMA_CREAM:
                    if (!Occ.requireItem(player, Material.MAGMA_CREAM, skills.getInt(String.format("%s.cost1", skillName), 10))) {
                        MessageManager.ActionBarMessage(player, "§c岩浆膏不足");
                        return;
                    }
                    SkillLib.ThrowPotions(player, Material.SPLASH_POTION);
                    break;
                case GOLDEN_APPLE:
                    if (!Occ.requireItem(player, Material.GOLDEN_APPLE, skills.getInt(String.format("%s.cost2", skillName), 1))) {
                        MessageManager.ActionBarMessage(player, "§c金苹果不足");
                        return;
                    }
                    SkillLib.ThrowPotions(player, Material.LINGERING_POTION);
                    break;
            }
            MessageManager.ActionBarMessage(player, "§c§l魔药喷洒§r§e发动!");
            Effect.activeSkillEffect(player);
            int coolDown = skills.getInt(String.format("%s.cooldown", skillName), 240);
            cache.set(String.format("OccSkillCoolDown.%s.%s", playerName, skillName), coolDown);
            new OccSkillsCoolDownTask().OccSkillsCoolDown(player, skillName);
            ConfigManager.saveConfigs();
        }
    }
}
