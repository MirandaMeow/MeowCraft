package cn.miranda.MeowCraft.Manager;

import cn.miranda.MeowCraft.Task.TabPingTask;
import cn.miranda.MeowCraft.Utils.IO;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static cn.miranda.MeowCraft.Manager.PlayerStatusManager.playerStatus;
import static cn.miranda.MeowCraft.Manager.TradeManager.trade;
import static cn.miranda.MeowCraft.MeowCraft.plugin;

public class ConfigManager {
    public static YamlConfiguration config;
    public static YamlConfiguration skills;
    public static YamlConfiguration playerData;
    public static YamlConfiguration temples;
    public static YamlConfiguration towns;
    public static YamlConfiguration cache;
    public static YamlConfiguration trades;
    public static YamlConfiguration monsterCard;
    public static HashMap<YamlConfiguration, File> configList = new HashMap<>();
    public static File configFile;

    public static YamlConfiguration loadFile(String fileName) {
        configFile = new File(plugin.getDataFolder(), fileName);
        if (!configFile.exists()) {
            plugin.saveResource(fileName, false);
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        configList.put(config, configFile);
        return config;
    }

    public static void loadConfigs() {
        configList = new HashMap<>();
        config = loadFile("config.yml");
        skills = loadFile("skills.yml");
        playerData = loadFile("playerData.yml");
        temples = loadFile("temples.yml");
        towns = loadFile("towns.yml");
        cache = loadFile("cache.yml");
        trades = loadFile("trades.yml");
        monsterCard = loadFile("monsterCard.yml");
    }

    public static void saveConfigs() {
        try {
            for (Map.Entry<YamlConfiguration, File> current : configList.entrySet()) {
                YamlConfiguration currentYaml = current.getKey();
                File currentFile = current.getValue();
                currentYaml.save(currentFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void enableTabPing() {
        new TabPingTask().TabPing();
        cache.set("TabPing.enabled", true);
        ConfigManager.saveConfigs();
    }

    public static void disableTabPing() {
        cache.set("TabPing.enabled", false);
        ConfigManager.saveConfigs();
    }

    public static void removeAllFlags() {
        cache.set("OccSkillCoolDown", null);
        saveConfigs();
    }

    public static void loadPlayerStatusData() {
        try {
            Object getObject = IO.decodeData(cache.getString("PlayerStatus"));
            if (getObject == null) {
                playerStatus = new HashMap<>();
            } else {
                playerStatus = (HashMap<Player, PlayerStatusManager>) getObject;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void loadTradePanelData() {
        try {
            Object getObject = IO.decodeData(trades.getString("trade"));
            if (getObject == null) {
                trade = new HashMap<>();
            } else {
                trade = (HashMap<Integer, TradeManager>) getObject;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
