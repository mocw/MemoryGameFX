package lodz.uni.math.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Properties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DbClass {
    private static Connection conn = null;

    public static void connectDB() throws SQLException {
        String url = "jdbc:mysql://localhost/memorygame";
        Properties prop = new Properties();
        prop.setProperty("user","root");
        prop.setProperty("password","");

        try {
            conn = DriverManager.getConnection(url, prop);
        } catch (SQLException e) {
            System.out.println("Błąd z połączeniem z bazą danych.");
            throw e;
        }

        System.out.println("Połączono z bazą danych.");
    }

    public static void disconnectDB() throws SQLException {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Zamknięto połączenie z bazą danych.");
            }
        } catch (SQLException e) {
            System.out.println("Błąd z zamknięciem połączenia z bazą danych.");
            throw e;
        }
    }

    public static void dmlOperation(String sqlInstruction) throws SQLException {
        Statement stat = null;

    }


}


