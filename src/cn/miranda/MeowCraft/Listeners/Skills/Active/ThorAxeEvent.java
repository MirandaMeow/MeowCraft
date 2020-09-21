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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static cn.miranda.MeowCraft.Manager.ConfigManager.*;

public class ThorAxeEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void ThorAxe(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        if (!player.hasPermission("occ.bypass")) {
            if (!Occ.isFitOcc(player, "Thug_ThorAxe") || !playerData.getBoolean(String.format("%s.occConfig.enabled", playerName), true)) {
                return;
            }
        }
        List<Material> axes = new ArrayList<>(Arrays.asList(Material.DIAMOND_AXE, Material.GOLDEN_AXE, Material.IRON_AXE, Material.WOODEN_AXE, Material.STONE_AXE));
        if (player.isSneaking() && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && axes.contains(player.getInventory().getItemInMainHand().getType()) && event.getHand() == EquipmentSlot.HAND) {
            if (playerData.get(String.format("%s.occConfig.occSkills.Thug_ThorAxe", playerName)) == null) {
                return;
            }
            if (cache.get(String.format("OccSkillCoolDown.%s.Thug_ThorAxe", playerName)) != null) {
                MessageManager.ActionBarMessage(player, String.format("§c§l雷神战斧§r§e冷却尚未结束  §e剩余 §b%s §e秒", cache.getInt(String.format("OccSkillCoolDown.%s.Thug_ThorAxe", playerName))));
                return;
            }
            if (!Occ.requireItem(player, Material.GOLD_INGOT, skills.getInt("Thug_ThorAxe.cost", 1))) {
                MessageManager.ActionBarMessage(player, "§c金锭不足");
                return;
            }
            MessageManager.ActionBarMessage(player, "§c§l雷神战斧§r§e发动!");
            SkillLib.ThorAxe(player);
            Effect.activeSkillEffect(player);
            int coolDown = skills.getInt("Thug_ThorAxe.cooldown", 20);
            cache.set(String.format("OccSkillCoolDown.%s.Thug_ThorAxe", playerName), coolDown);
            new OccSkillsCoolDownTask().OccSkillsCoolDown(player, "Thug_ThorAxe");
            ConfigManager.saveConfigs();
        }
    }
}
