package cn.miranda.MeowCraft.Manager;

import cn.miranda.MeowCraft.Utils.IO;
import cn.miranda.MeowCraft.Utils.Misc;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

import static cn.miranda.MeowCraft.Manager.ConfigManager.trades;

public class TradeManager implements Serializable {
    public static HashMap<Integer, TradeManager> trade = new HashMap<>();
    public HashMap<Integer, TradeObjectManager> tradeMap;
    public ItemStack[] inventory;
    public String invName;

    public TradeManager(HashMap<Integer, TradeObjectManager> tradeMap, ItemStack[] inventory, String invName) {
        this.tradeMap = tradeMap;
        this.inventory = inventory;
        this.invName = invName;
    }

    public void setTradeMap(HashMap<Integer, TradeObjectManager> tradeMap) {
        this.tradeMap = tradeMap;
    }

    public String getInvName() {
        return this.invName;
    }

    public ItemStack[] getInventory() {
        return this.inventory;
    }

    public void setInventory(ItemStack[] inventory) {
        this.inventory = inventory;
    }

    public static void saveTrade() {
        try {
            trades.set("trade", IO.encodeData(trade));
            ConfigManager.saveConfigs();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getTradeInvNames() {
        List<String> names = new ArrayList<>();
        for (Map.Entry<Integer, TradeManager> entry : trade.entrySet()) {
            names.add(entry.getValue().getInvName());
        }
        return names;
    }

    public static int getTradeManager(String invName) {
        for (Map.Entry<Integer, TradeManager> entry : trade.entrySet()) {
            if (Objects.equals(entry.getValue().getInvName(), invName)) {
                return entry.getKey();
            }
        }
        return -1;
    }

    public HashMap<Integer, TradeObjectManager> getTradeMap() {
        return this.tradeMap;
    }

    public static boolean existPanel(int id) {
        return trade.get(id) != null;
    }

    public static List<Integer> getNpcIds() {
        List<Integer> npcIds = new ArrayList<>();
        for (Map.Entry<Integer, TradeManager> entry : trade.entrySet()) {
            npcIds.add(entry.getKey());
        }
        return npcIds;
    }

    public static List<String> getPanelValidItemSlot(String id) {
        if (!Misc.isInt(id)) {
            return new ArrayList<>();
        }
        int id_int = Integer.parseInt(id);
        List<String> out = new ArrayList<>();
        for (int i = 0; i < 54; i++) {
            ItemStack itemStack = trade.get(id_int).getInventory()[i];
            if (itemStack == null) {
                continue;
            }
            out.add(String.valueOf(i));
        }
        return out;
    }
}
