package com.blueboxmc.database.table;

import com.blueboxmc.Bluebox;

public abstract class Table {

    public abstract String getPrepareStatement();

    public Table() {
        Bluebox.dbPreparation.addStatement(getPrepareStatement());
    }

    protected void logError(String operation, Exception e) {
        Bluebox.LOGGER.error(String.format("Failed to %s entry", operation), e);
    }
}
