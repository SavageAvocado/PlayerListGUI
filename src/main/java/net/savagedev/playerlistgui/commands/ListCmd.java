package net.savagedev.playerlistgui.commands;

import net.savagedev.imlib.gui.DynamicInteractionMenu;
import net.savagedev.imlib.gui.InteractionMenu;
import net.savagedev.playerlistgui.PlayerListGUI;
import net.savagedev.playerlistgui.gui.GuiBuilder;
import net.savagedev.playerlistgui.gui.scheduler.GuiScheduler;
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

        final InteractionMenu menu = new DynamicInteractionMenu("&7Loading...", 6, new GuiBuilder(this.plugin), 10L);
        menu.setGlobalAction(e -> e.setCancelled(true));
        menu.open((Player) sender);
        return true;
    }
}
