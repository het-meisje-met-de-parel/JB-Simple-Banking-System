package banking;

import java.sql.*;

public class DBConnector {

    public static void execute(
        String dbname, String sql,
        ThrowingConsumer <ResultSet, SQLException> onResult
    ) {
        String url = "jdbc:sqlite:" + dbname;
        try (Connection conn = DriverManager.getConnection(url)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            if (onResult != null) {
                try {
                    onResult.accept(statement.executeQuery());
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
            } else {
                statement.execute();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}

interface ThrowingConsumer <T, E extends Exception> {
    void accept (T object) throws E;
}
