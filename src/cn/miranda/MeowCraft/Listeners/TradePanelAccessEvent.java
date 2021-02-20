package cn.miranda.MeowCraft.Listeners;

import cn.miranda.MeowCraft.Manager.ConfigManager;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Manager.TradeManager;
import cn.miranda.MeowCraft.Manager.TradeObjectManager;
import cn.miranda.MeowCraft.Utils.Misc;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

import static cn.miranda.MeowCraft.Manager.PluginLoadManager.econ;
import static cn.miranda.MeowCraft.Manager.TradeManager.trade;

public class TradePanelAccessEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void TradePanelAccess(InventoryClickEvent event) {
        String invName_original = event.getView().getTitle();
        if (invName_original.length() <= 2) {
            return;
        }
        String invName = invName_original.substring(2);
        if (!TradeManager.getTradeInvNames().contains(invName)) {
            return;
        }
        int npcId = TradeManager.getTradeManager(invName);
        if (npcId == -1) {
            return;
        }
        event.setCancelled(true);
        HashMap<Integer, TradeObjectManager> tradeMap = trade.get(npcId).getTradeMap();
        if (!tradeMap.containsKey(event.getSlot())) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();
        TradeObjectManager tradeObject = tradeMap.get(slot);
        String type = tradeObject.type;
        int price = tradeObject.price;
        ConfigManager.loadTradePanelData();
        ItemStack[] itemStacks = trade.get(npcId).getInventory();
        double playerMoney = econ.getBalance(player);
        switch (type) {
            case "buy":
                if (playerMoney < price) {
                    MessageManager.Message(player, "§c无法购买, 你没有足够的钱");
                    return;
                }
                if (Misc.isInventoryFull(player)) {
                    MessageManager.Message(player, "§c无法购买, 你的背包满了");
                    return;
                }
                player.getInventory().addItem(itemStacks[slot]);
                econ.withdrawPlayer(player, price);
                String nowMoney = Misc.stringFormat(econ.getBalance(player));
                MessageManager.Message(player, String.format("§e购买成功, 花费了 §b%d§e, 现有金钱: §b%s", price, nowMoney));
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 10);
                return;
            case "sell":
                ItemStack targetItem = itemStacks[slot];
                int amount = targetItem.getAmount();
                targetItem.setAmount(1);
                if (!Misc.deductItem(player, targetItem, amount)) {
                    MessageManager.Message(player, "§c无法出售, 你没有指定物品");
                    return;
                }
                econ.depositPlayer(player, price);
                nowMoney = Misc.stringFormat(econ.getBalance(player));
                MessageManager.Message(player, String.format("§e出售成功, 获得了 §b%d§e, 现有金钱: §b%s", price, nowMoney));
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 10);
        }
    }
}
