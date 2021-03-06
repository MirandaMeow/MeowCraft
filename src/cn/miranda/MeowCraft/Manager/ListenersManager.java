package cn.miranda.MeowCraft.Manager;

import cn.miranda.MeowCraft.Listeners.*;
import cn.miranda.MeowCraft.Listeners.Skills.Active.*;
import cn.miranda.MeowCraft.Listeners.Skills.Misc.*;
import cn.miranda.MeowCraft.Listeners.Skills.Passive.ConcentrateEvent;
import cn.miranda.MeowCraft.Listeners.Skills.Passive.PickAxeAttackEvent;
import cn.miranda.MeowCraft.MeowCraft;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.LinkedList;
import java.util.List;

public class ListenersManager implements Listener {
    private static final List<Listener> list = new LinkedList<>();

    private static void register(Listener listener) {
        Bukkit.getServer().getPluginManager().registerEvents(listener, MeowCraft.plugin);
    }

    public static void registerAllEvents() {
        list.add(new ImprintActivateLogPosEvent());
        list.add(new ImprintActivateUseEvent());
        list.add(new DeadCostEvent());
        list.add(new FlyTimePlayerQuitEvent());
        list.add(new FlyTimePlayerJoinEvent());
        list.add(new OccChooseEvent());
        list.add(new BanBrewEvent());
        list.add(new ScrollUseEvent());
        list.add(new ArrowBoostEvent());
        list.add(new ArrowBoostCancelNoDamageTicksEvent());
        list.add(new BlessEvent());
        list.add(new DetectEvent());
        list.add(new ChargeEvent());
        list.add(new ThorAxeEvent());
        list.add(new TempleAccessEvent());
        list.add(new CommandMonitorEvent());
        list.add(new BankClickEvent());
        list.add(new BankCloseEvent());
        list.add(new ImmuneEvent());
        list.add(new ImmuneInterruptEvent());
        list.add(new ArrowBoostIncreaseDamageEvent());
        list.add(new SelfExplodeEvent());
        list.add(new SelfExplodeCancelByPlayerDeathEvent());
        list.add(new RollDiceEvent());
        list.add(new FireworkNoDamageEvent());
        list.add(new ConcentrateEvent());
        list.add(new EggCatcherEvent());
        list.add(new PickAxeAttackEvent());
        list.add(new ThrowPotionEvent());
        list.add(new TradePanelOpenEvent());
        list.add(new TradePanelSaveEvent());
        list.add(new TradePanelAccessEvent());
        list.add(new BookToItemEvent());
        list.add(new SummonsNotAttackPlayersAndTamedEntityEvent());
        list.add(new SummonDeadEvent());
        list.add(new PlayerUnavailableEvent());
        list.add(new SummonsNoFireEvent());
        list.add(new SummonSkeletonEvent());
        list.add(new MonsterCardCollectEvent());
        list.add(new MonsterCardUseEvent());
        list.add(new MonsterCardPlayerDeadEvent());
        list.add(new MonsterCardPlayerRespawnEvent());
        list.add(new MonsterCardPlayerQuitEvent());
        list.add(new MonsterCardPlayerJoinEvent());
        list.add(new MonsterCardNoAttackSimilarEvent());
        list.add(new MonsterCardPlayerMilkEvent());
        list.add(new PlayNoteEvent());
        list.add(new EntityDropItemEvent());
        list.add(new EntityDropItemViewEvent());
        list.add(new EggTokenUseEvent());
        list.add(new TreasureAccessEvent());
        for (Listener i : list) {
            register(i);
        }
    }
}
