package cn.miranda.MeowCraft;

import cn.miranda.MeowCraft.Manager.*;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

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
        ConfigManager.loadConfigs();
        ConfigManager.removeAllFlags();
        ConfigManager.enableTabPing();
        MessageManager.ConsoleMessage("§b[§6猫子组件§b] §e载入配置文件");
        ListenersManager.registerAllEvents();
        MessageManager.ConsoleMessage("§b[§6猫子组件§b] §e注册监听器");
        MessageManager.ConsoleMessage("§b[§6猫子组件§b] §e焊接前置插件");
        PluginLoadManager.LoadPlugins();
        CommandManager.registerCommands();
        MessageManager.ConsoleMessage("§b[§6猫子组件§b] §e注册命令");
        TempleManager.refreshTempleLists();
        try {
            PlayerStatusManager.decodePlayerStatus();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        MessageManager.ConsoleMessage("§b[§6猫子组件§b] §e载入玩家数据");
        MessageManager.ConsoleMessage("§b[§6猫子组件§b] §e启用成功");
    }

    public void onDisable() {
        ConfigManager.disableTabPing();
        MessageManager.ConsoleMessage("§b[§6猫子组件§b] §e正在禁用");
        HandlerList.unregisterAll(this);
        MessageManager.ConsoleMessage("§b[§6猫子组件§b] §e清除监听器");
        getScheduler().cancelTasks(this);
        MessageManager.ConsoleMessage("§b[§6猫子组件§b] §e清除任务");
        ConfigManager.saveConfigs();
        MessageManager.ConsoleMessage("§b[§6猫子组件§b] §e保存配置文件");
        MessageManager.ConsoleMessage("§b[§6猫子组件§b] §e禁用成功");
    }
}