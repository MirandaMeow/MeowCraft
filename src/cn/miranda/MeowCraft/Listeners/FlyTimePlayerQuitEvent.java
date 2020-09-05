package cn.miranda.MeowCraft.Listeners;

import cn.miranda.MeowCraft.Manager.ConfigMaganer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static cn.miranda.MeowCraft.Manager.ConfigMaganer.playerData;

public class FlyTimePlayerQuitEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void FlyTimePlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        if (playerData.get(String.format("%s.flytime", playerName)) == null) {
            return;
        }
        playerData.set(String.format("%s.flytime.quit", playerName), true);
        ConfigMaganer.saveConfigs();
    }
}
