package net.savagedev.playerlistgui.depend;

import net.savagedev.playerlistgui.PlayerListGUI;
import org.bukkit.Bukkit;

public abstract class DependencyLoader {
    protected final PlayerListGUI plugin;
    private final boolean found;

    protected DependencyLoader(final String dependencyName, final PlayerListGUI plugin) {
        this.found = Bukkit.getPluginManager().isPluginEnabled(dependencyName);
        this.plugin = plugin;

        if (this.found) {
            this.onDependencyFound();
        } else {
            this.onDependencyNotFound();
        }
    }

    protected abstract void onDependencyFound();

    protected abstract void onDependencyNotFound();

    protected boolean isFound() {
        return this.found;
    }
}
