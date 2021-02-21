package cn.miranda.MeowCraft.Enum;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static cn.miranda.MeowCraft.Manager.ConfigManager.config;

public enum EggCatcher {
    BAT("蝙蝠", EntityType.BAT, config.getBoolean("EggCatcher.mobs.BAT")),
    BEE("蜜蜂", EntityType.BEE, config.getBoolean("EggCatcher.mobs.BEE")),
    BLAZE("烈焰人", EntityType.BLAZE, config.getBoolean("EggCatcher.mobs.BLAZE")),
    CAT("猫", EntityType.CAT, config.getBoolean("EggCatcher.mobs.CAT")),
    CAVE_SPIDER("洞穴蜘蛛", EntityType.CAVE_SPIDER, config.getBoolean("EggCatcher.mobs.CAVE_SPIDER")),
    CHICKEN("鸡", EntityType.CHICKEN, config.getBoolean("EggCatcher.mobs.CHICKEN")),
    COD("鳕鱼", EntityType.COD, config.getBoolean("EggCatcher.mobs.COD")),
    COW("牛", EntityType.COW, config.getBoolean("EggCatcher.mobs.COW")),
    CREEPER("爬行者", EntityType.CREEPER, config.getBoolean("EggCatcher.mobs.CREEPER")),
    DOLPHIN("海豚", EntityType.DOLPHIN, config.getBoolean("EggCatcher.mobs.DOLPHIN")),
    DONKEY("驴", EntityType.DONKEY, config.getBoolean("EggCatcher.mobs.DONKEY")),
    DROWNED("溺尸", EntityType.DROWNED, config.getBoolean("EggCatcher.mobs.DROWNED")),
    ELDER_GUARDIAN("远古守卫者", EntityType.ELDER_GUARDIAN, config.getBoolean("EggCatcher.mobs.ELDER_GUARDIAN")),
    ENDERMAN("末影人", EntityType.ENDERMAN, config.getBoolean("EggCatcher.mobs.ENDERMAN")),
    ENDERMITE("末影螨", EntityType.ENDERMITE, config.getBoolean("EggCatcher.mobs.ENDERMITE")),
    EVOKER("唤魔者", EntityType.EVOKER, config.getBoolean("EggCatcher.mobs.EVOKER")),
    FOX("狐狸", EntityType.FOX, config.getBoolean("EggCatcher.mobs.FOX")),
    GHAST("恶魂", EntityType.GHAST, config.getBoolean("EggCatcher.mobs.GHAST")),
    GUARDIAN("守卫者", EntityType.GUARDIAN, config.getBoolean("EggCatcher.mobs.GUARDIAN")),
    HOGLIN("疣猪兽", EntityType.HOGLIN, config.getBoolean("EggCatcher.mobs.HOGLIN")),
    HORSE("马", EntityType.HORSE, config.getBoolean("EggCatcher.mobs.HORSE")),
    HUSK("尸壳", EntityType.HUSK, config.getBoolean("EggCatcher.mobs.HUSK")),
    LLAMA("羊驼", EntityType.LLAMA, config.getBoolean("EggCatcher.mobs.LLAMA")),
    MAGMA_CUBE("岩浆怪", EntityType.MAGMA_CUBE, config.getBoolean("EggCatcher.mobs.MAGMA_CUBE")),
    MULE("骡", EntityType.MULE, config.getBoolean("EggCatcher.mobs.MULE")),
    MUSHROOM("哞菇", EntityType.MUSHROOM_COW,config.getBoolean("EggCatcher.mobs.MUSHROOM")),
    OCELOT("豹猫", EntityType.OCELOT, config.getBoolean("EggCatcher.mobs.OCELOT")),
    PANDA("熊猫", EntityType.PANDA, config.getBoolean("EggCatcher.mobs.PANDA")),
    PARROT("鹦鹉", EntityType.PARROT, config.getBoolean("EggCatcher.mobs.PARROT")),
    PHANTOM("幻翼", EntityType.PHANTOM, config.getBoolean("EggCatcher.mobs.PHANTOM")),
    PIG("猪", EntityType.PIG, config.getBoolean("EggCatcher.mobs.PIG")),
    PIGLIN("猪灵", EntityType.PIGLIN, config.getBoolean("EggCatcher.mobs.PIGLIN")),
    PIGLIN_BRUTE("猪灵蛮兵", EntityType.PIGLIN_BRUTE, config.getBoolean("EggCatcher.mobs.PIGLIN_BRUTE")),
    PILLAGER("掠夺者", EntityType.PILLAGER, config.getBoolean("EggCatcher.mobs.PILLAGER")),
    POLAR_BEAR("北极熊", EntityType.POLAR_BEAR, config.getBoolean("EggCatcher.mobs.POLAR_BEAR")),
    PUFFERFISH("河豚", EntityType.PUFFERFISH, config.getBoolean("EggCatcher.mobs.PUFFERFISH")),
    RABBIT("兔子", EntityType.RABBIT, config.getBoolean("EggCatcher.mobs.RABBIT")),
    RAVAGER("劫掠兽", EntityType.RAVAGER, config.getBoolean("EggCatcher.mobs.RAVAGER")),
    SALMON("鲑鱼", EntityType.SALMON, config.getBoolean("EggCatcher.mobs.SALMON")),
    SHEEP("羊", EntityType.SHEEP, config.getBoolean("EggCatcher.mobs.SHEEP")),
    SHULKER("潜影贝", EntityType.SHULKER, config.getBoolean("EggCatcher.mobs.SHULKER")),
    SILVERFISH("蠹虫", EntityType.SILVERFISH, config.getBoolean("EggCatcher.mobs.SILVERFISH")),
    SKELETON("骷髅", EntityType.SKELETON, config.getBoolean("EggCatcher.mobs.SKELETON")),
    SKELETON_HORSE("骷髅马", EntityType.SKELETON_HORSE, config.getBoolean("EggCatcher.mobs.SKELETON_HORSE")),
    SLIME("史莱姆", EntityType.SLIME, config.getBoolean("EggCatcher.mobs.SLIME")),
    SPIDER("蜘蛛", EntityType.SPIDER, config.getBoolean("EggCatcher.mobs.SPIDER")),
    SQUID("鱿鱼", EntityType.SQUID, config.getBoolean("EggCatcher.mobs.SQUID")),
    STRAY("流浪者", EntityType.STRAY, config.getBoolean("EggCatcher.mobs.STRAY")),
    STRIDER("炽足兽", EntityType.STRIDER, config.getBoolean("EggCatcher.mobs.STRIDER")),
    TRADER_LLAMA("行商羊驼", EntityType.TRADER_LLAMA, config.getBoolean("EggCatcher.mobs.TRADER_LLAMA")),
    TROPICAL_FISH("热带鱼", EntityType.TROPICAL_FISH, config.getBoolean("EggCatcher.mobs.TROPICAL_FISH")),
    TURTLE("海龟", EntityType.TURTLE, config.getBoolean("EggCatcher.mobs.TURTLE")),
    VEX("恼鬼", EntityType.VEX, config.getBoolean("EggCatcher.mobs.VEX")),
    VILLAGER("村民", EntityType.VILLAGER, config.getBoolean("EggCatcher.mobs.VILLAGER")),
    VINDICATOR("卫道士", EntityType.VINDICATOR, config.getBoolean("EggCatcher.mobs.VINDICATOR")),
    WANDERING_TRADER("流浪商人", EntityType.WANDERING_TRADER, config.getBoolean("EggCatcher.mobs.WANDERING_TRADER")),
    WITCH("女巫", EntityType.WITCH, config.getBoolean("EggCatcher.mobs.WITCH")),
    WITHER_SKELETON("凋零骷髅", EntityType.WITHER_SKELETON, config.getBoolean("EggCatcher.mobs.WITHER_SKELETON")),
    WOLF("狼", EntityType.WOLF, config.getBoolean("EggCatcher.mobs.WOLF")),
    ZOGLIN("僵尸疣猪兽", EntityType.ZOGLIN, config.getBoolean("EggCatcher.mobs.ZOGLIN")),
    ZOMBIE("僵尸", EntityType.ZOMBIE, config.getBoolean("EggCatcher.mobs.ZOMBIE")),
    ZOMBIE_HORSE("僵尸马", EntityType.ZOMBIE_HORSE, config.getBoolean("EggCatcher.mobs.ZOMBIE_HORSE")),
    ZOMBIE_VILLAGER("僵尸村民", EntityType.ZOMBIE_VILLAGER, config.getBoolean("EggCatcher.mobs.ZOMBIE_VILLAGER")),
    ZOMBIFIED_PIGLIN("僵尸猪灵", EntityType.ZOMBIFIED_PIGLIN, config.getBoolean("EggCatcher.mobs.ZOMBIFIED_PIGLIN"));

