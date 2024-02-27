package com.blueboxmc.database;

import com.blueboxmc.database.table.PlayerTable;
import com.blueboxmc.database.table.TardisTable;

public class Tables {

    public static TardisTable tardisTable;
    public static PlayerTable playerTable;

    public static void init() {
        tardisTable = new TardisTable();
        playerTable = new PlayerTable();
    }
}
