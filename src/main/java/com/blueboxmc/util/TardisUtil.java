package com.blueboxmc.util;

import java.util.UUID;

public class TardisUtil {

    // btw do we want to move this to a tardis creation handler or something?
    // TODO add more options, and retrieve using a hash from uuid so is consistent
    public static String getDefaultNickname(UUID tardisUUID) {
        return "Type 40 TARDIS";
    }
}
