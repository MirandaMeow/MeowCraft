package cn.miranda.MeowCraft.Listeners;

import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Task.FlyTimeCoolDownTask;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static cn.miranda.MeowCraft.Manager.ConfigManager.playerData;

public class FlyTimePlayerJoinEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void FlyTimePlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        if (playerData.get(String.format("%s.flytime", playerName)) == null) {
            return;
        }
        int remainTime = playerData.getInt(String.format("%s.flytime.time", playerName));
        player.setAllowFlight(true);
        MessageManager.Message(player, String.format("§e飞行时间剩余 §b%d §e秒", remainTime));
        new FlyTimeCoolDownTask().initFlyTime(player);
    }
}
