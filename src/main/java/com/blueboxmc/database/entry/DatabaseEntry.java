package com.blueboxmc.database.entry;

import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DatabaseEntry {

    public abstract void populate(ResultSet rs) throws SQLException;

    @SneakyThrows
    public DatabaseEntry clone() {
        return (DatabaseEntry) super.clone();
    }
}
