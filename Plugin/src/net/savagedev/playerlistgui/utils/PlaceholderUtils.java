package net.savagedev.playerlistgui.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.List;
import java.util.function.Function;

public class PlaceholderUtils {
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

    private PlaceholderUtils() {
        throw new UnsupportedOperationException("This class may not be instantiated!");
    }

    public static String applyPlaceholders(Player player, String str, Function<String, String> function) {
        if (str != null && !str.isEmpty()) {
            return function.apply(PlaceholderUtils.applyDefaultPlaceholders(player, str));
        }
        return str;
    }

    public static List<String> applyPlaceholders(Player player, List<String> strs, Function<String, String> function) {
        for (int i = 0; i < strs.size(); i++) {
            strs.set(i, PlaceholderUtils.applyPlaceholders(player, strs.get(i), function));
        }
        return strs;
    }

    private static String applyDefaultPlaceholders(Player player, String str) {
        final Location location = player.getLocation();
        final String world = location.getWorld().getName();
        final String x = DECIMAL_FORMAT.format(location.getX());
        final String y = DECIMAL_FORMAT.format(location.getY());
        final String z = DECIMAL_FORMAT.format(location.getZ());

        str = str.replace("%location%", world + "; " + x + ", " + y + ", " + z);
        str = str.replace("%world%", world);
        str = str.replace("%locx%", x);
        str = str.replace("%locy%", y);
        str = str.replace("%locz%", z);
        str = str.replace("%gamemode%", player.getGameMode().name());
        str = str.replace("%health%", DECIMAL_FORMAT.format(player.getHealth()));
        str = str.replace("%food%", String.valueOf(player.getFoodLevel()));
        str = str.replace("%level%", String.valueOf(player.getLevel()));
        return str;
    }
}
