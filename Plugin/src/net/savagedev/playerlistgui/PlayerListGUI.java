package net.savagedev.playerlistgui;

import net.savagedev.playerlistgui.api.DebugLogger;
import net.savagedev.playerlistgui.api.SkullBuilder;
import net.savagedev.playerlistgui.api.TitleUpdater;
import net.savagedev.playerlistgui.commands.ListCmd;
import net.savagedev.playerlistgui.commands.PlayerListGUICmd;
import net.savagedev.playerlistgui.gui.Gui;
import net.savagedev.playerlistgui.gui.holder.GuiHolder;
import net.savagedev.playerlistgui.hook.PAPIProvider;
import net.savagedev.playerlistgui.hook.VaultProvider;
import net.savagedev.playerlistgui.listeners.InventoryListeners;
import net.savagedev.playerlistgui.listeners.PlayerListeners;
import net.savagedev.playerlistgui.metrics.Metrics;
import net.savagedev.playerlistgui.threads.GuiUpdateThread;
import net.savagedev.playerlistgui.user.UserManager;
import net.savagedev.playerlistgui.version.VersionHelper;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

public class PlayerListGUI extends JavaPlugin {
    private final Map<UUID, Gui> openGuis = new HashMap<>();

    private VaultProvider vaultProvider;
    private PAPIProvider papiProvider;
    private UserManager userManager;
    private VersionHelper helper;

    @Override
    public void onEnable() {
        this.initConfig();

        try {
            this.helper = new VersionHelper(this);
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            this.getLogger().log(Level.SEVERE, "Implementation failed. Disabling plugin: ", e);
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        new UpdateChecker(36641).check().thenAccept(result -> {
            if (result.isUpdateAvailable()) {
                this.getLogger().log(Level.INFO, "Version " + result.getVersion() + " is available at: https://www.spigotmc.org/resources/playerlistgui.36641/");
            } else {
                this.getLogger().log(Level.INFO, "Thank you for using " + this.getDescription().getName() + " v" + this.getDescription().getVersion() + " by " + this.getDescription().getAuthors().get(0) + ".");
            }
        });

        this.initCommands();
        this.initListeners();
        this.initMetrics();
        this.hookDependencies();
        this.userManager = new UserManager(this);
        new GuiUpdateThread(this).start();
    }

    @Override
    public void onDisable() {
        Collection<? extends Player> players = this.getServer().getOnlinePlayers();
        if (!players.isEmpty()) {
            for (Player user : players) {
                if (user.getOpenInventory().getTopInventory().getHolder() instanceof GuiHolder) {
                    user.closeInventory();
                }
            }
        }
        this.openGuis.clear();
    }

    public void reload() {
        this.userManager.resort();
        this.reloadConfig();
    }

    private void hookDependencies() {
        this.papiProvider = new PAPIProvider(this);
        this.vaultProvider = new VaultProvider(this);
    }

    private void initMetrics() {
        new Metrics(this);
    }

    private void initConfig() {
        this.saveDefaultConfig();
    }

    private void initCommands() {
        Objects.requireNonNull(this.getCommand("playerlistgui")).setExecutor(new PlayerListGUICmd(this));
        Objects.requireNonNull(this.getCommand("list")).setExecutor(new ListCmd(this));
    }

    private void initListeners() {
        PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new InventoryListeners(this), this);
        pluginManager.registerEvents(new PlayerListeners(this), this);
    }

    public TitleUpdater getTitleUpdater() {
        return this.helper.getTitleUpdater();
    }

    public SkullBuilder getSkullBuilder() {
        return this.helper.getSkullBuilder();
    }

    public VaultProvider getVaultProvider() {
        return this.vaultProvider;
    }

    public PAPIProvider getPapiProvider() {
        return this.papiProvider;
    }

    public UserManager getUserManager() {
        return this.userManager;
    }

    public Map<UUID, Gui> getOpenGuis() {
        return this.openGuis;
    }

    private class UpdateChecker {
        private static final String SPIGOT_API_URL = "https://api.spigotmc.org/legacy/update.php?resource=%d";

        private final int resourceId;

        private UpdateChecker(int resourceId) {
            this.resourceId = resourceId;
        }

        private CompletableFuture<Result> check() {
            return CompletableFuture.supplyAsync(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(String.format(SPIGOT_API_URL, this.resourceId)).openConnection().getInputStream()))) {
                    String version = reader.readLine();
                    return new Result(!PlayerListGUI.this.getDescription().getVersion().equalsIgnoreCase(version), version);
                } catch (IOException ignored) {
                    PlayerListGUI.this.getLogger().log(Level.WARNING, "Failed to check for updates.");
                }
                return new Result(false, PlayerListGUI.this.getDescription().getVersion());
            });
        }

        private class Result {
            private final boolean updateAvailable;
            private final String version;

            private Result(boolean updateAvailable, String version) {
                this.updateAvailable = updateAvailable;
                this.version = version;
            }

            private boolean isUpdateAvailable() {
                return this.updateAvailable;
            }

            private String getVersion() {
                return this.version;
            }
        }
    }
}
