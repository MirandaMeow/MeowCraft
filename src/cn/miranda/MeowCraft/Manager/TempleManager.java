package cn.miranda.MeowCraft.Manager;

import cn.miranda.MeowCraft.Utils.Misc;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

import static cn.miranda.MeowCraft.Manager.ConfigManager.temples;


public class TempleManager {
    public static List<Location> TempleList;

    public static void refreshTempleLists() {
        TempleList = new ArrayList<>();
        if (temples == null) {
            return;
        }
        for (Object key : temples.getValues(false).keySet()) {
            Location tempLocation = getLocationByConfig(key);
            TempleList.add(tempLocation);
        }
    }

    public static String giveTempleName(Location location) {
        if (temples == null) {
            return "undefined";
        }
        for (Object key : temples.getValues(false).keySet()) {
            Location tempLocation = getLocationByConfig(key);
            if (location.equals(tempLocation)) {
                return key.toString();
            }
        }
        return "undefined";
    }

    public static Location getLocationByConfig(Object key) {
        ConfigurationSection temp = temples.getConfigurationSection(key.toString());
        String world = temp.getString("world");
        double x_axis = temp.getDouble("x");
        double y_axis = temp.getDouble("y");
        double z_axis = temp.getDouble("z");
        return new Location(Misc.World(world), x_axis, y_axis, z_axis);
    }
}
