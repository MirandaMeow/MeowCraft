package cn.miranda.MeowCraft.Listeners.Skills.Misc;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import static cn.miranda.MeowCraft.Manager.ConfigMaganer.playerData;

public class SelfExplodeCancelByPlayerDeath implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void SelfExplodeCancelByPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        String playerName = player.getName();
        if (playerData.get(String.format("%s.temp.selfExplode", playerName), 5) != null && !playerData.getBoolean(String.format("%s.temp.selfExplodeCancel", playerName), false)) {
            playerData.set(String.format("%s.temp.selfExplodeCancel", playerName), true);
        }
    }
}
