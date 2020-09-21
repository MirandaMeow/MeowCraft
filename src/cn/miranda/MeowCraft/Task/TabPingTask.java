package cn.miranda.MeowCraft.Task;

import cn.miranda.MeowCraft.MeowCraft;
import cn.miranda.MeowCraft.Utils.Misc;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.Map;

import static org.bukkit.Bukkit.getScheduler;
import static cn.miranda.MeowCraft.Manager.ConfigManager.cache;

public class TabPingTask {
    private volatile BukkitTask task = null;

    public void TabPing() {
        task = getScheduler().runTaskTimer(MeowCraft.plugin, new Runnable() {
            public void run() {
                HashMap<String, Integer> pings = new HashMap<>();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    try {
                        pings.put(player.getName(), Misc.getPing(player));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                for (Player player : Bukkit.getOnlinePlayers()) {
                    Scoreboard scoreboard = player.getScoreboard();
                    if (scoreboard == null) {
                        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
                        player.setScoreboard(scoreboard);
                    }
                    Objective objective = scoreboard.getObjective("PingTab");
                    if (objective == null) {
                        scoreboard.registerNewObjective("PingTab", "dummy", "").setDisplaySlot(DisplaySlot.PLAYER_LIST);
                        objective = scoreboard.getObjective("PingTab");
                    }
                    for (Map.Entry<String, Integer> i : pings.entrySet())
                        objective.getScore(i.getKey()).setScore(i.getValue());
                    player.setScoreboard(scoreboard);
                }
                if (!cache.getBoolean("TabPing.enabled")) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (player.getScoreboard().getObjective("PingTab") == null) {
                            continue;
                        }
                        player.getScoreboard().getObjective("PingTab").unregister();
                    }
                    task.cancel();
                }
            }
        }, 0L, 20L);
    }
}