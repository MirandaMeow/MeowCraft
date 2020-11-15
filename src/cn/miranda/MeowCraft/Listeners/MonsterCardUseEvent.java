package cn.miranda.MeowCraft.Listeners;

import cn.miranda.MeowCraft.Enum.EggCatcher;
import cn.miranda.MeowCraft.Manager.ConfigManager;
import cn.miranda.MeowCraft.Utils.Misc;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

import static cn.miranda.MeowCraft.Manager.ConfigManager.monsterCard;
import static org.bukkit.event.block.Action.RIGHT_CLICK_AIR;
import static org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK;

public class MonsterCardUseEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void MonsterCardUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!player.isSneaking() && (event.getAction() == RIGHT_CLICK_BLOCK || event.getAction() == RIGHT_CLICK_AIR) && player.getInventory().getItemInMainHand().getType().equals(Material.PAPER) && event.getHand() == EquipmentSlot.HAND) {
            ItemMeta playerItemMeta = player.getInventory().getItemInMainHand().getItemMeta();
            if (playerItemMeta == null) {
                return;
            }
            if (playerItemMeta.getDisplayName().equals("§9怪物卡片") && Objects.equals(playerItemMeta.getLore().get(0), "§3已完成拓印的怪物卡片")) {
                String entityTypeChineseName = playerItemMeta.getLore().get(2).split(" ")[1];
                EntityType entityType = EggCatcher.getEntityType(entityTypeChineseName);
                String typeName = entityType.name();
                int duration = monsterCard.getInt(String.format("%s.duration", entityType));
                Misc.setMonsterCard(player, typeName, duration);
                Misc.disguisePlayer(player, entityType);
                ConfigManager.saveConfigs();
            }
        }
    }
}
