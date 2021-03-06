package net.savagedev.playerlistgui.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class MessageUtils {
    private MessageUtils() {
        throw new UnsupportedOperationException("This class may not be instantiated!");
    }

    public static void message(CommandSender sender, String message) {
        sender.sendMessage(MessageUtils.color(message));
    }

    public static List<String> color(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            final String str = list.get(i);
            if (str != null && !str.isEmpty()) {
                list.set(i, MessageUtils.color(str));
            }
        }
        return list;
    }

    public static String color(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }
}
