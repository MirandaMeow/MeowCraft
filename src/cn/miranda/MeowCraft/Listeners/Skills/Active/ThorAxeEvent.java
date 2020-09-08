package cn.miranda.MeowCraft.Listeners.Skills.Active;

import cn.miranda.MeowCraft.Manager.ConfigMaganer;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Task.OccSkillsCoolDownTask;
import cn.miranda.MeowCraft.Utils.Effect;
import cn.miranda.MeowCraft.Utils.Misc;
import cn.miranda.MeowCraft.Utils.Occ;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.ArrayList;

import static cn.miranda.MeowCraft.Manager.ConfigMaganer.*;

public class ThorAxeEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void ThorAxeEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        if (!player.hasPermission("occ.bypass")) {
            if (!Occ.isFitOcc(player, "Thug_ThorAxe") || !playerData.getBoolean(String.format("%s.occConfig.enabled", playerName), true)) {
                return;
            }
        }
        ArrayList<Material> axes = new ArrayList<Material>();
        axes.add(Material.DIAMOND_AXE);
        axes.add(Material.GOLDEN_AXE);
        axes.add(Material.IRON_AXE);
        axes.add(Material.WOODEN_AXE);
        axes.add(Material.STONE_AXE);
        if (player.isSneaking() && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && axes.contains(player.getInventory().getItemInMainHand().getType()) && event.getHand() == EquipmentSlot.HAND) {
            if (playerData.get(String.format("%s.occConfig.occSkills.Thug_ThorAxe", playerName)) == null) {
                return;
            }
            if (temp.get(String.format("OccSkillCoolDown.%s.Thug_ThorAxe", playerName)) != null) {
                MessageManager.Messager(player, String.format("§c§l雷神战斧§r§e冷却尚未结束, §e剩余 §b%s §e秒", temp.getInt(String.format("OccSkillCoolDown.%s.Thug_ThorAxe", playerName))));
                return;
            }
            if (!Occ.requireItem(player, Material.GOLD_INGOT, skills.getInt("Thug_ThorAxe.cost", 1))) {
                MessageManager.Messager(player, "§c金锭不足");
                return;
            }
            MessageManager.Messager(player, "§c§l雷神战斧§r§e发动!");
            activeThorAxe(player);
            Effect.activeSkillEffect(player);
            int coolDown = skills.getInt("Thug_ThorAxe.cooldown", 20);
            temp.set(String.format("OccSkillCoolDown.%s.Thug_ThorAxe", playerName), coolDown);
            new OccSkillsCoolDownTask().OccSkillsCoolDown(player, "Thug_ThorAxe");
            ConfigMaganer.saveConfigs();
        }
    }

    private void activeThorAxe(Player player) {
        int settings_distance = skills.getInt("Thug_ThorAxe.distance", 2);
        Misc.throwSomething(player, Material.GOLDEN_AXE, settings_distance, 20, true);
    }
}
