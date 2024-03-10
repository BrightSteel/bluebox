package com.blueboxmc.database.entry;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.ResultSet;
import java.sql.SQLException;

@EqualsAndHashCode(callSuper = true)
@Data
public class UUIDEntry extends DatabaseEntry {

    private String entityUUID;

    @Override
    public void populate(ResultSet rs) throws SQLException {
        setEntityUUID(rs.getString("entity_uuid"));
    }
}
