package cn.miranda.MeowCraft.Listeners;

import cn.miranda.MeowCraft.Enum.EggCatcher;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Utils.Effect;
import cn.miranda.MeowCraft.Utils.Misc;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.projectiles.ProjectileSource;

import static cn.miranda.MeowCraft.Manager.ConfigMaganer.config;

import static cn.miranda.MeowCraft.Manager.PluginLoadManager.econ;

public class EggCatcherEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void EggCatcherEvent(ProjectileHitEvent event) {
        EggCatcher eggCatcher;
        ProjectileSource shooter = event.getEntity().getShooter();
        if (!(shooter instanceof Player)) {
            return;
        }
        Player player = (Player) shooter;
        Entity projectile = event.getEntity();
        if (!(projectile.getType().equals(EntityType.SNOWBALL))) {
            return;
        }
        Entity target = event.getHitEntity();
        if (target == null) {
            return;
        }
        EntityType targetType = target.getType();
        Location targetLocation = target.getLocation();
        try {
            eggCatcher = EggCatcher.valueOf(targetType.toString());
        } catch (IllegalArgumentException e) {
            return;
        }
        if (!player.hasPermission(eggCatcher.getPermission())) {
            MessageManager.Messager(player, "§c你没有权限捕捉§b" + eggCatcher.getName());
            return;
        }
        if (!EggCatcher.getAvailableList().contains(targetType)) {
            MessageManager.Messager(player, String.format("§b%s §c不可被捕捉", eggCatcher.getName()));
            return;
        }
        if (EggCatcher.getCanTamedEntity().contains(targetType)) {
            Tameable t = (Tameable) target;
            if (t.isTamed()) {
                MessageManager.Messager(player, "§c无法捕捉已被驯服的动物");
                return;
            }
        }
        double playerMoney = econ.getBalance(player);
        int chance = config.getInt("EggCatcher.chance", 30);
        int cost = config.getInt("EggCatcher.cost", 100);
        if (playerMoney < cost) {
            MessageManager.Messager(player, "§c金钱不足无法捕捉");
            return;
        }
        econ.withdrawPlayer(player, cost);
        int opt = Misc.randomNum(1, 100);
        if (opt > chance) {
            MessageManager.Messager(player, String.format("§e扣除了 §b%d §e用于捕捉 §b%s§e, §c不过没捉到", cost, eggCatcher.getName()));
            return;
        }
        MessageManager.Messager(player, String.format("§e扣除了 §b%d §e用于捕捉 §b%s§e, §c捕捉成功了", cost, eggCatcher.getName()));
        target.getWorld().dropItem(targetLocation, eggCatcher.getItemStack());
        Effect.eggCatchDropItem(target);
        target.remove();
    }
}
