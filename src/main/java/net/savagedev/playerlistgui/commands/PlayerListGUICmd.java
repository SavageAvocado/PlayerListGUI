package net.savagedev.playerlistgui.commands;

import net.savagedev.playerlistgui.PlayerListGUI;
import net.savagedev.playerlistgui.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;

public class PlayerListGUICmd implements CommandExecutor {
    private final PlayerListGUI plugin;

    public PlayerListGUICmd(final PlayerListGUI plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String label, @Nonnull String[] args) {
        if (!sender.hasPermission("playerlist.reload")) {
            MessageUtils.message(sender, "&7This server is running &6" + this.plugin.getDescription().getName() + " &7v&6" + this.plugin.getDescription().getVersion() + " &7by &6" + this.plugin.getDescription().getAuthors().get(0) + "&7.");
            return true;
        }

        if (args.length == 0 || (!args[0].equalsIgnoreCase("reload") && !args[0].equalsIgnoreCase("rl"))) {
            MessageUtils.message(sender, "&6" + this.plugin.getDescription().getName() + " &7v&6" + this.plugin.getDescription().getVersion() + " &7by &6" + this.plugin.getDescription().getAuthors().get(0) + "&7.");
            return true;
        }

        this.plugin.getServer().getScheduler().runTaskAsynchronously(this.plugin, () -> {
            this.plugin.reload();
            MessageUtils.message(sender, "&6" + this.plugin.getDescription().getName() + " &7v&6" + this.plugin.getDescription().getVersion() + " &7by &6" + this.plugin.getDescription().getAuthors().get(0) + " &7reloaded.");
        });
        return true;
    }
}
