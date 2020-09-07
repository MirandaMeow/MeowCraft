package cn.miranda.MeowCraft;

import cn.miranda.MeowCraft.Manager.*;
import cn.miranda.MeowCraft.Utils.Misc;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.Bukkit.getScheduler;

public class MeowCraft extends JavaPlugin {
    public static MeowCraft plugin;
    public String Version;

    public MeowCraft() {
        plugin = this;
        Version = this.getDescription().getVersion();
    }

    public void onEnable() {
        MessageManager.ConsoleMessage("§b[§6猫子组件§b] §e正在启用");
        ConfigMaganer.loadConfigs();
        ConfigMaganer.removeAllFlags();
        MessageManager.ConsoleMessage("§b[§6猫子组件§b] §e载入配置文件");
        ListenersManager.registerAllEvents();
        MessageManager.ConsoleMessage("§b[§6猫子组件§b] §e注册监听器");
        MessageManager.ConsoleMessage("§b[§6猫子组件§b] §e焊接前置插件");
        PluginLoadManager.LoadPlugins();
        CommandManager.registerCommands();
        MessageManager.ConsoleMessage("§b[§6猫子组件§b] §e注册命令");
        TempleManager.refreshTempleLists();
        MessageManager.ConsoleMessage("§b[§6猫子组件§b] §e成功完成初始化");
    }

    public void onDisable() {
        MessageManager.ConsoleMessage("§b[§6猫子组件§b] §e正在禁用");
        Misc.disableTabPing();
        HandlerList.unregisterAll(this);
        MessageManager.ConsoleMessage("§b[§6猫子组件§b] §e清除监听器");
        getScheduler().cancelTasks(this);
        MessageManager.ConsoleMessage("§b[§6猫子组件§b] §e清除任务");
        ConfigMaganer.saveConfigs();
        MessageManager.ConsoleMessage("§b[§6猫子组件§b] §e保存配置文件");
        MessageManager.ConsoleMessage("§b[§6猫子组件§b] §e已禁用");
    }
}