package cn.miranda.MeowCraft.Manager;

import cn.miranda.MeowCraft.Command.*;
import org.bukkit.command.PluginCommand;

import static cn.miranda.MeowCraft.MeowCraft.plugin;

public class CommandManager {

    public static void registerCommands() {
        registerHPset();
        registerMeowPluginHelp();
        registerConvertExp();
        registerImprint();
        registerDeadCost();
        registerFlyTime();
        registerOcc();
        registerEsotericaScroll();
        registerCommandMonitor();
        registerTownBank();
        registerOccSkillCommand();
        registerTabPingCommand();
        registerTownApplyCommand();
        registerTownAdminCommand();
    }

    private static void registerMeowPluginHelp() {
        PluginCommand command = plugin.getCommand("meowcraft");
        command.setExecutor(new MeowCraftCommand());
    }

    private static void registerHPset() {
        PluginCommand command = plugin.getCommand("hpset");
        command.setExecutor(new HPsetCommand());
    }

    private static void registerConvertExp() {
        PluginCommand command = plugin.getCommand("convertexp");
        command.setExecutor(new ConvertExpCommand());
    }

    private static void registerImprint() {
        PluginCommand command = plugin.getCommand("imprint");
        command.setExecutor(new ImprintCommand());
    }

    private static void registerDeadCost() {
        PluginCommand command = plugin.getCommand("deadcost");
        command.setExecutor(new DeadCostCommand());
    }

    private static void registerFlyTime() {
        PluginCommand command = plugin.getCommand("flytime");
        command.setExecutor(new FlyTimeCommand());
    }

    private static void registerOcc() {
        PluginCommand command = plugin.getCommand("occ");
        command.setExecutor(new OccCommand());
    }

    private static void registerEsotericaScroll() {
        PluginCommand command = plugin.getCommand("esotericascroll");
        command.setExecutor(new EsotericaScrollCommand());
    }

    private static void registerCommandMonitor() {
        PluginCommand command = plugin.getCommand("cmdlogger");
        command.setExecutor(new CommandMonitorCommand());
    }

    private static void registerTownBank() {
        PluginCommand command = plugin.getCommand("bank");
        command.setExecutor(new TownBankCommand());
    }

    private static void registerOccSkillCommand() {
        PluginCommand command = plugin.getCommand("occskill");
        command.setExecutor(new OccSkillCommand());
    }

    private static void registerTabPingCommand() {
        PluginCommand command = plugin.getCommand("tabping");
        command.setExecutor(new TabPingCommand());
    }

    private static void registerTownApplyCommand() {
        PluginCommand command = plugin.getCommand("townapply");
        command.setExecutor(new TownApplyCommand());
    }
    private static void registerTownAdminCommand() {
        PluginCommand command = plugin.getCommand("townadmin");
        command.setExecutor(new TownAdminCommand());
    }
}
