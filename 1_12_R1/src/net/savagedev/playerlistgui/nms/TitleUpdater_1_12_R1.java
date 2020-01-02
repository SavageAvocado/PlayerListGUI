package net.savagedev.playerlistgui.nms;

import net.minecraft.server.v1_12_R1.ChatMessage;
import net.minecraft.server.v1_12_R1.Container;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.PacketPlayOutOpenWindow;
import net.savagedev.playerlistgui.api.TitleUpdater;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class TitleUpdater_1_12_R1 implements TitleUpdater {
    @Override
    public void update(Player user, String title) {
        EntityPlayer player = ((CraftPlayer) user).getHandle();
        Container container = player.activeContainer;

        player.playerConnection.networkManager.sendPacket(new PacketPlayOutOpenWindow(container.windowId, "minecraft:chest", new ChatMessage(title), user.getOpenInventory().getTopInventory().getSize()), future -> player.updateInventory(container));
    }
}
