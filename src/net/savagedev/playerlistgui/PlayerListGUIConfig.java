package net.savagedev.playerlistgui;

import net.savagedev.playerlistgui.utils.MessageUtils;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

@SuppressWarnings("ALL")
public class PlayerListGUIConfig {
    private final Map<String, Integer> groupPriorities = new HashMap<>();

    private final List<String> lore;
    private final String nameFormat;
    private final String guiTitle;

    private ItemStack placeholderBlock;
    private ItemStack nextPageBlock;
    private ItemStack lastPageBlock;

    private boolean prioritiesEnabled;

    PlayerListGUIConfig(final JavaPlugin plugin) {
        this.nameFormat = plugin.getConfig().getString("name-format");
        this.guiTitle = plugin.getConfig().getString("gui-title");
        this.lore = plugin.getConfig().getStringList("lore");

        final String placeholderMaterial = plugin.getConfig().getString("placeholder-block.material");
        try {
            if (PlayerListGUI.isLegacy()) {
                this.placeholderBlock = new ItemStack(Material.valueOf(placeholderMaterial.toUpperCase()), plugin.getConfig().getInt("placeholder-block.amount"), (short) plugin.getConfig().getInt("placeholder-block.damage"));
            } else {
                this.placeholderBlock = new ItemStack(Material.valueOf(placeholderMaterial.toUpperCase()), plugin.getConfig().getInt("placeholder-block.amount"));
            }
            final ItemMeta meta = this.placeholderBlock.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(MessageUtils.color(plugin.getConfig().getString("placeholder-block.name")));
                meta.setLore(plugin.getConfig().getStringList("placeholder-block.lore"));
            }
            this.placeholderBlock.setItemMeta(meta);
        } catch (IllegalArgumentException ignored) {
            plugin.getLogger().log(Level.WARNING, "Invalid placeholder block material type in config! (" + placeholderMaterial + ")");
        }

        final String nextPageMaterial = plugin.getConfig().getString("next-page-block.material");
        try {
            if (PlayerListGUI.isLegacy()) {
                this.nextPageBlock = new ItemStack(Material.valueOf(nextPageMaterial.toUpperCase()), plugin.getConfig().getInt("next-page-block.amount"), (short) plugin.getConfig().getInt("next-page-block.damage"));
            } else {
                this.nextPageBlock = new ItemStack(Material.valueOf(nextPageMaterial.toUpperCase()), plugin.getConfig().getInt("next-page-block.amount"));
            }
            final ItemMeta meta = this.nextPageBlock.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(MessageUtils.color(plugin.getConfig().getString("next-page-block.name")));
                meta.setLore(plugin.getConfig().getStringList("next-page-block.lore"));
            }
            this.nextPageBlock.setItemMeta(meta);
        } catch (IllegalArgumentException ignored) {
            plugin.getLogger().log(Level.WARNING, "Invalid next page block material type in config! (" + nextPageMaterial + ")");
        }

        final String lastPageMaterial = plugin.getConfig().getString("last-page-block.material");
        try {
            if (PlayerListGUI.isLegacy()) {
                this.lastPageBlock = new ItemStack(Material.valueOf(lastPageMaterial.toUpperCase()), plugin.getConfig().getInt("last-page-block.amount"), (short) plugin.getConfig().getInt("last-page-block.damage"));
            } else {
                this.lastPageBlock = new ItemStack(Material.valueOf(lastPageMaterial.toUpperCase()), plugin.getConfig().getInt("last-page-block.amount"));
            }
            final ItemMeta meta = this.lastPageBlock.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(MessageUtils.color(plugin.getConfig().getString("last-page-block.name")));
                meta.setLore(plugin.getConfig().getStringList("last-page-block.lore"));
            }
            this.lastPageBlock.setItemMeta(meta);
        } catch (IllegalArgumentException ignored) {
            plugin.getLogger().log(Level.WARNING, "Invalid last page block material type in config! (" + lastPageMaterial + ")");
        }

        final ConfigurationSection groups = plugin.getConfig().getConfigurationSection("priorities.groups");
        if (groups != null) {
            for (String group : groups.getKeys(false)) {
                this.groupPriorities.putIfAbsent(group, plugin.getConfig().getInt("priorities.groups." + group));
            }
        } else {
            plugin.getLogger().log(Level.WARNING, "Groups configuration section is null!");
        }

        this.prioritiesEnabled = plugin.getConfig().getBoolean("priorities.enabled", false);
    }

    public List<String> getLore() {
        return this.lore;
    }

    public String getGuiTitle() {
        return this.guiTitle;
    }

    public String getNameFormat() {
        return this.nameFormat;
    }

    public ItemStack getPlaceholderBlock() {
        return this.placeholderBlock;
    }

    public ItemStack getNextPageBlock() {
        return this.nextPageBlock;
    }

    public ItemStack getLastPageBlock() {
        return this.lastPageBlock;
    }

    public Map<String, Integer> getGroupPriorities() {
        return this.groupPriorities;
    }

    public boolean isPrioritiesEnabled() {
        return this.prioritiesEnabled;
    }
}
