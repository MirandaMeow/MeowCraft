package cn.miranda.MeowCraft.Task;

import cn.miranda.MeowCraft.Manager.ConfigManager;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.MeowCraft;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import static cn.miranda.MeowCraft.Manager.ConfigManager.skills;
import static cn.miranda.MeowCraft.Manager.ConfigManager.temp;
import static org.bukkit.Bukkit.getScheduler;

public class OccSkillsCoolDownTask {
    private volatile BukkitTask task = null;

    public void OccSkillsCoolDown(Player player, String skill) {
        String playerName = player.getName();
        task = getScheduler().runTaskTimer(MeowCraft.plugin, new Runnable() {
            @Override
            public void run() {
                if (temp.get(String.format("OccSkillCoolDown.%s.%s", playerName, skill)) == null) {
                    task.cancel();
                    return;
                }
                int timeLeft = temp.getInt(String.format("OccSkillCoolDown.%s.%s", playerName, skill)) - 1;
                temp.set(String.format("OccSkillCoolDown.%s.%s", playerName, skill), timeLeft);
                if (temp.getInt(String.format("OccSkillCoolDown.%s.%s", playerName, skill)) <= 0) {
                    temp.set(String.format("OccSkillCoolDown.%s.%s", playerName, skill), null);
                    String skillChineseName = skills.getString(String.format("%s.name", skill));
                    MessageManager.ActionBarMessage(player, String.format("§c§l%s§r§e已经准备就绪", skillChineseName));
                    ConfigManager.saveConfigs();
                    task.cancel();
                }
            }
        }, 0L, 20L);
    }
}
