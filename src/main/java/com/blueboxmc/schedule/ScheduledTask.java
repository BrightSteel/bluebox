package com.blueboxmc.schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduledTask {
    private Runnable runnable;
    private int scheduledTick;
}
