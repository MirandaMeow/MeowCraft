package cn.miranda.MeowCraft.Listeners.Skills.Active;

import cn.miranda.MeowCraft.Manager.ConfigManager;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Task.OccSkillsCoolDownTask;
import cn.miranda.MeowCraft.Utils.Effect;
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
            if (temp.get(String.format("OccSkillCoolDown.%s.Voodoo_Bless", playerName)) != null) {
                MessageManager.Message(player, String.format("§c§l巫神祝福§r§e冷却尚未结束, §e剩余 §b%s §e秒", temp.getInt(String.format("OccSkillCoolDown.%s.Voodoo_Bless", playerName))));
                return;
            }
            Player target = Occ.getTarget(player);
            if (target == null) {
                MessageManager.Message(player, "§c目标不是玩家");
                return;
            }
            if (!Occ.requireItem(player, Material.GLOWSTONE_DUST, skills.getInt("Voodoo_Bless.cost", 10))) {
                MessageManager.Message(player, "§c萤石粉不足");
                return;
            }
            MessageManager.Message(player, "§c§l巫神祝福§r§e发动!");
            activeBless(target);
            Effect.activeSkillEffect(player);
            int coolDown = skills.getInt("Voodoo_Bless.cooldown", 30);
            temp.set(String.format("OccSkillCoolDown.%s.Voodoo_Bless", playerName), coolDown);
            new OccSkillsCoolDownTask().OccSkillsCoolDown(player, "Voodoo_Bless");
            ConfigManager.saveConfigs();
        }
    }

    private void activeBless(Player player) {
        int duration = skills.getInt("Voodoo_Bless.duration", 30);
        int level1 = skills.getInt("Voodoo_Bless.effect1_level", 1);
        int level2 = skills.getInt("Voodoo_Bless.effect2_level", 1);
        int level3 = skills.getInt("Voodoo_Bless.effect3_level", 1);
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, duration * 20, level1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, duration * 20, level2));
        player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, duration * 20, level3));
        MessageManager.Message(player, "§e你被祝福了, 快上!");
    }
}
