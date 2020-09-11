package cn.miranda.MeowCraft.Listeners;

import cn.miranda.MeowCraft.Manager.ConfigManager;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Utils.Misc;
import cn.miranda.MeowCraft.Utils.Town;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static cn.miranda.MeowCraft.Manager.ConfigManager.towns;
import static cn.miranda.MeowCraft.Manager.ConfigManager.playerData;
import static cn.miranda.MeowCraft.Manager.PluginLoadManager.econ;

public class BankCloseEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void BankCloseEvent(InventoryCloseEvent event) {
        String invName = event.getView().getTitle();
        if (!invName.contains("§9小镇银行")) {
            return;
        }
        if (!(event.getPlayer() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getPlayer();
        String playerName = player.getName();
        String playerTown = Town.getPlayerTownChinese(player);
        if (invName.contains("存款")) {
            List setValue = getSettingValue(event.getInventory());
            int money = (int) setValue.get(0);
            if (money == 0) {
                return;
            }
            double rate = (double) setValue.get(1);
            String rateString = Misc.stringFormat(rate);
            econ.withdrawPlayer(player, money);
            econ.withdrawPlayer(player, rate);
            String playerMoney = Misc.stringFormat(econ.getBalance(player));
            int playerBank = playerData.getInt(String.format("%s.bank", playerName)) + money;
            playerData.set(String.format("%s.bank", playerName), playerBank);
            double TownPublic = towns.getDouble(String.format("%s.publicAccount", playerTown), 0.0);
            TownPublic += rate;
            double TownPublic_formatted = Misc.doubleFixed(TownPublic);
            towns.set(String.format("%s.publicAccount", playerTown), TownPublic_formatted);
            MessageManager.Message(player, String.format("§e存入了 §b%d§e, 手续费 §b%s§e, 当前有 §b%s§e, 银行存款 §b%s", money, rateString, playerMoney, playerBank));
            ConfigManager.saveConfigs();
            return;
        }
        if (invName.contains("取款")) {
            List setValue = getSettingValue(event.getInventory());
            int money = (int) setValue.get(0);
            if (money == 0) {
                return;
            }
            double rate = (double) setValue.get(1);
            String rateString = Misc.stringFormat(rate);
            econ.depositPlayer(player, money);
            econ.withdrawPlayer(player, rate);
            String playerMoney = Misc.stringFormat(econ.getBalance(player));
            int playerBank = playerData.getInt(String.format("%s.bank", playerName)) - money;
            playerData.set(String.format("%s.bank", playerName), playerBank);
            double TownPublic = towns.getDouble(String.format("%s.publicAccount", playerTown), 0.0);
            TownPublic += rate;
            double TownPublic_formatted = Misc.doubleFixed(TownPublic);
            towns.set(String.format("%s.publicAccount", playerTown), TownPublic_formatted);
            MessageManager.Message(player, String.format("§e取出了 §b%d§e, 手续费 §b%s§e, 当前有 §b%s§e, 银行存款 §b%s", money, rateString, playerMoney, playerBank));
            ConfigManager.saveConfigs();
        }
    }

    private List getSettingValue(Inventory inv) {
        ItemStack item = inv.getItem(39);
        return new ArrayList<>(Arrays.asList(Integer.parseInt(item.getItemMeta().getLore().get(0).split(" ")[1].substring(2)), Double.parseDouble(item.getItemMeta().getLore().get(1).split(" ")[1].substring(2))));
    }
}
