package cn.miranda.MeowCraft.Task;

import cn.miranda.MeowCraft.MeowCraft;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitTask;

import static org.bukkit.Bukkit.getScheduler;

public class SummonTimeLeftTask {
    private volatile BukkitTask task = null;

    public void SummonTimeLeft(Entity entity, long timeLeft) {
        task = getScheduler().runTaskLater(MeowCraft.plugin, () -> {
            if (entity == null) {
                return;
            }
            ((LivingEntity) entity).setHealth(0);
        }, timeLeft);
    }
}

