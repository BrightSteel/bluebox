package com.blueboxmc.database;

import com.blueboxmc.Bluebox;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabasePreparation {

    private final List<String> prepareStatements;

    public DatabasePreparation() {
        this.prepareStatements = new ArrayList<>();
    }

    public void addStatement(String statement) {
        prepareStatements.add(statement);
    }

    public void executePrepareStatements() {
        try {
            for (String statement : prepareStatements) {
                Bluebox.dbConnector.executeUpdate(statement);
            }
        } catch (SQLException e) {
            Bluebox.LOGGER.error("Failed to prepare database!", e);
        }
    }
}
