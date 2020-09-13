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

public class ImmuneEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void Immune(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        if (!player.hasPermission("occ.bypass")) {
            if (!Occ.isFitOcc(player, "All_Immune") || !playerData.getBoolean(String.format("%s.occConfig.enabled", playerName), true)) {
                return;
            }
        }
        if (player.isSneaking() && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && player.getInventory().getItemInMainHand().getType() == Material.DIAMOND && event.getHand() == EquipmentSlot.HAND) {
            if (playerData.get(String.format("%s.occConfig.occSkills.All_Immune", playerName)) == null) {
                return;
            }
            if (temp.get(String.format("OccSkillCoolDown.%s.All_Immune", playerName)) != null) {
                MessageManager.ActionBarMessage(player, String.format("§c§l神圣护甲§r§e冷却尚未结束, §e剩余 §b%s §e秒", temp.getInt(String.format("OccSkillCoolDown.%s.All_Immune", playerName))));
                return;
            }
            if (!Occ.requireItem(player, Material.DIAMOND, skills.getInt("All_Immune.cost", 25))) {
                MessageManager.ActionBarMessage(player, "§c钻石不足");
                return;
            }
            MessageManager.ActionBarMessage(player, "§c§l神圣护甲§r§e发动!");
            SkillLib.Immune(player);
            Effect.activeSkillEffect(player);
            int coolDown = skills.getInt("All_Immune.cooldown", 30);
            temp.set(String.format("OccSkillCoolDown.%s.All_Immune", playerName), coolDown);
            new OccSkillsCoolDownTask().OccSkillsCoolDown(player, "All_Immune");
            ConfigManager.saveConfigs();
        }
    }
}
