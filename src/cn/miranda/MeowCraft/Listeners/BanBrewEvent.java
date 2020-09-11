package cn.miranda.MeowCraft.Listeners;

import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Utils.Occ;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class BanBrewEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void DeadCostEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (!event.hasBlock()) {
            return;
        }
        Block block = event.getClickedBlock();
        if (block.getType() != Material.BREWING_STAND) {
            return;
        }
        if (Occ.inGroup(player, "D")) {
            return;
        }
        MessageManager.Message(player, "§c你的知识不足以使用酿造台! 这种活还是让巫毒来吧!");
        event.setCancelled(true);
    }
}
