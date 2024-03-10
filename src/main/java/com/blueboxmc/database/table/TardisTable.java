package com.blueboxmc.database.table;

import com.blueboxmc.Bluebox;
import com.blueboxmc.database.entry.TardisEntry;
import com.blueboxmc.database.entry.UUIDEntry;

import java.sql.SQLException;
import java.util.UUID;

public class TardisTable extends Table {
    // todo for mysql - need AUTO_INCREMENT keyword

    @Override
    public String getPrepareStatement() {
        return """
               CREATE TABLE IF NOT EXISTS tardis(
                   entity_uuid varchar(36) NOT NULL,
                   owner_uuid varchar(36) NOT NULL,
                   nickname varchar(36) NOT NULL,
                   interior_bound_min int,
                   interior_bound_max int,
                   entity_location varchar(128) NOT NULL,
                   interior_spawn_location varchar(128),
                   PRIMARY KEY (entity_uuid)
               )
               """;
    }

    public TardisEntry getEntry(String entity_uuid) {
        try {
            String query = "SELECT * FROM tardis WHERE entity_uuid = ? LIMIT 1";
            return (TardisEntry) Bluebox.dbConnector.querySingleton(query, new TardisEntry(), entity_uuid);
        } catch (SQLException e) {
            logError("get", e);
            return null;
        }
    }

    public boolean createEntry(TardisEntry tardisEntry) {
        try {
            String update = """
                            INSERT INTO tardis (
                                entity_uuid,
                                owner_uuid,
                                nickname,
                                entity_location
                            ) VALUES (?, ?, ?, ?)
                            """;
            Bluebox.dbConnector.executeUpdate(update,
                    tardisEntry.getEntityUUID(),
                    tardisEntry.getOwnerUUID(),
                    tardisEntry.getNickname(),
                    tardisEntry.getEntityLocation().toJson()
            );
        } catch (SQLException e) {
            logError("create", e);
            return false;
        }
        return true;
    }

    public boolean updateInterior(TardisEntry tardisEntry) {
        try {
            String update = """
                        UPDATE tardis SET
                            interior_bound_min = ?,
                            interior_bound_max = ?,
                            interior_spawn_location = ?
                        WHERE entity_uuid = ?
                        """;
            Bluebox.dbConnector.executeUpdate(update,
                    tardisEntry.getInteriorBoundMin(),
                    tardisEntry.getInteriorBoundMax(),
                    tardisEntry.getInteriorSpawnLocation().toJson(),
                    tardisEntry.getEntityUUID()
            );
        } catch (SQLException e) {
            logError("update", e);
            return false;
        }
        return true;
    }

    public UUIDEntry getTardisUUIDFromInterior(int posZ) {
        try {
            String query = "SELECT entity_uuid FROM tardis WHERE interior_bound_min <= ? AND interior_bound_max >= ? LIMIT 1";
            return (UUIDEntry) Bluebox.dbConnector.querySingleton(query, new UUIDEntry(), posZ, posZ);
        } catch (SQLException e) {
            logError("select", e);
            return null;
        }
    }
}
