package cn.miranda.MeowCraft.Listeners;

import cn.miranda.MeowCraft.Manager.ConfigManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import static cn.miranda.MeowCraft.Manager.ConfigManager.playerData;

public class MonsterCardPlayerDeadEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void MonsterCardPlayerDead(PlayerDeathEvent event) {
        Player player = event.getEntity();
        String playerName = player.getName();
        if (playerData.get(String.format("%s.monsterCard", playerName)) == null) {
            return;
        }
        playerData.set(String.format("%s.monsterCard.pause", playerName), true);
        ConfigManager.saveConfigs();
    }
}
