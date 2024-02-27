package com.blueboxmc.database.table;

import com.blueboxmc.Bluebox;
import com.blueboxmc.database.entry.PlayerEntry;

import java.sql.SQLException;

public class PlayerTable extends Table {

    @Override
    public String getPrepareStatement() {
        return """
               CREATE TABLE IF NOT EXISTS player(
               player_uuid varchar(36) NOT NULL,
               username varchar(16) NOT NULL,
               PRIMARY KEY (player_uuid)
               )
               """;
    }

    public PlayerEntry getEntry(String playerUUID) {
        try {
            String query = "SELECT * FROM player WHERE player_uuid = ?";
            return (PlayerEntry) Bluebox.dbConnector.querySingleton(query, new PlayerEntry(), playerUUID);
        } catch (SQLException e) {
            logError("get", e);
            return null;
        }
    }

    public boolean createEntry(PlayerEntry playerEntry) {
        try {
            String update = """
                        INSERT INTO player (
                            player_uuid,
                            username
                        ) VALUES (?, ?)
                        """;
            Bluebox.dbConnector.executeUpdate(update,
                    playerEntry.getPlayerUUID(),
                    playerEntry.getUsername()
            );
            return true;
        } catch (SQLException e) {
            logError("create", e);
            return false;
        }
    }

    public boolean updateEntry(PlayerEntry playerEntry) {
        try {
            String update = "UPDATE player SET username = ? WHERE player_uuid = ?";
            Bluebox.dbConnector.executeUpdate(update,
                    playerEntry.getUsername(),
                    playerEntry.getPlayerUUID()
            );
            return true;
        } catch (SQLException e) {
            logError("update", e);
            return false;
        }
    }
}
