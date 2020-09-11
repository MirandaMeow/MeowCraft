package cn.miranda.MeowCraft.Listeners.Skills.Active;

import cn.miranda.MeowCraft.Manager.ConfigManager;
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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

import static cn.miranda.MeowCraft.Manager.ConfigManager.*;

public class ChargeEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void ChargeEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        if (!player.hasPermission("occ.bypass")) {
            if (!Occ.isFitOcc(player, "Swordsman_Charge") || !playerData.getBoolean(String.format("%s.occConfig.enabled", playerName), true)) {
                return;
            }
        }
        ArrayList<Material> swords = new ArrayList<Material>();
        swords.add(Material.DIAMOND_SWORD);
        swords.add(Material.GOLDEN_SWORD);
        swords.add(Material.IRON_SWORD);
        swords.add(Material.WOODEN_SWORD);
        swords.add(Material.STONE_SWORD);
        if (player.isSneaking() && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && swords.contains(player.getInventory().getItemInMainHand().getType()) && event.getHand() == EquipmentSlot.HAND) {
            if (playerData.get(String.format("%s.occConfig.occSkills.Swordsman_Charge", playerName)) == null) {
                return;
            }
            if (temp.get(String.format("OccSkillCoolDown.%s.Swordsman_Charge", playerName)) != null) {
                MessageManager.Messager(player, String.format("§c§l无畏冲锋§r§e冷却尚未结束, §e剩余 §b%s §e秒", temp.getInt(String.format("OccSkillCoolDown.%s.Swordsman_Charge", playerName))));
                return;
            }
            if (!Occ.requireItem(player, Material.IRON_INGOT, skills.getInt("Swordsman_Charge.cost", 1))) {
                MessageManager.Messager(player, "§c铁锭不足");
                return;
            }
            MessageManager.Messager(player, "§c§l无畏冲锋§r§e发动!");
            activeCharge(player);
            Effect.activeSkillEffect(player);
            int coolDown = skills.getInt("Swordsman_Charge.cooldown", 30);
            temp.set(String.format("OccSkillCoolDown.%s.Swordsman_Charge", playerName), coolDown);
            new OccSkillsCoolDownTask().OccSkillsCoolDown(player, "Swordsman_Charge");
            ConfigManager.saveConfigs();
        }
    }

    private void activeCharge(Player player) {
        int settings_duration = skills.getInt("Swordsman_Charge.duration", 20);
        Misc.moveForward(player, 4);
        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, settings_duration * 20, 0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, settings_duration * 20, 3));
    }
}

