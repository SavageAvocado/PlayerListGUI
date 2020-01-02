package net.savagedev.playerlistgui.api;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface SkullBuilder {
    SkullBuilder newInstance();

    SkullBuilder setOwner(Player owner);

    SkullBuilder setLore(List<String> lore);

    SkullBuilder setName(String name);

    ItemStack create();
}
