package lodz.uni.math.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import lodz.uni.math.User;

import javax.security.auth.login.FailedLoginException;

public class DbClass {

    private static Connection conn = null;
    public static Connection getConn() {
        return conn;
    }
    private static List<RankItem> rankItemList;

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

    public static void updateResult(float time) throws SQLException {
        Statement stat = null;
        String query = "SELECT time FROM rank WHERE userID =" + User.getId();

        try {
            connectDB();
            stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(query);
            boolean userExistsInRank = false;
            while(rs.next()){
                userExistsInRank = true;
                break;
            }

            if(userExistsInRank){
                float previousTime = rs.getFloat("time");
                if(previousTime > time){
                   String updatedResult = "UPDATE rank " +
                           "SET time=" + time +
                           "WHERE userID=" + User.getId();
                    DbClass.dmlOperation(updatedResult);
                }
            } else {
                String sql = "INSERT INTO rank (userID, time) VALUES('"+User.getId()+"', '"+time+"')";
                DbClass.dmlOperation(sql);
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

        dmlOperation(registerSQL);

    }

    public static void login(String nick, String password) throws SQLException, FailedLoginException {
        Statement stat = null;
        String userData = "SELECT id, nick, password FROM users " +
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
                    User.getUser();
                    User.setId(rs.getInt("id"));
                    User.setNickname(rs.getString("nick"));
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

    public static void getRank() throws SQLException{
        Statement stat = null;
        rankItemList = new ArrayList<>();
        String sql = "Select nick, time\n" +
                "from rank \n" +
                "join users\n" +
                "ON rank.userID=users.id\n" +
                "ORDER BY time\n" +
                "LIMIT 21";
        try {
            connectDB();
            stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            int pos = 0;
            while(rs.next()){
                RankItem item = new RankItem(++pos, rs.getString("nick"), rs.getFloat("time"));
                rankItemList.add(item);
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

    public static List<RankItem> getRankItemList() {
        return rankItemList;
    }
}


