package cn.miranda.MeowCraft.Listeners;

import cn.miranda.MeowCraft.Enum.EggCatcher;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Utils.Occ;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class EggTokenUseEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void EggTokenUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if ((event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) && player.getInventory().getItemInMainHand().getType() == Material.PAPER && event.getHand() == EquipmentSlot.HAND) {
            ItemMeta playerItemMeta = player.getInventory().getItemInMainHand().getItemMeta();
            if (playerItemMeta == null) {
                return;
            }
            if (!Objects.equals(playerItemMeta.getDisplayName(), "§9怪物捕捉许可证")) {
                return;
            }
            if (playerItemMeta.getLore() == null) {
                return;
            }
            if (!playerItemMeta.getLore().get(playerItemMeta.getLore().size() - 1).equals("§7使用后可以使用雪球捕捉指定的怪物")) {
                return;
            }
            String type = playerItemMeta.getLore().get(0).split("§e可以捕捉 §b")[1];
            EggCatcher eggCatcher = EggCatcher.getByName(type);
            assert eggCatcher != null;
            String permission = eggCatcher.getPermission();
            if (player.hasPermission(permission)) {
                MessageManager.Message(player, String.format("§e你已经可以捕捉 §b%s, §e无需再次使用许可证", type));
                return;
            }
            Occ.addPermission(player, permission);
            MessageManager.Message(player, String.format("§e成功使用许可证, 你现在可以用雪球捕捉 §b%s §e了", type));
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        }
    }
}
