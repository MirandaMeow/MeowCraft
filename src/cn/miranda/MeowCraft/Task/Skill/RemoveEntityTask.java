package cn.miranda.MeowCraft.Task.Skill;

import cn.miranda.MeowCraft.MeowCraft;
import org.bukkit.entity.Entity;

import static org.bukkit.Bukkit.getScheduler;

public class RemoveEntityTask {
    public void RemoveEntity(Entity entity, long delay, boolean thunder) {
        getScheduler().runTaskLater(MeowCraft.plugin, () -> {
            if (thunder) {
                entity.getWorld().strikeLightning(entity.getLocation());
            }
            entity.remove();
        }, delay);
    }
}

