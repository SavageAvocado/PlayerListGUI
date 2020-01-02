package net.savagedev.playerlistgui.api;

import org.bukkit.entity.Player;

public interface TitleUpdater {
    void update(final Player player, final String title);
}
