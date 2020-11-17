package cn.miranda.MeowCraft.Listeners;

import cn.miranda.MeowCraft.Enum.EggCatcher;
import cn.miranda.MeowCraft.Enum.PotionEffect;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Utils.Effect;
import cn.miranda.MeowCraft.Utils.Misc;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cn.miranda.MeowCraft.Manager.ConfigManager.monsterCard;

public class MonsterCardCollectEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void MonsterCardCollect(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        String entityTypeName = entity.getType().toString();
        if (player.isSneaking() && player.getInventory().getItemInMainHand().getType() == Material.PAPER && event.getHand() == EquipmentSlot.HAND) {
            ItemMeta playerItemMeta = player.getInventory().getItemInMainHand().getItemMeta();
            if (playerItemMeta == null) {
                return;
            }
            if (!playerItemMeta.getDisplayName().equals("§9怪物卡片") || !playerItemMeta.hasLore()) {
                return;
            }
            if (entity.getCustomName() != null) {
                if (entity.getCustomName().contains("已被拓印的")) {
                    MessageManager.ActionBarMessage(player, "§e已被拓印的怪物不能再次被拓印");
                    return;
                }
            }
            if (!Misc.getMonsterCardTypes().contains(entity.getType())) {
                MessageManager.ActionBarMessage(player, String.format("§e怪物 §b%s §e不能被拓印", EggCatcher.valueOf(entityTypeName).getName()));
                return;
            }
            if (playerItemMeta.getLore().get(0).equals("§3空白的怪物卡片")) {
                List<String> loreList = playerItemMeta.getLore();
                loreList.set(0, "§3正在拓印的怪物卡片");
                List<String> monsterSetting = new ArrayList<>();
                monsterSetting.add("");
                monsterSetting.add(String.format("§a怪物类型: %s", EggCatcher.valueOf(entityTypeName).getName()));
                monsterSetting.add(String.format("§a灵魂数量: %s%s", String.join("", Collections.nCopies(1, "■")), String.join("", Collections.nCopies(monsterCard.getInt(String.format("%s.maxCount", entityTypeName.toUpperCase())) - 1, "□"))));
                monsterSetting.add("");
                monsterSetting.add("§a卡片效果: ");
                for (String current : monsterCard.getConfigurationSection(String.format("%s.effects", entityTypeName.toUpperCase())).getValues(false).keySet()) {
                    monsterSetting.add(String.format("§a    %s - 等级: %d", PotionEffect.valueOf(current).getName(), monsterCard.getInt(String.format("%s.effects.%s", entityTypeName.toUpperCase(), current)) + 1));
                }
                monsterSetting.add("");
                monsterSetting.add(String.format("§a持续时间: %d 秒", monsterCard.getInt(String.format("%s.duration", entityTypeName.toUpperCase()))));
                loreList.addAll(1, monsterSetting);
                playerItemMeta.setLore(loreList);
                giveCard(player, playerItemMeta);
                entity.setCustomName("已被拓印的 " + EggCatcher.valueOf(entity.getType().toString()).getName());
                MessageManager.ActionBarMessage(player, String.format("§e拓印 §b%s §e成功", EggCatcher.valueOf(entityTypeName).getName()));
                Effect.collectMonster(entity);
                return;
            } else if (playerItemMeta.getLore().get(0).equals("§3正在拓印的怪物卡片")) {
                String cardMonsterType = playerItemMeta.getLore().get(2).split(" ")[1];
                if (EggCatcher.getEntityType(cardMonsterType) != entity.getType()) {
                    MessageManager.ActionBarMessage(player, "§e必须拓印同种类型的怪物");
                    return;
                }
                countPlus(playerItemMeta);
                MessageManager.ActionBarMessage(player, String.format("§e拓印 §b%s §e成功", EggCatcher.valueOf(entityTypeName).getName()));
                if (getCharCount(playerItemMeta.getLore().get(3), "□") != 0) {
                    giveCard(player, playerItemMeta);
                    entity.setCustomName("已被拓印的 " + EggCatcher.valueOf(entity.getType().toString()).getName());
                    return;
                }
                List<String> newLore = playerItemMeta.getLore();
                newLore.set(0, "§3已完成拓印的怪物卡片");
                for (int line = 0; line < newLore.size(); line++) {
                    if (newLore.get(line).contains("shift")) {
                        newLore.set(line, "§6拿着卡片右击以使用怪物卡片");
                        break;
                    }
                }
                playerItemMeta.setLore(newLore);
                giveCard(player, playerItemMeta);
                Effect.collectMonster(entity);
            } else {
                return;
            }

            Effect.finishedCollect(player);
            MessageManager.ActionBarMessage(player, "§e怪物卡片已经拓印到了足够的怪物灵魂");
        }
    }

    private void giveCard(Player player, ItemMeta playerItemMeta) {
        ItemStack monsterCardItem = new ItemStack(Material.PAPER, 1);
        player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        monsterCardItem.setItemMeta(playerItemMeta);
        player.getInventory().addItem(monsterCardItem);
    }

    private void countPlus(ItemMeta itemMeta) {
        List<String> loreList = itemMeta.getLore();
        int collected = getCharCount(loreList.get(3), "■") + 1;
        int notCollectend = getCharCount(loreList.get(3), "□") - 1;
        loreList.set(3, String.format("§a灵魂数量: %s%s", String.join("", Collections.nCopies(collected, "■")), String.join("", Collections.nCopies(notCollectend, "□"))));
        itemMeta.setLore(loreList);
    }

    private int getCharCount(String string, String match) {
        int originalLength = string.length();
        String newString = string.replace(match, "");
        int nowLength = newString.length();
        return originalLength - nowLength;
    }
}