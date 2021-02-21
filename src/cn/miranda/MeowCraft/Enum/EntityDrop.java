package cn.miranda.MeowCraft.Enum;

import cn.miranda.MeowCraft.Utils.IO;
import cn.miranda.MeowCraft.Utils.ItemDropTable;
import com.sun.istack.internal.NotNull;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import static cn.miranda.MeowCraft.Manager.ConfigManager.entityDrop;

public enum EntityDrop {
    BAT("蝙蝠", loadData("BAT")),
    BEE("蜜蜂", loadData("BEE")),
    BLAZE("烈焰人", loadData("BLAZE")),
    CAT("猫", loadData("CAT")),
    CAVE_SPIDER("洞穴蜘蛛", loadData("CAVE_SPIDER")),
    CHICKEN("鸡", loadData("CHICKEN")),
    COD("鳕鱼", loadData("COD")),
    COW("牛", loadData("COW")),
    CREEPER("爬行者", loadData("CREEPER")),
    DOLPHIN("海豚", loadData("DOLPHIN")),
    DONKEY("驴", loadData("DONKEY")),
    DROWNED("溺尸", loadData("DROWNED")),
    ELDER_GUARDIAN("远古守卫者", loadData("ELDER_GUARDIAN")),
    ENDER_DRAGON("末影龙", loadData("ENDER_DRAGON")),
    ENDERMAN("末影人", loadData("ENDERMAN")),
    ENDERMITE("末影螨", loadData("ENDERMITE")),
    EVOKER("唤魔者", loadData("EVOKER")),
    FOX("狐狸", loadData("FOX")),
    GHAST("恶魂", loadData("GHAST")),
    GIANT("僵尸巨人", loadData("GIANT")),
    GUARDIAN("守卫者", loadData("GUARDIAN")),
    HOGLIN("疣猪兽", loadData("HOGLIN")),
    HORSE("马", loadData("HORSE")),
    HUSK("尸壳", loadData("HUSK")),
    ILLUSIONER("幻术师", loadData("ILLUSIONER")),
    IRON_GOLEM("铁傀儡", loadData("IRON_GOLEM")),
    LLAMA("羊驼", loadData("LLAMA")),
    MAGMA_CUBE("岩浆怪", loadData("MAGMA_CUBE")),
    MULE("骡", loadData("MULE")),
    MUSHROOM_COW("哞菇", loadData("MUSHROOM_COW")),
    OCELOT("豹猫", loadData("OCELOT")),
    PANDA("熊猫", loadData("PANDA")),
    PARROT("鹦鹉", loadData("PARROT")),
    PHANTOM("幻翼", loadData("PHANTOM")),
    PIG("猪", loadData("PIG")),
    PIGLIN("猪灵", loadData("PIGLIN")),
    PIGLIN_BRUTE("猪灵蛮兵", loadData("PIGLIN_BRUTE")),
    PILLAGER("掠夺者", loadData("PILLAGER")),
    POLAR_BEAR("北极熊", loadData("POLAR_BEAR")),
    PUFFERFISH("河豚", loadData("PUFFERFISH")),
    RABBIT("兔子", loadData("RABBIT")),
    RAVAGER("劫掠兽", loadData("RAVAGER")),
    SALMON("鲑鱼", loadData("SALMON")),
    SHEEP("羊", loadData("SHEEP")),
    SHULKER("潜影贝", loadData("SHULKER")),
    SILVERFISH("蠹虫", loadData("SILVERFISH")),
    SKELETON("骷髅", loadData("SKELETON")),
    SKELETON_HORSE("骷髅马", loadData("SKELETON_HORSE")),
    SLIME("史莱姆", loadData("SLIME")),
    SNOWMAN("雪人", loadData("SNOWMAN")),
    SPIDER("蜘蛛", loadData("SPIDER")),
    SQUID("鱿鱼", loadData("SQUID")),
    STRAY("流浪者", loadData("STRAY")),
    STRIDER("炽足兽", loadData("STRIDER")),
    TRADER_LLAMA("行商羊驼", loadData("TRADER_LLAMA")),
    TROPICAL_FISH("热带鱼", loadData("TROPICAL_FISH")),
    TURTLE("海龟", loadData("TURTLE")),
    VEX("恼鬼", loadData("VEX")),
    VILLAGER("村民", loadData("VILLAGER")),
    VINDICATOR("卫道士", loadData("VINDICATOR")),
    WANDERING_TRADER("流浪商人", loadData("WANDERING_TRADER")),
    WITCH("女巫", loadData("WITCH")),
    WITHER("凋零", loadData("WITHER")),
    WITHER_SKELETON("凋零骷髅", loadData("WITHER_SKELETON")),
    WOLF("狼", loadData("WOLF")),
    ZOGLIN("僵尸疣猪兽", loadData("ZOGLIN")),
    ZOMBIE("僵尸", loadData("ZOMBIE")),
    ZOMBIE_HORSE("僵尸马", loadData("ZOMBIE_HORSE")),
    ZOMBIE_VILLAGER("僵尸村民", loadData("ZOMBIE_VILLAGER")),
    ZOMBIFIED_PIGLIN("僵尸猪灵", loadData("ZOMBIFIED_PIGLIN"));

    private final String name;
    private ItemDropTable data;

    EntityDrop(String name, ItemDropTable data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return this.name;
    }

    @NotNull
    public ItemDropTable getItemDropTable() {
        return this.data;
    }

    private static ItemDropTable loadData(String entityType) {
        ItemDropTable itemDropTable = new ItemDropTable();
        try {
            String rawData = entityDrop.getString(entityType);
            if (Objects.equals(rawData, "")) {
                return itemDropTable;
            }
            itemDropTable = (ItemDropTable) IO.decodeData(rawData);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return itemDropTable;
        }
        return itemDropTable;
    }

    public void setData(ItemDropTable data) {
        this.data = data;
    }

    public static ArrayList<String> getList() {
        ArrayList<String> EntityList = new ArrayList<>();
        for (EntityDrop entityDrop : EntityDrop.values()) {
            EntityList.add(entityDrop.getName());
        }
        return EntityList;
    }

    public static EntityDrop getByName(String name) {
        for (EntityDrop entityDrop : EntityDrop.values()) {
            if (Objects.equals(entityDrop.getName(), name)) {
                return entityDrop;
            }
        }
        return null;
    }

    public ItemStack[] getItems() {
        ArrayList<ItemStack> itemStacks = new ArrayList<>();
        for (Map.Entry<ItemStack, Integer> entry : this.data.getData().entrySet()) {
            itemStacks.add(entry.getKey());
        }
        return itemStacks.toArray(new ItemStack[0]);
    }
}
