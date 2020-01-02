package net.savagedev.playerlistgui.items;

import net.savagedev.playerlistgui.api.SkullBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class SkullBuilder_1_14_R1 implements SkullBuilder {
    private final ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
    private final SkullMeta meta = (SkullMeta) this.item.getItemMeta();

    @Override
    public SkullBuilder newInstance() {
        return new SkullBuilder_1_14_R1();
    }

    @Override
    public SkullBuilder setOwner(Player owner) {
        this.meta.setOwningPlayer(owner);
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
}
