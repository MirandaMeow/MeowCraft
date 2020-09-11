package cn.miranda.MeowCraft.Utils;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static cn.miranda.MeowCraft.Manager.ConfigManager.temp;
import static cn.miranda.MeowCraft.Manager.ConfigManager.towns;

public class Town {
    public static List<String> getTownsPermission() {
        List<String> permList = new ArrayList<>();
        for (Object key : towns.getValues(false).keySet()) {
            String perm = towns.getString(String.format("%s.permission", key.toString()));
            permList.add(perm);
        }
        return permList;
    }

    public static String getPlayerTownChinese(Player player) {
        for (Object key : towns.getValues(false).keySet()) {
            if (player.hasPermission(towns.getString(String.format("%s.permission", key.toString())))) {
                return key.toString();
            }
        }
        return "undefined";
    }

    public static List<String> getTownList() {
        List<String> townList = new ArrayList<String>();
        for (Object key : towns.getValues(false).keySet()) {
            townList.add(key.toString());
        }
        return townList;
    }

    public static List<String> getApplyList(String townName) {
        List<String> applyList = new ArrayList<String>();
        ConfigurationSection applySection = temp.getConfigurationSection("TownApply");
        for (Object key : applySection.getValues(false).keySet()) {
            if (Objects.equals(temp.getString(String.format("TownApply.%s", key.toString())), townName)) {
                applyList.add(key.toString());
            }
        }
        return applyList;
    }

    public static String getTownPermissionGroup(String townName) {
        return towns.getString(String.format("%s.permission", townName)).replace(".", "_");
    }
}
