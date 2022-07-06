package net.savagedev.playerlistgui;

import net.savagedev.imlib.IMLib;
import net.savagedev.playerlistgui.commands.ListCmd;
import net.savagedev.playerlistgui.commands.PlayerListGUICmd;
import net.savagedev.playerlistgui.depend.papi.PlaceholderAPIProvider;
import net.savagedev.playerlistgui.depend.vanish.VanishProvider;
import net.savagedev.playerlistgui.depend.vanish.VanishProviderLoader;
import net.savagedev.playerlistgui.depend.vault.VaultLoader;
import net.savagedev.playerlistgui.depend.vault.VaultProvider;
import net.savagedev.playerlistgui.listeners.PlayerListeners;
import net.savagedev.playerlistgui.metrics.Metrics;
import net.savagedev.playerlistgui.user.UserManager;
import net.savagedev.updatechecker.ResourceUpdateChecker;
import org.bukkit.Material;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Level;

public class PlayerListGUI extends JavaPlugin {
    private static boolean legacy = false;

    static {
        try {
            final Material m = Material.PLAYER_HEAD; // Modern server, don't change anything. (legacy = false by default)
        } catch (NoSuchFieldError ignored) {
            legacy = true;
        }
    }

    // Returns whether the server implementation is before or after 1.13. (Legacy = 1.12.2-, modern = 1.13+)
    public static boolean isLegacy() {
        return legacy;
    }

    private PlaceholderAPIProvider papiProvider;
    private VanishProvider vanishProvider;
    private VaultProvider vaultProvider;
    private PlayerListGUIConfig config;
    private UserManager userManager;

    @Override
    public void onEnable() {
        this.initConfig();
        new IMLib(this);

        new ResourceUpdateChecker(36641)
                .addFailureListener(exception -> this.getLogger().log(Level.WARNING, "Failed to check for updates."))
                .addSuccessListener(version -> {
                    if (version.equalsIgnoreCase(this.getDescription().getVersion())) {
                        this.getLogger().log(Level.INFO, "Thank you for using " + this.getDescription().getName() + " v" + this.getDescription().getVersion() + " by " + this.getDescription().getAuthors().get(0) + ".");
                    } else {
                        this.getLogger().log(Level.INFO, "Version " + version + " is available at: https://www.spigotmc.org/resources/playerlistgui.36641/");
                    }
                }).checkUpdatesAsync();

        this.initCommands();
        this.initListeners();
        this.initMetrics();
        this.hookDependencies();
        this.userManager = new UserManager(this);
    }

    @Override
    public void onDisable() {
        IMLib.getInstance().closeAll();
    }

    public void reload() {
        this.reloadConfig();
        this.config = new PlayerListGUIConfig(this);
        this.userManager.resort();
    }

    private void hookDependencies() {
        this.papiProvider = new PlaceholderAPIProvider(this);
        this.vanishProvider = new VanishProviderLoader(this);
        this.vaultProvider = new VaultLoader(this);
    }

    private void initMetrics() {
        new Metrics(this);
    }

    private void initConfig() {
        this.saveDefaultConfig();
        this.config = new PlayerListGUIConfig(this);
    }

    private void initCommands() {
        Objects.requireNonNull(this.getCommand("playerlistgui")).setExecutor(new PlayerListGUICmd(this));
        Objects.requireNonNull(this.getCommand("list")).setExecutor(new ListCmd(this));
    }

    private void initListeners() {
        PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerListeners(this), this);
    }

    public PlaceholderAPIProvider getPapiProvider() {
        return this.papiProvider;
    }

    public VanishProvider getVanishProvider() {
        return this.vanishProvider;
    }

    public VaultProvider getVaultProvider() {
        return this.vaultProvider;
    }

    public PlayerListGUIConfig getConfiguration() {
        return this.config;
    }

    public UserManager getUserManager() {
        return this.userManager;
    }
}
