package cn.miranda.MeowCraft.Utils;

import cn.miranda.MeowCraft.Manager.ConfigMaganer;
import cn.miranda.MeowCraft.Task.RemoveEntityTask;
import com.sun.istack.internal.NotNull;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Random;

import static cn.miranda.MeowCraft.Manager.ConfigMaganer.config;
import static org.bukkit.Bukkit.getOnlinePlayers;
import static cn.miranda.MeowCraft.Manager.ConfigMaganer.towns;

public class Misc {
    public static Player player(@NotNull String playerName) {
        return org.bukkit.Bukkit.getPlayer(playerName);
    }

    public static List<String> getOnlinePlayerMames() {
        ArrayList<String> players = new ArrayList<>();
        for (Player player : getOnlinePlayers()) {
            players.add(player.getName());
        }
        return players;
    }

    public static boolean isInt(@NotNull String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isInventoryFull(@NotNull Player player) {
        Inventory playerInventory = player.getInventory();
        int count = 0;
        for (int i = 0; i <= 35; i++) {
            if (playerInventory.getItem(i) == null) {
                count += 1;
            }
        }
        return count == 0;
    }

    public static World World(@NotNull String worldName) {
        return Bukkit.getWorld(worldName);
    }

    public static String stringFormat(@NotNull double value) {
        return new Formatter().format("%.2f", value).toString();
    }

    public static double doubleFixed(@NotNull double value) {
        String fixed = stringFormat(value);
        return Double.parseDouble(fixed);
    }

    public static void moveForward(Player player, int ratio) {
        Vector playerVector = player.getEyeLocation().getDirection().setY(0);
        player.setVelocity(playerVector.multiply(ratio));
    }

    public static void setHP(@NotNull Player player, @NotNull int hp) {
        player.setHealth(hp);
    }

    public static void resetHP(@NotNull Player player) {
        player.resetMaxHealth();
        double maxHP = player.getMaxHealth();
        Misc.setHP(player, (int) maxHP);
    }

    public static void setMaxHP(@NotNull Player player, @NotNull int hp) {
        player.setMaxHealth(hp);
        Misc.setHP(player, hp);
    }

    public static void throwSomething(@NotNull Player player, @NotNull Material material, @NotNull int ratio, @NotNull long delay, @NotNull boolean thunder) {
        ArmorStand armorstand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
        armorstand.setVisible(false);
        armorstand.setItemInHand(new ItemStack(material, 1));
        Vector playerVector = player.getEyeLocation().getDirection().setY(0);
        armorstand.setVelocity(playerVector.multiply(ratio));
        new RemoveEntityTask().RemoveEntity(armorstand, delay, thunder);
    }

    public static Location getFixedLocation(Location location) {
        World world = location.getWorld();
        int fixedX = (int) location.getX();
        int fixedY = (int) location.getY();
        int fixedZ = (int) location.getZ();
        Location fixedLocation = new Location(world, fixedX, fixedY, fixedZ);
        fixedLocation.add(-0.5, 0, 0.5);
        return fixedLocation;

    }

    public static List getTownsPermission() {
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

    public static int randomNum(int min, int max) {
        Random rand = new Random();
        return rand.nextInt(max - min + 1) + min;
    }

    public static int getPing(Player player) throws Exception {
        // 日后升级这里需要改版本号
        Class<?> craftPlayer = Class.forName("org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer");
        Object converted = craftPlayer.cast(player);
        Method handle = converted.getClass().getMethod("getHandle");
        Object entityPlayer = handle.invoke(converted);
        Field pingField = entityPlayer.getClass().getField("ping");
        int playerPing = pingField.getInt(entityPlayer);
        return Math.min(playerPing, 9999);
    }

    public static void disableTabPing() {
        config.set("TabPing.enabled", false);
    }
}
