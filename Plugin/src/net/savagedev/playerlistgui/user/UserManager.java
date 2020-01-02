package net.savagedev.playerlistgui.user;

import net.savagedev.playerlistgui.PlayerListGUI;
import net.savagedev.playerlistgui.hook.VaultProvider;
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
        Collection<? extends Player> players = plugin.getServer().getOnlinePlayers();
        if (!players.isEmpty()) {
            for (Player player : players) {
                this.load(player);
            }
        }
    }

    public void load(Player player) {
        int priority = 0;

        if (this.plugin.getConfig().getBoolean("priorities.enabled")) {
            VaultProvider vault = this.plugin.getVaultProvider();
            priority = vault.getPermission().isPresent() ? this.plugin.getConfig().getInt("priorities.groups." + vault.getPermission().get().getPrimaryGroup(player)) : 0;
        }

        User user = new User(player, priority);
        this.sortedPlayers.add(user);
        this.sortedPlayers.sort(User::compareTo);
    }

    public void remove(Player player) {
        this.sortedPlayers.stream().filter(user1 -> player.equals(user1.getPlayer())).findFirst().ifPresent(this.sortedPlayers::remove);
        this.sortedPlayers.sort(User::compareTo);
    }

    public List<User> getSortedPlayers() {
        return this.sortedPlayers;
    }
}
