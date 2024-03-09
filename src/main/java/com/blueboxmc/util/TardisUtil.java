package com.blueboxmc.util;

import com.blueboxmc.Bluebox;

public class TardisUtil {

    /**
     * Gets a 'random' nickname for given tardisUUID, and always returns the same result.
     * Note - can change if tardisNames list changes
     * @param tardisUUID uuid used to make a consistent hash
     * @return default nickname for given tardis
     */
    public static String getDefaultNickname(String tardisUUID) {
        String[] tardisNames = Bluebox.getTardisNames();
        int hash = HashUtil.getHash(tardisUUID);
        return tardisNames[hash % tardisNames.length];
    }
}
