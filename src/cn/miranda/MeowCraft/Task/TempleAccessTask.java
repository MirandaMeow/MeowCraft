package cn.miranda.MeowCraft.Task;

import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.MeowCraft;
import cn.miranda.MeowCraft.Utils.Misc;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import static cn.miranda.MeowCraft.Manager.ConfigManager.temples;
import static org.bukkit.Bukkit.getScheduler;

public class TempleAccessTask {
    public void TempleAccess(Player player, String templeName) {
        getScheduler().runTaskLater(MeowCraft.plugin, () -> {
            String title = temples.getString(String.format("%s.title", templeName));
            String subtitle = temples.getString(String.format("%s.subtitle", templeName));
            int setPlayerMaxHealth = Misc.getTempleVisitAmount(player) + 20;
            player.setMaxHealth(setPlayerMaxHealth);
            player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
            player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 40, 0, 2, 0);
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 10);
            player.sendTitle(title, subtitle, 10, 70, 20);
            MessageManager.Message(player, temples.getString(String.format("%s.accessMessage", templeName)));
        }, 20);
    }
}
