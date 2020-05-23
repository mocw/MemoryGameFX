package lodz.uni.math.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Properties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.security.auth.login.FailedLoginException;
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

        try {
            connectDB();
            stat = conn.createStatement();
            stat.execute(sqlInstruction);
        } catch (SQLException e) {
            System.out.println("Błąd z wykonaniem instruckji SQL typu DML.");
            throw e;
        }
        finally {
            if (!stat.isClosed()) {
                stat.close();
            }
            disconnectDB();
        }
    }

    public static void register(String nick, String password) throws SQLException, UserAlreadyExistAuthenticationException {
        Statement stat = null;
        String registerSQL = "INSERT INTO users(nick, password) " +
                "VALUES('" + nick +"','" + BCrypt.hashpw(password) + "')";
        String checkUsername = "SELECT id, nick FROM users " +
                "WHERE nick ='" + nick + "'";
        try {
            connectDB();
            stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(checkUsername);
            while(rs.next()) {
                if(rs.getString("nick").equals(nick)){
                    throw new UserAlreadyExistAuthenticationException("Nazwa użytkownika zajęta!");
                }
            }
        } catch (SQLException e) {
            System.out.println("Błąd z wykonaniem instruckji SQL typu DQL.");
            throw e;
        }
        finally {
            if (!stat.isClosed()) {
                stat.close();
            }
            disconnectDB();
        }

        try {
            connectDB();
            stat = conn.createStatement();
            stat.execute(registerSQL);
        } catch (SQLException e) {
            System.out.println("Błąd z wykonaniem instruckji SQL typu DML.");
            throw e;
        }
        finally {
            if (!stat.isClosed()) {
                stat.close();
            }
            disconnectDB();
        }
    }

    public static void login(String nick, String password) throws SQLException, FailedLoginException {
        Statement stat = null;
        String userData = "SELECT nick, password FROM users " +
                "WHERE nick ='" + nick + "'";

        try {
            connectDB();
            stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(userData);
            int i = 0;
            while(rs.next()) {
                if(!BCrypt.checkpw(password,rs.getString("password"))){
                    throw new FailedLoginException("Nieprawidlowe haslo!");
                } else {
                    i++;
                }
            }
            if( i == 0){
                throw new FailedLoginException("No such account!");
            }
        } catch (SQLException e) {
            System.out.println("Błąd z wykonaniem instruckji SQL typu DQL.");
            throw e;
        }
        finally {
            if (!stat.isClosed()) {
                stat.close();
            }
            disconnectDB();
        }
    }


}


