package cn.miranda.MeowCraft.Listeners;

import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Utils.Misc;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import static cn.miranda.MeowCraft.Manager.ConfigManager.config;
import static cn.miranda.MeowCraft.Manager.ConfigManager.playerData;
import static cn.miranda.MeowCraft.Manager.PluginLoadManager.econ;


public class DeadCostEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void DeadCost(PlayerDeathEvent event) {
        Player player = event.getEntity();
        String playerName = player.getName();
        int cost = config.getInt("DeadCost.ratio", 10);
        double playerMoney = econ.getBalance(playerName);
        if (player.isOp()) {
            event.setKeepInventory(true);
            event.setKeepLevel(true);
            return;
        }
        if (player.hasPermission("deadcost.ban")) {
            return;
        }
        if (playerData.get(String.format("%s.deadcost", playerName)) == null || !playerData.getBoolean(String.format("%s.deadcost", playerName))) {
            return;
        }
        if (playerMoney < 100) {
            event.setKeepInventory(false);
            event.setKeepLevel(false);
            player.sendMessage("§c金钱不足 100 无法使用死亡不掉落功能！");
        } else {
            String playerLoseMoney = Misc.stringFormat((double) cost / 100 * playerMoney);
            double value = Double.parseDouble(playerLoseMoney);
            econ.withdrawPlayer(playerName, value);
            String nowMoney = Misc.stringFormat(econ.getBalance(playerName));
            MessageManager.Message(player, String.format("§e你因为死亡扣除了 §b%s§e (§b%d%%§e) 金钱", playerLoseMoney, cost));
            MessageManager.Message(player, String.format("§e剩余金钱 §b%s", nowMoney));
            event.setKeepInventory(true);
            event.setKeepLevel(true);
            event.getDrops().clear();
            event.setDroppedExp(0);
        }
    }
}
