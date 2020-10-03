package cn.miranda.MeowCraft.Task.Skill;

import cn.miranda.MeowCraft.MeowCraft;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.bukkit.Bukkit.getScheduler;

public class ArrowBoostShootTask {
    private volatile BukkitTask task = null;

    public List<Integer> ArrowBoostShoot(Player player, int settings_range, int settings_per_wave_amount, int settings_wave, long settings_interval) {
        Vector z_axis = new Vector(0, 0, 1);
        Vector x_axis = new Vector(1, 0, 0);
        Vector y_axis = new Vector(0, 1, 0);
        List<Integer> arrowTargetEntityIDs = new ArrayList<>();
        final int[] wave_count = {0};
        task = getScheduler().runTaskTimer(MeowCraft.plugin, () -> {
            for (int i = 0; i < settings_per_wave_amount; i++) {
                Vector a, b, loc = player.getEyeLocation().getDirection();
                double range = Math.abs(settings_range) % 360;
                double phi = (range / 180) * Math.PI;
                Vector ax1 = loc.getCrossProduct(z_axis);
                if (ax1.length() < 0.01) {
                    a = x_axis.clone();
                    b = y_axis.clone();
                } else {
                    a = ax1.normalize();
                    b = loc.getCrossProduct(a).normalize();
                }
                double z = (range == 0) ? 1.0D : ThreadLocalRandom.current().nextDouble(Math.cos(phi), 1.0D);
                double det = ThreadLocalRandom.current().nextDouble(0.0D, Math.PI * 2);
                double theta = Math.acos(z);
                Vector v = a.clone().multiply(Math.cos(det)).add(b.clone().multiply(Math.sin(det))).multiply(Math.sin(theta)).add(loc.clone().multiply(Math.cos(theta)));
                Projectile projectile = player.launchProjectile(org.bukkit.entity.Arrow.class, v.normalize().multiply(2));
                new RemoveEntityTask().RemoveEntity(projectile, 100, false);
                arrowTargetEntityIDs.add(projectile.getEntityId());
            }
            wave_count[0] += 1;
            if (wave_count[0] == settings_wave) {
                task.cancel();
            }
        }, 0, settings_interval);
        return arrowTargetEntityIDs;
    }
}
