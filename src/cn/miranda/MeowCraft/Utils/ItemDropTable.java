package cn.miranda.MeowCraft.Utils;

import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.HashMap;

public class ItemDropTable implements Serializable {
    private final HashMap<ItemStack, Integer> data;

    public ItemDropTable() {
        this.data = new HashMap<>();
    }

    public void add(ItemStack itemStack, int chance) {
        this.data.put(itemStack, chance);
    }

    public void remove(ItemStack itemStack) {
        this.data.remove(itemStack);
    }

    public int count() {
        return this.data.size();
    }

    public HashMap<ItemStack, Integer> getData() {
        return this.data;
    }
}
