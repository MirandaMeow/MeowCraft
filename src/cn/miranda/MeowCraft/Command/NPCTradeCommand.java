package cn.miranda.MeowCraft.Command;

import cn.miranda.MeowCraft.Enum.Notify;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Manager.TradeManager;
import cn.miranda.MeowCraft.Manager.TradeObjectManager;
import cn.miranda.MeowCraft.Utils.Misc;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;

import static cn.miranda.MeowCraft.Manager.TradeManager.trade;

public class NPCTradeCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            MessageManager.Message(sender, Notify.No_Console.getString());
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("trade.admin")) {
            MessageManager.Message(player, Notify.No_Permission.getString());
            return true;
        }
        if (args.length < 2) {
            MessageManager.Message(player, Notify.Invalid_Input_Length.getString());
            return true;
        }
        String npcId = args[0];
        if (!Misc.isInt(npcId)) {
            MessageManager.Message(player, Notify.Invalid_Input.getString());
            return true;
        }
        int npcId_int = Integer.parseInt(npcId);

        String option = args[1];
        switch (option) {
            case "create":
                if (args.length != 3) {
                    MessageManager.Message(player, Notify.Invalid_Input_Length.getString());
                    return true;
                }
                String invName = args[2];
                trade.put(npcId_int, new TradeManager(new HashMap<>(), Bukkit.createInventory(null, 54).getContents(), invName));
                TradeManager.saveTrade();
                MessageManager.Message(player, String.format("§e已为编号为 §b%d §e的 NPC 创建名为 §b%s §e的交易面板", npcId_int, invName));
                return true;
            case "delete":
                if (trade.get(npcId_int) == null) {
                    MessageManager.Message(player, "§c交易面板不存在");
                    return true;
                }
                trade.remove(npcId_int);
                TradeManager.saveTrade();
                MessageManager.Message(player, String.format("§e删除了编号为 §b%d §e的 NPC 的交易面板", npcId_int));
                return true;
            case "edit":
                if (trade.get(npcId_int) == null) {
                    MessageManager.Message(player, "§c交易面板不存在");
                    return true;
                }
                String invNameInTrade = trade.get(npcId_int).getInvName();
                Inventory inventory = Bukkit.createInventory(null, 54, "§e设置_§9" + invNameInTrade);
                inventory.setContents(trade.get(npcId_int).inventory);
                player.openInventory(inventory);
                MessageManager.Message(player, String.format("§e打开编号为 §b%s §e的 NPC 的设置面板", npcId));
                return true;
            case "set":
                if (trade.get(npcId_int) == null) {
                    MessageManager.Message(player, "§c交易面板不存在");
                    return true;
                }
                if (args.length != 5) {
                    MessageManager.Message(player, Notify.Invalid_Input_Length.getString());
                    return true;
                }
                if (!args[3].equals("buy") && !args[3].equals("sell")) {
                    MessageManager.Message(player, Notify.Invalid_Input.getString());
                    return true;
                }
                String type = args[3];
                if (!Misc.isInt(args[2]) || !Misc.isInt(args[4])) {
                    MessageManager.Message(player, Notify.Invalid_Input.getString());
                    return true;
                }
                int slot = Integer.parseInt(args[2]);
                int price = Integer.parseInt(args[4]);
                if (slot < 0 || slot > 53 || price < 0) {
                    MessageManager.Message(player, Notify.Invalid_Input.getString());
                    return true;
                }
                if (trade.get(npcId_int).inventory[slot] == null) {
                    MessageManager.Message(player, String.format("§c不存在序号为 §b%d §c的交易项", slot));
                    return true;
                }
                if (!TradeManager.existPanel(npcId_int)) {
                    MessageManager.Message(player, Notify.No_TradePanel.getString());
                    return true;
                }
                TradeObjectManager tradeObject = new TradeObjectManager(type, price);
                HashMap<Integer, TradeObjectManager> tradeMap = trade.get(npcId_int).getTradeMap();
                String itemType = trade.get(npcId_int).getInventory()[slot].getType().toString();
                tradeMap.put(slot, tradeObject);
                trade.get(npcId_int).setTradeMap(tradeMap);
                TradeManager.saveTrade();
                MessageManager.Message(player, String.format("§e序号为 §b%d §e的物品 (类型: §b%s§e) 的交易类型为 §b%s§e, 价格为 §b%d", slot, itemType, type, price));
                return true;
            case "remove":
                if (trade.get(npcId_int) == null) {
                    MessageManager.Message(player, "§c交易面板不存在");
                    return true;
                }
                if (args.length != 3) {
                    MessageManager.Message(player, Notify.Invalid_Input_Length.getString());
                    return true;
                }
                if (!Misc.isInt(args[2])) {
                    MessageManager.Message(player, Notify.Invalid_Input.getString());
                    return true;
                }
                if (!TradeManager.existPanel(npcId_int)) {
                    MessageManager.Message(player, Notify.No_TradePanel.getString());
                    return true;
                }
                slot = Integer.parseInt(args[2]);
                tradeMap = trade.get(npcId_int).tradeMap;
                if (!trade.get(npcId_int).tradeMap.containsKey(slot)) {
                    MessageManager.Message(player, "§c交易项不存在");
                    return true;
                }
                tradeMap.remove(slot);
                TradeManager.saveTrade();
                MessageManager.Message(player, String.format("§e移除了序号为 §b%d §e的交易项", slot));
                return true;
            default:
                MessageManager.Message(player, "§e用法: §6/trade §b<NpcID> <create|remove|set|delete> [slot] [value]");
                return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length != 0) {
            if (!Misc.isInt(strings[0]) || Objects.equals(strings[0], "")) {
                return new ArrayList<>();
            }
            int npcId_int = Integer.parseInt(strings[0]);
            if (!TradeManager.existPanel(npcId_int)) {
                return new ArrayList<>();
            }
            if (strings.length == 2) {
                return Misc.returnSelectList(new ArrayList<>(Arrays.asList("create", "delete", "edit", "set", "remove")), strings[1]);
            }
            if (strings.length == 3 && Objects.equals(strings[1], "set")) {
                return Misc.returnSelectList(TradeManager.getPanelValidItemSlot(strings[0]), strings[2]);
            }
            if (strings.length == 4 && Objects.equals(strings[1], "set")) {
                return Misc.returnSelectList(new ArrayList<>(Arrays.asList("buy", "sell")), strings[3]);
            }
        }
        return new ArrayList<>();
    }
}
