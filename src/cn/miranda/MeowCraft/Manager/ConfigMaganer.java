package cn.miranda.MeowCraft.Manager;

import cn.miranda.MeowCraft.MeowCraft;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static cn.miranda.MeowCraft.MeowCraft.plugin;

public class ConfigMaganer {
    public static YamlConfiguration config;
    public static YamlConfiguration playerData;
    public static List<List> configList;
    public static File configFile;

    public static void resetConfigList() {
        configList = new ArrayList();
    }

    public static YamlConfiguration loadFile(String fileName) {
        configFile = new File(plugin.getDataFolder(), fileName);
        if (!configFile.exists()) {
            plugin.saveResource(fileName, false);
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        List CurrentConfig = new ArrayList();
        CurrentConfig.add(config);
        CurrentConfig.add(configFile);
        configList.add(CurrentConfig);
        return config;
    }

    public static void loadConfigs() {
        resetConfigList();
        config = loadFile("config.yml");
        playerData = loadFile("playerData.yml");
    }

    public static void saveConfigs() {
        try {
            int length = configList.size();
            for (int i = 0; i < length; i++) {
                List current = configList.get(i);
                YamlConfiguration currentYaml = (YamlConfiguration) current.get(0);
                File currentFile = (File) current.get(1);
                currentYaml.save(currentFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeAllFlags() {
        for (Object key : playerData.getValues(false).keySet()) {
            playerData.set(String.format("%s.occConfig.occSkills.occCoolDown", key.toString()), null);
            playerData.set(String.format("%s.temp", key.toString()), null);
        }
        saveConfigs();
    }
}
