package cn.miranda.MeowCraft.Listeners.Skills.Active;

import cn.miranda.MeowCraft.Manager.ConfigManager;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Task.Skill.OccSkillsCoolDownTask;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static cn.miranda.MeowCraft.Manager.ConfigManager.*;

public class ThorAxeEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void ThorAxe(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        String skillName = "Thug_ThorAxe";
        if (!player.hasPermission("occ.bypass")) {
            if (!Occ.isFitOcc(player, skillName) || !playerData.getBoolean(String.format("%s.occConfig.enabled", playerName), true)) {
                return;
            }
        }
        List<Material> axes = new ArrayList<>(Arrays.asList(Material.DIAMOND_AXE, Material.GOLDEN_AXE, Material.IRON_AXE, Material.WOODEN_AXE, Material.STONE_AXE));
        if (player.isSneaking() && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && axes.contains(player.getInventory().getItemInMainHand().getType()) && event.getHand() == EquipmentSlot.HAND) {
            if (playerData.get(String.format("%s.occConfig.occSkills.%s", playerName, skillName)) == null) {
                return;
            }
            if (cache.get(String.format("OccSkillCoolDown.%s.%s", playerName, skillName)) != null) {
                MessageManager.ActionBarMessage(player, String.format("§c§l雷神战斧§r§e冷却尚未结束  §e剩余 §b%s §e秒", cache.getInt(String.format("OccSkillCoolDown.%s.%s", playerName, skillName))));
                return;
            }
            if (!Occ.requireItem(player, Material.GOLD_INGOT, skills.getInt(String.format("%s.cost", skillName), 1))) {
                MessageManager.ActionBarMessage(player, "§c金锭不足");
                return;
            }
            MessageManager.ActionBarMessage(player, "§c§l雷神战斧§r§e发动!");
            SkillLib.ThorAxe(player);
            Effect.activeSkillEffect(player);
            int coolDown = skills.getInt(String.format("%s.cooldown", skillName), 20);
            cache.set(String.format("OccSkillCoolDown.%s.%s", playerName, skillName), coolDown);
            new OccSkillsCoolDownTask().OccSkillsCoolDown(player, skillName);
            ConfigManager.saveConfigs();
        }
    }
}
