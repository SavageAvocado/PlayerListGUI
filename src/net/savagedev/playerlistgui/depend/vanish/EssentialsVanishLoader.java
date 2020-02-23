package net.savagedev.playerlistgui.depend.vanish;

import com.earth2me.essentials.Essentials;
import net.savagedev.playerlistgui.PlayerListGUI;
import net.savagedev.playerlistgui.depend.DependencyLoader;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.logging.Level;

public class EssentialsVanishLoader extends DependencyLoader implements VanishProvider {
    private Essentials essentials;

    EssentialsVanishLoader(final PlayerListGUI plugin) {
        super("Essentials", plugin);
    }

    @Override
    protected void onHookSuccess() {
        this.essentials = Essentials.getPlugin(Essentials.class);
        this.plugin.getLogger().log(Level.INFO, "Successfully hooked into Essentials.");
    }

    @Override
    protected void onHookFail() {
    }

    @Override
    public boolean isVanished(@Nonnull Player player) {
        return this.essentials.getUser(player).isHidden();
    }

    @Override
    public int getVanishedTotal() {
        return this.essentials.getVanishedPlayers().size();
    }
}
