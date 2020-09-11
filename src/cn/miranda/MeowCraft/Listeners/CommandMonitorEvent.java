package cn.miranda.MeowCraft.Listeners;

import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Utils.Misc;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import static cn.miranda.MeowCraft.Manager.ConfigManager.playerData;

public class CommandMonitorEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void CommandMonitorEvent(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        String command = event.getMessage();
        for (String p : Misc.getOnlinePlayerNames()) {
            if (playerData.getBoolean(String.format("%s.cmdlogger", p))) {
                MessageManager.Message(Misc.player(p), String.format("§e玩家 §b%s §e使用了命令 §b%s", playerName, command));
            }
        }
    }
}
