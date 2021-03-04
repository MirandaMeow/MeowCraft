package cn.miranda.MeowCraft.Command;

import cn.miranda.MeowCraft.Cores.Task;
import cn.miranda.MeowCraft.Enum.Notify;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Utils.Misc;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.*;

public class TaskCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("task.admin")) {
            MessageManager.Message(sender, Notify.No_Permission.getString());
            return true;
        }
        if (args.length < 1) {
            MessageManager.Message(sender, "§e用法: §6/task §b<create|delete|edit|list> <taskName>");
            return true;
        }
        String option = args[0];
        ArrayList<String> validOption = new ArrayList<>(Arrays.asList("create", "delete", "edit", "list"));
        if (!validOption.contains(option)) {
            MessageManager.Message(sender, "§e用法: §6/task §b<create|delete|edit|list> <taskName>");
            return true;
        }
        if (Objects.equals(option, "create") && args.length == 2) {
            String taskName = args[1];
            Task task = new Task(taskName);
            task.saveToConfig();
            MessageManager.Message(sender, String.format("§e已创建计划任务 §b%s", taskName));
            return true;
        }
        if (Objects.equals(option, "delete") && args.length == 2) {
            String taskName = args[1];
            Task task = Task.getByConfig(taskName);
            if (task == null) {
                MessageManager.Message(sender, String.format("§c计划任务 §b%s §c不存在", taskName));
                return true;
            }
            task.remove();
            MessageManager.Message(sender, String.format("§e计划任务 §b%s §e已删除", taskName));
            return true;
        }
        if (Objects.equals(option, "edit") && args.length >= 3) {
            String taskName = args[1];
            Task task = Task.getByConfig(taskName);
            if (task == null) {
                MessageManager.Message(sender, String.format("§c计划任务 §b%s §c不存在", taskName));
                return true;
            }
            String subOption = args[2];
            ArrayList<String> validSubOption = new ArrayList<>(Arrays.asList("time", "loop", "repeat", "add", "remove"));
            if (!validSubOption.contains(subOption)) {
                MessageManager.Message(sender, Notify.Invalid_Input.getString());
                return true;
            }
            if (Objects.equals(subOption, "time") && args.length > 3) {
                ArrayList<String> argList = new ArrayList<>(Arrays.asList(args));
                String time = String.join(" ", argList.subList(3, argList.size()));
                int timeStamp = Misc.stringToTimeStamp(time);
                if (timeStamp == 0) {
                    MessageManager.Message(sender, "§c时间格式为 yyyy-MM-dd hh:mm:ss");
                    return true;
                }
                task.setTimestamp(timeStamp);
                task.saveToConfig();
                MessageManager.Message(sender, String.format("§e时间设定为 §b%s", time));
                return true;
            }
            if (Objects.equals(subOption, "loop") && args.length == 4) {
                String loop = args[3];
                if (Objects.equals(loop, "true")) {
                    task.setLoop(true);
                    MessageManager.Message(sender, "§e是否重复设定为 §b是");
                } else if (Objects.equals(loop, "false")) {
                    task.setLoop(false);
                    MessageManager.Message(sender, "§e是否重复设定为 §b否");
                } else {
                    MessageManager.Message(sender, "§e必须为 §btrue §e或者 §bfalse");
                }
                task.saveToConfig();
                return true;
            }
            if (Objects.equals(subOption, "repeat") && args.length == 4) {
                String repeat = args[3];
                task.setRepeat(repeat);
                task.saveToConfig();
                MessageManager.Message(sender, String.format("§e重复时间设置为 §b%s", repeat));
                return true;
            }
            if (Objects.equals(subOption, "add") && args.length > 3) {
                ArrayList<String> argList = new ArrayList<>(Arrays.asList(args));
                String commandLine = String.join(" ", argList.subList(3, argList.size()));
                task.addCommand(commandLine);
                task.saveToConfig();
                MessageManager.Message(sender, String.format("§e已添加命令 §b%s", commandLine));
                return true;
            }
            if (Objects.equals(subOption, "remove") && args.length == 4) {
                String index = args[3];
                if (!Misc.isInt(index)) {
                    MessageManager.Message(sender, "§c索引项目必须是数字");
                    return true;
                }
                int intIndex = Integer.parseInt(index);
                if (intIndex - 1 >= task.getCommands().size() || intIndex <= 0) {
                    MessageManager.Message(sender, "§c索引超出范围");
                    return true;
                }
                String removeCommand = task.getCommandLine(intIndex - 1);
                task.removeCommand(intIndex - 1);
                task.saveToConfig();
                MessageManager.Message(sender, String.format("§e已删除命令 §b%s", removeCommand));
                return true;
            }
        }
        if (Objects.equals(option, "list") && args.length == 1) {
            if (Task.tasks.size() == 0) {
                MessageManager.Message(sender, "§e没有计划任务");
                return true;
            }
            if (sender instanceof Player) {
                MessageManager.Message(sender, "§b---- §e计划任务 §b----");
                for (Task task : Task.tasks) {
                    task.show(sender, false);
                }
                MessageManager.Message(sender, "§b-------------------");
            } else {
                MessageManager.Message(sender, "§b---- §e计划任务 §b----");
                for (Task task : Task.tasks) {
                    task.show(sender, true);
                }
                MessageManager.Message(sender, "§b-------------------");
            }
            return true;
        }
        MessageManager.Message(sender, "§e用法: §6/task §b<create|delete|edit|list> <taskName>");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            ArrayList<String> validOption = new ArrayList<>(Arrays.asList("create", "delete", "edit", "list"));
            return Misc.returnSelectList(validOption, strings[0]);
        }
        if (strings.length == 2) {
            if (Objects.equals(strings[0], "delete") || Objects.equals(strings[0], "edit")) {
                return Misc.returnSelectList(Task.getTaskNames(), strings[1]);
            }
        }
        if (strings.length == 3 && Objects.equals(strings[0], "edit")) {
            ArrayList<String> validSubOption = new ArrayList<>(Arrays.asList("time", "loop", "repeat", "add", "remove"));
            return Misc.returnSelectList(validSubOption, strings[2]);
        }
        if (strings.length == 4 && Objects.equals(strings[0], "edit") && Objects.equals(strings[2], "loop")) {
            return Misc.returnSelectList(new ArrayList<>(Arrays.asList("true", "false")), strings[3]);
        }
        if (strings.length == 4 && Objects.equals(strings[0], "edit") && Objects.equals(strings[2], "time")) {
            return Misc.returnSelectList(new ArrayList<>(Collections.singletonList(Misc.showDate())), strings[3]);
        }
        if (strings.length == 4 && Objects.equals(strings[0], "edit") && Objects.equals(strings[2], "remove")) {
            Task task = Task.getByConfig(strings[1]);
            if (task == null) {
                return new ArrayList<>();
            } else {
                return Misc.returnSelectList(task.getCommandIndexList(), strings[3]);
            }
        }
        return new ArrayList<>();
    }
}
