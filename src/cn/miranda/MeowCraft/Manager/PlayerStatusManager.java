package cn.miranda.MeowCraft.Manager;

import cn.miranda.MeowCraft.Utils.Misc;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.tehkode.libs.com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

public class PlayerStatusManager {
    public static HashMap<Player, PlayerStatusManager> playerStatus = new HashMap<>();
    public GameMode gameMode;
    public ItemStack[] inventory;
    public float exp;
    public int level;
    public int food;
    public boolean isFlying;

    public PlayerStatusManager(GameMode gameMode, ItemStack[] inventory, float exp, int level, int food, boolean isFlying) {
        this.gameMode = gameMode;
        this.inventory = inventory;
        this.exp = exp;
        this.level = level;
        this.food = food;
        this.isFlying = isFlying;
    }

    public static void setDefault(Player player) {
        PlayerStatusManager playerStatusManager = new PlayerStatusManager(
                player.getGameMode(),
                player.getInventory().getContents(),
                player.getExp(),
                player.getLevel(),
                player.getFoodLevel(),
                player.getAllowFlight()
        );
        playerStatus.put(player, playerStatusManager);
        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().clear();
        player.setExp(0);
        player.setLevel(0);
        player.setMaxHealth(20);
        player.setFoodLevel(20);
        player.setFlying(false);
    }

    public static void setRestore(Player player) {
        if (playerStatus.get(player) == null) {
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
        playerStatus.remove(player);
    }
}
