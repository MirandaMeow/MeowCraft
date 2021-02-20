package cn.miranda.MeowCraft.Task;

import cn.miranda.MeowCraft.MeowCraft;
import cn.miranda.MeowCraft.Utils.NoteWithTime;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Collection;

import static org.bukkit.Bukkit.getScheduler;

public class PlayNoteTask {
    public void PlayNote(Player player, int offset, NoteWithTime noteWithTime, Instrument instrument) {
        getScheduler().runTaskLater(MeowCraft.plugin, () -> {
            Note note = noteWithTime.getNote();
            if (note == null) {
                return;
            }
            Collection<Entity> near = player.getWorld().getNearbyEntities(player.getLocation(), 10, 10, 10);
            for (Entity entity : near) {
                if (!(entity instanceof Player)) {
                    continue;
                }
                ((Player) entity).playNote(player.getLocation(), instrument, note);
            }
            player.spawnParticle(Particle.NOTE, player.getLocation().add(0, 2, 0), 1);
        }, offset);
    }
}
