package cn.miranda.MeowCraft.Task;

import cn.miranda.MeowCraft.Manager.ConfigMaganer;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.MeowCraft;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import static cn.miranda.MeowCraft.Manager.ConfigMaganer.skills;
import static cn.miranda.MeowCraft.Manager.ConfigMaganer.playerData;
import static org.bukkit.Bukkit.getScheduler;

public class OccSkillsCoolDownTask {
    private volatile BukkitTask task = null;

    public void OccSkillsCoolDown(Player player, String skill) {
        String playerName = player.getName();
        task = getScheduler().runTaskTimer(MeowCraft.plugin, new Runnable() {
            @Override
            public void run() {
                if (playerData.get(String.format("%s.occConfig.occSkills.occCoolDown.%s", playerName, skill)) == null) {
                    task.cancel();
                    return;
                }
                int timeLeft = playerData.getInt(String.format("%s.occConfig.occSkills.occCoolDown.%s", playerName, skill)) - 1;
                playerData.set(String.format("%s.occConfig.occSkills.occCoolDown.%s", playerName, skill), timeLeft);
                if (playerData.getInt(String.format("%s.occConfig.occSkills.occCoolDown.%s", playerName, skill)) <= 0) {
                    playerData.set(String.format("%s.occConfig.occSkills.occCoolDown.%s", playerName, skill), null);
                    String skillChineseName = skills.getString(String.format("OccSkillConfig.%s.name", skill));
                    MessageManager.Messager(player, String.format("§c§l%s§r§e已经准备就绪", skillChineseName));
                    ConfigMaganer.saveConfigs();
                    task.cancel();
                }
            }
        }, 0L, 20L);
    }
}
