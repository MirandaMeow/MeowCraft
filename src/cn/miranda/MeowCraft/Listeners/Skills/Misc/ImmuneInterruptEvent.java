package cn.miranda.MeowCraft.Listeners.Skills.Misc;

import cn.miranda.MeowCraft.Manager.MessageManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import static cn.miranda.MeowCraft.Manager.ConfigManager.playerData;

public class ImmuneInterruptEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void ImmuneInterrupt(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location from = event.getFrom().clone();
        Location to = event.getTo().clone();
        if (from.distance(to) < 0.2) {
            return;
        }
        if (!player.isInvulnerable() || !player.isGlowing()) {
            return;
        }
        String playerName = player.getName();
        if (playerData.get(String.format("%s.occConfig.occSkills.occCoolDown.All_Immune", playerName), 0) != null) {
            player.setGlowing(false);
            player.setInvulnerable(false);
            MessageManager.Message(player, "§c§l神圣护甲§r§e被中断!");
        }
    }
}
