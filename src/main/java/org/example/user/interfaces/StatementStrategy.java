package org.example.user.interfaces;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementStrategy {
    PreparedStatement makePreparesStatement(Connection c) throws SQLException;
}
