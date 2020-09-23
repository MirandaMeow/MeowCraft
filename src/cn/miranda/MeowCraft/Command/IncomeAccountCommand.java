package cn.miranda.MeowCraft.Command;

import cn.miranda.MeowCraft.Enum.Notify;
import cn.miranda.MeowCraft.Manager.ConfigManager;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Utils.Misc;
import cn.miranda.MeowCraft.Utils.Town;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static cn.miranda.MeowCraft.Manager.ConfigManager.towns;

public class IncomeAccountCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission("incomeaccount.admin")) {
            MessageManager.Message(sender, Notify.No_Permission.getString());
            return true;
        }
        if (args.length < 2 || args.length > 3) {
            MessageManager.Message(sender, "§e用法: §6/incomeaccount §b<town> <check|give|take|set> [value]");
            return true;
        }
        String town = args[0];
        if (!Town.getTownList().contains(town)) {
            MessageManager.Message(sender, Notify.No_Town.getString());
            return true;
        }
        String option = args[1];
        if (Objects.equals(option, "check")) {
            MessageManager.Message(sender, String.format("§e小镇§b%s§e的收入账户: §b%s", town, Misc.stringFormat(getTownIncomeAccountValue(town))));
            return true;
        }
        if (args.length != 3) {
            MessageManager.Message(sender, Notify.Invalid_Input_Length.getString());
            return true;
        }
        String valueString = args[2];
        double townValue = getTownIncomeAccountValue(town);
        if (!Misc.canDouble(valueString)) {
            MessageManager.Message(sender, Notify.Invalid_Input.getString());
            return true;
        }
        double value = Double.parseDouble(valueString);
        String value_string = Misc.stringFormat(value);
        double setValue;
        switch (option) {
            case "give":
                setValue = Misc.doubleFixed(townValue + value);
                setTownIncomeAccountValue(town, setValue);
                MessageManager.Message(sender, String.format("§e小镇§b%s§e收入账户已添加 §b%s", town, value_string));
                return true;
            case "take":
                if (value > townValue) {
                    MessageManager.Message(sender, String.format("§c小镇§b%s§c的收入账户余额不足", town));
                    return true;
                }
                setValue = Misc.doubleFixed(townValue - value);
                setTownIncomeAccountValue(town, setValue);
                MessageManager.Message(sender, String.format("§e小镇§b%s§e收入账户已扣除 §b%s", town, value_string));
                return true;
            case "set":
                setTownIncomeAccountValue(town, value);
                MessageManager.Message(sender, String.format("§e小镇§b%s§e收入账户被设置为 §b%s", town, value_string));
                return true;
            default:
                MessageManager.Message(sender, "§e用法: §6/incomeaccount §b<town> <check|give|take|set> [value]");
                return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            return Misc.returnSelectList(Town.getTownList(), strings[0]);
        }
        if (strings.length == 2) {
            return Misc.returnSelectList(new ArrayList<>(Arrays.asList("check", "give", "take", "set")), strings[1  ]);
        }
        return new ArrayList<>();
    }

    private double getTownIncomeAccountValue(String town) {
        return Misc.doubleFixed(towns.getDouble(String.format("%s.incomeAccount", town)));
    }

    private void setTownIncomeAccountValue(String town, double value) {
        towns.set(String.format("%s.incomeAccount", town), value);
        ConfigManager.saveConfigs();
    }
}
