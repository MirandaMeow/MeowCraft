package cn.miranda.MeowCraft;

import cn.miranda.MeowCraft.Manager.*;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.Bukkit.getScheduler;

public class MeowCraft extends JavaPlugin {
    public static MeowCraft plugin;
    public String Version;

    public MeowCraft() {
        plugin = this;
        Version = "0.1.1";
    }

    public void onEnable() {
        MessageManager.ConsoleMessage("§b[§6猫子组件§b] §e正在启用");
        ConfigMaganer.loadConfigs();
        ConfigMaganer.removeAllFlags();
        ListenersManager.registerAllEvents();
        PluginLoadManager.LoadPlugins();
        CommandManager.registerCommands();
        TempleManager.refreshTempleLists();
    }

    public void onDisable() {
        MessageManager.ConsoleMessage("§b[§6猫子组件§b] §e正在禁用");
        HandlerList.unregisterAll(this);
        MessageManager.ConsoleMessage("§b[§6猫子组件§b] §e清除监听器");
        getScheduler().cancelTasks(this);
        MessageManager.ConsoleMessage("§b[§6猫子组件§b] §e清除任务");
        ConfigMaganer.saveConfigs();
        MessageManager.ConsoleMessage("§b[§6猫子组件§b] §e保存配置文件");
        MessageManager.ConsoleMessage("§b[§6猫子组件§b] §e已禁用");
    }
}