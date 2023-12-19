import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnector {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/music_store";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Whitesox14";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    public static void executeUpdate(String sql, String tableName) throws Exception {
      try (Connection connection = getConnection();
           Statement statement = connection.createStatement()) {
  
          System.out.println("Executing SQL statement for table '" + tableName + "'...");

          DatabaseMetaData metaData = connection.getMetaData();
          ResultSet tables = metaData.getTables(null, null, tableName, null);
          if (!tables.next()) {
              statement.executeUpdate(sql);
              System.out.println("Table '" + tableName + "' created successfully.");
          } else {
              System.out.println("Table '" + tableName + "' already exists.");
          }
  
      } catch (SQLException e) {
          throw new Exception(e.getMessage());
      }
  }
  public static boolean tableExists(String tableName) throws SQLException {
    Connection connection = null;
    try {
        connection = getConnection();
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet tables = metaData.getTables(null, null, tableName, null);

        return tables.next(); 
    } finally {
        if (connection != null) {
            connection.close();
        }
    }
}

    public static ResultSet executeQuery(String sql) throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        return statement.executeQuery(sql);
    }
}