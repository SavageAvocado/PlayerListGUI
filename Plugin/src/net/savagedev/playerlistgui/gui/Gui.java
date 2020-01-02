package net.savagedev.playerlistgui.gui;

import net.savagedev.playerlistgui.PlayerListGUI;
import net.savagedev.playerlistgui.api.DebugLogger;
import net.savagedev.playerlistgui.gui.holder.GuiHolder;
import net.savagedev.playerlistgui.hook.VaultProvider;
import net.savagedev.playerlistgui.user.User;
import net.savagedev.playerlistgui.utils.MessageUtils;
import net.savagedev.playerlistgui.utils.PlaceholderUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Gui {
    private static ItemStack GLASS_PANE;
    private static ItemStack NEXT_PAGE;
    private static ItemStack LAST_PAGE;

    private final PlayerListGUI plugin;
    private final Inventory inventory;
    private final Player owner;

    private int currentPage = 0;

    public Gui(final Player owner, final PlayerListGUI plugin) {
        this.inventory = plugin.getServer().createInventory(new GuiHolder(this), 54, "");

        this.plugin = plugin;
        this.owner = owner;

        GLASS_PANE = new ItemStack(Material.valueOf(this.plugin.getConfig().getString("placeholder-block.material").toUpperCase()), this.plugin.getConfig().getInt("placeholder-block.amount"), (short) this.plugin.getConfig().getInt("placeholder-block.damage"));
        ItemMeta glass_pane_meta = GLASS_PANE.getItemMeta();
        if (glass_pane_meta != null) {
            glass_pane_meta.setDisplayName(this.plugin.getConfig().getString("placeholder-block.name"));
            glass_pane_meta.setLore(MessageUtils.color(this.plugin.getConfig().getStringList("placeholder-block.lore")));
            GLASS_PANE.setItemMeta(glass_pane_meta);
        }

        NEXT_PAGE = new ItemStack(Material.valueOf(this.plugin.getConfig().getString("next-page-block.material").toUpperCase()), this.plugin.getConfig().getInt("next-page-block.amount"), (short) this.plugin.getConfig().getInt("next-page-block.damage"));
        ItemMeta next_page_meta = NEXT_PAGE.getItemMeta();
        if (next_page_meta != null) {
            next_page_meta.setDisplayName(MessageUtils.color(this.plugin.getConfig().getString("next-page-block.name")));
            next_page_meta.setLore(MessageUtils.color(this.plugin.getConfig().getStringList("next-page-block.lore")));
            NEXT_PAGE.setItemMeta(next_page_meta);
        }

        LAST_PAGE = new ItemStack(Material.valueOf(this.plugin.getConfig().getString("last-page-block.material").toUpperCase()), this.plugin.getConfig().getInt("last-page-block.amount"), (short) this.plugin.getConfig().getInt("last-page-block.damage"));
        ItemMeta last_page_meta = LAST_PAGE.getItemMeta();
        if (last_page_meta != null) {
            last_page_meta.setDisplayName(MessageUtils.color(this.plugin.getConfig().getString("last-page-block.name")));
            last_page_meta.setLore(MessageUtils.color(this.plugin.getConfig().getStringList("last-page-block.lore")));
            LAST_PAGE.setItemMeta(last_page_meta);
        }

        this.owner.openInventory(this.inventory);

        this.plugin.getServer().getScheduler().runTaskAsynchronously(this.plugin, () -> {
            long start = System.currentTimeMillis();
            this.update();
            DebugLogger.log("Gui update method took " + (System.currentTimeMillis() - start) + "ms.");
        });
    }

    public void update() {
        this.inventory.clear();

        for (int i = 46; i < 53; i++) {
            this.inventory.setItem(i, GLASS_PANE);
        }

        long sortPlayersStart = System.currentTimeMillis();
        final List<User> players = this.plugin.getUserManager().getSortedPlayers();
        DebugLogger.log("Sorting and fetching players took " + (System.currentTimeMillis() - sortPlayersStart) + "ms.");

        final int pages = players.size() / 45;

        this.inventory.setItem(53, pages > this.currentPage ? NEXT_PAGE : GLASS_PANE);
        this.inventory.setItem(45, this.currentPage > 0 ? LAST_PAGE : GLASS_PANE);

        long populateInventoryStart = System.currentTimeMillis();
        final int startingIndex = this.currentPage * 45;
        for (int i = 0, j = startingIndex; this.currentPage >= pages ? i < players.size() - startingIndex : i < 45; i++, j++) {
            final Player player = players.get(j).getPlayer();
            this.inventory.setItem(i, this.plugin.getSkullBuilder().newInstance()
                    .setOwner(player)
                    .setName(MessageUtils.color(PlaceholderUtils.applyPlaceholders(player, this.plugin.getConfig().getString("name-format"), str -> {
                                str = str.replace("%username%", player.getName());
                                str = str.replace("%displayname%", player.getDisplayName());
                                return str;
                            }
                    )))
                    .setLore(MessageUtils.color(PlaceholderUtils.applyPlaceholders(player, this.plugin.getConfig().getStringList("lore"), str -> {
                        str = this.plugin.getPapiProvider().setPlaceholders(player, str);

                        VaultProvider vault = this.plugin.getVaultProvider();
                        if (vault.getPermission().isPresent()) {
                            str = str.replace("%rank%", vault.getPermission().get().getPrimaryGroup(player));
                        }
                        if (vault.getEconomy().isPresent()) {
                            str = str.replace("%bal%", PlaceholderUtils.DECIMAL_FORMAT.format(vault.getEconomy().get().getBalance(player)));
                        }

                        return str;
                    })))
                    .create()
            );
        }
        DebugLogger.log("Populating inventory took " + (System.currentTimeMillis() - populateInventoryStart) + "ms.");

        this.plugin.getTitleUpdater().update(this.owner, MessageUtils.color(this.plugin.getConfig().getString("gui-title").replace("%player-count%", String.valueOf(players.size()))));
    }

    public void nextPage() {
        this.currentPage++;
        this.update();
    }

    public void lastPage() {
        this.currentPage--;
        this.update();
    }

    public Inventory getInventory() {
        return this.inventory;
    }
}
