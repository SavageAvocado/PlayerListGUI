package net.savagedev.playerlistgui.depend;

import net.savagedev.playerlistgui.PlayerListGUI;
import org.bukkit.Bukkit;

public abstract class DependencyLoader {
    protected final PlayerListGUI plugin;
    private boolean hooked;

    protected DependencyLoader(final String dependencyName, final PlayerListGUI plugin) {
        this.hooked = Bukkit.getPluginManager().isPluginEnabled(dependencyName);
        this.plugin = plugin;

        if (this.hooked) {
            this.onHookSuccess();
        } else {
            this.onHookFail();
        }
    }

    protected abstract void onHookSuccess();

    protected abstract void onHookFail();

    protected boolean isHooked() {
        return this.hooked;
    }
}
