package cn.miranda.MeowCraft.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RollDice {
    private static int[] getChatRDValue(String message) {
        String pattNoTest = "^\\.[rR](\\d+)[dD](\\d+)$";
        Matcher matcherNoTest = Pattern.compile(pattNoTest).matcher(message);
        String pattWithTest = "^\\.[rR](\\d+)[dD](\\d+) (\\d+)$";
        Matcher matcherWithTest = Pattern.compile(pattWithTest).matcher(message);
        int r = 0;
        int d = 0;
        int test = 0;
        if (matcherWithTest.find()) {
            r = Integer.parseInt(matcherWithTest.group(1));
            d = Integer.parseInt(matcherWithTest.group(2));
            test = Integer.parseInt(matcherWithTest.group(3));
            return new int[]{r, d, test};
        }
        if (matcherNoTest.find()) {
            r = Integer.parseInt(matcherNoTest.group(1));
            d = Integer.parseInt(matcherNoTest.group(2));
            return new int[]{r, d};
        }
        return new int[]{};
    }

    public static String getResultMessage(String playerName, String message) {
        int[] list_int = getChatRDValue(message);
        List<Integer> RDlist = new ArrayList<Integer>();
        List<Integer> result = new ArrayList<>();
        int sum = 0;
        for (int i : list_int) {
            RDlist.add(i);
        }
        if (RDlist.size() == 0 || RDlist.indexOf(0) != -1) {
            throw new ArithmeticException();
        }
        int r = RDlist.get(0);
        int d = RDlist.get(1);
        if (r > 20 || d > 100) {
            throw new ArithmeticException();
        }
        String userCMD = "r" + r + "d" + d;
        StringBuilder resultString = new StringBuilder();
        for (int i = 0; i < r; i++) {
            int tempResult = Misc.randomNum(1, d);
            result.add(tempResult);
            sum += tempResult;
            if (i != r - 1) {
                resultString.append(tempResult).append("+");
            } else {
                resultString.append(tempResult);
            }
        }
        if (RDlist.size() == 2) {
            return String.format("§e玩家 §b%s §d%s §e结果: §a%s=%d", playerName, userCMD, resultString.toString(), sum);
        }
        int test = RDlist.get(2);
        if (sum >= 95) {
            return String.format("§e玩家 §b%s §e检定 §d%s => %d §e结果: §a%s=%d §6大失败", playerName, userCMD, test, resultString.toString(), sum);
        }
        if (sum <= 5) {
            return String.format("§e玩家 §b%s §e检定 §d%s => %d §e结果: §a%s=%d §6大成功", playerName, userCMD, test, resultString.toString(), sum);
        }
        if (test >= sum) {
            int hardToSuccess = (int) test / 2;
            int VeryHardToSuccess = (int) test / 2;
            if (sum <= VeryHardToSuccess) {
                return String.format("§e玩家 §b%s §e检定 §d%s => %d §e结果: §a%s=%d §6极难成功", playerName, userCMD, test, resultString.toString(), sum);
            }
            if (sum <= hardToSuccess) {
                return String.format("§e玩家 §b%s §e检定 §d%s => %d §e结果: §a%s=%d §6困难成功", playerName, userCMD, test, resultString.toString(), sum);
            }
            return String.format("§e玩家 §b%s §e检定 §d%s => %d §e结果: §a%s=%d §6成功", playerName, userCMD, test, resultString.toString(), sum);
        } else {
            return String.format("§e玩家 §b%s §e检定 §d%s => %d §e结果: §a%s=%d §6失败", playerName, userCMD, test, resultString.toString(), sum);
        }
    }

}
