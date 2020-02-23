package net.savagedev.playerlistgui.depend.vanish;

import net.savagedev.playerlistgui.PlayerListGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import javax.annotation.Nonnull;

public class VanishProviderLoader implements VanishProvider {
    private VanishProvider provider;

    public VanishProviderLoader(final PlayerListGUI plugin) {
        final PluginManager pluginManager = Bukkit.getPluginManager();
        if (pluginManager.isPluginEnabled("Essentials")) { // Check for Essentials first, because most (the good) vanish plugins already hook into Essentials for us.
            this.provider = new EssentialsVanishLoader(plugin);
        } else if (pluginManager.isPluginEnabled("SuperVanish")) {
            this.provider = new SuperVanishLoader(plugin);
        } else if (pluginManager.isPluginEnabled("VanishNoPacket")) {
            this.provider = new VanishNoPacketLoader(plugin);
        } else {
            this.provider = new GenericVanishLoader(plugin);
        }
    }

    @Override
    public boolean isVanished(@Nonnull Player player) {
        if (this.provider == null) {
            return false;
        }
        return this.provider.isVanished(player);
    }

    @Override
    public int getVanishedTotal() {
        if (this.provider == null) {
            return 0;
        }
        return this.provider.getVanishedTotal();
    }
}
