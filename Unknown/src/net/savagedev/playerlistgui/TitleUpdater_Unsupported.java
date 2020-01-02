package net.savagedev.playerlistgui;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.savagedev.playerlistgui.api.TitleUpdater;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class TitleUpdater_Unsupported implements TitleUpdater {
    private static final String NMS_PACKAGE = "net.minecraft.server.";

    private final String version;

    private Constructor<?> packetPlayOutOpenWindow;
    private Constructor<?> chatMessage;

    private Class<?> containersClass;

    public TitleUpdater_Unsupported(final String version) throws ClassNotFoundException, NoSuchMethodException {
        this.version = version;

        try {
            this.chatMessage = Class.forName(NMS_PACKAGE + this.version + ".ChatMessage").getConstructor(String.class, Object[].class);
            this.packetPlayOutOpenWindow = Class.forName(NMS_PACKAGE + this.version + ".PacketPlayOutOpenWindow").getConstructor(int.class, String.class, Class.forName(NMS_PACKAGE + this.version + ".IChatBaseComponent"), int.class);
        } catch (NoSuchMethodException ignored) {
            this.containersClass = Class.forName(NMS_PACKAGE + this.version + ".Containers");
            this.packetPlayOutOpenWindow = Class.forName(NMS_PACKAGE + this.version + ".PacketPlayOutOpenWindow").getConstructor(int.class, containersClass, Class.forName(NMS_PACKAGE + this.version + ".IChatBaseComponent"));
        }
    }

    @Override
    public void update(Player player, String title) {
        Object entityPlayer = this.getEntityPlayer(player);
        if (entityPlayer != null) {
            Object container = this.getActiveContainer(entityPlayer);
            if (container != null) {
                try {
                    this.sendPacketLegacy(entityPlayer, this.packetPlayOutOpenWindow.newInstance(container.getClass().getField("windowId").get(container), "minecraft:chest", this.newChatMessage(title), player.getOpenInventory().getTopInventory().getSize()), () -> this.updateInventory(entityPlayer, container));
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException ignored) {
                    try {
                        this.sendPacket(entityPlayer, this.packetPlayOutOpenWindow.newInstance(container.getClass().getField("windowId").get(container), this.getContainer(player.getOpenInventory().getTopInventory()), this.newChatMessage(title)), future -> this.updateInventory(entityPlayer, container));
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Object getContainer(Inventory inventory) throws NoSuchFieldException, IllegalAccessException {
        switch (inventory.getSize()) {
            case 9:
                return this.containersClass.getField("GENERIC_9X1").get(null);
            case 18:
                return this.containersClass.getField("GENERIC_9X2").get(null);
            case 27:
                return this.containersClass.getField("GENERIC_9X3").get(null);
            case 36:
                return this.containersClass.getField("GENERIC_9X4").get(null);
            case 45:
                return this.containersClass.getField("GENERIC_9X5").get(null);
            case 54:
                return this.containersClass.getField("GENERIC_9X6").get(null);
        }
        return this.containersClass.getField("GENERIC_9X6").get(null);
    }

    private Object newChatMessage(String message) {
        try {
            return this.chatMessage.newInstance(message, new Object[]{});
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void sendPacketLegacy(Object player, Object packet, Runnable futureListener) {
        try {
            Object connection = player.getClass().getField("playerConnection").get(player);
            connection.getClass().getMethod("sendPacket", Class.forName(NMS_PACKAGE + this.version + ".Packet")).invoke(connection, packet);
            futureListener.run();
        } catch (IllegalAccessException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void sendPacket(Object player, Object packet, GenericFutureListener<? extends Future<? super Void>> futureListener) {
        try {
            Object connection = player.getClass().getField("playerConnection").get(player);
            connection.getClass().getMethod("a", Class.forName(NMS_PACKAGE + this.version + ".Packet"), GenericFutureListener.class).invoke(connection, packet, futureListener);
        } catch (NoSuchMethodException | IllegalAccessException | NoSuchFieldException | InvocationTargetException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateInventory(Object player, Object container) {
        try {
            player.getClass().getMethod("updateInventory", Class.forName(NMS_PACKAGE + this.version + ".Container")).invoke(player, container);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Object getActiveContainer(Object player) {
        try {
            return player.getClass().getField("activeContainer").get(player);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Object getEntityPlayer(Player player) {
        try {
            return player.getClass().getMethod("getHandle").invoke(player);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
