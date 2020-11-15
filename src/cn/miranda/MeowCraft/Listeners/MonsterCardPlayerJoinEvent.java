package cn.miranda.MeowCraft.Listeners;

import cn.miranda.MeowCraft.Utils.Misc;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static cn.miranda.MeowCraft.Manager.ConfigManager.playerData;

public class MonsterCardPlayerJoinEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void MonsterCardPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        if (playerData.get(String.format("%s.monsterCard", playerName)) == null) {
            return;
        }
        int duration = playerData.getInt(String.format("%s.monsterCard.duration", playerName));
        String typeName = playerData.getString(String.format("%s.monsterCard.type", playerName));
        Misc.setMonsterCard(player, typeName, duration);
        Misc.disguisePlayer(player, EntityType.valueOf(typeName));
    }
}
