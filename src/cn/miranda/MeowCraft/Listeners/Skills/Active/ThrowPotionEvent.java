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
        if (!player.hasPermission("occ.bypass")) {
            if (!Occ.isFitOcc(player, "Voodoo_ThrowPotion") || !playerData.getBoolean(String.format("%s.occConfig.enabled", playerName), true)) {
                return;
            }
        }
        if (player.isSneaking() && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && (player.getInventory().getItemInMainHand().getType() == Material.MAGMA_CREAM || player.getInventory().getItemInMainHand().getType() == Material.GOLDEN_APPLE) && event.getHand() == EquipmentSlot.HAND) {
            if (playerData.get(String.format("%s.occConfig.occSkills.Voodoo_ThrowPotion", playerName)) == null) {
                return;
            }
            if (cache.get(String.format("OccSkillCoolDown.%s.Voodoo_ThrowPotion", playerName)) != null) {
                MessageManager.ActionBarMessage(player, String.format("§c§l魔药喷洒§r§e冷却尚未结束  §e剩余 §b%s §e秒", cache.getInt(String.format("OccSkillCoolDown.%s.Voodoo_ThrowPotion", playerName))));
                return;
            }
            Material material = player.getInventory().getItemInMainHand().getType();
            MessageManager.ActionBarMessage(player, "§c§l魔药喷洒§r§e发动!");
            switch (material) {
                case MAGMA_CREAM:
                    SkillLib.ThrowPotions(player, Material.SPLASH_POTION);
                    break;
                case GOLDEN_APPLE:
                    SkillLib.ThrowPotions(player, Material.LINGERING_POTION);
                    break;
            }
            Effect.activeSkillEffect(player);
            int coolDown = skills.getInt("Voodoo_ThrowPotion.cooldown", 240);
            cache.set(String.format("OccSkillCoolDown.%s.Voodoo_ThrowPotion", playerName), coolDown);
            new OccSkillsCoolDownTask().OccSkillsCoolDown(player, "Voodoo_ThrowPotion");
            ConfigManager.saveConfigs();
        }
    }
}
