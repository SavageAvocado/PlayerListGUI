package net.savagedev.playerlistgui.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class PlaceholderUtils {
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

    private PlaceholderUtils() {
        throw new UnsupportedOperationException("This class may not be instantiated!");
    }

    public static String applyPlaceholders(@Nonnull Player player, @Nonnull String str, Function<MessageUtils.FastString, String> function) {
        if (!str.isEmpty()) {
            if (function != null) {
                return function.apply(new MessageUtils.FastString(PlaceholderUtils.applyDefaultPlaceholders(player, str)));
            }
            return PlaceholderUtils.applyDefaultPlaceholders(player, str);
        }
        return str;
    }

    public static List<String> applyPlaceholders(@Nonnull Player player, @Nonnull List<String> strs, Function<MessageUtils.FastString, String> function) {
        strs = new ArrayList<>(strs);
        for (int i = 0; i < strs.size(); i++) {
            strs.set(i, PlaceholderUtils.applyPlaceholders(player, strs.get(i), function));
        }
        return strs;
    }

    private static String applyDefaultPlaceholders(@Nonnull Player player, @Nonnull String str) {
        final Location location = player.getLocation();

        final String worldName = player.getWorld().getName();
        final String x = DECIMAL_FORMAT.format(location.getX());
        final String y = DECIMAL_FORMAT.format(location.getY());
        final String z = DECIMAL_FORMAT.format(location.getZ());

        return new MessageUtils.FastString(str)
                .replace("%location%", worldName + "; " + x + ", " + y + ", " + z)
                .replace("%gamemode%", player.getGameMode().name().toLowerCase())
                .replace("%health%", DECIMAL_FORMAT.format(player.getHealth()))
                .replace("%food%", String.valueOf(player.getFoodLevel()))
                .replace("%level%", String.valueOf(player.getLevel()))
                .replace("%world%", worldName)
                .replace("%locx%", x)
                .replace("%locy%", y)
                .replace("%locz%", z)
                .toString();
    }
}
