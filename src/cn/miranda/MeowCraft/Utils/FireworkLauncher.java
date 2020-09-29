package cn.miranda.MeowCraft.Utils;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworkLauncher {
    public boolean trail;
    public boolean flicker;
    public Location location;
    public FireworkEffect.Type type;
    public Color[] color;
    public Color[] fade;
    public Firework firework;
    public int power;

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
