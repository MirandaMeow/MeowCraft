package cn.miranda.MeowCraft.Command;

import cn.miranda.MeowCraft.Enum.Notify;
import cn.miranda.MeowCraft.Manager.MessageManager;
import cn.miranda.MeowCraft.Utils.Misc;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;


public class StrikeCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission("strike.admin")) {
            MessageManager.Message(sender, Notify.No_Permission.getString());
            return true;
        }
        if (args.length > 1) {
            MessageManager.Message(sender, "§e用法: §6/strike §b[radius]");
            return true;
        }
        switch (args.length) {
            case 0:
                if (sender instanceof Player) {
                    strike(((Player) sender).getWorld(), sender, -1);
                    return true;
                }
                for (World world : Bukkit.getServer().getWorlds()) {
                    strike(world, sender, -1);
                }
                return true;
            case 1:
                if (!(sender instanceof Player)) {
                    MessageManager.Message(sender, Notify.Not_For_Console.getString());
                    return true;
                }
                Player player = (Player) sender;
                if (!Misc.isInt(args[0])) {
                    MessageManager.Message(sender, Notify.Invalid_Input.getString());
                    return true;
                }
                int radius = Integer.parseInt(args[0]);
                if (radius <= 0 || radius >= 100) {
                    MessageManager.Message(sender, "§c范围是 1 - 100");
                    return true;
                }
                strike(player.getWorld(), sender, radius);
                return true;
            default:
                return true;
        }
    }

    private static void strike(World world, CommandSender sender, int radius) {
        int count = 0;
        if (radius == -1) {
            for (Entity e : world.getLivingEntities()) {
                if (!(e instanceof Monster)) {
                    continue;
                }
                count += 1;
                world.strikeLightningEffect(e.getLocation());
                e.remove();
            }
            MessageManager.Message(sender, String.format("§e在世界 §b%s §e清除了 §b%d §e个怪物", world.getName(), count));
            return;
        }
        Player player = (Player) sender;
        for (Entity e : world.getNearbyEntities(player.getLocation(), radius, 255, radius)) {
            if (!(e instanceof Monster)) {
                continue;
            }
            count += 1;
            world.strikeLightningEffect(e.getLocation());
            e.remove();
        }
        MessageManager.Message(player, String.format("§e在范围 §b%d §e内清除了 §b%d §e个怪物", radius, count));
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return new ArrayList<>();
    }
}
