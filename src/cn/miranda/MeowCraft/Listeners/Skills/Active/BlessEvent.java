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

public class BlessEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void Bless(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        if (!player.hasPermission("occ.bypass")) {
            if (!Occ.isFitOcc(player, "Voodoo_Bless") || !playerData.getBoolean(String.format("%s.occConfig.enabled", playerName), true)) {
                return;
            }
        }
        if (player.isSneaking() && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && player.getInventory().getItemInMainHand().getType() == Material.GLOWSTONE_DUST && event.getHand() == EquipmentSlot.HAND) {
            if (playerData.get(String.format("%s.occConfig.occSkills.Voodoo_Bless", playerName)) == null) {
                return;
            }
            if (cache.get(String.format("OccSkillCoolDown.%s.Voodoo_Bless", playerName)) != null) {
                MessageManager.ActionBarMessage(player, String.format("§c§l巫神祝福§r§e冷却尚未结束  §e剩余 §b%s §e秒", cache.getInt(String.format("OccSkillCoolDown.%s.Voodoo_Bless", playerName))));
                return;
            }
            Player target = Occ.getTarget(player);
            if (target == null) {
                MessageManager.ActionBarMessage(player, "§c目标不是玩家");
                return;
            }
            if (!Occ.requireItem(player, Material.GLOWSTONE_DUST, skills.getInt("Voodoo_Bless.cost", 10))) {
                MessageManager.ActionBarMessage(player, "§c萤石粉不足");
                return;
            }
            MessageManager.ActionBarMessage(player, "§c§l巫神祝福§r§e发动!");
            SkillLib.Bless(target);
            Effect.activeSkillEffect(player);
            int coolDown = skills.getInt("Voodoo_Bless.cooldown", 30);
            cache.set(String.format("OccSkillCoolDown.%s.Voodoo_Bless", playerName), coolDown);
            new OccSkillsCoolDownTask().OccSkillsCoolDown(player, "Voodoo_Bless");
            ConfigManager.saveConfigs();
        }
    }
}
