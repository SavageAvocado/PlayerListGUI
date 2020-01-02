package net.savagedev.playerlistgui;

import net.savagedev.playerlistgui.api.SkullBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class SkullBuilder_Unsupported implements SkullBuilder {
    private final ItemStack item = new ItemStack(this.getSkullMaterial(), 1, (short) 3);
    private final SkullMeta meta = (SkullMeta) this.item.getItemMeta();

    @Override
    public SkullBuilder newInstance() {
        return new SkullBuilder_Unsupported();
    }

    @Override
    public SkullBuilder setOwner(Player owner) {
        this.meta.setOwner(owner.getName());
        return this;
    }

    @Override
    public SkullBuilder setLore(List<String> lore) {
        this.meta.setLore(lore);
        return this;
    }

    @Override
    public SkullBuilder setName(String name) {
        this.meta.setDisplayName(name);
        return this;
    }

    @Override
    public ItemStack create() {
        this.item.setItemMeta(this.meta);
        return this.item;
    }

    private Material getSkullMaterial() {
        try {
            return Material.valueOf("SKULL_ITEM");
        } catch (IllegalArgumentException ignored) {
            return Material.PLAYER_HEAD;
        }
    }
}
