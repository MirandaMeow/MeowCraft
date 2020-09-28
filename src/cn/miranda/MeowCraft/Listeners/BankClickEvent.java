package cn.miranda.MeowCraft.Listeners;

import cn.miranda.MeowCraft.Manager.BankManager;
import cn.miranda.MeowCraft.Manager.ConfigManager;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Utils.Misc;
import cn.miranda.MeowCraft.Utils.Town;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

import static cn.miranda.MeowCraft.Manager.ConfigManager.playerData;
import static cn.miranda.MeowCraft.Manager.ConfigManager.towns;
import static cn.miranda.MeowCraft.Manager.PluginLoadManager.econ;

public class BankClickEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void BankClick(InventoryClickEvent event) {
        String invName = event.getView().getTitle();
        if (!invName.contains("§9小镇银行")) {
            return;
        }
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }
        ItemStack currentItem = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getClickedInventory();
        HandleClick(player, inv, currentItem, event.getClick(), invName);
        event.setCancelled(true);
    }

    private void HandleClick(Player player, Inventory inv, ItemStack item, ClickType type, String invName) {
        if (item == null) {
            return;
        }
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) {
            return;
        }
        String itemName = itemMeta.getDisplayName();
        switch (itemName) {
            case "§6存款服务":
                player.openInventory(BankManager.DepositORWithdrawPanel(true, player));
                return;
            case "§6取款服务":
                player.openInventory(BankManager.DepositORWithdrawPanel(false, player));
                return;
            case "§6数值调整 1":
                if (type == ClickType.LEFT) {
                    setValue(player, inv, 1, false, invName);
                    return;
                }
                setValue(player, inv, -1, false, invName);
                return;
            case "§6数值调整 10":
                if (type == ClickType.LEFT) {
                    setValue(player, inv, 10, false, invName);
                    return;
                }
                setValue(player, inv, -10, false, invName);
                return;
            case "§6数值调整 100":
                if (type == ClickType.LEFT) {
                    setValue(player, inv, 100, false, invName);
                    return;
                }
                setValue(player, inv, -100, false, invName);
                return;
            case "§6数值调整 1000":
                if (type == ClickType.LEFT) {
                    setValue(player, inv, 1000, false, invName);
                    return;
                }
                setValue(player, inv, -1000, false, invName);
                return;
            case "§6数值调整 10000":
                if (type == ClickType.LEFT) {
                    setValue(player, inv, 10000, false, invName);
                    return;
                }
                setValue(player, inv, -10000, false, invName);
                return;
            case "§c§l重置数值":
                setValue(player, inv, 0, true, invName);
                return;
            case "§e§l确认并返回":
                player.updateInventory();
                player.openInventory(BankManager.mainPanel(player));
            case "§e返回":
                player.openInventory(BankManager.mainPanel(player));
                return;
            case "§6小镇收入账户":
                if (!player.hasPermission("town.admin")) {
                    MessageManager.Message(player, "§c只有镇长才可以使用");
                    return;
                }
                String playerTownChinese = Town.getPlayerTownChinese(player);
                double townAccount = towns.getDouble(String.format("%s.incomeAccount", playerTownChinese), 0.0);
                if (townAccount == 0) {
                    MessageManager.Message(player, "§c收入账户金额为零");
                    return;
                }
                econ.depositPlayer(player, townAccount);
                towns.set(String.format("%s.incomeAccount", playerTownChinese), 0);
                ConfigManager.saveConfigs();
                player.closeInventory();
                MessageManager.Message(player, String.format("§e你提取了收入账户的全部金额 §b%s", Misc.stringFormat(townAccount)));
                return;
            case "§6小镇银行服务费率调整":
                if (!player.hasPermission("town.admin")) {
                    MessageManager.Message(player, "§c只有镇长才可以使用");
                    return;
                }
                player.openInventory(BankManager.rateSettingPanel());
                return;
            case "§e将服务费率设为 §b0.1%":
                towns.set(String.format("%s.rate", Town.getPlayerTownChinese(player)), 0.1);
                ConfigManager.saveConfigs();
                return;
            case "§e将服务费率设为 §b0.3%":
                towns.set(String.format("%s.rate", Town.getPlayerTownChinese(player)), 0.3);
                ConfigManager.saveConfigs();
                return;
            case "§e将服务费率设为 §b0.5%":
                towns.set(String.format("%s.rate", Town.getPlayerTownChinese(player)), 0.5);
                ConfigManager.saveConfigs();
                return;
            case "§e将服务费率设为 §b1.0%":
                towns.set(String.format("%s.rate", Town.getPlayerTownChinese(player)), 1.0);
                ConfigManager.saveConfigs();
                return;
            case "§e将服务费率设为 §b1.5%":
                towns.set(String.format("%s.rate", Town.getPlayerTownChinese(player)), 1.5);
                ConfigManager.saveConfigs();
                return;
            case "§e将服务费率设为 §b2.0%":
                towns.set(String.format("%s.rate", Town.getPlayerTownChinese(player)), 2.0);
                ConfigManager.saveConfigs();
                return;
            case "§e将服务费率设为 §b2.5%":
                towns.set(String.format("%s.rate", Town.getPlayerTownChinese(player)), 2.5);
                ConfigManager.saveConfigs();
                return;
            case "§e将服务费率设为 §b5.0%":
                towns.set(String.format("%s.rate", Town.getPlayerTownChinese(player)), 5.0);
                ConfigManager.saveConfigs();
                return;
            case "§e将服务费率设为 §b10.0%":
                towns.set(String.format("%s.rate", Town.getPlayerTownChinese(player)), 10.0);
                ConfigManager.saveConfigs();
                return;
            case "§e将服务费率设为 §b50.0%":
                towns.set(String.format("%s.rate", Town.getPlayerTownChinese(player)), 50.0);
                ConfigManager.saveConfigs();
                return;
            default:
        }
    }

    private void setValue(Player player, Inventory inv, int value, boolean reset, String invName) {
        ItemStack item = inv.getItem(39);
        ItemMeta itemMeta = item.getItemMeta();
        List<String> loreList = itemMeta.getLore();
        boolean isDeposit = invName.contains("存款");
        double playerMoney = econ.getBalance(player);
        int playerBank = playerData.getInt(String.format("%s.bank", player.getName()));
        if (!reset) {
            int setValue_int = Integer.parseInt(loreList.get(0).split(" ")[1].substring(2)) + value;
            if (setValue_int < 0) {
                return;
            }
            if (isDeposit) {
                if (setValue_int > playerMoney) {
                    return;
                }
                loreList.set(0, String.format("§e存入金额: §b%d", setValue_int));
            } else {
                if (setValue_int > playerBank) {
                    return;
                }
                loreList.set(0, String.format("§e取出金额: §b%d", setValue_int));
            }
            int valueRate = Integer.parseInt(loreList.get(0).split(" ")[1].substring(2));
            String playerTownChinese = Town.getPlayerTownChinese(player);
            double townRate = towns.getDouble(String.format("%s.rate", playerTownChinese), 0.1);
            double commission = valueRate * townRate / 100;
            if (commission < 0.1) {
                commission = 0.1;
            }
            if (setValue_int == 0) {
                commission = 0;
            }
            String commission_String = Misc.stringFormat(commission);
            double commission_value = Double.parseDouble(commission_String);

            if (isDeposit) {
                if (playerMoney - setValue_int - commission_value < 0) {
                    return;
                }
            }
            loreList.set(1, String.format("§e服务费: §b%s", commission_String));
            itemMeta.setLore(loreList);
            item.setItemMeta(itemMeta);
            inv.setItem(39, item);
            return;
        }
        if (isDeposit) {
            loreList.set(0, String.format("§e存入金额: §b%d", 0));
        } else {
            loreList.set(0, String.format("§e取出金额: §b%d", 0));
        }
        loreList.set(1, "§e服务费: §b0.00");
        itemMeta.setLore(loreList);
        item.setItemMeta(itemMeta);
        inv.setItem(39, item);
    }
}