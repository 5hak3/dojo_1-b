package net.azisaba.dojo.task1b;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Task1BCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (command.getName()) {
            case "heal":
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.RED + "このコマンドはプレイヤー専用です．");
                    return false;
                }
                return heal((Player) sender);

            case "broadcast":
                if (args.length == 0) return false;
                return broadcast(String.join(" ", args));

            case "loc":
                String res;
                if (args.length == 0) {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage(ChatColor.RED + "このコマンドをプレイヤー以外が使用する場合は対象プレイヤーを指定してください．");
                        return false;
                    }
                    res = loc((Player) sender);
                }
                else res = loc(args[0]);

                if (res == null) {
                    sender.sendMessage(ChatColor.RED + "対象プレイヤーが見つかりませんでした．");
                    return false;
                }
                sender.sendMessage(res);
                return true;

        }
        return false;
    }

    private boolean heal(Player player) {
        AttributeInstance pMaxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (pMaxHealth == null) return false;
        player.setHealth(pMaxHealth.getValue());
        return true;
    }

    private boolean broadcast(String msg) {
        Bukkit.getServer().broadcastMessage(
                ChatColor.translateAlternateColorCodes('&', "&6[&eお知らせ&6] &r"+msg));
        return true;
    }

    private String loc(String pName) {
        Player p = Bukkit.getPlayer(pName);
        if (p == null) return null;
        return loc(p);
    }

    private String loc(Player p) {
        Location pLoc = p.getLocation();
        String ret = "";
        ret += "[" + p.getName() + "'s Location]\n";
        ret += "World: " + pLoc.getWorld().getName() + "\n";
        ret += String.format(
                "Locations:\n    x: %.1f\n    y: %.1f\n    z: %.1f\n",
                pLoc.getX(), pLoc.getY(), pLoc.getZ());
        ret += "Direction: \n    Yaw: ";

        double yaw = pLoc.getYaw() + 180.0;
        if (yaw < 45.0) ret += "North";
        else if (yaw < 135.0) ret += "East";
        else if (yaw < 225.0) ret += "South";
        else if (yaw < 315.0) ret += "West";
        else ret += "North";
        ret += String.format(
                " (%.2f)\n    Pitch: %.2f",
                yaw, pLoc.getPitch());

        return ret;
    }
}
