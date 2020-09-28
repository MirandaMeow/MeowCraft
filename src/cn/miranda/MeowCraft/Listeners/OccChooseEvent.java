package cn.miranda.MeowCraft.Listeners;

import cn.miranda.MeowCraft.Manager.ConfigManager;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Utils.Occ;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static cn.miranda.MeowCraft.Manager.ConfigManager.playerData;

public class OccChooseEvent implements Listener {
    public HashMap<String, String> occList = new HashMap<String, String>() {{
        put("莽夫", "A");
        put("游侠", "B");
        put("剑使", "C");
        put("巫毒", "D");
        put("匠师", "E");
    }};
    List<Material> signs = new ArrayList<>(Arrays.asList(Material.OAK_WALL_SIGN, Material.SPRUCE_WALL_SIGN, Material.BIRCH_WALL_SIGN, Material.ACACIA_WALL_SIGN, Material.JUNGLE_WALL_SIGN, Material.DARK_OAK_WALL_SIGN));

    @EventHandler(priority = EventPriority.NORMAL)
    private void OccChoose(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getHand() != EquipmentSlot.HAND) {
            return;
        }
        if (!event.hasBlock()) {
            return;
        }
        Block block = event.getClickedBlock();
        if (!signs.contains(block.getType())) {
            return;
        }
        Player player = event.getPlayer();
        String playerName = player.getName();
        Sign blockSign = (Sign) block.getState();
        if (!blockSign.getLine(0).equals("[职业选择]")) {
            return;
        }
        if (!player.hasPermission("occ.set")) {
            MessageManager.Message(player, "§c你无法选择职业");
            return;
        }
        String occName = blockSign.getLine(1);
        if (occList.get(occName) == null) {
            MessageManager.Message(player, "§c职业不存在");
            return;
        }
        Occ.removePermission(player, "occ.set");
        String occ = occList.get(occName);
        Occ.removeALLOccGroups(player);
        Occ.removeGroup(player, "Essential");
        Occ.addGroup(player, occ);
        playerData.set(String.format("%s.occConfig.name", playerName), occ);
        playerData.set(String.format("%s.occConfig.enabled", playerName), true);
        ConfigManager.saveConfigs();
        MessageManager.Message(player, String.format("§e你已经选择了§a%s§e作为职业", occName));
    }
}
