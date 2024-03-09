package com.blueboxmc.util;

import com.blueboxmc.document.DocumentHandler;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class TardisUtil {

    // btw do we want to move this to a tardis creation handler or something?
    // TODO add more options, and retrieve using a hash from uuid so is consistent
    // TODO cache this, reading a file each time is expensive
    public static String getDefaultNickname(UUID tardisUUID) {
        String[] tardisNames = new DocumentHandler<>("tardis_names", String[].class).getDocument();
        return tardisNames[ThreadLocalRandom.current().nextInt(tardisNames.length)];
    }
}
