package cn.miranda.MeowCraft.Utils;

import cn.miranda.MeowCraft.Task.RemoveEntityTask;
import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import com.sun.istack.internal.NotNull;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Pattern;

import static cn.miranda.MeowCraft.Manager.ConfigManager.playerData;
import static org.bukkit.Bukkit.getOnlinePlayers;

public class Misc {
    public static Player player(@NotNull String playerName) {
        return Bukkit.getPlayer(playerName);
    }

    public static List<String> getOnlinePlayerNames() {
        ArrayList<String> players = new ArrayList<>();
        for (Player player : getOnlinePlayers()) {
            players.add(player.getName());
        }
        return players;
    }

    public static boolean isInt(@NotNull String string) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(string).matches();
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
        armorstand.setItemInHand(new ItemStack(material));
        Vector playerVector = player.getEyeLocation().getDirection().setY(0);
        armorstand.setVelocity(playerVector.multiply(ratio));
        new RemoveEntityTask().RemoveEntity(armorstand, delay, thunder);
    }

    public static Location getFixedLocation(@NotNull Location location) {
        World world = location.getWorld();
        int fixedX = (int) location.getX();
        int fixedY = (int) location.getY();
        int fixedZ = (int) location.getZ();
        Location fixedLocation = new Location(world, fixedX, fixedY, fixedZ);
        fixedLocation.add(0.5, 0, 0.5);
        return fixedLocation;

    }

    public static int randomNum(@NotNull int min, @NotNull int max) {
        Random rand = new Random();
        return rand.nextInt(max - min + 1) + min;
    }

    public static int getPing(@NotNull Player player) throws Exception {
        // TODO 日后升级这里需要改版本号
        Class<?> craftPlayer = Class.forName("org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer");
        Object converted = craftPlayer.cast(player);
        Method handle = converted.getClass().getMethod("getHandle");
        Object entityPlayer = handle.invoke(converted);
        Field pingField = entityPlayer.getClass().getField("ping");
        int playerPing = pingField.getInt(entityPlayer);
        return Math.min(playerPing, 9999);
    }

    @Deprecated
    public static double getPlayerRealMaxHealth(@NotNull Player player) {
        double Op0 = 0;
        double Op1 = 0;
        double Op2 = 1;
        double playerMaxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        List<EquipmentSlot> slots = new ArrayList<>(Arrays.asList(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET, EquipmentSlot.HAND, EquipmentSlot.OFF_HAND));
        for (EquipmentSlot slot : slots) {
            ItemStack itemstack = player.getEquipment().getItem(slot);
            if (itemstack == null) {
                continue;
            }
            if (itemstack.hasItemMeta()) {
                ItemMeta itemMeta = itemstack.getItemMeta();
                for (AttributeModifier itemAttr : itemMeta.getAttributeModifiers(Attribute.GENERIC_MAX_HEALTH)) {
                    if (itemAttr == null) {
                        continue;
                    }
                    if (itemAttr.getSlot() != slot) {
                        continue;
                    }
                    AttributeModifier.Operation operation = itemAttr.getOperation();
                    switch (operation) {
                        case ADD_NUMBER:
                            Op0 += itemAttr.getAmount();
                            continue;
                        case ADD_SCALAR:
                            Op1 += itemAttr.getAmount();
                            continue;
                        case MULTIPLY_SCALAR_1:
                            Op2 *= itemAttr.getAmount() + 1;
                    }
                }
            }
        }
        return Math.round(playerMaxHealth / Op2 / (Op1 + 1) - Op0);
    }

    public static int getTempleVisitAmount(Player player) {
        ConfigurationSection configurationSection = playerData.getConfigurationSection(String.format("%s.temples", player.getName()));
        if (configurationSection == null) {
            return 0;
        }
        return configurationSection.getValues(false).keySet().size();
    }

    @Deprecated
    public static String test(Player player) {
        Essentials ess = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");
        User user = ess.getUserMap().getUser(player.getName());
        return user.getNick(true);
    }

    public static List<String> returnSelectList(List<String> list, String select) {
        List<String> selected = new ArrayList<>();
        for (String s : list) {
            if (s.contains(select)) {
                selected.add(s);
            }
        }
        if (selected.size() != 0) {
            return selected;
        }
        return list;
    }

    public static boolean canDouble(String string) {
        Pattern pattern = Pattern.compile("[0-9]*|[0-9]*\\.[0-9]*");
        return pattern.matcher(string).matches();
    }
}
