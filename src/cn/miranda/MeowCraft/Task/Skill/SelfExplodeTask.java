package cn.miranda.MeowCraft.Task.Skill;

import cn.miranda.MeowCraft.Manager.ConfigManager;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.MeowCraft;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import static cn.miranda.MeowCraft.Manager.ConfigManager.cache;
import static cn.miranda.MeowCraft.Manager.ConfigManager.skills;
import static org.bukkit.Bukkit.getScheduler;

public class SelfExplodeTask {
    private volatile BukkitTask task = null;

    public void SelfExplode(Player player) {
        int intensity = skills.getInt("All_SelfExplode.intensity", 5);
        task = getScheduler().runTaskTimer(MeowCraft.plugin, () -> {
            String playerName = player.getName();
            if (cache.getBoolean(String.format("OccSkillCoolDown.%s.temp.selfExplodeCancel", playerName))) {
                cache.set(String.format("OccSkillCoolDown.%s.temp.selfExplode", playerName), null);
                cache.set(String.format("OccSkillCoolDown.%s.temp.selfExplodeCancel", playerName), null);
                task.cancel();
                return;
            }
            if (cache.getInt(String.format("OccSkillCoolDown.%s.temp.selfExplode", playerName), 5) < 0) {
                task.cancel();
                return;
            }
            int playerTimeLeft = cache.getInt(String.format("OccSkillCoolDown.%s.temp.selfExplode", playerName));
            if (cache.getInt(String.format("OccSkillCoolDown.%s.temp.selfExplode", playerName), 5) == 0) {
                MessageManager.Message(player, "§e安拉胡阿克巴!");
                cache.set(String.format("OccSkillCoolDown.%s.temp.selfExplode", playerName), null);
                cache.set(String.format("OccSkillCoolDown.%s.temp.selfExplodeCancel", playerName), null);
                ConfigManager.saveConfigs();
                player.getWorld().createExplosion(player.getLocation(), intensity);
                player.setGlowing(false);
                player.setHealth(0);
                task.cancel();
                return;
            }
            player.setGlowing(playerTimeLeft % 2 == 0);
            MessageManager.Message(player, String.format("§c倒计时剩余 §b%d §c秒", playerTimeLeft));
            cache.set(String.format("OccSkillCoolDown.%s.temp.selfExplode", playerName), playerTimeLeft - 1);
        }, 0L, 20);
    }
}
