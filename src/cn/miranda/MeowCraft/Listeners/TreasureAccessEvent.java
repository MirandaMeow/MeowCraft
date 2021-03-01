package cn.miranda.MeowCraft.Listeners;

import cn.miranda.MeowCraft.Cores.Treasure;
import cn.miranda.MeowCraft.Cores.TreasureSet;
import cn.miranda.MeowCraft.Manager.ConfigManager;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Utils.IO;
import cn.miranda.MeowCraft.Utils.Occ;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

import static cn.miranda.MeowCraft.Manager.ConfigManager.playerData;

public class TreasureAccessEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void Treasure(InventoryCloseEvent event) throws IOException, ClassNotFoundException {
        HumanEntity eventPlayer = event.getPlayer();
        if (!(eventPlayer instanceof Player)) {
            return;
        }
        Player player = (Player) eventPlayer;
        String title = event.getView().getTitle();
        TreasureSet treasureSet = new TreasureSet();
        Treasure treasure = treasureSet.getByName(title);
        if (treasure == null) {
            return;
        }
        Inventory inventory = event.getInventory();
        if (title.contains("设置")) {
            ItemStack[] items = inventory.getContents();
            treasure.setInventory(items);
            treasureSet.addTreasure(treasure.getDisplayName(), treasure);
            MessageManager.Message(player, "§e奖励箱已保存");
            return;
        }
        if (inventory.isEmpty()) {
            ConfigurationSection targetConfig = playerData.getConfigurationSection(String.format("%s.treasures", player.getName()));
            if (targetConfig == null) {
                return;
            }
            playerData.set(String.format("%s.treasures.%s", player.getName(), treasure.getDisplayName()), null);
            if (targetConfig.getValues(false).isEmpty()) {
                playerData.set(String.format("%s.treasures", player.getName()), null);
            }
            Occ.removePermission(player, treasure.getPermission());
            ConfigManager.saveConfigs();
            MessageManager.Message(player, "§e奖励箱内物品已经全部领取");
        } else {
            treasure.setInventory(event.getInventory().getContents());
            playerData.set(String.format("%s.treasures.%s", player.getName(), treasure.getDisplayName()), IO.encodeData(treasure));
            ConfigManager.saveConfigs();
            MessageManager.Message(player, "§e奖励箱内还有剩余物品, 请尽快领取");
        }
    }
}