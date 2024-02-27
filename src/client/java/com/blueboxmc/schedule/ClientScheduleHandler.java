package com.blueboxmc.schedule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// thought this was needed for something but turns out not
// can remove if never ends up being used
public class ClientScheduleHandler {

    private int ticks;
    private final List<ScheduledTask> scheduledTasks = new ArrayList<>();

    public void scheduleLater(Runnable runnable, int ticks) {
        scheduledTasks.add(new ScheduledTask(runnable, this.ticks + ticks));
    }

    public void tick() {
        ticks++;
        runScheduledTasks();
    }

    private void runScheduledTasks() {
        Iterator<ScheduledTask> itr = scheduledTasks.iterator();
        while (itr.hasNext()) {
            ScheduledTask scheduledTask = itr.next();
            if (scheduledTask.getScheduledTick() >= ticks) {
                scheduledTask.getRunnable().run();
                itr.remove();
            }
        }
    }
}
