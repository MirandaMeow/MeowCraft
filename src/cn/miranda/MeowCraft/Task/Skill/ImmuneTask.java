package cn.miranda.MeowCraft.Task.Skill;

import cn.miranda.MeowCraft.MeowCraft;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getScheduler;

public class ImmuneTask {

    public void RemoveImmune(Player player, long duration) {
        getScheduler().runTaskLater(MeowCraft.plugin, () -> {
            player.setGlowing(false);
            player.setInvulnerable(false);
        }, duration);
    }
}

