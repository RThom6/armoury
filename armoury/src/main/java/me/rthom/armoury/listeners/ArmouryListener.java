package me.rthom.armoury.listeners;

import me.rthom.armoury.Armoury;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import static me.rthom.armoury.Armoury.armouries;

public class ArmouryListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        PersistentDataContainer data = p.getPersistentDataContainer();

        // Check if armoury exists for player, if not create one
        if (!data.has(new NamespacedKey(Armoury.getInstance(), "Armoury"), PersistentDataType.STRING)) {
            data.set(new NamespacedKey(Armoury.getInstance(), "Armoury"), PersistentDataType.STRING, "");
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (!player.hasMetadata("ArmouryGUI"))
            return;

        if (!(event.getClickedInventory() == player.getOpenInventory().getTopInventory()))
            return;

        if (event.getCurrentItem().equals(new ItemStack(Material.REDSTONE))) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        if (!player.hasMetadata("ArmouryGUI"))
            return;

        if (event.getView().getTitle().equalsIgnoreCase("Armoury Menu")) {
            armouries.put(event.getPlayer().getUniqueId().toString(), event.getInventory().getContents());
        } else {
            return;
        }

        player.removeMetadata("ArmouryGUI", Armoury.getInstance());
    }
}
