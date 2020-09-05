package cn.miranda.MeowCraft.Manager;

import cn.miranda.MeowCraft.Listeners.*;
import cn.miranda.MeowCraft.Listeners.BankClickEvent;
import cn.miranda.MeowCraft.Listeners.Skills.Active.*;
import cn.miranda.MeowCraft.Listeners.Skills.Misc.ArrowBoostCancelNoDamageTicksEvent;
import cn.miranda.MeowCraft.Listeners.Skills.Misc.ArrowBoostIncreaseDamageEvent;
import cn.miranda.MeowCraft.Listeners.Skills.Misc.ImmuneInterruptEvent;
import cn.miranda.MeowCraft.Listeners.Skills.Misc.SelfExplodeCancelByPlayerDeath;
import cn.miranda.MeowCraft.Listeners.Skills.Passive.ConcentrateEvent;
import cn.miranda.MeowCraft.MeowCraft;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class ListenersManager implements Listener {
    public static void registerAllEvents() {
        Bukkit.getServer().getPluginManager().registerEvents(new ImprintActivateLogPosEvent(), MeowCraft.plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new ImprintActivateUseEvent(), MeowCraft.plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new DeadCostEvent(), MeowCraft.plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new FlyTimePlayerQuitEvent(), MeowCraft.plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new FlyTimePlayerJoinEvent(), MeowCraft.plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new OccChooseEvent(), MeowCraft.plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new BanBrewEvent(), MeowCraft.plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new ScrollUseEvent(), MeowCraft.plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new ArrowBoostEvent(), MeowCraft.plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new ArrowBoostCancelNoDamageTicksEvent(), MeowCraft.plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new BlessEvent(), MeowCraft.plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new DetectEvent(), MeowCraft.plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new ChargeEvent(), MeowCraft.plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new ThorAxeEvent(), MeowCraft.plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new TempleAccessEvent(), MeowCraft.plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new CommandMonitorEvent(), MeowCraft.plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new BankClickEvent(), MeowCraft.plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new BankCloseEvent(), MeowCraft.plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new ImmuneEvent(), MeowCraft.plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new ImmuneInterruptEvent(), MeowCraft.plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new ArrowBoostIncreaseDamageEvent(), MeowCraft.plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new SelfExplodeEvent(), MeowCraft.plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new SelfExplodeCancelByPlayerDeath(), MeowCraft.plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new RollDiceEvent(), MeowCraft.plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new FireworkNoDamageEvent(), MeowCraft.plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new ConcentrateEvent(), MeowCraft.plugin);
    }
}
