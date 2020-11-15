package cn.miranda.MeowCraft.Listeners;

import cn.miranda.MeowCraft.Task.MonsterCardPlayerRespawnLaterTask;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import static cn.miranda.MeowCraft.Manager.ConfigManager.playerData;

public class MonsterCardPlayerRespawnEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void MonsterCardPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        if (playerData.get(String.format("%s.monsterCard", playerName)) == null) {
            return;
        }
        new MonsterCardPlayerRespawnLaterTask().MonsterCardPlayerRespawnLater(player);
    }
}
