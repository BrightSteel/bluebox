package com.blueboxmc.database.entry;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter @Setter
@NoArgsConstructor
@Accessors(chain = true)
public class PlayerEntry extends DatabaseEntry {

    private String playerUUID;
    private String username;

    @Override
    public void populate(ResultSet rs) throws SQLException {
        setPlayerUUID(rs.getString("player_uuid"));
        setUsername(rs.getString("username"));
    }
}
