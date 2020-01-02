package net.savagedev.playerlistgui.hook;

import net.milkbowl.vault.Vault;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;
import java.util.logging.Level;

public class VaultProvider extends DependencyProvider<Vault> {
    private Permission permission;
    private Economy economy;

    public VaultProvider(JavaPlugin plugin) {
        super(plugin, "Vault");
    }

    @Override
    void onHook() {
        RegisteredServiceProvider<Permission> permissionsServiceProvider = this.plugin.getServer().getServicesManager().getRegistration(Permission.class);
        if (permissionsServiceProvider != null) {
            this.permission = permissionsServiceProvider.getProvider();
        }

        RegisteredServiceProvider<Economy> economyServiceProvider = this.plugin.getServer().getServicesManager().getRegistration(Economy.class);
        if (economyServiceProvider != null) {
            this.economy = economyServiceProvider.getProvider();
        }

        this.plugin.getLogger().log(Level.INFO, "Successfully hooked Vault.");
    }

    public Optional<Permission> getPermission() {
        return Optional.ofNullable(this.permission);
    }

    public Optional<Economy> getEconomy() {
        return Optional.ofNullable(this.economy);
    }
}
