package cn.miranda.MeowCraft.Listeners;

import cn.miranda.MeowCraft.Manager.ConfigManager;
import cn.miranda.MeowCraft.Manager.TradeManager;
import cn.miranda.MeowCraft.Manager.TradeObjectManager;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

import static cn.miranda.MeowCraft.Manager.TradeManager.trade;

public class TradePanelOpenEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void TradePanelOpen(NPCRightClickEvent event) {
        NPC npc = event.getNPC();
        Player player = event.getClicker();
        int npcID = npc.getId();
        if (!TradeManager.getNpcIds().contains(npcID)) {
            return;
        }
        String invName = trade.get(npcID).getInvName();
        ItemStack[] itemStacks = trade.get(npcID).getInventory().clone();
        HashMap<Integer, TradeObjectManager> tradeMap = trade.get(npcID).tradeMap;
        for (Map.Entry<Integer, TradeObjectManager> entry : tradeMap.entrySet()) {
            int slot = entry.getKey();
            TradeObjectManager tradeObject = entry.getValue();
            ItemStack itemStack = itemStacks[slot];
            ItemMeta itemMeta = itemStack.getItemMeta();
            List<String> lore;
            if (itemMeta.hasLore()) {
                lore = itemMeta.getLore();
            } else {
                lore = new ArrayList<>();
            }
            lore.add("");
            if (Objects.equals(tradeObject.type, "buy")) {
                lore.add(String.format("§e购买价格: §b%d", tradeObject.price));
            } else {
                lore.add(String.format("§e出售价格: §b%d", tradeObject.price));
            }
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
        }
        Inventory inventory = Bukkit.createInventory(null, 54, "§9" + invName);
        inventory.setContents(itemStacks);
        player.openInventory(inventory);
        ConfigManager.loadTradePanelData();
    }
}
