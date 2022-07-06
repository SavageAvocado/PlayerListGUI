package net.savagedev.playerlistgui.depend.vanish;

import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public interface VanishProvider {
    /**
     * Get the player's vanish status from the vnaish plugin currently running on the server.
     *
     * @param player - The player whose vanish status you want to retrieve.
     * @return {@link Boolean} - Whether or not the player is currently vanished.
     */
    boolean isVanished(@Nonnull Player player);

    /**
     * Get the total amount of players currently vanished on the server.
     *
     * @return {@link Integer} - The amount of vanished players.
     */
    int getVanishedTotal();
}
