package net.savagedev.playerlistgui.depend.vanish;

import net.savagedev.playerlistgui.PlayerListGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

import javax.annotation.Nonnull;
import java.util.logging.Level;

public class GenericVanishLoader implements VanishProvider {
    GenericVanishLoader(final PlayerListGUI plugin) {
        plugin.getLogger().log(Level.INFO, "Unsupported/no vanish plugin discovered. Using generic implementation.");
    }

    @Override
    public boolean isVanished(@Nonnull Player player) {
        if (!player.hasMetadata("vanished")) {
            return false;
        }
        for (MetadataValue meta : player.getMetadata("vanished")) {
            if (meta.asBoolean()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getVanishedTotal() {
        int vanishTotal = 0;
        for (final Player player : Bukkit.getOnlinePlayers()) {
            if (this.isVanished(player)) {
                vanishTotal++;
            }
        }
        return vanishTotal;
    }
}
