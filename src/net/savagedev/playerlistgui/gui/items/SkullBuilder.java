package net.savagedev.playerlistgui.gui.items;

import net.savagedev.playerlistgui.PlayerListGUI;
import net.savagedev.playerlistgui.utils.MessageUtils;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class SkullBuilder {
    private static SkullOwnerEditor ownerEditor = PlayerListGUI.isLegacy() ? new LegacySkullOwnerEditor() : new ModernSkullOwnerEditor();

    private ItemStack skull;
    private SkullMeta meta;

    private SkullBuilder() {
        if (PlayerListGUI.isLegacy()) {
            this.skull = new ItemStack(Material.valueOf("SKULL_ITEM"), 1, (short) 3);
        } else {
            this.skull = new ItemStack(Material.PLAYER_HEAD, 1);
        }
        this.meta = (SkullMeta) this.skull.getItemMeta(); // Slow AF on the first run.
    }

    public static SkullBuilder newBuilder() {
        return new SkullBuilder();
    }

    public SkullBuilder setOwner(Player owner) {
        ownerEditor.setOwner(this.meta, owner);
        return this;
    }

    public SkullBuilder setLore(List<String> lore) {
        this.meta.setLore(MessageUtils.color(lore));
        return this;
    }

    public SkullBuilder setName(String name) {
        this.meta.setDisplayName(MessageUtils.color(name));
        return this;
    }

    public ItemStack create() {
        this.skull.setItemMeta(this.meta); // Also slow AF on the first run.
        return this.skull;
    }

    private interface SkullOwnerEditor {
        void setOwner(final SkullMeta meta, final OfflinePlayer owner);
    }

    private static class LegacySkullOwnerEditor implements SkullOwnerEditor {
        @Override
        public void setOwner(SkullMeta meta, OfflinePlayer owner) {
            meta.setOwner(owner.getName());
        }
    }

    private static class ModernSkullOwnerEditor implements SkullOwnerEditor {
        @Override
        public void setOwner(SkullMeta meta, OfflinePlayer owner) {
            meta.setOwningPlayer(owner);
        }
    }
}
