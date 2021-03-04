package cn.miranda.MeowCraft.Task;

import cn.miranda.MeowCraft.Cores.Task;
import cn.miranda.MeowCraft.MeowCraft;
import cn.miranda.MeowCraft.Utils.Misc;

import java.util.Date;

import static org.bukkit.Bukkit.getScheduler;

public class TaskExecuteTask {

    public void TaskExecute() {
        getScheduler().runTaskTimer(MeowCraft.plugin, () -> {
            int timestamp = Misc.getSecondTimestampFromDate(new Date());
            for (Task newTask : Task.tasks) {
                if (newTask.getTimestamp() != timestamp) {
                    continue;
                }
                newTask.execute();
                if (newTask.getLoop()) {
                    newTask.updateDate();
                    continue;
                }
                newTask.remove();
            }
        }, 0L, 20L);
    }
}
