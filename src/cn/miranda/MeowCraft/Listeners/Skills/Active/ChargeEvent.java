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

public class ChargeEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void Charge(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        if (!player.hasPermission("occ.bypass")) {
            if (!Occ.isFitOcc(player, "Swordsman_Charge") || !playerData.getBoolean(String.format("%s.occConfig.enabled", playerName), true)) {
                return;
            }
        }
        List<Material> swords = new ArrayList<>(Arrays.asList(Material.DIAMOND_SWORD, Material.GOLDEN_SWORD, Material.IRON_SWORD, Material.WOODEN_SWORD, Material.STONE_SWORD));
        if (player.isSneaking() && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && swords.contains(player.getInventory().getItemInMainHand().getType()) && event.getHand() == EquipmentSlot.HAND) {
            if (playerData.get(String.format("%s.occConfig.occSkills.Swordsman_Charge", playerName)) == null) {
                return;
            }
            if (temp.get(String.format("OccSkillCoolDown.%s.Swordsman_Charge", playerName)) != null) {
                MessageManager.Message(player, String.format("§c§l无畏冲锋§r§e冷却尚未结束, §e剩余 §b%s §e秒", temp.getInt(String.format("OccSkillCoolDown.%s.Swordsman_Charge", playerName))));
                return;
            }
            if (!Occ.requireItem(player, Material.IRON_INGOT, skills.getInt("Swordsman_Charge.cost", 1))) {
                MessageManager.Message(player, "§c铁锭不足");
                return;
            }
            MessageManager.Message(player, "§c§l无畏冲锋§r§e发动!");
            SkillLib.Charge(player);
            Effect.activeSkillEffect(player);
            int coolDown = skills.getInt("Swordsman_Charge.cooldown", 30);
            temp.set(String.format("OccSkillCoolDown.%s.Swordsman_Charge", playerName), coolDown);
            new OccSkillsCoolDownTask().OccSkillsCoolDown(player, "Swordsman_Charge");
            ConfigManager.saveConfigs();
        }
    }
}

