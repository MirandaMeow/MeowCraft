package cn.miranda.MeowCraft.Manager;

import cn.miranda.MeowCraft.MeowCraft;

//TODO:找到 ClassNotFoundException 的原因争取删掉这坨屎
@Deprecated
public class ClassManager {
    public static void loadClass() {
        try {
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Utils.NoteWithTime");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Utils.Effect");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Utils.Town");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Utils.Note");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Utils.RollDice");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Utils.SkillLib");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Utils.FireworkLauncher");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Utils.IO");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Utils.Occ");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Utils.Misc");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Utils.ItemDropTable");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Task.MonsterCardPlayerRespawnLaterTask");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Task.TempleAccessTask");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Task.TaskExecuteTask");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Task.Skill.SelfExplodeTask");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Task.Skill.RemoveEntityTask");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Task.Skill.ArrowBoostShootTask");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Task.Skill.ImmuneTask");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Task.Skill.SummonTimeLeftTask");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Task.Skill.OccSkillsCoolDownTask");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Task.MonsterCardTimeTask");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Task.PlayNoteTask");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Task.TabPingTask");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Task.FlyTimeCoolDownTask");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Cores.Task");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Cores.TreasureSet");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Cores.Treasure");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.ScrollUseEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.TreasureAccessEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.OccChooseEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.BanBrewEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.CommandMonitorEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.MonsterCardPlayerQuitEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.FlyTimePlayerJoinEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.FlyTimePlayerQuitEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.MonsterCardUseEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.MonsterCardNoAttackSimilarEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.TradePanelAccessEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.MonsterCardCollectEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.EggCatcherEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.TempleAccessEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.TradePanelOpenEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.BankClickEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.MonsterCardPlayerMilkEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.MonsterCardPlayerJoinEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.BookToItemEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.ImprintActivateLogPosEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.Skills.Active.ImmuneEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.Skills.Active.ArrowBoostEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.Skills.Active.PlayNoteEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.Skills.Active.ChargeEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.Skills.Active.SummonSkeletonEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.Skills.Active.ThrowPotionEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.Skills.Active.ThorAxeEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.Skills.Active.BlessEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.Skills.Active.DetectEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.Skills.Active.SelfExplodeEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.Skills.Passive.PickAxeAttackEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.Skills.Passive.ConcentrateEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.Skills.Misc.SummonDeadEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.Skills.Misc.ImmuneInterruptEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.Skills.Misc.SummonsNotAttackPlayersAndTamedEntityEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.Skills.Misc.ArrowBoostCancelNoDamageTicksEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.Skills.Misc.SummonsNoFireEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.Skills.Misc.PlayerUnavailableEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.Skills.Misc.ArrowBoostIncreaseDamageEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.Skills.Misc.SelfExplodeCancelByPlayerDeathEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.EntityDropItemEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.BankCloseEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.DeadCostEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.MonsterCardPlayerDeadEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.EntityDropItemViewEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.EggTokenUseEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.MonsterCardPlayerRespawnEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.RollDiceEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.FireworkNoDamageEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.TradePanelSaveEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.ImprintActivateUseEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Command.StrikeCommand");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Command.OccSkillCommand");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Command.ImprintCommand");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Command.CommandMonitorCommand");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Command.TownApplyCommand");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Command.PlayerStatusCommand");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Command.TaskCommand");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Command.DeadCostCommand");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Command.EggTokenCommand");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Command.EggTokenCheckCommand");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Command.TownBankCommand");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Command.EsotericaScrollCommand");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Command.FlyTimeCommand");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Command.NPCTradeCommand");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Command.TownAdminCommand");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Command.HealthPointSetCommand");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Command.TreasureCommand");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Command.MonsterCardCommand");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Command.ConvertExpCommand");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Command.IncomeAccountCommand");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Command.TabPingCommand");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Command.EntityDropCommand");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Command.MeowCraftCommand");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Command.OccCommand");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Manager.TempleManager");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Manager.ConfigManager");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Manager.ListenersManager");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Manager.PlayerStatusManager");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Manager.MessageManager");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Manager.TradeManager");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Manager.PluginLoadManager");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Manager.BankManager");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Manager.TradeObjectManager");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Manager.ClassManager");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Manager.CommandManager");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Enum.PotionEffect");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Enum.EntityDrop");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Enum.EggCatcher");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Enum.Notify");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.MeowCraft");
        } catch (Exception e) {
            e.printStackTrace();
            MessageManager.ConsoleMessage("出错了");
        }
    }
}
