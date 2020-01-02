package net.savagedev.playerlistgui.threads;

import net.savagedev.playerlistgui.PlayerListGUI;
import net.savagedev.playerlistgui.api.DebugLogger;
import net.savagedev.playerlistgui.gui.Gui;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class GuiUpdateThread extends Thread {
    private final PlayerListGUI plugin;

    public GuiUpdateThread(final PlayerListGUI plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        while (this.plugin.isEnabled()) {
            Map<UUID, Gui> openGuis = this.plugin.getOpenGuis();
            if (!openGuis.isEmpty()) {
                DebugLogger.log("Asynchronously updating " + openGuis.size() + " open inventories.");
                long totalStart = System.currentTimeMillis();
                for (Gui gui : openGuis.values()) {
                    long start = System.currentTimeMillis();
                    gui.update();
                    DebugLogger.log("Gui update method took " + (System.currentTimeMillis() - start) + "ms.");
                }
                DebugLogger.log("Total: " + (System.currentTimeMillis() - totalStart) + "ms.");
            }

            try {
                TimeUnit.MILLISECONDS.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
