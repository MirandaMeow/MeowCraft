package cn.miranda.MeowCraft.Manager;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static cn.miranda.MeowCraft.MeowCraft.plugin;

public class ConfigManager {
    public static YamlConfiguration config;
    public static YamlConfiguration skills;
    public static YamlConfiguration playerData;
    public static YamlConfiguration temples;
    public static YamlConfiguration towns;
    public static YamlConfiguration cache;
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

    public static void removeAllFlags() {
        cache.set("OccSkillCoolDown", null);
        saveConfigs();
    }
}
