package net.savagedev.playerlistgui.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;
import java.util.List;

public class MessageUtils {
    private MessageUtils() {
        throw new UnsupportedOperationException("This class may not be instantiated!");
    }

    /**
     * Sends a message to a specified {@link CommandSender}.
     *
     * @param receiver - The receiver of the message.
     * @param message  - The message the receiver will receive.
     */
    public static void message(@Nonnull CommandSender receiver, @Nonnull String message) {
        receiver.sendMessage(MessageUtils.color(message));
    }

    /**
     * Colorizes a list of strings. {@link #color(String)}
     *
     * @param list - The list of strings to be colorized.
     * @return The colorized list of strings.
     */
    public static List<String> color(@Nonnull List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            final String str = list.get(i);
            if (str != null && !str.isEmpty()) {
                list.set(i, MessageUtils.color(str));
            }
        }
        return list;
    }

    /**
     * Translates Minecraft {@link ChatColor}s using the char '&' and returns the colorized string.
     *
     * @param str - The string to be colorized.
     * @return The colorized string.
     */
    public static String color(@Nonnull String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static class FastString {
        private String string;

        /**
         * Creates a new FastString instance with an empty string.
         */
        public FastString() {
            this("");
        }

        /**
         * Creates a new FastString instance with a specified string.
         *
         * @param string - The string you want to initialize the FastString instance with.
         */
        public FastString(@Nonnull final String string) {
            this.string = string;
        }

        /**
         * Replaces a specific string within {@link #string} with a specified string.
         * The method is ~6x faster than {@link String#replace(CharSequence, CharSequence)}.
         *
         * @param placeholder - The String to be replaced.
         * @param replacement - The String that will take the place of the placeholder.
         * @return The {@link FastString} instance.
         */
        @SuppressWarnings("StringBufferReplaceableByString")
        public FastString replace(@Nonnull final String placeholder, @Nonnull final String replacement) {
            if (placeholder.isEmpty() || replacement.isEmpty() || placeholder.equals(replacement)) {
                return this;
            }

            final int startIndex = this.string.indexOf(placeholder);
            if (startIndex == -1) {
                return this;
            }

            this.string = new StringBuilder().append(this.string, 0, startIndex)
                    .append(replacement)
                    .append(this.string, startIndex + placeholder.length(), this.string.length())
                    .toString();
            return this;
        }

        /**
         * @return {@link #string}
         */
        @Override
        public String toString() {
            return this.string;
        }
    }
}
