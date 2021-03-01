package cn.miranda.MeowCraft.Cores;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

public class Treasure implements Serializable {
    private static final long serialVersionUID = -9028865014202729817L;
    private ItemStack[] items;
    private final String displayName;
    private final String perm;

    public Treasure(String displayName, String perm) {
        this.displayName = displayName;
        this.items = new ItemStack[0];
        this.perm = "treasure." + perm;
    }

    public void setInventory(ItemStack[] items) {
        this.items = items;
    }

    public ItemStack[] getItems() {
        return this.items;
    }

    public boolean isEmpty() {
        return this.items.length == 0;
    }

    public String getPermission() {
        return this.perm;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void show(Player player, boolean isSetting) {
        Inventory inventory;
        if (isSetting) {
            inventory = Bukkit.createInventory(null, 54, "§6设置奖励箱 §9" + this.displayName);
        } else {
            inventory = Bukkit.createInventory(null, 54, "§6奖励箱 §9" + this.displayName);
        }
        inventory.setContents(this.items);
        player.openInventory(inventory);
    }
}