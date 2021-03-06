package cn.miranda.MeowCraft.Utils;

import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Task.MonsterCardTimeTask;
import cn.miranda.MeowCraft.Task.Skill.RemoveEntityTask;
import com.sun.istack.internal.NotNull;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.miranda.MeowCraft.Manager.ConfigManager.monsterCard;
import static cn.miranda.MeowCraft.Manager.ConfigManager.playerData;
import static cn.miranda.MeowCraft.Utils.SkillLib.summons;
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

    public static String div(int a, int b) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format((float) a / b);
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
        Class<?> craftPlayer = Class.forName("org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer");
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

    public static List<Entity> getAllSummons() {
        List<Entity> out = new ArrayList<>();
        for (Map.Entry<Player, List<Entity>> entry : summons.entrySet()) {
            out.addAll(entry.getValue());
        }
        return out;
    }

    public static boolean deductItem(Player player, ItemStack itemStack, int amount) {
        Inventory inventory = player.getInventory();
        for (int i = 0; i < 36; i++) {
            ItemStack current = inventory.getItem(i);
            if (current == null) {
                continue;
            }
            if (current.getType() != itemStack.getType()) {
                continue;
            }
            if (current.getAmount() < amount) {
                continue;
            }
            current.setAmount(current.getAmount() - amount);
            return true;
        }
        return false;
    }

    public static List<EntityType> getMonsterCardTypes() {
        List<EntityType> list = new ArrayList<>();
        for (Object key : monsterCard.getValues(false).keySet()) {
            list.add(EntityType.valueOf(key.toString()));
        }
        return list;
    }

    public static void disguisePlayer(Player player, EntityType entityType) {
        DisguiseAPI.disguiseEntity(player, new MobDisguise(DisguiseType.valueOf(entityType.name())));
    }

    public static void activeMonsterCard(Player player, String entityType, int duration) {
        for (String current : monsterCard.getConfigurationSection(String.format("%s.effects", entityType)).getValues(false).keySet()) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(current), duration * 20, monsterCard.getInt(String.format("%s.effects.%s", entityType, current))));
        }
        player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        playerData.set(String.format("%s.monsterCard.duration", player.getName()), duration);
        playerData.set(String.format("%s.monsterCard.type", player.getName()), entityType);
        new MonsterCardTimeTask().MonsterCardTime(player);
    }

    public static ArrayList<NoteWithTime> readPlayerNote(Player player) {
        ArrayList<NoteWithTime> noteAll = new ArrayList<>();
        if (!player.getInventory().getItemInMainHand().getType().equals(Material.WRITABLE_BOOK)) {
            return null;
        }
        ItemStack book = player.getInventory().getItemInMainHand();
        BookMeta bookMeta = (BookMeta) book.getItemMeta();
        assert bookMeta != null;
        StringBuilder outString = new StringBuilder();
        for (String s : bookMeta.getPages()) {
            outString.append(s);
        }
        String notes = outString.toString();
        String tonality = notes.split(" ")[0];
        ArrayList<String> valid = new ArrayList<>(Arrays.asList("C", "D", "E", "F", "G", "A", "B"));
        if (!valid.contains(tonality.toUpperCase())) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder(notes);
        stringBuilder.deleteCharAt(0);
        stringBuilder.deleteCharAt(0);
        notes = stringBuilder.toString();
        Pattern pattern1 = Pattern.compile("(\\(.+?\\))");
        Matcher matcher1 = pattern1.matcher(notes);
        notes = matcher1.replaceAll("$1,");
        Pattern pattern2 = Pattern.compile("(?<=\\d| )(\\()");
        Matcher matcher2 = pattern2.matcher(notes);
        String[] notesWithDot = matcher2.replaceAll(",$1").split(",");
        ArrayList<String> noteString = new ArrayList<>(Arrays.asList(notesWithDot));
        for (String string : noteString) {
            try {
                ArrayList<NoteWithTime> temp = convert(string, tonality);
                noteAll.addAll(temp);
            } catch (Exception e) {
                MessageManager.Message(player, "§c乐谱有误");
                return null;
            }
        }
        return noteAll;
    }

    public static ArrayList<NoteWithTime> convert(String string, String tonality) {
        ArrayList<NoteWithTime> out = new ArrayList<>();
        boolean series = false;
        if (string.contains("(")) {
            series = true;
        }
        string = string.replace("(", "").replace(")", "");
        Pattern pattern = Pattern.compile("(\\d)");
        Matcher matcher = pattern.matcher(string);
        string = matcher.replaceAll("$1,");
        string = string.replace(" ", " ,");
        string = string.replace("\n", "");
        String[] strings = string.split(",");
        ArrayList<String> temp = new ArrayList<>(Arrays.asList(strings));
        int count = temp.size();
        if (!series) {
            count = 1;
        }
        for (String s : temp) {
            int pitch;
            if (s.contains("b") && s.contains("+")) {
                Pattern pattern1 = Pattern.compile("(\\d)");
                Matcher matcher1 = pattern1.matcher(s);
                if (matcher1.find()) {
                    pitch = Integer.parseInt(matcher1.group(1));
                    Note note = new Note(pitch, tonality, 1);
                    out.add(new NoteWithTime(note.getNote(-1), count));
                    continue;
                }
            }
            if (s.contains("b") && s.contains("-")) {
                Pattern pattern1 = Pattern.compile("(\\d)");
                Matcher matcher1 = pattern1.matcher(s);
                if (matcher1.find()) {
                    pitch = Integer.parseInt(matcher1.group(1));
                    Note note = new Note(pitch, tonality, -1);
                    out.add(new NoteWithTime(note.getNote(-1), count));
                    continue;
                }
            }
            if (s.contains("#") && s.contains("+")) {
                Pattern pattern1 = Pattern.compile("(\\d)");
                Matcher matcher1 = pattern1.matcher(s);
                if (matcher1.find()) {
                    pitch = Integer.parseInt(matcher1.group(1));
                    Note note = new Note(pitch, tonality, 1);
                    out.add(new NoteWithTime(note.getNote(1), count));
                    continue;
                }
            }
            if (s.contains("#") && s.contains("-")) {
                Pattern pattern1 = Pattern.compile("(\\d)");
                Matcher matcher1 = pattern1.matcher(s);
                if (matcher1.find()) {
                    pitch = Integer.parseInt(matcher1.group(1));
                    Note note = new Note(pitch, tonality, -1);
                    out.add(new NoteWithTime(note.getNote(1), count));
                    continue;
                }
            }
            if (s.contains("b")) {
                Pattern pattern1 = Pattern.compile("(\\d)");
                Matcher matcher1 = pattern1.matcher(s);
                if (matcher1.find()) {
                    pitch = Integer.parseInt(matcher1.group(1));
                    Note note = new Note(pitch, tonality, 0);
                    out.add(new NoteWithTime(note.getNote(-1), count));
                    continue;
                }
            }
            if (s.contains("#")) {
                Pattern pattern1 = Pattern.compile("(\\d)");
                Matcher matcher1 = pattern1.matcher(s);
                if (matcher1.find()) {
                    pitch = Integer.parseInt(matcher1.group(1));
                    Note note = new Note(pitch, tonality, 0);
                    out.add(new NoteWithTime(note.getNote(1), count));
                    continue;
                }
            }
            if (s.contains("+")) {
                Pattern pattern1 = Pattern.compile("(\\d)");
                Matcher matcher1 = pattern1.matcher(s);
                if (matcher1.find()) {
                    pitch = Integer.parseInt(matcher1.group(1));
                    Note note = new Note(pitch, tonality, 1);
                    out.add(new NoteWithTime(note.getNote(0), count));
                    continue;
                }
            }
            if (s.contains("-")) {
                Pattern pattern1 = Pattern.compile("(\\d)");
                Matcher matcher1 = pattern1.matcher(s);
                if (matcher1.find()) {
                    pitch = Integer.parseInt(matcher1.group(1));
                    Note note = new Note(pitch, tonality, -1);
                    out.add(new NoteWithTime(note.getNote(0), count));
                    continue;
                }
            }
            if (s.contains(" ")) {
                out.add(new NoteWithTime(null, count));
                continue;

            }
            Note note = new Note(Integer.parseInt(s), tonality, 0);
            out.add(new NoteWithTime(note.getNote(0), count));
        }
        return out;
    }

    public static int getSecondTimestampFromDate(Date date) {
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime());
        int length = timestamp.length();
        if (length > 3) {
            return Integer.parseInt(timestamp.substring(0, length - 3));
        } else {
            return 0;
        }
    }

    public static int stringToTimeStamp(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            return getSecondTimestampFromDate(dateFormat.parse(dateString));
        } catch (ParseException e) {
            return 0;
        }
    }
    public static String showDate() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
}
