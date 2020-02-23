package net.savagedev.playerlistgui.depend.vault;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import net.savagedev.playerlistgui.PlayerListGUI;
import net.savagedev.playerlistgui.depend.DependencyLoader;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;

import javax.annotation.Nonnull;
import java.util.logging.Level;

public class VaultLoader extends DependencyLoader implements VaultProvider {
    private Permission permission;
    private Economy economy;
    private Chat chat;

    public VaultLoader(final PlayerListGUI plugin) {
        super("Vault", plugin);
    }

    @Override
    protected void onHookSuccess() { // Woo! Vault was found on the server. We can start to gather all of the services we need from Bukkit's ServicesManager.
        final ServicesManager servicesManager = Bukkit.getServicesManager(); // Get the ServicesManager.

        final RegisteredServiceProvider<Permission> permissionRegistration = servicesManager.getRegistration(Permission.class); // Get the Permission registration.
        if (permissionRegistration != null) { // If one is available...
            this.permission = permissionRegistration.getProvider(); // Then we good, get the provider.
        } // If not, we do nothing.

        final RegisteredServiceProvider<Economy> economyRegistration = servicesManager.getRegistration(Economy.class); // The rest of the code in this method is the same as above, just for different services.
        if (economyRegistration != null) {
            this.economy = economyRegistration.getProvider();
        }

        final RegisteredServiceProvider<Chat> chatRegistration = servicesManager.getRegistration(Chat.class);
        if (chatRegistration != null) {
            this.chat = chatRegistration.getProvider();
        }

        // Now something new happens...
        this.plugin.getLogger().log(Level.INFO, "Successfully hooked into Vault."); // Let the user know the plugin discovered and successfully hooked into Vault.
    }

    @Override
    protected void onHookFail() {
        // Vault wasn't on the server. Oh well, their loss.
        // And we don't want to bug them about this, since it's not really a big deal, so we just do nothing.
        // Maybe I'll change this in the future, and just log a message that lets them know they can gain access to additional features by installing Vault.
        // No one would see it. Who doesn't use Vault anyway?
    }

    @Override @Nonnull
    public String getPrefix(@Nonnull Player player) {
        if (this.chat == null) { // No chat plugin available to get the player's prefix from, so...
            return "N/A"; // We just send this to let them know it's not available.
        }
        return this.chat.getPlayerPrefix(player); // Ask Vault for the player's prefix.
    }

    @Override @Nonnull
    public String getGroup(@Nonnull Player player) {
        if (this.permission == null) { // No permissions plugin available to get the player's group from, so...
            return "N/A"; // Again, we just send this.
        }
        return this.permission.getPrimaryGroup(player); // Ask Vault for the player's primary group.
    }

    @Override
    public double getBalance(@Nonnull Player player) {
        if (this.economy == null) { // And finally, no economy plugin available to get the player's balance from, so...
            return 0.0D; // Send a default balance.
        }
        return this.economy.getBalance(player); // Ask Vault for the player's balance.
    }
}
