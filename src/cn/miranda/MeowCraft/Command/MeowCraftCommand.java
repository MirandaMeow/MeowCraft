package cn.miranda.MeowCraft.Command;

import cn.miranda.MeowCraft.Manager.ConfigMaganer;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Manager.TempleManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.*;

import static cn.miranda.MeowCraft.MeowCraft.plugin;

public final class MeowCraftCommand implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("meow.admin")) {
            MessageManager.Messager(sender, "§c你没有权限");
            return true;
        }
        if (args.length == 0) {
            MessageManager.Messager(sender, "§e用法: §6/meowcraft §b<help|reload>");
            return true;
        }
        if (Objects.equals(args[0], "help")) {
            MessageManager.Messager(sender, "§e----------- 猫子组件 -----------");
            MessageManager.Messager(sender, "§e作者: §b猫子");
            MessageManager.Messager(sender, String.format("§e版本 §b%s", plugin.Version));
            MessageManager.Messager(sender, "§e--------------------------------");
            return true;
        }
        if (Objects.equals(args[0], "reload")) {
            ConfigMaganer.loadConfigs();
            TempleManager.refreshTempleLists();
            MessageManager.Messager(sender, "§e成功重载配置文件");
            return true;
        }
        if (Objects.equals(args[0], "save")) {
            ConfigMaganer.saveConfigs();
            MessageManager.Messager(sender, "§e配置文件已保存");
            return true;
        }
        //TODO 测试用命令，需要移除
        if (Objects.equals(args[0], "test")) {
            MessageManager.Messager(sender, "§e测试完成");
            return true;
        }
        MessageManager.Messager(sender, "§e用法: §6/meowcraft §b<help|reload>");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            return new ArrayList<>(Arrays.asList("help", "reload", "save"));
        }
        return new ArrayList<>();
    }
}