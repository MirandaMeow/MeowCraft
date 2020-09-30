package cn.miranda.MeowCraft.Utils;

import com.gmail.nossr50.datatypes.player.PlayerProfile;
import com.gmail.nossr50.datatypes.skills.PrimarySkillType;
import com.gmail.nossr50.util.player.UserManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static cn.miranda.MeowCraft.Manager.ConfigManager.playerData;
import static cn.miranda.MeowCraft.Manager.ConfigManager.skills;

public class Occ {
    public static void addGroup(Player player, String group) {
        PermissionUser pexUser = PermissionsEx.getUser(player);
        pexUser.addGroup(group);
    }

    public static void removeGroup(Player player, String group) {
        PermissionUser pexUser = PermissionsEx.getUser(player);
        pexUser.removeGroup(group);
    }

    public static void removeALLOccGroups(Player player) {
        PermissionUser pexUser = PermissionsEx.getUser(player);
        for (String currentGroup : new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E"))) {
            pexUser.removeGroup(currentGroup);
        }
    }

    public static void reversePlayerGroup(String playerName) {
        PermissionUser pexUser = PermissionsEx.getUser(playerName);
        List<PermissionGroup> userGroup = new ArrayList<>(Arrays.asList(pexUser.getGroups()));
        List<String> groupList = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E"));
        if (!groupList.contains(userGroup.get(0).getName())) {
            return;
        }
        pexUser.addGroup("default");
        for (PermissionGroup permissionGroup : userGroup) {
            pexUser.removeGroup(permissionGroup);
        }
        for (PermissionGroup permissionGroup : userGroup) {
            pexUser.addGroup(permissionGroup);
        }
        pexUser.removeGroup("default");
    }

    public static void mcMMOSkillsReset(Player player) {
        PlayerProfile mcmmoProfile = UserManager.getPlayer(player).getProfile();
        for (PrimarySkillType skillType : PrimarySkillType.NON_CHILD_SKILLS) {
            mcmmoProfile.modifySkill(skillType, 0);
        }
    }

    public static void removePermission(Player player, String permission) {
        PermissionUser pexUser = PermissionsEx.getUser(player);
        pexUser.removePermission(permission);
    }

    public static boolean inGroup(Player player, String group) {
        PermissionUser pexUser = PermissionsEx.getUser(player);
        return pexUser.inGroup(group);
    }

    public static List<String> skillList() {
        List<String> skillListName = new ArrayList<>();
        Map<String, Object> skillConfigMap = skills.getValues(true);
        for (Object key : skillConfigMap.keySet()) {
            String path = key.toString();
            if (path.contains("name")) {
                skillListName.add(skills.getString(path));
            }
        }
        return skillListName;
    }

    public static String getSkillID(String skill) {
        String path = null;
        Map<String, Object> skillConfigMap = skills.getValues(false);
        for (Object key : skillConfigMap.keySet()) {
            path = key.toString();
            if (skills.getString(String.format("%s.name", path)).contains(skill)) {
                break;
            }
        }
        return path.split("\\.")[0];
    }

    public static List<String> getSkillLore(String skill) {
        return (List<String>) skills.getList(String.format("%s.lore", skill));
    }

    public static String getHoverLore(String skill) {
        return skills.getString(String.format("%s.hoverLore", skill));
    }

    public static List<?> getSkillOccGroup(String skill) {
        String path = getSkillID(skill);
        return skills.getList(String.format("%s.group", path));
    }

    public static boolean isFitOcc(Player player, String skill) {
        String playerName = player.getName();
        List<?> fitOcc = skills.getList(String.format("%s.group", skill));
        String playerOcc = playerData.getString(String.format("%s.occConfig.name", playerName));
        return fitOcc.contains(playerOcc);
    }

    public static boolean requireItem(Player player, Material type, int cost) {
        Inventory playerInventory = player.getInventory();
        for (int i = 0; i <= 35; i++) {
            ItemStack currentItem = playerInventory.getItem(i);
            if (currentItem == null) {
                continue;
            }
            if (currentItem.getType() != type) {
                continue;
            }
            if (currentItem.getAmount() < cost) {
                continue;
            }
            currentItem.setAmount(currentItem.getAmount() - cost);
            return true;
        }
        return false;
    }

    public static Player getTarget(Player source) {
        List<Block> blocksInSight = source.getLineOfSight(null, 100);
        List<Entity> nearEntities = source.getNearbyEntities(100, 100, 100);
        if (blocksInSight != null && nearEntities != null) {
            for (Block block : blocksInSight) {
                int xBlock = block.getX();
                int yBlock = block.getY();
                int zBlock = block.getZ();

                for (Entity entity : nearEntities) {
                    if (entity instanceof Player) {
                        Location entityLocation = entity.getLocation();
                        int xEntity = entityLocation.getBlockX();
                        int yEntity = entityLocation.getBlockY();
                        int zEntity = entityLocation.getBlockZ();
                        if (xEntity == xBlock && (yEntity <= yBlock && yEntity >= yBlock - 1) && zEntity == zBlock) {
                            return (Player) entity;
                        }
                    }
                }
            }
        }
        return null;
    }

    public static List<String> playerNoSkillsChinese(Player player, String[] list) {
        String playerName = player.getName();
        List<String> noList = new ArrayList<>();
        for (String i : list) {
            if (!playerData.getBoolean(String.format("%s.occConfig.occSkills.%s", playerName, getSkillID(i)))) {
                noList.add(i);
            }
        }
        return noList;
    }

    public static String getSkillChineseStringFromList(List<String> list) {
        StringBuilder out = new StringBuilder();
        for (Object i : list) {
            out.append(i.toString()).append(" ");
        }
        return out.toString();
    }

    public static boolean isPlayerHasSkillOnce(Player player, String[] list) {
        String playerName = player.getName();
        for (String i : list) {
            if (playerData.getBoolean(String.format("%s.occConfig.occSkills.%s", playerName, getSkillID(i)))) {
                return true;
            }
        }
        return false;
    }

    public static String getSkillChineseString(String[] list) {
        StringBuilder out = new StringBuilder();
        for (String i : list) {
            out.append(i).append(" ");
        }
        return out.toString();
    }

    public static String getSkillChineseById(String skillID) {
        return skills.getString(String.format("%s.name", skillID));
    }

    public static List<String> getPlayerSkills(Player player) {
        List<String> list = new ArrayList<>();
        ConfigurationSection skillsList = playerData.getConfigurationSection(String.format("%s.occConfig.occSkills", player.getName()));
        if (skillsList == null) {
            return new ArrayList<>();
        }
        for (Object key : skillsList.getValues(false).keySet()) {
            list.add(key.toString());
        }
        list.remove("occCoolDown");
        return list;
    }

    public static List<String> getSkillDescribe(String skill) {
        List<String> out = new ArrayList<>();
        String hoverLore = getHoverLore(skill);
        String[] args = hoverLore.split(",");
        List<String> SkillLore = Occ.getSkillLore(skill);
        for (String s : args) {
            out.add(SkillLore.get(Integer.parseInt(s)));
        }
        return out;
    }
}
