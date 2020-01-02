package net.savagedev.playerlistgui.hook;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public abstract class DependencyProvider<T extends JavaPlugin> {
    protected final JavaPlugin plugin;

    private boolean hooked = false;
    private T dependency;

    DependencyProvider(final JavaPlugin plugin, final String dependency) {
        this.plugin = plugin;

        PluginManager pluginManager = plugin.getServer().getPluginManager();
        if (pluginManager.isPluginEnabled(dependency)) {
            this.dependency = (T) pluginManager.getPlugin(dependency);
            this.hooked = true;
            this.onHook();
        }
    }

    abstract void onHook();

    public Optional<T> getDependency() {
        return Optional.ofNullable(this.dependency);
    }

    boolean isHooked() {
        return this.hooked;
    }
}
