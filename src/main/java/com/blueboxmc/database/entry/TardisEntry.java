package com.blueboxmc.database.entry;

import com.blueboxmc.database.entry.nested.LocationEntry;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter @Setter
@NoArgsConstructor
@Accessors(chain = true)
public class TardisEntry extends DatabaseEntry {
    private String entityUUID, ownerUUID, nickname;
    private int interiorBoundMin, interiorBoundMax;
    private LocationEntry entityLocation, interiorSpawnLocation;

    @Override
    public void populate(ResultSet rs) throws SQLException {
        setEntityUUID(rs.getString("entity_uuid"));
        setOwnerUUID(rs.getString("owner_uuid"));
        setNickname(rs.getString("nickname"));
        setInteriorBoundMin(rs.getInt("interior_bound_min"));
        setInteriorBoundMax(rs.getInt("interior_bound_max"));
        setEntityLocation(LocationEntry.fromJson(rs.getString("entity_location")));
        setInteriorSpawnLocation(LocationEntry.fromJson(rs.getString("interior_spawn_location")));
    }
}
