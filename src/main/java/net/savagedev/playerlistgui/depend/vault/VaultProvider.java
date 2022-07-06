package net.savagedev.playerlistgui.depend.vault;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public interface VaultProvider {
    /**
     * Get the player's prefix from Vault. {@link Chat#getPlayerPrefix(Player)}
     *
     * @param player - The player whose prefix you want to retrieve from Vault.
     * @return {@link String} - The provided player's prefix.
     */
    @Nonnull
    String getPrefix(@Nonnull Player player);

    /**
     * Get the player's group from Vault. {@link Permission#getPrimaryGroup(Player)}
     *
     * @param player - The player whose group you want to retrieve from Vault.
     * @return {@link String} - The provided player's default group.
     */
    @Nonnull
    String getGroup(@Nonnull Player player);

    /**
     * Get the player's balance from Vault. {@link Economy#getBalance(OfflinePlayer)}
     *
     * @param player - The player whose balance you want to retrieve from Vault.
     * @return {@link Double} - The provided player's balance.
     */
    double getBalance(@Nonnull Player player);
}
