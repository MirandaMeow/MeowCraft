package cn.miranda.MeowCraft.Utils;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworkLauncher {
    public final boolean trail;
    public final boolean flicker;
    public final Location location;
    public final FireworkEffect.Type type;
    public final Color[] color;
    public final Color[] fade;
    public final Firework firework;
    public final int power;

    public FireworkLauncher(Location location, boolean trail, boolean flicker, FireworkEffect.Type type, Color[] color, Color[] fade, int power) {
        this.trail = trail;
        this.flicker = flicker;
        this.location = location;
        this.type = type;
        this.color = color;
        this.fade = fade;
        this.power = power;
        FireworkEffect.Builder fireworkEffect = FireworkEffect.builder();
        fireworkEffect.trail(this.trail);
        fireworkEffect.flicker(this.flicker);
        fireworkEffect.with(this.type);
        for (Color i : this.color) {
            fireworkEffect.withColor(i);
        }
        for (Color i : this.fade) {
            fireworkEffect.withFade(i);
        }
        firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
        FireworkMeta fireworkMeta = firework.getFireworkMeta();
        fireworkMeta.setPower(power);
        fireworkMeta.addEffect(fireworkEffect.build());
        firework.setFireworkMeta(fireworkMeta);
    }
}
