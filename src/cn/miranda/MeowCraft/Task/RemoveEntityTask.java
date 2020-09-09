package cn.miranda.MeowCraft.Task;

import cn.miranda.MeowCraft.MeowCraft;
import org.bukkit.entity.Entity;

import static org.bukkit.Bukkit.getScheduler;

public class RemoveEntityTask {
    public void RemoveEntity(Entity entity, long delay, boolean thunder) {
        getScheduler().runTaskLater(MeowCraft.plugin, new Runnable() {
            @Override
            public void run() {
                if (thunder) {
                    entity.getWorld().strikeLightning(entity.getLocation());
                }
                entity.remove();
            }
        }, delay);
    }
}

