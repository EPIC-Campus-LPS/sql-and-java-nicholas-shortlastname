package src;

import java.sql.*;
import java.util.LinkedList;

public class MySQLAccess {
    Statement statement;
    public MySQLAccess(String url, String username, String password) throws SQLException {
        System.out.println("Connecting database ...");
        Connection connection = DriverManager.getConnection(url, username, password);
        System.out.println("Database connected!");
        this.statement = connection.createStatement();
    }
    public int getRowAmount(String table) throws SQLException {
        ResultSet resultSet = statement.executeQuery("select count(*) from " + table + ";");
        while (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return -1;
    }
    public int getColumnAmount(String table) throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from " + table + ";");
        ResultSetMetaData metadata = resultSet.getMetaData();
        return metadata.getColumnCount();
    }
    public void createRow(String table, LinkedList valueList) throws SQLException {
        String values = "";
        int columns = getColumnAmount(table);
        while(valueList.size() <= columns){
            valueList.add(null);
        }
        for (int i = 0; i < columns-1; i++) {
            values += valueList.get(i) + ", ";
        }
        values += valueList.get(columns-1);
        statement.execute("insert into " + table + " values(" + values + ");");
    }
    public void deleteRow(String table, Integer index) throws SQLException {
        ResultSet forMetadata = statement.executeQuery("select * from " + table + ";");
        ResultSetMetaData metadata = forMetadata.getMetaData();
        String indexColumn = metadata.getColumnName(1);
        statement.execute("delete from  " + table + " where " + indexColumn + "=" + index + ";");
    }
    public void changeRow(String table, LinkedList columns, LinkedList values, LinkedList index) throws SQLException {
        ResultSet forMetadata = statement.executeQuery("select * from " + table + ";");
        ResultSetMetaData metadata = forMetadata.getMetaData();
        String indexColumn = metadata.getColumnName(1);

        while(columns.size() >= values.size()){
            values.add(null);
        }
        String setValues = "";
        for (int i = 0; i < columns.size()-1; i++) {
            setValues += columns.get(i) + "=" + values.get(i) + ",";
        }
        setValues += columns.get(columns.size()-1) + "=" + values.get(columns.size()-1);

        statement.execute("update  " + table + " set " + setValues + " where " + indexColumn + "=" + index + ";");
    }
    public void closeStatement() throws SQLException {
        statement.close();
    }
}
