package me.rthom.armoury.commands;

import me.rthom.armoury.Armoury;
import me.rthom.armoury.helpers.ArmouryHelper;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.metadata.FixedMetadataValue;

import static me.rthom.armoury.Armoury.armouries;

public class ArmouryCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage("Only players can use this command");

            return true;
        }

        Inventory inv = Bukkit.createInventory(player, 9 * 6, "Armoury Menu");

        if (armouries.containsKey(player.getUniqueId().toString())) {
            inv.setContents(armouries.get(player.getUniqueId().toString()));
        } else {
            ArmouryHelper.setArmouryInventory(inv);
            armouries.put(player.getUniqueId().toString(), inv.getContents());
        }

        player.openInventory(inv);

        player.setMetadata("ArmouryGUI", new FixedMetadataValue(Armoury.getInstance(), "Armoury Menu"));

        return true;
    }
}
