package cn.miranda.MeowCraft.Listeners;

import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Utils.RollDice;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class RollDiceEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void RollDiceEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String playerDisplayName = player.getDisplayName();
        String message = event.getMessage();
        String result;
        result = RollDice.getResultMessage(playerDisplayName, message);
        if (result == null) {
            return;
        }
        MessageManager.Broadcast(result);
        event.setCancelled(true);
    }
}
