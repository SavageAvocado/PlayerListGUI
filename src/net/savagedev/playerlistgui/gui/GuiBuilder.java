package net.savagedev.playerlistgui.gui;

import net.savagedev.imlib.gui.InteractionMenu;
import net.savagedev.playerlistgui.PlayerListGUI;
import net.savagedev.playerlistgui.gui.items.SkullBuilder;
import net.savagedev.playerlistgui.user.User;
import net.savagedev.playerlistgui.utils.MessageUtils;
import net.savagedev.playerlistgui.utils.PlaceholderUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.BiConsumer;

public class GuiBuilder implements BiConsumer<InteractionMenu, Player> {
    private final PlayerListGUI plugin;

    private int currentPage = 0;

    public GuiBuilder(final PlayerListGUI plugin) {
        this.plugin = plugin;
    }

    @Override
    public void accept(InteractionMenu menu, Player viewer) {
        menu.clear();

        for (int i = 46; i < 53; i++) {
            menu.setItem(i, this.plugin.getConfiguration().getPlaceholderBlock(), e -> e.setCancelled(true));
        }

        final List<User> players = this.plugin.getUserManager().getSortedPlayers();

        int sizeModified = players.size();
        if (!viewer.hasPermission("playerlistgui.vanished")) {
            sizeModified -= this.plugin.getVanishProvider().getVanishedTotal();
            if (this.plugin.getVanishProvider().isVanished(viewer)) {
                sizeModified++;
            }
        }

        final int pages = (sizeModified - 1) / 45;

        if (pages > this.currentPage) {
            menu.setItem(53, this.plugin.getConfiguration().getNextPageBlock(), e -> {
                e.setCancelled(true);
                this.currentPage++;
                menu.refresh();
            });
        } else {
            menu.setItem(53, this.plugin.getConfiguration().getPlaceholderBlock(), e -> e.setCancelled(true));
        }
        if (this.currentPage > 0) {
            menu.setItem(45, this.plugin.getConfiguration().getLastPageBlock(), e -> {
                e.setCancelled(true);
                this.currentPage--;
                menu.refresh();
            });
        } else {
            menu.setItem(45, this.plugin.getConfiguration().getPlaceholderBlock(), e -> e.setCancelled(true));
        }

        final int startingIndex = this.currentPage * 45;
        for (int slot = 0, index = startingIndex; this.currentPage >= pages ? slot < sizeModified - startingIndex : slot < 45; slot++, index++) {
            final User user = players.get(index);
            final Player player = user.getPlayer();
            if (!viewer.hasPermission("playerlistgui.vanished") && user.isVanished() && player != viewer) {
                slot--;
                continue;
            }
            menu.setItem(slot, this.getSkull(user), e -> e.setCancelled(true));
        }

        menu.setTitle(this.plugin.getConfiguration().getGuiTitle().replace("%player-count%", String.valueOf(sizeModified)).replace("%page%", String.valueOf(this.currentPage + 1)).replace("%pages%", String.valueOf(pages + 1)));
    }

    private ItemStack getSkull(final User user) {
        final Player player = user.getPlayer();
        return SkullBuilder.newBuilder().setOwner(player)
                .setName(MessageUtils.color(PlaceholderUtils.applyPlaceholders(player, this.plugin.getConfiguration().getNameFormat(), str -> {
                            str = str.replace("%username%", player.getName());
                            str = str.replace("%displayname%", player.getDisplayName());
                            return str;
                        }
                )))
                .setLore(MessageUtils.color(PlaceholderUtils.applyPlaceholders(player, this.plugin.getConfiguration().getLore(), str -> {
                    str = this.plugin.getPapiProvider().setPlaceholders(player, str);

                    str = str.replace("%bal%", PlaceholderUtils.DECIMAL_FORMAT.format(user.getBalance()));
                    str = str.replace("%prefix%", user.getPrefix());
                    str = str.replace("%rank%", user.getGroup());

                    return str;
                }))).create();
    }
}
