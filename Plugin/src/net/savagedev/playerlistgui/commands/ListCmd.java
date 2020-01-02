package net.savagedev.playerlistgui.commands;

import net.savagedev.playerlistgui.PlayerListGUI;
import net.savagedev.playerlistgui.gui.Gui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.stream.Collectors;

public class ListCmd implements CommandExecutor {
    private PlayerListGUI plugin;

    public ListCmd(PlayerListGUI plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String d, @Nonnull String[] args) {
        if (!(sender instanceof Player)) {
            Collection<? extends Player> players = this.plugin.getServer().getOnlinePlayers();
            sender.sendMessage("Players (" + players.size() + "/" + this.plugin.getServer().getMaxPlayers() + "): " + players.stream().map(HumanEntity::getName).collect(Collectors.joining(", ")));
            return true;
        }

        Player player = (Player) sender;
        this.plugin.getOpenGuis().putIfAbsent(player.getUniqueId(), new Gui(player, this.plugin));
        return true;
    }
}
