package cn.miranda.MeowCraft.Command;

import cn.miranda.MeowCraft.Enum.Notify;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Utils.Misc;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static cn.miranda.MeowCraft.Manager.PluginLoadManager.econ;

public final class ConvertExpCommand implements TabExecutor {
    private static int getExpAtLevel(Player player) {
        return getExpAtLevel(player.getLevel());
    }

    public static int getExpAtLevel(int level) {
        if (level <= 15) {
            return 2 * level + 7;
        }
        if (level <= 30) {
            return 5 * level - 38;
        }
        return 9 * level - 158;
    }

    public static int getTotalExperience(Player player) {
        int exp = Math.round(getExpAtLevel(player) * player.getExp());
        int currentLevel = player.getLevel();

        while (currentLevel > 0) {
            currentLevel--;
            exp += getExpAtLevel(currentLevel);
        }
        if (exp < 0) {
            exp = Integer.MAX_VALUE;
        }
        return exp;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            MessageManager.Message(sender, Notify.No_Console.getString());
            return true;
        }
        if (!sender.hasPermission("convert.admin")) {
            MessageManager.Message(sender, Notify.No_Permission.getString());
            return true;
        }
        Player player = (Player) sender;
        if (args.length != 3) {
            MessageManager.Message(sender, "§e用法: §6/convertexp §b<tomoney|toexp> <exp|money> <money|exp>");
            return true;
        }
        if (!Misc.isInt(args[1]) || !Misc.isInt(args[2])) {
            MessageManager.Message(sender, Notify.Invalid_Input.getString());
            return true;
        }
        int setValue1 = Integer.parseInt(args[1]);
        int setValue2 = Integer.parseInt(args[2]);
        if (setValue1 <= 0 || setValue1 > 65535 || setValue2 <= 0 || setValue2 > 65535) {
            MessageManager.Message(sender, "§e输入的数值范围是 §b1 - 65535 §e之间的正整数");
            return true;
        }
        String playerName = player.getName();
        double playerExp = getTotalExperience(player);
        double playerMoney = econ.getBalance(playerName);
        if (Objects.equals(args[0], "tomoney")) {
            if (playerExp < setValue1) {
                MessageManager.Message(player, "§c经验值不足无法完成转换");
                return true;
            }
            double playerSetExp = playerExp - setValue1;
            player.setExp(0);
            player.setLevel(0);
            player.setTotalExperience(0);
            player.giveExp((int) playerSetExp);
            econ.depositPlayer(playerName, setValue2);
            double nowMoney = econ.getBalance(playerName);
            MessageManager.Message(sender, String.format("§e用 §b%d §e经验值换到了 §b%d §e金钱", setValue1, setValue2));
            MessageManager.Message(sender, String.format("§e当前经验值 §b%s", Misc.stringFormat(playerSetExp)));
            MessageManager.Message(sender, String.format("§e当前金钱 §b%s", Misc.stringFormat(nowMoney)));
            return true;
        }
        if (Objects.equals(args[0], "toexp")) {
            if (playerMoney < setValue1) {
                MessageManager.Message(sender, "§c金钱不足无法完成转换");
                return true;
            }
            econ.withdrawPlayer(playerName, setValue1);
            player.giveExp(setValue2);
            double nowExp = getTotalExperience(player);
            double nowMoney = econ.getBalance(playerName);
            MessageManager.Message(sender, String.format("§e用 §b%d §e金钱换到了 §b%d §e经验值", setValue1, setValue2));
            MessageManager.Message(sender, String.format("§e当前经验值 §b%s", Misc.stringFormat(nowExp)));
            MessageManager.Message(sender, String.format("§e当前金钱 §b%s", Misc.stringFormat(nowMoney)));
            return true;
        }
        MessageManager.Message(sender, Notify.Invalid_Input.getString());
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            return Misc.returnSelectList(new ArrayList<>(Arrays.asList("tomoney", "toexp")), strings[0]);
        }
        return new ArrayList<>();
    }
}
