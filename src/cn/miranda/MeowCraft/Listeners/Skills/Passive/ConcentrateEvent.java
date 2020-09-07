package cn.miranda.MeowCraft.Listeners.Skills.Passive;

import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Utils.Misc;
import cn.miranda.MeowCraft.Utils.Occ;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import static cn.miranda.MeowCraft.Manager.ConfigMaganer.skills;
import static cn.miranda.MeowCraft.Manager.ConfigMaganer.playerData;

public class ConcentrateEvent implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    private void ConcentrateEvent(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof Player)) {
            return;
        }
        Player player = (Player) entity;
        String playerName = player.getName();
        if (!Occ.isFitOcc(player, "All_Concentrate") || !playerData.getBoolean(String.format("%s.occConfig.enabled", playerName), true)) {
            return;
        }
        int chance = skills.getInt("All_Concentrate.chance", 20);
        int currentProbability = Misc.randomNum(0, 100);
        if (currentProbability > chance) {
            return;
        }
        event.setCancelled(true);
        MessageManager.Messager(player, "§e伤害闪避!");
    }
}
