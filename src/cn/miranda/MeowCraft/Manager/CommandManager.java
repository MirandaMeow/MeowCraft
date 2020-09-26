package cn.miranda.MeowCraft.Manager;

import cn.miranda.MeowCraft.Command.*;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;

import java.util.HashMap;
import java.util.Map;

import static cn.miranda.MeowCraft.MeowCraft.plugin;

public class CommandManager {
    private static final HashMap<String, CommandExecutor> map = new HashMap<>();

    private static void register(String commandName, CommandExecutor Executor) {
        PluginCommand command = plugin.getCommand(commandName);
        command.setExecutor(Executor);
    }

    public static void registerCommands() {
        map.put("meowcraft", new MeowCraftCommand());
        map.put("hpset", new HealthPointSetCommand());
        map.put("convertexp", new ConvertExpCommand());
        map.put("imprint", new ImprintCommand());
        map.put("deadcost", new DeadCostCommand());
        map.put("flytime", new FlyTimeCommand());
        map.put("occ", new OccCommand());
        map.put("esotericascroll", new EsotericaScrollCommand());
        map.put("cmdlogger", new CommandMonitorCommand());
        map.put("bank", new TownBankCommand());
        map.put("occskill", new OccSkillCommand());
        map.put("tabping", new TabPingCommand());
        map.put("townapply", new TownApplyCommand());
        map.put("townadmin", new TownAdminCommand());
        map.put("playerstatus", new PlayerStatusCommand());
        map.put("strike", new StrikeCommand());
        map.put("incomeaccount", new IncomeAccountCommand());
        map.put("trade", new NPCTradeCommand());
        for (Map.Entry<String, CommandExecutor> entry : map.entrySet()) {
            register(entry.getKey(), entry.getValue());
        }
    }
}
