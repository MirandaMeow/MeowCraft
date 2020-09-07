package cn.miranda.MeowCraft.Listeners;

import cn.miranda.MeowCraft.Manager.ConfigMaganer;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Utils.Effect;
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

import java.util.List;
import java.util.Objects;

import static cn.miranda.MeowCraft.Manager.ConfigMaganer.skills;
import static cn.miranda.MeowCraft.Manager.ConfigMaganer.playerData;

public class ScrollUseEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    private void ScrollUseEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        if ((event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) && player.getInventory().getItemInMainHand().getType() == Material.PAPER && event.getHand() == EquipmentSlot.HAND) {
            ItemMeta playerItemMeta = player.getInventory().getItemInMainHand().getItemMeta();
            if (playerItemMeta == null) {
                return;
            }
            if (!Objects.equals(playerItemMeta.getDisplayName(), "§9秘传之书")) {
                return;
            }
            if (playerData.get(String.format("%s.occConfig", playerName)) == null) {
                MessageManager.Messager(player, "§e没有职业, 不能学习技能");
                return;
            }
            String skillChineseName = playerItemMeta.getLore().get(0).split(" ")[1].substring(2);
            String skillID = Occ.getSkillID(skillChineseName);
            String playerOcc = playerData.getString(String.format("%s.occConfig.name", playerName));
            List occ = Occ.getSkillOccGroup(skillChineseName);
            if (!player.hasPermission("occ.bypass")) {
                if (!occ.contains(playerOcc)) {
                    MessageManager.Messager(player, skills.getString(String.format("%s.notFitMessage", skillID)));
                    return;
                }
            }
            if (playerData.getBoolean(String.format("%s.occConfig.occSkills.%s", playerName, skillID))) {
                MessageManager.Messager(player, "§e你已经习得此技能");
                return;
            }
            int isRequiredPreposeSkill = playerItemMeta.getLore().indexOf("§3需要前置技能");
            if (isRequiredPreposeSkill != -1) {
                String[] requirePreposeSkillChinese = playerItemMeta.getLore().get(isRequiredPreposeSkill + 1).substring(2).split(" ");
                int once = playerItemMeta.getLore().indexOf("§3其中之一");
                if (once != -1) {
                    boolean pass = Occ.isPlayerhasSkillOnce(player, requirePreposeSkillChinese);
                    if (!pass) {
                        MessageManager.Messager(player, String.format("§e需要前置技能: §c§l%s§r§e其中之一", Occ.getSkillChineseString(requirePreposeSkillChinese)));
                        return;
                    }
                } else {
                    List noSkillChinese = Occ.playerNoSkillsChinese(player, requirePreposeSkillChinese);
                    if (noSkillChinese.size() != 0) {
                        MessageManager.Messager(player, String.format("§e需要前置技能: §c§l%s§r", Occ.getSkillChineseStringFromList(noSkillChinese)));
                        return;
                    }
                }
            }
            int isOverRide = playerItemMeta.getLore().indexOf("§3覆盖以下技能");
            boolean overRide = false;
            StringBuilder hint = new StringBuilder();
            if (isOverRide != -1) {
                String[] overRideSkillChineseNameList = playerItemMeta.getLore().get(isOverRide + 1).substring(2).split(" ");
                for (String i : overRideSkillChineseNameList) {
                    String nowSkillID = Occ.getSkillID(i);
                    if (playerData.getBoolean(String.format("%s.occConfig.occSkills.%s", playerName, nowSkillID))) {
                        playerData.set(String.format("%s.occConfig.occSkills.%s", playerName, nowSkillID), null);
                        hint.append(i).append(" ");
                        overRide = true;
                    }
                }
            }
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
            if (overRide) {
                MessageManager.Messager(player, String.format("§e技能 §c§l%s§r§e被替换为了 §c§l%s§r", hint, skillChineseName));
            } else {
                MessageManager.Messager(player, String.format("§e你习得了技能 §c§l%s§r", skillChineseName));
            }
            playerData.set(String.format("%s.occConfig.occSkills.%s", playerName, skillID), true);
            Effect.useEsotericaScroll(player);
            ConfigMaganer.saveConfigs();
        }

    }
}
