package cn.miranda.MeowCraft.Cores;

import cn.miranda.MeowCraft.Manager.ConfigManager;
import cn.miranda.MeowCraft.Manager.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.miranda.MeowCraft.Manager.ConfigManager.task;

public class Task {
    public static ArrayList<Task> tasks = new ArrayList<>();
    private final String taskName;
    private int timestamp;
    private boolean isLoop;
    private String repeat;
    private final ArrayList<String> commands;

    public Task(String taskName) {
        this.taskName = taskName;
        this.timestamp = 0;
        this.isLoop = false;
        this.repeat = "";
        this.commands = new ArrayList<>();
    }

    public Task(String newTaskName, int newTimeStamp, boolean newIsLoop, String newRepeat, ArrayList<String> newCommands) {
        this.taskName = newTaskName;
        this.timestamp = newTimeStamp;
        this.isLoop = newIsLoop;
        this.repeat = newRepeat;
        this.commands = newCommands;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public String showDate() {
        Date date = new Date(this.timestamp * 1000L);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    public boolean getLoop() {
        return this.isLoop;
    }

    public int getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public void setLoop(boolean loop) {
        this.isLoop = loop;
    }

    public ArrayList<String> getCommands() {
        return this.commands;
    }

    public String getCommandLine(int index) {
        return this.commands.get(index);
    }

    public ArrayList<String> getCommandIndexList() {
        ArrayList<String> indexList = new ArrayList<>();
        for (int i = 0; i < this.commands.size(); i++) {
            indexList.add(String.valueOf(i + 1));
        }
        return indexList;
    }

    public void addCommand(String command) {
        this.commands.add(command);
    }

    public void removeCommand(int index) {
        if (this.commands.size() <= index) {
            return;
        }
        this.commands.remove(index);
    }

    private int convertRepeat() {
        Pattern pattern = Pattern.compile("((\\d+)d)?((\\d+)h)?((\\d+)m)?((\\d+)s)?");
        Matcher matcher = pattern.matcher(this.repeat);
        int dd;
        int hh;
        int mm;
        int ss;
        ArrayList<String> conv = new ArrayList<>();
        if (matcher.find()) {
            for (int i = 2; i < 9; i += 2) {
                if (matcher.group(i) == null) {
                    conv.add("");
                    continue;
                }
                conv.add(matcher.group(i));
            }
        }
        dd = !Objects.equals(conv.get(0), "") ? Integer.parseInt(conv.get(0)) : 0;
        hh = !Objects.equals(conv.get(1), "") ? Integer.parseInt(conv.get(1)) : 0;
        mm = !Objects.equals(conv.get(2), "") ? Integer.parseInt(conv.get(2)) : 0;
        ss = !Objects.equals(conv.get(3), "") ? Integer.parseInt(conv.get(3)) : 0;
        return dd * 86400 + hh * 3600 + mm * 60 + ss;
    }

    private void fixRepeat() {
        int offset = convertRepeat();
        int d = offset / 86400;
        int h = offset - d * 86400 > 0 ? (offset - d * 86400) / 3600 : 0;
        int m = offset - d * 86400 - h * 3600 > 0 ? (offset - d * 86400 - h * 3600) / 60 : 0;
        int s = Math.max(offset - d * 86400 - h * 3600 - m * 60, 0);
        String dString = d != 0 ? d + "d" : "";
        String hString = h != 0 ? h + "h" : "";
        String mString = m != 0 ? m + "m" : "";
        String sString = s != 0 ? s + "s" : "";
        this.repeat = dString + hString + mString + sString;
    }

    public void updateDate() {
        if (!this.isLoop) {
            return;
        }
        this.timestamp += convertRepeat();
        saveToConfig();
    }

    public void execute() {
        for (String command : this.commands) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        }
    }

    public void show(CommandSender sender, boolean isConsole) {
        ArrayList<String> hover = new ArrayList<>();
        String loop = isLoop ? "是" : "否";
        hover.add(String.format("§e时间: §b%s", showDate()));
        hover.add(String.format("§e是否循环: §b%s", loop));
        hover.add(String.format("§e重复: §b%s", this.repeat));
        hover.add("§e命令行:");
        if (this.commands.size() != 0) {
            for (String command : this.commands) {
                hover.add(String.format("§b- §9%s", command));
            }
        } else {
            hover.add("§b- §9无");
        }
        if (isConsole) {
            MessageManager.Message(sender, "任务: " + this.taskName);
            for (String s : hover) {
                MessageManager.Message(sender, s);
            }
        } else {
            MessageManager.HoverMessage((Player) sender, "§b - §6" + this.taskName, hover);
        }
    }

    public void saveToConfig() {
        fixRepeat();
        task.set(String.format("%s.taskName", this.taskName), this.taskName);
        ConfigurationSection config = task.getConfigurationSection(this.taskName);
        assert config != null;
        config.set("timeStamp", this.timestamp);
        config.set("isLoop", this.isLoop);
        config.set("repeat", this.repeat);
        config.set("commands", this.commands);
        ConfigManager.saveConfigs();
        Task.loadAllTask();
    }

    public void remove() {
        task.set(this.taskName, null);
        ConfigManager.saveConfigs();
        Task.loadAllTask();
    }

    public static void loadAllTask() {
        ArrayList<String> taskNames = new ArrayList<>(task.getKeys(false));
        tasks = new ArrayList<>();
        for (String taskName : taskNames) {
            tasks.add(getByConfig(taskName));
        }
    }

    public static Task getByConfig(String taskName) {
        ConfigurationSection config = task.getConfigurationSection(taskName);
        if (config == null) {
            return null;
        }
        String newTaskName = config.getString("taskName", "");
        int newTimeStamp = config.getInt("timeStamp", 0);
        boolean newIsLoop = config.getBoolean("isLoop", false);
        String newRepeat = config.getString("repeat", "");
        ArrayList<String> newCommands = (ArrayList<String>) config.getList("commands", new ArrayList<>());
        return new Task(newTaskName, newTimeStamp, newIsLoop, newRepeat, newCommands);
    }

    public static ArrayList<String> getTaskNames() {
        ArrayList<String> taskNames = new ArrayList<>();
        for (Task task : Task.tasks) {
            taskNames.add(task.getTaskName());
        }
        return taskNames;
    }
}
