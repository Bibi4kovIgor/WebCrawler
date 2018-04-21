package main;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLiteJDBC {

    private static String url = MyUtils.lodProperty("dbpath");
    private static String jdbcName = MyUtils.lodProperty("jdbc");

    /**
     * Connect to DataBase
     */
    public static void connectToDB() {
        Connection c = null;

        try {
            Class.forName(jdbcName);
            c = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }
}
