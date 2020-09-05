package cn.miranda.MeowCraft.Task;

import cn.miranda.MeowCraft.MeowCraft;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getScheduler;

public class ImmuneTask {

    public void RemoveImmune(Player player, long duration) {
        getScheduler().runTaskLater(MeowCraft.plugin, new Runnable() {
            @Override
            public void run() {
                player.setGlowing(false);
                player.setInvulnerable(false);
            }
        }, duration);
    }
}

