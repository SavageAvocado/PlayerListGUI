package net.savagedev.playerlistgui.depend.vanish;

import de.myzelyam.api.vanish.VanishAPI;
import net.savagedev.playerlistgui.PlayerListGUI;
import net.savagedev.playerlistgui.depend.DependencyLoader;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.logging.Level;

public class SuperVanishLoader extends DependencyLoader implements VanishProvider {
    SuperVanishLoader(final PlayerListGUI plugin) {
        super("SuperVanish", plugin);
    }

    @Override
    protected void onDependencyFound() {
        this.plugin.getLogger().log(Level.INFO, "Successfully hooked into SuperVanish.");
    }

    @Override
    protected void onDependencyNotFound() {
        // Also nothing to do here.
    }

    @Override
    public boolean isVanished(@Nonnull Player player) {
        return VanishAPI.isInvisible(player);
    }

    @Override
    public int getVanishedTotal() {
        return VanishAPI.getAllInvisiblePlayers().size();
    }
}
