package server;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Contains methods to access the database of registered
 * user accounts. For simplicity, it supports only two operations:
 * registering a new user and authenticating a user.
 *
 * @author Alston
 * last updated 12/25/2018
 */
class Database {

    private static Database DEFAULT_INSTANCE = new Database();

    /**
     * Returns a Singleton instance of the database.
     *
     * @return the database
     */
    static Database getInstance() {
        return DEFAULT_INSTANCE;
    }


    /*
     * The class itself does not contain the Connection, Statements, or ResultSets
     * of the database. If multiple threads try to access the Database, the resources
     * are not shared amongst them (which is advised against by Apache Derby).
     */

    private ConnectionPool pool = new ConnectionPool();
    private String tableSQL = "create table users(username varchar(20) not null unique, password varchar(20) not null)";
    private String insertSQL = "insert into users (username, password) values (?,?)";
    private String authenticateSQL = "select * from users where username=? and password=?";

    private Database() {
        createUserTable();
    }

    /**
     * Registers a new account to the database with a proposed username and password.
     * A valid username is any String under 20 characters that is unique from existing registered
     * usernames. A valid password is any String under 20 characters. If the two are valid and no
     * other error occurs, the account will be successfully registered and true will be returned.
     *
     * @param username the proposed username of the new user
     * @param password the proposed password of the new user
     * @return true if the account was successfully registered; false otherwise
     */
    boolean register(String username, String password) {
        try (Connection con = pool.getConnection();
             PreparedStatement insert = con.prepareStatement(insertSQL)) {

            insert.setString(1, username);
            insert.setString(2, password);
            insert.executeUpdate();
            con.commit();
            return true;

        } catch (SQLException e) {
            //an SQLException should, in theory, only be thrown if
            // a) the username of password is null
            // b) the username is not unique
            //either way, any other potential causes for error will still fail to register the account

            return false;
        }
    }

    /**
     * Authenticates a user's attempt to log into their account.
     * In short, verifies if there exists a row in the database with the username and password argument.
     * Returns true, if such an entry exists and can be successfully found. If the entry does not
     * exist, or an error occurs in retrieving it, false is returned.
     *
     * @param username the username of the account
     * @param password the password of the account
     * @return true, if an account under the username and password arguments is successfully found; false, otherwise
     */
    boolean authenticate(String username, String password) {
        try (Connection con = pool.getConnection();
             PreparedStatement authenticate = con.prepareStatement(authenticateSQL)) {

            authenticate.setString(1, username);
            authenticate.setString(2, password);

            try (ResultSet res = authenticate.executeQuery()) {
                return res.next(); //next() will return false if res is empty
            }

        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Creates the 'USER' table of the SQL database, if it does not already exist.
     */
    private void createUserTable() {
        try (Connection con = pool.getConnection();
             PreparedStatement createTable = con.prepareStatement(tableSQL)) {

            createTable.executeUpdate();
            con.commit();

        } catch (SQLException e) {
            if (e.getSQLState().equals("X0Y32")) {  //error state XOY32 is when a table that already exists
                System.out.println("Users table already exists.");
            } else {
                e.printStackTrace();
            }
        }
    }

    /**
     * Connection pool implementation by Apache Commons DBCP
     */
    private class ConnectionPool {

        private BasicDataSource dataSource = new BasicDataSource();

        /**
         * Constructs a new ConnectionPool
         */
        private ConnectionPool() {
            dataSource.setUrl("jdbc:derby:UserDatabase;create=true");

            //insert more configuration parameters later to optimize...
        }

        /**
         * @return a Connection from the pool
         * @throws SQLException if a database access error occurs
         */
        private Connection getConnection() throws SQLException {
            return dataSource.getConnection();
        }
    }

}
