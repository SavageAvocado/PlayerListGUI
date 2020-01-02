package net.savagedev.playerlistgui.nms;

import net.minecraft.server.v1_14_R1.*;
import net.savagedev.playerlistgui.api.TitleUpdater;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class TitleUpdater_1_14_R1 implements TitleUpdater {
    @Override
    public void update(Player user, String title) {
        EntityPlayer player = ((CraftPlayer) user).getHandle();
        Container container = player.activeContainer;

        player.playerConnection.a(new PacketPlayOutOpenWindow(container.windowId, this.getContainer(user.getOpenInventory().getTopInventory()), new ChatMessage(title)), future -> player.updateInventory(container));
    }

    private Containers<ContainerChest> getContainer(Inventory inventory) {
        switch (inventory.getSize()) {
            case 9:
                return Containers.GENERIC_9X1;
            case 18:
                return Containers.GENERIC_9X2;
            case 27:
                return Containers.GENERIC_9X3;
            case 36:
                return Containers.GENERIC_9X4;
            case 45:
                return Containers.GENERIC_9X5;
            case 54:
                return Containers.GENERIC_9X6;
        }
        return Containers.GENERIC_9X6;
    }
}
