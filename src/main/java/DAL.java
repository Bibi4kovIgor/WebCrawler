import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DAL {
    public static void createNewTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:/home/igor/";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS articles (\n"
                + "	id integer PRIMARY KEY AUTO INCREMENT,\n"
                + "	name text NOT NULL,\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
