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

        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage("Only players can use this command");

            return true;
        }

        if (args.length == 0) {
            openArmoury(player.getName());
        }

        return true;
    }

    private void openArmoury(String playerName) {
        Player player = Bukkit.getPlayer(playerName);

        if (player == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + playerName + ChatColor.RED + "is not online");
        }

        ArmouryGUI.createArmouryGUI(player);
    }
}
