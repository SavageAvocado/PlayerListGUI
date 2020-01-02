package net.savagedev.playerlistgui.listeners;

import net.savagedev.playerlistgui.PlayerListGUI;
import net.savagedev.playerlistgui.utils.MessageUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerListeners implements Listener {
    private static final UUID AVOCADOS_UUID = UUID.fromString("4db0a788-716a-4d59-894d-f9bbb23ce851");

    private final PlayerListGUI plugin;

    public PlayerListeners(final PlayerListGUI plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void on(PlayerJoinEvent e) {
        final Player player = e.getPlayer();
        if (player.getUniqueId().equals(AVOCADOS_UUID)) {
            MessageUtils.message(player, "&7This server is running your plugin: &6" + this.plugin.getDescription().getName() + " &7v&6" + this.plugin.getDescription().getVersion() + "&7.");
        }
        this.plugin.getUserManager().load(player);
    }

    @EventHandler
    public void on(PlayerQuitEvent e) {
        final Player player = e.getPlayer();
        this.plugin.getOpenGuis().remove(player.getUniqueId());
        this.plugin.getUserManager().remove(player);
    }
}
