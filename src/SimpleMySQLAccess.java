package src;

import java.sql.*;
import java.util.LinkedList;

public class SimpleMySQLAccess {
    private Statement statement;
    public SimpleMySQLAccess(String url, String username, String password) throws SQLException {
        System.out.println("Connecting database ...");
        Connection connection = DriverManager.getConnection(url, username, password);
        System.out.println("Database connected!");
        this.statement = connection.createStatement();
    }

    /**
     * Executes the given SQL statement without returning the result
     * @param str SQL statement
     * @throws SQLException if a SQL error occurs
     */
    public void executeStatement(String str) throws SQLException {
        statement.execute(str);
    }

    /**
     * Executes the given SQL statement and returns the result
     * @param str SQL statement
     * @return result of SQL statement
     * @throws SQLException if a SQL error occurs
     */
    public LinkedList returnStatement(String str, String table) throws SQLException {
        ResultSet metatdataSet = statement.executeQuery("select * from " + table + ";");
        ResultSetMetaData metadata = metatdataSet.getMetaData();

        ResultSet resultSet = statement.executeQuery(str);


        LinkedList list = new LinkedList();
        for (int i = 1; i < metadata.getColumnCount(); i++) {
            while (resultSet.next()) {
                list.add(resultSet.getString(i));
            }
        }
        return list;
    }
    /**
     * Closes the statement to save resources
     * @throws SQLException if a MySQL error occurs
     */
    public void closeStatement() throws SQLException {
        statement.close();
    }
}
