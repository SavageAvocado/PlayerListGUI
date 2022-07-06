package net.savagedev.playerlistgui.depend.papi;

import me.clip.placeholderapi.PlaceholderAPI;
import net.savagedev.playerlistgui.PlayerListGUI;
import net.savagedev.playerlistgui.depend.DependencyLoader;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class PlaceholderAPIProvider extends DependencyLoader {
    public PlaceholderAPIProvider(final PlayerListGUI plugin) {
        super("PlaceholderAPI", plugin);
    }

    @Override
    protected void onDependencyFound() {
        this.plugin.getLogger().log(Level.INFO, "Successfully hooked PlaceholderAPI.");
    }

    @Override
    protected void onDependencyNotFound() {
        // PlaceholderAPI wasn't on the server. Their loss.
    }

    public String setPlaceholders(Player player, String str) {
        if (this.isFound()) {
            return PlaceholderAPI.setPlaceholders(player, str);
        }
        return str;
    }
}
