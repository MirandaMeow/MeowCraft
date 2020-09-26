package cn.miranda.MeowCraft.Listeners;

import cn.miranda.MeowCraft.Manager.ConfigManager;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Manager.TradeManager;
import cn.miranda.MeowCraft.Manager.TradeObjectManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.HashMap;

import static cn.miranda.MeowCraft.Manager.TradeManager.trade;

public class TradePanelSaveEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void TradePanelSave(InventoryCloseEvent event) {
        String settingInvName = event.getView().getTitle();
        String[] invName_string = settingInvName.split("_");
        if (invName_string.length == 1) {
            return;
        }
        String invName = invName_string[1].substring(2);
        if (!TradeManager.getTradeInvNames().contains(invName)) {
            return;
        }
        ConfigManager.loadTradePanelData();
        int npcId = TradeManager.getTradeManager(invName);
        trade.get(npcId).setInventory(event.getInventory().getContents());
        HashMap<Integer, TradeObjectManager> tradeMap = trade.get(npcId).tradeMap;
        for (int i = 0; i < 54; i++) {
            if (trade.get(npcId).getInventory()[i] == null) {
                tradeMap.remove(i);
            }
        }
        trade.get(npcId).setTradeMap(tradeMap);
        MessageManager.Message(event.getPlayer(), "§e交易面板已保存");
        TradeManager.saveTrade();
    }
}
