package net.savagedev.playerlistgui.hook;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class PAPIProvider extends DependencyProvider<PlaceholderAPIPlugin> {

    public PAPIProvider(JavaPlugin plugin) {
        super(plugin, "PlaceholderAPI");
    }

    @Override
    void onHook() {
        this.plugin.getLogger().log(Level.INFO, "Successfully hooked PlaceholderAPI.");
    }

    public String setPlaceholders(Player player, String str) {
        if (this.isHooked()) {
            return PlaceholderAPI.setPlaceholders(player, str);
        }
        return str;
    }
}
