package me.rthom.armoury.commands;

import me.rthom.armoury.gui.ArmouryGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArmouryCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        // Sender must be logged in and playing as a player and not a console client
        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage("Only players can use this command");
            return true;
        }
        // No extra arguments
        if (args.length == 0) {
            openArmoury(player);
            return true;
        }

        // Check Player entered their own name
        if (player.getName().equals(args[0])) {
            openArmoury(player);
            return true;
        }

        // Check whether Player has permission to check others' armoury
        if (!player.hasPermission("armoury.command.armoury.others")) {
            player.sendMessage(ChatColor.RED + "You don't have permission to view other players' armoury");
            return false;
        } else {
            openArmoury(args[0]);
        }

        return true;
    }

    /**
     * Private method to open armoury of player specified
     *
     * @param playerName name of player, must be online
     */
    private void openArmoury(String playerName) {
        Player player = Bukkit.getPlayer(playerName);

        if (player == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + playerName + ChatColor.RED + "doesn't exist or is not online");
        }

        new ArmouryGUI().createArmouryGUI(player);
    }

    /**
     * Private method to open armoury of player specified
     *
     * @param player instance of the player whose armoury will be opened
     */
    private void openArmoury(Player player) {
        if (player == null) {
            Bukkit.getConsoleSender().sendMessage("Player is invalid");
        }

        new ArmouryGUI().createArmouryGUI(player);
    }
}
