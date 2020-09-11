package cn.miranda.MeowCraft.Task;

import cn.miranda.MeowCraft.Manager.ConfigManager;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.MeowCraft;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import static cn.miranda.MeowCraft.Manager.ConfigManager.playerData;
import static org.bukkit.Bukkit.getScheduler;

public class FlyTimeCoolDownTask {
    private volatile BukkitTask task = null;

    public void initFlyTime(Player player) {
        String playerName = player.getName();
        task = getScheduler().runTaskTimer(MeowCraft.plugin, new Runnable() {
            @Override
            public void run() {
                if (playerData.getBoolean(String.format("%s.flytime.quit", playerName))) {
                    playerData.set(String.format("%s.flytime.quit", playerName), null);
                    ConfigManager.saveConfigs();
                    task.cancel();
                    return;
                }
                if (playerData.getBoolean(String.format("%s.flytime.cancel", playerName))) {
                    player.setAllowFlight(false);
                    MessageManager.Message(player, "§e限时飞行已终止");
                    playerData.set(String.format("%s.flytime", playerName), null);
                    ConfigManager.saveConfigs();
                    task.cancel();
                    return;
                }
                int currentTime = playerData.getInt(String.format("%s.flytime.time", playerName)) - 1;
                playerData.set(String.format("%s.flytime.time", playerName), currentTime);
                if (playerData.getInt(String.format("%s.flytime.time", playerName)) <= 10) {
                    MessageManager.Message(player, String.format("§e飞行时间剩余 §b%d §e秒", playerData.getInt(String.format("%s.flytime.time", playerName))));
                }
                if (playerData.getInt(String.format("%s.flytime.time", playerName)) == 0) {
                    player.setAllowFlight(false);
                    MessageManager.Message(player, "§e限时飞行已经结束");
                    playerData.set(String.format("%s.flytime", playerName), null);
                    ConfigManager.saveConfigs();
                    task.cancel();
                }
            }
        }, 0L, 20L);
    }
}
