package main;

import java.sql.*;

public class DBManager {
    public DBManager() {
        connectToDB();
    }

    private static String url = MyUtils.lodProperty("dbpath");
    private Connection connect;

    /**
     * Get connection to database
     */
    private void connectToDB() {
        try {
            connect = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Create Table "articles" query
     */
    public void createArticlesTable() {
        // SQLite connection string

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS articles (\n"
                + "	id integer PRIMARY KEY AUTOINCREMENT NOT NULL,\n"
                + "	name text NOT NULL\n"
                + ");";

        createTableQueryExecute(sql);
    }

    /**
     * Insert data to "articles" table
     *
     * @param name
     */
    public void insertArticleToDB(String name) {
        String sql = "INSERT INTO articles (name) VALUES(?)";
        insertDataQueryExecute(sql, name);

    }

    /**
     * Returns duplicate state of values in the DB
     * returns true if data already exist
     *
     * @param name
     * @return boolean
     * @throws SQLException
     */
    private boolean isExistedValue(String name) {
        String sql = "SELECT name FROM articles " +
                "WHERE name='" + name + "'";

        try (Statement stmt = connect.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next())
                return true;
            // loop through the result set
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Create table execution request
     *
     * @param sql
     * @throws SQLException
     */
    private void createTableQueryExecute(String sql) {
        try (Statement stmt = connect.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Insert data with checking for duplicate
     *
     * @param sql
     * @param params
     * @throws SQLException
     */
    private void insertDataQueryExecute(String sql, String... params) {
        try (PreparedStatement pstmt = connect.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                if (isExistedValue(params[i])) {
                    System.out.println("This page is already exists in DB!");
                    return;
                }
                pstmt.setString(i + 1, params[i]);
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
