package net.savagedev.playerlistgui.nms;

import net.minecraft.server.v1_13_R2.ChatMessage;
import net.minecraft.server.v1_13_R2.Container;
import net.minecraft.server.v1_13_R2.EntityPlayer;
import net.minecraft.server.v1_13_R2.PacketPlayOutOpenWindow;
import net.savagedev.playerlistgui.api.TitleUpdater;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class TitleUpdater_1_13_R2 implements TitleUpdater {
    @Override
    public void update(Player user, String title) {
        EntityPlayer player = ((CraftPlayer) user).getHandle();
        Container container = player.activeContainer;

        player.playerConnection.a(new PacketPlayOutOpenWindow(container.windowId, "minecraft:chest", new ChatMessage(title), user.getOpenInventory().getTopInventory().getSize()), future -> player.updateInventory(container));
    }
}
