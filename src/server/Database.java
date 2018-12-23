package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Database that stores the username and passwords of registered users.
 * For simplicity, it supports only two operations: registering a new user
 * and authenticating a user.
 *
 * @author Alston
 * last updated 12/22/2018
 */
class Database {

    private static Database DEFAULT_INSTANCE = new Database();

    /**
     * Returns the Singleton instance of the database.
     *
     * @return the database
     */
    static Database getInstance() {
        return DEFAULT_INSTANCE;
    }

    //Resources stored as private fields so they can be closed at the very end
    private Connection con;
    private PreparedStatement createTable;
    private PreparedStatement insert;
    private PreparedStatement authenticate;
    private ResultSet res;

    /**
     * Constructs a Database. As the Database is implemented with Apache Derby, the constructor
     * connects to the SQL database and creates the prepared statements. If an error occurs in trying
     * to do so, any opened resources are first closed, and then the program is killed.
     */
    private Database() {
        try {
            Properties props = new Properties();
            con = DriverManager.getConnection("jdbc:derby:UserDatabase;create=true", props);

            //create tables if they don't exist
            createTable = con.prepareStatement(
                    "create table users(username varchar(20) not null unique, password varchar(20) not null)");
            createUserTable();

            //make the prepared statements
            insert = con.prepareStatement("insert into users (username, password) values (?, ?)");
            authenticate = con.prepareStatement("select * from users where username=? and password=?");

            con.commit();

        } catch (SQLException e) { //an error has occurred so resources are closed and the program is killed
            close();
            e.printStackTrace();
            System.exit(1);
        }
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
        try {
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
        try {
            authenticate.setString(1, username);
            authenticate.setString(2, password);

            res = authenticate.executeQuery();
            return res.next(); //next() will return false if res is empty

        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Closes all opened resources, including the prepared statements,
     * result sets, and the connection to the database.
     */
    void close() {
        ServerUtils.close(createTable);
        ServerUtils.close(insert);
        ServerUtils.close(authenticate);
        ServerUtils.close(res);
        ServerUtils.close(con);
    }

    /**
     * Creates the 'USER' table of the SQL database, if it does not already exist.
     */
    private void createUserTable() {
        try {
            createTable.executeUpdate();

        } catch (SQLException e) {
            if (e.getSQLState().equals("X0Y32")) {  //error state XOY32 is when a table that already exists
                System.out.println("Users table already exists.");
            } else {
                e.printStackTrace();
            }
        }
    }
}
