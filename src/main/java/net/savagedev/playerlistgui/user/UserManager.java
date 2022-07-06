package net.savagedev.playerlistgui.user;

import net.savagedev.playerlistgui.PlayerListGUI;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserManager {
    private final List<User> sortedPlayers = new ArrayList<>();
    private final PlayerListGUI plugin;

    public UserManager(final PlayerListGUI plugin) {
        this.plugin = plugin;
        this.resort();
    }

    public void resort() {
        this.sortedPlayers.clear();
        final Collection<? extends Player> players = plugin.getServer().getOnlinePlayers();
        for (Player player : players) {
            this.load(player);
        }
    }

    public void load(Player player) {
        int priority = 0;

        if (this.plugin.getConfiguration().isPrioritiesEnabled()) {
            priority = this.plugin.getConfiguration().getGroupPriorities().getOrDefault(this.plugin.getVaultProvider().getGroup(player), 0);
        }

        User user = new User(player, priority, this.plugin);
        this.sortedPlayers.add(user);
        this.sortedPlayers.sort(User::compareTo);
    }

    public void remove(Player player) {
        this.sortedPlayers.stream().filter(user -> player.equals(user.getPlayer())).findFirst().ifPresent(this.sortedPlayers::remove);
    }

    public List<User> getSortedUsers() {
        return this.sortedPlayers;
    }
}
