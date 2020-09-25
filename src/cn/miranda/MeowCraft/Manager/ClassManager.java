package cn.miranda.MeowCraft.Manager;

import cn.miranda.MeowCraft.MeowCraft;

//TODO:找到 ClassNotFoundException 的原因争取删掉这坨屎
@Deprecated
public class ClassManager {
    public static void loadClass() {
        try {
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Utils.Effect");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Utils.Occ");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Utils.RollDice");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Utils.SkillLib");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Utils.Town");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Utils.FireworkLauncher");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Utils.Misc");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Task.SelfExplodeTask");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Task.TabPingTask");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Task.RemoveEntityTask");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Task.FlyTimeCoolDownTask");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.ScrollUseEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.BanBrewEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Task.ArrowBoostShootTask");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.FlyTimePlayerJoinEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Task.OccSkillsCoolDownTask");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Task.TempleAccessTask");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.TempleAccessEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Task.ImmuneTask");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.CommandMonitorEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.FlyTimePlayerQuitEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.EggCatcherEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.OccChooseEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.BankClickEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.ImprintActivateLogPosEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.Skills.Active.ImmuneEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.Skills.Active.ArrowBoostEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.Skills.Active.ChargeEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.Skills.Active.ThrowPotionEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.Skills.Active.ThorAxeEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.Skills.Active.BlessEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.Skills.Active.DetectEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.Skills.Active.SelfExplodeEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.Skills.Passive.PickAxeAttackEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.Skills.Passive.ConcentrateEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.Skills.Misc.ImmuneInterruptEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.RollDiceEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.Skills.Misc.ArrowBoostIncreaseDamageEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.Skills.Misc.SelfExplodeCancelByPlayerDeathEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.BankCloseEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.DeadCostEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.Skills.Misc.ArrowBoostCancelNoDamageTicksEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.FireworkNoDamageEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Command.TownApplyCommand");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Command.DeadCostCommand");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Command.ImprintCommand");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Command.CommandMonitorCommand");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Listeners.ImprintActivateUseEvent");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Command.PlayerStatusCommand");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Command.OccSkillCommand");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Command.HealthPointSetCommand");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Command.EsotericaScrollCommand");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Command.FlyTimeCommand");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Command.OccCommand");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Manager.TempleManager");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Command.ConvertExpCommand");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Command.TabPingCommand");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Command.MeowCraftCommand");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Command.TownAdminCommand");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Command.TownBankCommand");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Command.IncomeAccountCommand");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Command.StrikeCommand");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Manager.ConfigManager");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Manager.PluginLoadManager");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Manager.PlayerStatusManager");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Manager.MessageManager");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Manager.BankManager");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Manager.ListenersManager");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Manager.ClassManager");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Manager.CommandManager");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Enum.EggCatcher");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.MeowCraft");
            MeowCraft.plugin.getClass().getClassLoader().loadClass("cn.miranda.MeowCraft.Utils.IO");
        } catch (Exception e) {
            e.printStackTrace();
            MessageManager.ConsoleMessage("出错了");
        }
    }
}
