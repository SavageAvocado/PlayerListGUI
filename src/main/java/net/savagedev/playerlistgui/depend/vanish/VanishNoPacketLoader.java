package net.savagedev.playerlistgui.depend.vanish;

import net.savagedev.playerlistgui.PlayerListGUI;
import net.savagedev.playerlistgui.depend.DependencyLoader;
import org.bukkit.entity.Player;
import org.kitteh.vanish.VanishPlugin;

import javax.annotation.Nonnull;
import java.util.logging.Level;

public class VanishNoPacketLoader extends DependencyLoader implements VanishProvider {
    private VanishPlugin vanishNoPacket;

    VanishNoPacketLoader(final PlayerListGUI plugin) {
        super("VanishNoPacket", plugin);
    }

    @Override
    protected void onDependencyFound() {
        this.vanishNoPacket = VanishPlugin.getPlugin(VanishPlugin.class);
        this.plugin.getLogger().log(Level.INFO, "Successfully hooked into VanishNoPacket.");
    }

    @Override
    protected void onDependencyNotFound() {
        // You guessed it, nothing to do here.
    }

    @Override
    public boolean isVanished(@Nonnull Player player) {
        return this.vanishNoPacket.getManager().isVanished(player);
    }

    @Override
    public int getVanishedTotal() {
        return this.vanishNoPacket.getManager().getVanishedPlayers().size();
    }
}
