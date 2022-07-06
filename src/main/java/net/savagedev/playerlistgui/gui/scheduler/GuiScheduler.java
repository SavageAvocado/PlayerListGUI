package net.savagedev.playerlistgui.gui.scheduler;

import net.savagedev.imlib.IMLib;
import net.savagedev.imlib.scheduler.Scheduler;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

import javax.annotation.Nonnull;

public class GuiScheduler implements Scheduler {
    private final BukkitScheduler scheduler = Bukkit.getScheduler();

    private int taskId = -1;

    @Override
    public void scheduleAsync(@Nonnull Runnable runnable, long delay, long period) {
        if (this.taskId != -1) {
            throw new IllegalStateException("You must cancel the original task before scheduling a new one!");
        }
        this.taskId = (
                this.scheduler.runTaskTimer(IMLib.getInstance().getPlugin(), runnable, delay, period)
        ).getTaskId();
    }

    @Override
    public void cancel() {
        if (this.taskId == -1) {
            throw new IllegalStateException("No task timer was started!");
        }
        this.scheduler.cancelTask(this.taskId);
        this.taskId = -1;
    }
}
