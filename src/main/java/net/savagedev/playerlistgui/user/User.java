package net.savagedev.playerlistgui.user;

import net.savagedev.playerlistgui.PlayerListGUI;
import org.bukkit.entity.Player;

public class User implements Comparable<User> {
    private final PlayerListGUI plugin;
    private final Player player;
    private final int priority;

    User(final Player player, final int priority, final PlayerListGUI plugin) {
        this.priority = priority;
        this.player = player;
        this.plugin = plugin;
    }

    @Override
    public int compareTo(User user) {
        return Integer.compare(user.priority, this.priority);
    }

    public String getPrefix() {
        return this.plugin.getVaultProvider().getPrefix(this.player);
    }

    public String getGroup() {
        return this.plugin.getVaultProvider().getGroup(this.player);
    }

    public Player getPlayer() {
        return this.player;
    }

    public boolean isVanished() {
        return this.plugin.getVanishProvider().isVanished(this.player);
    }

    public double getBalance() {
        return this.plugin.getVaultProvider().getBalance(this.player);
    }

    public int getPriority() {
        return this.priority;
    }
}
