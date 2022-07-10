package net.savagedev.playerlistgui.listeners;

import net.savagedev.playerlistgui.PlayerListGUI;
import net.savagedev.playerlistgui.utils.MessageUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;
import java.util.logging.Level;

public class ConnectionListener implements Listener {
    private final PlayerListGUI plugin;

    public ConnectionListener(final PlayerListGUI plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void on(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        this.plugin.getUserManager().load(player);

        if (!player.hasPermission("playerlist.updates")) {
            return;
        }

        this.plugin.getUpdateChecker().checkForUpdateAsync().whenComplete((version, exception) -> {
            if (exception != null) {
                return;
            }

            if (version.equalsIgnoreCase(this.plugin.getDescription().getVersion())) {
                MessageUtils.message(player, "&aThank you for using " + this.plugin.getDescription().getName() + " v" + this.plugin.getDescription().getVersion() + " by " + this.plugin.getDescription().getAuthors().get(0) + ".");
            } else {
                MessageUtils.message(player, "&a[PlayerListGUI] Version " + version + " is available at: &b&n" + this.plugin.getUpdateChecker().getResourceUrl());
            }
        });
    }

    @EventHandler
    public void on(PlayerQuitEvent event) {
        this.plugin.getUserManager().remove(event.getPlayer());
    }
}
