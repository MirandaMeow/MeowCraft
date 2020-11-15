package cn.miranda.MeowCraft.Listeners;

import cn.miranda.MeowCraft.Enum.EggCatcher;
import cn.miranda.MeowCraft.Manager.MessageManager;
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
                return;
            }
            if (!Misc.getMonsterCardTypes().contains(entity.getType())) {
                MessageManager.Message(player, String.format("§e怪物 §b%s §e不能被拓印", EggCatcher.valueOf(entityTypeName).getName()));
                return;
            }
            if (playerItemMeta.getLore().get(0).equals("§3空白的怪物卡片")) {
                List<String> loreList = playerItemMeta.getLore();
                loreList.set(0, "§3已拓印的怪物卡片");
                List<String> monsterSetting = new ArrayList<>();
                monsterSetting.add("");
                monsterSetting.add(String.format("§a怪物类型: %s", EggCatcher.valueOf(entityTypeName).getName()));
                monsterSetting.add(String.format("§a灵魂数量: %s%s", String.join("", Collections.nCopies(1, "■")), String.join("", Collections.nCopies(monsterCard.getInt(String.format("%s.maxCount", entityTypeName.toUpperCase())) - 1, "□"))));
                loreList.addAll(1, monsterSetting);
                playerItemMeta.setLore(loreList);
                giveCard(player, entity, playerItemMeta);
            } else if (playerItemMeta.getLore().get(0).equals("§3已拓印的怪物卡片")) {
                String cardMonsterType = playerItemMeta.getLore().get(2).split(" ")[1];
                if (EggCatcher.getEntityType(cardMonsterType) != entity.getType()) {
                    MessageManager.Message(player, "§e必须拓印同种类型的怪物");
                    return;
                }
                countPlus(playerItemMeta);
                giveCard(player, entity, playerItemMeta);
            } else {
                return;
            }
            MessageManager.Message(player, "§e拓印成功");
            if (getCharCount(player.getInventory().getItemInMainHand().getItemMeta().getLore().get(3), "□") != 0) {
                return;
            }
            ItemMeta newItemMeta = player.getInventory().getItemInMainHand().getItemMeta();
            List<String> newLore = newItemMeta.getLore();
            newLore.set(0, "§3已完成拓印的怪物卡片");
            newLore.set(5, "§6拿着卡片右击以使用怪物卡片");
            newItemMeta.setLore(newLore);
            player.getInventory().getItemInMainHand().setItemMeta(newItemMeta);
        }
    }

    private void giveCard(Player player, Entity entity, ItemMeta playerItemMeta) {
        ItemStack monsterCardItem = new ItemStack(Material.PAPER, 1);
        player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        monsterCardItem.setItemMeta(playerItemMeta);
        player.getInventory().addItem(monsterCardItem);
        entity.setCustomName("已被拓印的 " + EggCatcher.valueOf(entity.getType().toString()).getName());
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