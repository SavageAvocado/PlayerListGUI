package net.savagedev.playerlistgui.listeners;

import net.savagedev.playerlistgui.PlayerListGUI;
import net.savagedev.playerlistgui.gui.Gui;
import net.savagedev.playerlistgui.gui.holder.GuiHolder;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class InventoryListeners implements Listener {
    private final PlayerListGUI plugin;

    public InventoryListeners(final PlayerListGUI plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void on(InventoryClickEvent e) {
        if (this.plugin.getOpenGuis().containsKey(e.getWhoClicked().getUniqueId())) {
            e.setCancelled(true);
        }

        Inventory inventory = e.getClickedInventory();

        if (inventory == null) {
            return;
        }

        InventoryHolder holder = inventory.getHolder();
        if (holder instanceof GuiHolder) {
            final Gui gui = ((GuiHolder) holder).getGui();
            final int slot = e.getSlot();

            final ItemStack item = inventory.getItem(slot);
            if (item == null) {
                e.setCancelled(true);
                return;
            }

            final Material material = item.getType();
            if (slot == 53 && material == Material.ARROW) {
                gui.nextPage();
            }

            if (slot == 45 && material == Material.ARROW) {
                gui.lastPage();
            }
        }
    }

    @EventHandler
    public void on(InventoryCloseEvent e) {
        InventoryHolder holder = e.getInventory().getHolder();
        if (holder instanceof GuiHolder) {
            this.plugin.getOpenGuis().remove(e.getPlayer().getUniqueId());
        }
    }
}
