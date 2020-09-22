package cn.miranda.MeowCraft.Manager;

import cn.miranda.MeowCraft.Utils.Misc;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

import static cn.miranda.MeowCraft.Manager.ConfigManager.cache;

public class PlayerStatusManager implements Serializable {
    public static HashMap<Player, PlayerStatusManager> playerStatus = new HashMap<>();
    public final GameMode gameMode;
    public final ItemStack[] inventory;
    public final float exp;
    public final int level;
    public final int food;
    public final boolean isFlying;
    public final List<String> groups;
    public final List<String> permissions;
    public final Collection<PotionEffect> potionEffects;

    public PlayerStatusManager(GameMode gameMode,
                               ItemStack[] inventory,
                               float exp,
                               int level,
                               int food,
                               boolean isFlying,
                               List<String> groups,
                               List<String> permissions,
                               Collection<PotionEffect> potionEffects
    ) {
        this.gameMode = gameMode;
        this.inventory = inventory;
        this.exp = exp;
        this.level = level;
        this.food = food;
        this.isFlying = isFlying;
        this.groups = groups;
        this.permissions = permissions;
        this.potionEffects = potionEffects;
    }

    public static void setDefault(Player player) {
        if (playerStatus.containsKey(player)) {
            MessageManager.Message(player, "§e已存在玩家数据, 跳过保存");
            return;
        }
        PlayerStatusManager playerStatusManager = new PlayerStatusManager(
                player.getGameMode(),
                player.getInventory().getContents(),
                player.getExp(),
                player.getLevel(),
                player.getFoodLevel(),
                player.getAllowFlight(),
                getPlayerPermissionGroups(player),
                getPlayerPermissions(player),
                player.getActivePotionEffects()
        );
        playerStatus.put(player, playerStatusManager);
        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().clear();
        player.setExp(0);
        player.setLevel(0);
        player.setMaxHealth(20);
        player.setFoodLevel(20);
        player.setFlying(false);
        removeAllPotionEffect(player);
        try {
            cache.set("PlayerStatus", encodePlayerStatus());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ConfigManager.saveConfigs();
        MessageManager.Message(player, "§e玩家数据已保存");
    }

    public static void setRestore(Player player) {
        if (playerStatus.get(player) == null) {
            MessageManager.Message(player, "§c没有你的数据");
            return;
        }
        PlayerStatusManager playerStatusManager = playerStatus.get(player);
        player.setGameMode(playerStatusManager.gameMode);
        player.setMaxHealth(Misc.getTempleVisitAmount(player) + 20);
        player.getInventory().clear();
        player.getInventory().setContents(playerStatusManager.inventory);
        player.setExp(playerStatusManager.exp);
        player.setLevel(playerStatusManager.level);
        player.setFoodLevel(playerStatusManager.food);
        player.setFlying(playerStatusManager.isFlying);
        player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        restorePlayerPermissionGroups(player, playerStatusManager.groups);
        restorePlayerPermissions(player, playerStatusManager.permissions);
        player.addPotionEffects(playerStatusManager.potionEffects);
        playerStatus.remove(player);
        try {
            cache.set("PlayerStatus", encodePlayerStatus());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ConfigManager.saveConfigs();
        MessageManager.Message(player, "§e玩家数据已恢复");
    }

    private static List<String> getPlayerPermissionGroups(Player player) {
        PermissionUser pexUser = PermissionsEx.getUser(player);
        Map<String, List<PermissionGroup>> groupMap = pexUser.getAllParents();
        pexUser.setParents(new ArrayList<>());
        pexUser.addGroup("Event");
        List<String> out = new ArrayList<>();
        List<PermissionGroup> permissionGroups = groupMap.entrySet().stream().findFirst().get().getValue();
        if (permissionGroups.size() == 0) {
            return new ArrayList<>();
        }
        for (PermissionGroup current : permissionGroups) {
            out.add(current.getName());
        }
        return out;
    }

    private static void restorePlayerPermissionGroups(Player player, List<String> groups) {
        PermissionUser pexUser = PermissionsEx.getUser(player);
        if (groups.size() == 0) {
            pexUser.setParents(new ArrayList<>(Collections.singletonList(PermissionsEx.getPermissionManager().getGroup("default"))));
        }
        List<PermissionGroup> pexGroup = new ArrayList<>();
        for (String current : groups) {
            pexGroup.add(PermissionsEx.getPermissionManager().getGroup(current));
        }
        pexUser.setParents(pexGroup);
    }

    private static List<String> getPlayerPermissions(Player player) {
        PermissionUser pexUser = PermissionsEx.getUser(player);
        Map<String, List<String>> permissionMap = pexUser.getAllPermissions();
        pexUser.setPermissions(new ArrayList<>());
        return permissionMap.entrySet().stream().findFirst().get().getValue();
    }

    private static void restorePlayerPermissions(Player player, List<String> permissions) {
        PermissionUser pexUser = PermissionsEx.getUser(player);
        pexUser.setPermissions(permissions);
    }

    private static void removeAllPotionEffect(Player player) {
        for (PotionEffect potionEffect : player.getActivePotionEffects()) {
            player.removePotionEffect(potionEffect.getType());
        }
    }

    private static String encodePlayerStatus() throws IOException {
        BASE64Encoder encoder = new BASE64Encoder();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BukkitObjectOutputStream objectOutputStream = new BukkitObjectOutputStream(outputStream);
        objectOutputStream.writeObject(playerStatus);
        return encoder.encode(outputStream.toByteArray());
    }

    public static void decodePlayerStatus() throws IOException, ClassNotFoundException {
        BASE64Decoder decoder = new BASE64Decoder();
        String json = cache.getString("PlayerStatus");
        if (json == null) {
            return;
        }
        ByteArrayInputStream inputStream = new ByteArrayInputStream(decoder.decodeBuffer(json));
        BukkitObjectInputStream objectInputStream = new BukkitObjectInputStream(inputStream);
        playerStatus = (HashMap<Player, PlayerStatusManager>) objectInputStream.readObject();
    }
}
