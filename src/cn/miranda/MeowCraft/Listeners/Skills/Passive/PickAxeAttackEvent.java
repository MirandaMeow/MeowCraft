package cn.miranda.MeowCraft.Listeners.Skills.Passive;

import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Utils.Effect;
import cn.miranda.MeowCraft.Utils.Misc;
import cn.miranda.MeowCraft.Utils.Occ;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static cn.miranda.MeowCraft.Manager.ConfigManager.playerData;
import static cn.miranda.MeowCraft.Manager.ConfigManager.skills;

public class PickAxeAttackEvent implements Listener {
    public Player player;
    boolean success = false;

    @EventHandler(priority = EventPriority.NORMAL)
    private void PickAxeAttack(EntityDamageByEntityEvent event) {
        Entity entity = event.getDamager();
        if (!(entity instanceof Player)) {
            return;
        }
        player = (Player) entity;
        String playerName = player.getName();
        if (!Occ.isFitOcc(player, "Artisan_PickAxeAttack") || !playerData.getBoolean(String.format("%s.occConfig.enabled", playerName), true)) {
            return;
        }
        if (playerData.get(String.format("%s.occConfig.occSkills.Artisan_PickAxeAttack", playerName)) == null) {
            return;
        }
        Material playerHold = player.getInventory().getItemInMainHand().getType();
        List<Material> swords = new ArrayList<>(Arrays.asList(Material.DIAMOND_SWORD, Material.GOLDEN_SWORD, Material.IRON_SWORD, Material.WOODEN_SWORD, Material.STONE_SWORD));
        List<Material> axes = new ArrayList<>(Arrays.asList(Material.DIAMOND_AXE, Material.GOLDEN_AXE, Material.IRON_AXE, Material.WOODEN_AXE, Material.STONE_AXE));
        if (swords.contains(playerHold) || axes.contains(playerHold)) {
            event.setDamage(event.getDamage() - 3);
            return;
        }
        success = false;
        int chance = skills.getInt("Artisan_PickAxeAttack.chance", 40);
        int currentProbability = Misc.randomNum(0, 100);
        if (currentProbability > chance) {
            return;
        }
        int plusDamage;
        switch (playerHold) {
            case DIAMOND_PICKAXE:
                plusDamage = 8;
                break;
            case IRON_PICKAXE:
                plusDamage = 7;
                break;
            case GOLDEN_PICKAXE:
                plusDamage = 6;
                break;
            case STONE_PICKAXE:
                plusDamage = 5;
                break;
            case WOODEN_PICKAXE:
                plusDamage = 4;
                break;
            default:
                return;
        }
        MessageManager.ActionBarMessage(player, "§e你使用手中的镐狠狠的击中了对手");
        Effect.activeSkillEffect(player);
        success = true;
        event.setDamage(event.getDamage() + plusDamage);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    private void reduceDuration(PlayerItemDamageEvent event) {
        Player playerE = event.getPlayer();
        if (player == null) {
            return;
        }
        if (playerE.hashCode() != player.hashCode() || !success) {
            return;
        }
        event.setDamage(1);
        success = false;
    }
}
