package net.savagedev.playerlistgui.gui.holder;

import net.savagedev.playerlistgui.gui.Gui;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import javax.annotation.Nonnull;

public class GuiHolder implements InventoryHolder {
    private final Gui gui;

    public GuiHolder(final Gui gui) {
        this.gui = gui;
    }

    @Override
    @Nonnull
    public Inventory getInventory() {
        return this.gui.getInventory();
    }

    public Gui getGui() {
        return this.gui;
    }
}
