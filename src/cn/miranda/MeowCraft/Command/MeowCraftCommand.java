package cn.miranda.MeowCraft.Command;

import cn.miranda.MeowCraft.Enum.EggCatcher;
import cn.miranda.MeowCraft.Enum.Notify;
import cn.miranda.MeowCraft.Manager.ClassManager;
import cn.miranda.MeowCraft.Manager.ConfigManager;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Manager.TempleManager;
import cn.miranda.MeowCraft.Utils.Misc;
import cn.miranda.MeowCraft.Utils.SkillLib;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import javax.xml.soap.Text;
import java.util.*;

import static cn.miranda.MeowCraft.MeowCraft.plugin;

public final class MeowCraftCommand implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("meow.admin")) {
            MessageManager.Message(sender, Notify.No_Permission.getString());
            return true;
        }
        if (args.length == 0) {
            MessageManager.Message(sender, "§e用法: §6/meowcraft §b<help|reload|save|debug>");
            return true;
        }
        if (Objects.equals(args[0], "help")) {
            MessageManager.Message(sender, "§e----------- 猫子组件 -----------");
            MessageManager.Message(sender, "§e作者: §b猫子");
            MessageManager.Message(sender, String.format("§e版本 §b%s", plugin.Version));
            MessageManager.Message(sender, "§e--------------------------------");
            return true;
        }
        if (Objects.equals(args[0], "reload")) {
            ConfigManager.loadConfigs();
            TempleManager.refreshTempleLists();
            EggCatcher.flushAvailable();
            MessageManager.Message(sender, "§e成功重载配置文件");
            return true;
        }
        if (Objects.equals(args[0], "save")) {
            ConfigManager.saveConfigs();
            MessageManager.Message(sender, "§e配置文件已保存");
            return true;
        }
        //TODO 测试用命令，需要移除
        if (Objects.equals(args[0], "debug")) {
            ClassManager.loadClass();
            MessageManager.Message(sender, "§e所有类加载完成");
            return true;
        }
        //TODO 测试用命令，需要移除
        if (Objects.equals(args[0], "test")) {
            MessageManager.Message(sender, "§e测试完成");
            return true;
        }
        MessageManager.Message(sender, "§e用法: §6/meowcraft §b<help|reload|save|debug>");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            return Misc.returnSelectList(new ArrayList<>(Arrays.asList("help", "reload", "save", "debug")), strings[0]);
        }
        return new ArrayList<>();
    }
}