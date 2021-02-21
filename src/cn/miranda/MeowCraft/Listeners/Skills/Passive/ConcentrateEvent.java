package cn.miranda.MeowCraft.Listeners.Skills.Passive;

import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Utils.Effect;
import cn.miranda.MeowCraft.Utils.Misc;
import cn.miranda.MeowCraft.Utils.Occ;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import static cn.miranda.MeowCraft.Manager.ConfigManager.playerData;
import static cn.miranda.MeowCraft.Manager.ConfigManager.skills;

public class ConcentrateEvent implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    private void Concentrate(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof Player)) {
            return;
        }
        Player player = (Player) entity;
        String playerName = player.getName();
        String skillName = "All_Concentrate";
        if (!Occ.isFitOcc(player, skillName) || !playerData.getBoolean(String.format("%s.occConfig.enabled", playerName), true)) {
            return;
        }
        if (playerData.get(String.format("%s.occConfig.occSkills.%s", playerName, skillName)) == null) {
            return;
        }
        int chance = skills.getInt(String.format("%s.chance", skillName), 20);
        int currentProbability = Misc.randomNum(0, 100);
        if (currentProbability > chance) {
            return;
        }
        int reduce = skills.getInt(String.format("%s.reduce", skillName), 30);
        Effect.activeSkillEffect(player);
        event.setDamage(event.getDamage() * (1 - Double.parseDouble(Misc.div(reduce, 100))));
        MessageManager.ActionBarMessage(player, "§e受到的伤害减轻了");
    }
}
