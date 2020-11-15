package cn.miranda.MeowCraft.Task;

import cn.miranda.MeowCraft.MeowCraft;
import cn.miranda.MeowCraft.Utils.Misc;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import static cn.miranda.MeowCraft.Manager.ConfigManager.playerData;
import static org.bukkit.Bukkit.getScheduler;

public class MonsterCardPlayerRespawnLaterTask {
    public void MonsterCardPlayerRespawnLater(Player player) {
        getScheduler().runTaskLater(MeowCraft.plugin, () -> {
            String playerName = player.getName();
            int duration = playerData.getInt(String.format("%s.monsterCard.duration", playerName));
            String typeName = playerData.getString(String.format("%s.monsterCard.type", playerName));
            Misc.setMonsterCard(player, typeName, duration);
            Misc.disguisePlayer(player, EntityType.valueOf(typeName));
        }, 20);
    }
}
