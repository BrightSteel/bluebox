package com.blueboxmc.database;

import com.blueboxmc.database.entry.DatabaseEntry;
import com.blueboxmc.mixin.PersistentStateMixin;
import net.minecraft.server.MinecraftServer;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class DatabaseConnector {

    private final Connection sqliteConnection;
    private boolean sqlite = true;

    public DatabaseConnector(MinecraftServer server) {
        try {
            this.sqliteConnection = DriverManager.getConnection(String.format("jdbc:sqlite:%s/data/bluebox.db", getWorldDirectory(server)));
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to database!");
        }
    }

    public void executeUpdate(String string, Object... obj) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement ps = connection.prepareStatement(string);
        for(int i = 0; i < obj.length; i++) {
            ps.setObject(i + 1, obj[i]);
        }
        ps.executeUpdate();
        ps.close();
        if (!sqlite) {
            connection.close();
        }
    }

    public DatabaseEntry querySingleton(String query, DatabaseEntry dbEntry, Object... obj) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement ps = connection.prepareStatement(query);
        for(int i = 0; i < obj.length; i++) {
            ps.setObject(i + 1, obj[i]);
        }
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            dbEntry.populate(rs);
        } else {
            dbEntry = null;
        }

        rs.close();
        ps.close();
        if (!sqlite) {
            connection.close();
        }
        return dbEntry;
    }

    public Collection<DatabaseEntry> queryCollection(String query, DatabaseEntry dbEntry, Object... obj) throws SQLException {
        Collection<DatabaseEntry> collection = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement ps = connection.prepareStatement(query);
        for(int i = 0; i < obj.length; i++) {
            ps.setObject(i + 1, obj[i]);
        }
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            DatabaseEntry entry = dbEntry.clone();
            entry.populate(rs);
            collection.add(entry);
        }

        rs.close();
        ps.close();
        if (!sqlite) {
            connection.close();
        }
        return collection;
    }

    // TODO - use dataSource#getConnection for non-local mysql server
    private Connection getConnection() {
        return sqliteConnection;
    }

    private File getWorldDirectory(MinecraftServer server) {
        return ((PersistentStateMixin) server.getOverworld().getPersistentStateManager())
                .getDirectory() // overworld dimension directory
                .getParentFile(); // world save directory
    }
}
