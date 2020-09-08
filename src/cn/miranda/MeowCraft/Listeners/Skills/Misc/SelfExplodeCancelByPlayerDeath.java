package cn.miranda.MeowCraft.Listeners.Skills.Misc;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import static cn.miranda.MeowCraft.Manager.ConfigMaganer.temp;

public class SelfExplodeCancelByPlayerDeath implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void SelfExplodeCancelByPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        String playerName = player.getName();
        if (temp.get(String.format("OccSkillCoolDown.%s.temp.selfExplode", playerName)) != null && !temp.getBoolean(String.format("OccSkillCoolDown.%s.temp.selfExplodeCancel", playerName))) {
            temp.set(String.format("OccSkillCoolDown.%s.temp.selfExplodeCancel", playerName), true);
        }
    }
}
