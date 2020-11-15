package cn.miranda.MeowCraft.Task;

import cn.miranda.MeowCraft.Manager.ConfigManager;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.MeowCraft;
import me.libraryaddict.disguise.DisguiseAPI;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import static cn.miranda.MeowCraft.Manager.ConfigManager.playerData;
import static org.bukkit.Bukkit.getScheduler;

public class MonsterCardTimeTask {
    private volatile BukkitTask task = null;

    public void MonsterCardTime(Player player) {
        String playerName = player.getName();
        task = getScheduler().runTaskTimer(MeowCraft.plugin, () -> {
            if (playerData.getBoolean(String.format("%s.monsterCard.pause", playerName))) {
                playerData.set(String.format("%s.monsterCard.pause", playerName), null);
                ConfigManager.saveConfigs();
                task.cancel();
                return;
            }
            if (playerData.getBoolean(String.format("%s.monsterCard.cancel", playerName))) {
                playerData.set(String.format("%s.monsterCard", playerName), null);
                DisguiseAPI.undisguiseToAll(player);
                ConfigManager.saveConfigs();
                task.cancel();
                return;
            }
            int currentTime = playerData.getInt(String.format("%s.monsterCard.duration", playerName)) - 1;
            playerData.set(String.format("%s.monsterCard.duration", playerName), currentTime);
            if (playerData.getInt(String.format("%s.monsterCard.duration", playerName)) == 0) {
                MessageManager.Message(player, "§e怪物卡片效果已经结束");
                playerData.set(String.format("%s.monsterCard", playerName), null);
                DisguiseAPI.undisguiseToAll(player);
                ConfigManager.saveConfigs();
                task.cancel();
            }
        }, 0L, 20L);
    }
}
