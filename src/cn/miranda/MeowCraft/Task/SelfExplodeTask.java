package cn.miranda.MeowCraft.Task;

import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.MeowCraft;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import static cn.miranda.MeowCraft.Manager.ConfigMaganer.playerData;
import static org.bukkit.Bukkit.getScheduler;

public class SelfExplodeTask {
    private volatile BukkitTask task = null;

    public void SelfExplode(Player player, int intensity) {
        task = getScheduler().runTaskTimer(MeowCraft.plugin, new Runnable() {
            @Override
            public void run() {
                String playerName = player.getName();
                if (playerData.getBoolean(String.format("%s.temp.selfExplodeCancel", playerName))) {
                    playerData.set(String.format("%s.temp.selfExplode", playerName), null);
                    playerData.set(String.format("%s.temp.selfExplodeCancel", playerName), null);
                    task.cancel();
                    return;
                }
                int playerTimeLeft = playerData.getInt(String.format("%s.temp.selfExplode", playerName));
                if (playerData.getInt(String.format("%s.temp.selfExplode", playerName), 5) == 0) {
                    MessageManager.Messager(player, "§e安拉胡阿克巴!");
                    playerData.set(String.format("%s.temp.selfExplode", playerName), null);
                    playerData.set(String.format("%s.temp.selfExplodeCancel", playerName), null);
                    player.getWorld().createExplosion(player.getLocation(), intensity);
                    player.setGlowing(false);
                    player.setHealth(0);
                    task.cancel();
                    return;
                }
                if (playerTimeLeft % 2 == 0) {
                    player.setGlowing(true);
                } else {
                    player.setGlowing(false);
                }
                MessageManager.Messager(player, String.format("§c倒计时剩余 §b%d §c秒", playerTimeLeft));
                playerData.set(String.format("%s.temp.selfExplode", playerName), playerTimeLeft - 1);
            }
        }, 0L, 20);
    }
}
