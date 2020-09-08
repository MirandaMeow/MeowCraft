package cn.miranda.MeowCraft.Manager;

import cn.miranda.MeowCraft.Utils.Misc;
import cn.miranda.MeowCraft.Utils.Town;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static cn.miranda.MeowCraft.Manager.ConfigMaganer.towns;
import static cn.miranda.MeowCraft.Manager.ConfigMaganer.playerData;
import static cn.miranda.MeowCraft.Manager.PluginLoadManager.econ;

public class ChestManager {
    private static Inventory initInventory(String invName) {
        Inventory panel = Bukkit.createInventory(null, 54, invName);
        ItemStack pinkPane = new ItemStack(Material.PINK_STAINED_GLASS_PANE, 1);
        ItemStack whitePane = new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1);
        int[] pink = {0, 2, 4, 6, 8, 18, 26, 36, 44, 46, 48, 50, 52};
        int[] white = {1, 3, 5, 7, 9, 17, 27, 35, 45, 47, 49, 51, 53};
        for (int i : pink) {
            panel.setItem(i, pinkPane);
        }
        for (int i : white) {
            panel.setItem(i, whitePane);
        }
        return panel;
    }

    private static ItemStack ItemWithSettings(@NotNull Material material, @NotNull String name, @Nullable List lore) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        if (lore != null) {
            itemMeta.setLore(lore);
        }
        item.setItemMeta(itemMeta);
        return item;
    }

    public static Inventory mainPanel(Player player) {
        String playName = player.getName();
        String playerTown = Town.getPlayerTownChinese(player);
        String playerMoney_String = Misc.stringFormat(econ.getBalance(player));
        String playerBank_String = Misc.stringFormat(playerData.getInt(String.format("%s.bank", playName), 0));
        String townRate = Misc.stringFormat(towns.getDouble(String.format("%s.rate", playerTown), 5.0));
        String townPublicAccount = Misc.stringFormat(towns.getDouble(String.format("%s.publicAccount", playerTown), 0.0));
        Inventory panel = initInventory(String.format("§9小镇银行 §b- §c%s", Town.getPlayerTownChinese(player)));
        ItemStack depositButton = ItemWithSettings(Material.GOLD_INGOT, "§6存款服务", null);
        ItemStack withdrawButton = ItemWithSettings(Material.IRON_INGOT, "§6取款服务", null);
        ItemStack checkButton = ItemWithSettings(Material.EMERALD, "§6账户信息", new ArrayList(Arrays.asList(String.format("§e目前持有: §b%s", playerMoney_String), String.format("§e银行存款: §b%s", playerBank_String))));
        ItemStack publicButton = ItemWithSettings(Material.BRICK_STAIRS, "§6小镇税收账户", new ArrayList<>(Arrays.asList(String.format("§e小镇税收收入: §b%s", townPublicAccount), String.format("§e小镇税率: §b%s%%", townRate), "§c只有镇长可以提取")));
        ItemStack rateButton = ItemWithSettings(Material.BLAZE_POWDER, "§6小镇税率调整", new ArrayList<>(Arrays.asList("§c只有镇长可以使用")));
        panel.setItem(20, depositButton);
        panel.setItem(22, withdrawButton);
        panel.setItem(24, checkButton);
        panel.setItem(29, publicButton);
        panel.setItem(31, rateButton);
        return panel;
    }

    public static Inventory DepositORWithdrawPanel(boolean isDeposit, Player player) {
        String playName = player.getName();
        Inventory panel;
        ItemStack value_item;
        String playerMoney_String = Misc.stringFormat(econ.getBalance(player));
        String playerBank_String = Misc.stringFormat(playerData.getInt(String.format("%s.bank", playName), 0));
        ItemStack value_1_buttom = ItemWithSettings(Material.NETHER_STAR, "§6数值调整 1", new ArrayList<>(Arrays.asList("§e左击增加 1", "§e右击减少 1")));
        ItemStack value_10_buttom = ItemWithSettings(Material.NETHER_STAR, "§6数值调整 10", new ArrayList<>(Arrays.asList("§e左击增加 10", "§e右击减少 10")));
        ItemStack value_100_buttom = ItemWithSettings(Material.NETHER_STAR, "§6数值调整 100", new ArrayList<>(Arrays.asList("§e左击增加 100", "§e右击减少 100")));
        ItemStack value_1000_buttom = ItemWithSettings(Material.NETHER_STAR, "§6数值调整 1000", new ArrayList<>(Arrays.asList("§e左击增加 1000", "§e右击减少 1000")));
        ItemStack value_10000_buttom = ItemWithSettings(Material.NETHER_STAR, "§6数值调整 10000", new ArrayList<>(Arrays.asList("§e左击增加 10000", "§e右击减少 10000")));
        ItemStack playerMoney = ItemWithSettings(Material.DIAMOND, "§6玩家资产", new ArrayList(Arrays.asList(String.format("§e目前持有: §b%s", playerMoney_String), String.format("§e银行存款: §b%s", playerBank_String))));
        ItemStack resetButton = ItemWithSettings(Material.BARRIER, "§c§l重置数值", null);
        ItemStack applyButton = ItemWithSettings(Material.BLAZE_ROD, "§e§l确认", null);
        if (isDeposit) {
            panel = initInventory("§9小镇银行 §b- §c存款");
            value_item = ItemWithSettings(Material.GOLD_NUGGET, "§6金额", new ArrayList<>(Arrays.asList("§e存入金额: §b0", "§e手续费: §b0.00")));

        } else {
            panel = initInventory("§9小镇银行 §b- §c取款");
            value_item = ItemWithSettings(Material.GOLD_NUGGET, "§6金额", new ArrayList<>(Arrays.asList("§e取出金额: §b0", "§e手续费: §b0.00")));

        }
        panel.setItem(24, value_1_buttom);
        panel.setItem(23, value_10_buttom);
        panel.setItem(22, value_100_buttom);
        panel.setItem(21, value_1000_buttom);
        panel.setItem(20, value_10000_buttom);
        panel.setItem(37, resetButton);
        panel.setItem(39, value_item);
        panel.setItem(41, playerMoney);
        panel.setItem(43, applyButton);
        return panel;
    }

    public static Inventory rateSettingPanel() {
        Inventory panel = initInventory("§9小镇银行 §b- §c税率设置");
        ItemStack value_rate_1 = ItemWithSettings(Material.IRON_NUGGET, "§e将税率设为 §b0.1%", null);
        ItemStack value_rate_2 = ItemWithSettings(Material.IRON_NUGGET, "§e将税率设为 §b0.3%", null);
        ItemStack value_rate_3 = ItemWithSettings(Material.IRON_NUGGET, "§e将税率设为 §b0.5%", null);
        ItemStack value_rate_4 = ItemWithSettings(Material.IRON_NUGGET, "§e将税率设为 §b1.0%", null);
        ItemStack value_rate_5 = ItemWithSettings(Material.IRON_NUGGET, "§e将税率设为 §b1.5%", null);
        ItemStack value_rate_6 = ItemWithSettings(Material.IRON_NUGGET, "§e将税率设为 §b2.0%", null);
        ItemStack value_rate_7 = ItemWithSettings(Material.IRON_NUGGET, "§e将税率设为 §b2.5%", null);
        ItemStack value_rate_8 = ItemWithSettings(Material.IRON_NUGGET, "§e将税率设为 §b5.0%", null);
        ItemStack value_rate_9 = ItemWithSettings(Material.IRON_NUGGET, "§e将税率设为 §b10.0%", null);
        ItemStack value_rate_10 = ItemWithSettings(Material.IRON_NUGGET, "§e将税率设为 §b50.0%", null);
        ItemStack backButton = ItemWithSettings(Material.IRON_DOOR, "§e返回", null);
        panel.setItem(16, backButton);
        panel.setItem(20, value_rate_1);
        panel.setItem(21, value_rate_2);
        panel.setItem(22, value_rate_3);
        panel.setItem(23, value_rate_4);
        panel.setItem(24, value_rate_5);
        panel.setItem(29, value_rate_6);
        panel.setItem(30, value_rate_7);
        panel.setItem(31, value_rate_8);
        panel.setItem(32, value_rate_9);
        panel.setItem(33, value_rate_10);
        return panel;
    }
}
