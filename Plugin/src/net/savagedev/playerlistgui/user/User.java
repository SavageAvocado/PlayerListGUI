package net.savagedev.playerlistgui.user;

import org.bukkit.entity.Player;

public class User implements Comparable<User> {
    private final Player player;
    private final int priority;

    User(final Player player, final int priority) {
        this.priority = priority;
        this.player = player;
    }

    @Override
    public int compareTo(User user) {
        return -Integer.compare(user.priority, this.priority);
    }

    public Player getPlayer() {
        return this.player;
    }
}
