package cn.miranda.MeowCraft.Utils;

import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Task.PlayNoteTask;
import cn.miranda.MeowCraft.Task.Skill.ArrowBoostShootTask;
import cn.miranda.MeowCraft.Task.Skill.ImmuneTask;
import cn.miranda.MeowCraft.Task.Skill.SelfExplodeTask;
import cn.miranda.MeowCraft.Task.Skill.SummonTimeLeftTask;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static cn.miranda.MeowCraft.Manager.ConfigManager.skills;

public class SkillLib {
    public static List<Integer> arrowIDs = null;
    public static final HashMap<Player, List<Entity>> summons = new HashMap<>();

    public static void ArrowBoost(Player player, int settings_range, int settings_per_wave_amount, int settings_wave, long settings_interval) {
        arrowIDs = new ArrowBoostShootTask().ArrowBoostShoot(player, settings_range, settings_per_wave_amount, settings_wave, settings_interval);
    }

    public static void Bless(Player player) {
        int duration = skills.getInt("Voodoo_Bless.duration", 30);
        int level1 = skills.getInt("Voodoo_Bless.effect1_level", 1);
        int level2 = skills.getInt("Voodoo_Bless.effect2_level", 1);
        int level3 = skills.getInt("Voodoo_Bless.effect3_level", 1);
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, duration * 20, level1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, duration * 20, level2));
        player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, duration * 20, level3));
        MessageManager.Message(player, "§e你被祝福了, 快上!");
    }

    public static void Charge(Player player, int ratio) {
        int settings_duration = skills.getInt("Swordsman_Charge.duration", 20);
        Misc.moveForward(player, ratio);
        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, settings_duration * 20, 0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, settings_duration * 20, 3));
    }

    public static void Detect(Player player) {
        int settings_radius = skills.getInt("Artisan_Detect.radius", 20);
        Location playerLocation = player.getLocation();
        int playerX = (int) Math.round(playerLocation.getX());
        int playerY = (int) Math.round(playerLocation.getY());
        int playerZ = (int) Math.round(playerLocation.getZ());
        for (int currentRadius = 1; currentRadius <= settings_radius; currentRadius++) {
            for (int x = -(2 * currentRadius - 1); x <= (2 * currentRadius - 1); x++) {
                for (int z = -(2 * currentRadius - 1); z <= (2 * currentRadius - 1); z++) {
                    int currentX = playerX + x;
                    int currentZ = playerZ + z;
                    Location currentLocation = new Location(player.getWorld(), currentX, playerY, currentZ);
                    if (currentLocation.getBlock().getType().equals(Material.DIAMOND_ORE)) {
                        player.setCompassTarget(currentLocation);
                        MessageManager.Message(player, String.format("§e当前层离你最近的钻石矿位于 §b(%d %d %d)", (int) currentLocation.getX(), (int) currentLocation.getY(), (int) currentLocation.getZ()));
                        return;
                    }
                }
            }
        }
        MessageManager.Message(player, String.format("§e当前层 §b%d §e范围内没有钻石矿", settings_radius));
    }

    public static void Immune(Player player) {
        int duration = skills.getInt("All_Immune.duration", 10);
        player.setGlowing(true);
        player.setInvulnerable(true);
        new ImmuneTask().RemoveImmune(player, duration * 20);
    }

    public static void SelfExplode(Player player) {
        new SelfExplodeTask().SelfExplode(player);
    }

    public static void ThorAxe(Player player) {
        int settings_distance = skills.getInt("Thug_ThorAxe.distance", 2);
        Misc.throwSomething(player, Material.GOLDEN_AXE, settings_distance, 20, true);
    }

    // 传入的 potionType 只能是两种药水类型 (Material.SPLASH_POTION, Material.LINGERING_POTION)
    public static void ThrowPotions(Player player, Material potionType) {
        for (int i = 0; i <= 8; i++) {
            ItemStack itemStack = player.getInventory().getItem(i);
            if (itemStack == null) {
                continue;
            }
            Material material = itemStack.getType();
            List<Material> materialList = new ArrayList<>(Arrays.asList(Material.POTION, Material.SPLASH_POTION, Material.LINGERING_POTION));
            if (!materialList.contains(material)) {
                continue;
            }
            ItemStack itemStack_throw = new ItemStack(potionType);
            itemStack_throw.setItemMeta(itemStack.getItemMeta());
            ThrownPotion thrownPotion = player.launchProjectile(ThrownPotion.class);
            thrownPotion.setItem(itemStack_throw);
        }
    }

    public static void summonMob(Player player, EntityType type, int amount, int hp, long timeLeft) {
        for (int i = 0; i < amount; i++) {
            Entity summon = player.getWorld().spawnEntity(player.getLocation(), type);
            if (hp != 0) {
                LivingEntity summon_Living = (LivingEntity) summon;
                summon_Living.setMaxHealth(hp);
                summon_Living.setHealth(summon_Living.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
            }
            summon.setGlowing(true);
            new SummonTimeLeftTask().SummonTimeLeft(summon, timeLeft);
            if (summons.get(player) == null) {
                List<Entity> summonList = new CopyOnWriteArrayList<>();
                summonList.add(summon);
                summons.put(player, summonList);
            } else {
                summons.get(player).add(summon);
            }
        }
    }

    public static void playNote(Player player) {
        ArrayList<NoteWithTime> notes = Misc.readPlayerNote(player);
        if (notes == null) {
            return;
        }
        int offset = 12;
        for (NoteWithTime note : notes) {
            new PlayNoteTask().PlayNote(player, offset, note, Instrument.PIANO);
            offset += Long.parseLong(String.valueOf(12 / note.getTime()));
        }
    }
}
