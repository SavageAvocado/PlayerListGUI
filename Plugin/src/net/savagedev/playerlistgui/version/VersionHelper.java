package net.savagedev.playerlistgui.version;

import net.savagedev.playerlistgui.SkullBuilder_Unsupported;
import net.savagedev.playerlistgui.TitleUpdater_Unsupported;
import net.savagedev.playerlistgui.api.SkullBuilder;
import net.savagedev.playerlistgui.api.TitleUpdater;
import net.savagedev.playerlistgui.items.*;
import net.savagedev.playerlistgui.nms.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class VersionHelper {
    private final TitleUpdater titleUpdater;
    private final SkullBuilder skullBuilder;

    public VersionHelper(final JavaPlugin plugin) throws NoSuchMethodException, ClassNotFoundException {
        final String version = plugin.getServer().getClass().getPackage().getName().split("\\.")[3];
        switch (version) {
            case "v1_12_R1":
                this.titleUpdater = new TitleUpdater_1_12_R1();
                this.skullBuilder = new SkullBuilder_1_12_R1();
                break;
            case "v1_13_R1":
                this.titleUpdater = new TitleUpdater_1_13_R1();
                this.skullBuilder = new SkullBuilder_1_13_R1();
                break;
            case "v1_13_R2":
                this.titleUpdater = new TitleUpdater_1_13_R2();
                this.skullBuilder = new SkullBuilder_1_13_R2();
                break;
            case "v1_14_R1":
                this.titleUpdater = new TitleUpdater_1_14_R1();
                this.skullBuilder = new SkullBuilder_1_14_R1();
                break;
            case "v1_15_R1":
                this.titleUpdater = new TitleUpdater_1_15_R1();
                this.skullBuilder = new SkullBuilder_1_15_R1();
                break;
            case "1_16_R1":
            default:
                plugin.getLogger().log(Level.WARNING, "[!] Unsupported version detected. Attempting to use version agnostic implementation... Good luck! :) [!]");
                this.titleUpdater = new TitleUpdater_Unsupported(version);
                this.skullBuilder = new SkullBuilder_Unsupported();
        }
    }

    public TitleUpdater getTitleUpdater() {
        return this.titleUpdater;
    }

    public SkullBuilder getSkullBuilder() {
        return this.skullBuilder;
    }
}
