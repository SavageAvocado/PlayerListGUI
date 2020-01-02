package net.savagedev.playerlistgui.api;

public class DebugLogger {
    private static boolean debugging = false;

    private DebugLogger() {
        throw new UnsupportedOperationException("This class may not be instantiated!");
    }

    public static void log(String message) {
        if (DebugLogger.debugging) {
            System.out.println("[PlayerListGUI] [Debug] " + message);
        }
    }

    public static void setDebugging(boolean debugging) {
        DebugLogger.debugging = debugging;
    }
}
