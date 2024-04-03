package me.xlonx.xlonxhelpop;

import me.xlonx.utils.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements CommandExecutor {

    @Override
    public void onEnable() {
        // Plugin startup logic

        saveDefaultConfig();

        reloadConfig();

        getLogger().info(ColorUtil.colorize("&a&lPlugin został załadowany"));

        Bukkit.getPluginManager().addPermission(new Permission("xlonx-helpop.admin"));

        getCommand("helpop").setExecutor(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info(ColorUtil.colorize("&a&lPlugin został wyłączony"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            ConsoleCommandSender console = (ConsoleCommandSender) sender;
            console.sendMessage(ColorUtil.colorize(getConfig().getString("not_allow_by_console")));
            return false;
        }

        Player p = (Player) sender;

        if(args.length == 0) {
            p.sendMessage(ColorUtil.colorize(getConfig().getString("usage")));
            return false;
        }

        StringBuilder text = new StringBuilder();

        if(args.length == 1) {
            text.append(args[0]);
        }

        if(args.length >= 2) {
            for (int i = 0; i < args.length; i++ ) {
                text.append(args[i]).append(" ");
            }
        }

        for (Player pi : Bukkit.getOnlinePlayers()) {
            if (pi.hasPermission("xlonx-helpop.admin")) {
                pi.sendMessage(ColorUtil.colorize(getConfig().getString("helpop_msg_plr").replace("{SENDER}", p.getDisplayName()).replace("{MSG}", text.toString())));
            }
        }

        p.sendMessage(ColorUtil.colorize(getConfig().getString("helpop_msg_sender").replace("{MSG}", text.toString())));
        return super.onCommand(sender, command, label, args);
    }
}
