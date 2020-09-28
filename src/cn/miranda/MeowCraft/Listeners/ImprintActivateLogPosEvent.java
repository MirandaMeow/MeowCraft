package cn.miranda.MeowCraft.Listeners;

import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Utils.Misc;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Objects;

import static org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK;

public class ImprintActivateLogPosEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void ImprintActivateLogPos(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.isSneaking() && event.getAction() == RIGHT_CLICK_BLOCK && player.getInventory().getItemInMainHand().getType() == Material.PAPER && event.getHand() == EquipmentSlot.HAND) {
            ItemMeta playerItemMeta = player.getInventory().getItemInMainHand().getItemMeta();
            if (playerItemMeta == null) {
                return;
            }
            if (playerItemMeta.getDisplayName().equals("§9烙印卷轴") && playerItemMeta.hasLore()) {
                if (!Objects.equals(playerItemMeta.getLore().get(0), "§3空白的烙印卷轴")) {
                    return;
                }
                if (Misc.isInventoryFull(player)) {
                    MessageManager.Message(player, "§9[§d烙印§9] §c你的背包满了");
                    return;
                }
                Location playerLocation = player.getLocation();
                String playerWorld = playerLocation.getWorld().getName();
                String playerX = Misc.stringFormat(playerLocation.getX());
                String playerY = Misc.stringFormat(playerLocation.getY());
                String playerZ = Misc.stringFormat(playerLocation.getZ());
                String playerPitch = Misc.stringFormat(playerLocation.getPitch());
                String playerYaw = Misc.stringFormat(playerLocation.getYaw());
                ArrayList<String> loreList = new ArrayList<>();
                loreList.add("§3附有烙印的卷轴");
                loreList.add("");
                loreList.add("§e----烙印位置----");
                loreList.add(String.format("§a世界: %s", playerWorld));
                loreList.add(String.format("§aX: %s", playerX));
                loreList.add(String.format("§aY: %s", playerY));
                loreList.add(String.format("§aZ: %s", playerZ));
                loreList.add(String.format("§a角度: %s", playerYaw));
                loreList.add(String.format("§a仰角: %s", playerPitch));
                loreList.add("§e-----------------");
                loreList.add("");
                loreList.add("§6右击地面烙印归还");
                loreList.add("");
                loreList.add("§7这种卷轴能够依据使用者对当时所在地的");
                loreList.add("§7强烈印象留下烙印, 当再次打开它时, ");
                loreList.add("§7使用者将被吸入裂隙回到印记之处, ");
                loreList.add("§7但偶尔会有人使用后不知所踪");
                ItemStack imprintedScroll = new ItemStack(Material.PAPER, 1);
                ItemMeta imprintedScrollMeta = imprintedScroll.getItemMeta();
                imprintedScrollMeta.setDisplayName("§9烙印卷轴");
                imprintedScrollMeta.setLore(loreList);
                imprintedScrollMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                imprintedScroll.setItemMeta(imprintedScrollMeta);
                imprintedScroll.addUnsafeEnchantment(Enchantment.OXYGEN, 1);
                player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
                player.getInventory().addItem(imprintedScroll);
                player.updateInventory();
                playerLocation.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, playerLocation, 100);
                playerLocation.getWorld().playSound(player.getLocation(), Sound.BLOCK_PORTAL_TRIGGER, 10, 10);
                player.sendTitle("§l§c烙 印 已 缔 结", "", 10, 70, 20);
            }
        }

    }
}