    private final String name;
    private final EntityType entityType;
    private boolean available;

    EggCatcher(String name, EntityType entityType, boolean available) {
        this.name = name;
        this.entityType = entityType;
        this.available = available;
    }

    public static List<EntityType> getAvailableList() {
        List<EntityType> list = new ArrayList<>();
        for (EggCatcher i : EggCatcher.values()) {
            if (i.getAvailable()) {
                list.add(i.getEntityType());
            }
        }
        return list;
    }

    public static EntityType getEntityType(String name) {
        for (EggCatcher i : EggCatcher.values()) {
            if (i.getName().equals(name)) {
                return i.getEntityType();
            }
        }
        return null;
    }

    public static void flushAvailable() {
        for (EggCatcher i : EggCatcher.values()) {
            i.setAvailable(config.getBoolean(String.format("EggCatcher.mobs.%s", i.getEntityType().toString())));
        }
    }

    public String getName() {
        return this.name;
    }

    public EntityType getEntityType() {
        return this.entityType;
    }

    public boolean getAvailable() {
        return this.available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getPermission() {
        return "eggcatcher." + this.entityType.toString().toLowerCase();
    }

    public ItemStack getItemStack() {
        Material material = Material.valueOf(this.getEntityType().toString().toUpperCase() + "_SPAWN_EGG");
        return new ItemStack(material);
    }
}
