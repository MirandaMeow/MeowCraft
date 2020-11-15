package cn.miranda.MeowCraft.Manager;

import cn.miranda.MeowCraft.MeowCraft;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import static org.bukkit.Bukkit.getServer;

public class PluginLoadManager {
    public static Economy econ = null;

    public static void LoadPlugins() {
        loadVault();
        loadPermissionsEx();
        loadmcMMO();
        loadCitizens();
        loadLibsDisguises();
    }

    private static boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return true;
    }

    public static void loadVault() {
        if (!PluginLoadManager.setupEconomy()) {
            MessageManager.ConsoleMessage("§b[§6猫子组件§b] §e未找到 Vault 或没有 Economy 管理插件, 组件即将禁用");
            Bukkit.getServer().getPluginManager().disablePlugin(MeowCraft.plugin);
        } else {
            MessageManager.ConsoleMessage("§b[§6猫子组件§b] §e发现 Vault，焊接成功");
        }
    }

    public static void loadPermissionsEx() {
        if (getServer().getPluginManager().getPlugin("PermissionsEx") == null) {
            MessageManager.ConsoleMessage("§b[§6猫子组件§b] §e未找到 PermissionsEx, 组件即将禁用");
            Bukkit.getServer().getPluginManager().disablePlugin(MeowCraft.plugin);
        } else {
            MessageManager.ConsoleMessage("§b[§6猫子组件§b] §e发现 PermissionsEx，焊接成功");
        }

    }

    public static void loadmcMMO() {
        if (getServer().getPluginManager().getPlugin("mcMMO") == null) {
            MessageManager.ConsoleMessage("§b[§6猫子组件§b] §e未找到 mcMMO, 组件即将禁用");
            Bukkit.getServer().getPluginManager().disablePlugin(MeowCraft.plugin);
        } else {
            MessageManager.ConsoleMessage("§b[§6猫子组件§b] §e发现 mcMMO，焊接成功");
        }
    }

    public static void loadCitizens() {
        if (getServer().getPluginManager().getPlugin("Citizens") == null) {
            MessageManager.ConsoleMessage("§b[§6猫子组件§b] §e未找到 Citizens, 组件即将禁用");
            Bukkit.getServer().getPluginManager().disablePlugin(MeowCraft.plugin);
        } else {
            MessageManager.ConsoleMessage("§b[§6猫子组件§b] §e发现 Citizens，焊接成功");
        }
    }

    public static void loadLibsDisguises() {
        if (getServer().getPluginManager().getPlugin("LibsDisguises") == null) {
            MessageManager.ConsoleMessage("§b[§6猫子组件§b] §e未找到 LibsDisguises, 组件即将禁用");
            Bukkit.getServer().getPluginManager().disablePlugin(MeowCraft.plugin);
        } else {
            MessageManager.ConsoleMessage("§b[§6猫子组件§b] §e发现 LibsDisguises，焊接成功");
        }
    }
}
