package src;

import java.sql.*;
import java.util.LinkedList;

/**
 * Class to do certain actions in a MySQL database
 */
public class MySQLAccess {
    Statement statement;
    public MySQLAccess(String url, String username, String password) throws SQLException {
        System.out.println("Connecting database ...");
        Connection connection = DriverManager.getConnection(url, username, password);
        System.out.println("Database connected!");
        this.statement = connection.createStatement();
    }

    /**
     * Gets how many rows there are
     * @param table Which table to use
     * @return The amount of rows the table has, or returns -1 if invalid
     * @throws SQLException if a MySQl error occurs
     */
    public int getRowAmount(String table) throws SQLException { //select
        ResultSet resultSet = statement.executeQuery("select count(*) from " + table + ";");
        while (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return -1;
    }

    /**
     * Gets how many columns there are
     * @param table Which table to use
     * @return The amount of columns a table has
     * @throws SQLException if a MySQl error occurs
     */
    public int getColumnAmount(String table) throws SQLException { //select
        ResultSet resultSet = statement.executeQuery("select * from " + table + ";");
        ResultSetMetaData metadata = resultSet.getMetaData();
        return metadata.getColumnCount();
    }

    /**
     * Gets the content of the cell containing index in column
     * @param table Which table to use
     * @param column Which column to use
     * @param index Which index in the column to search for
     * @return The contents of the cell at index in column
     * @throws SQLException if a MySQl error occurs
     */
    public String doesColumnExist(String table, String column) throws SQLException { //select
        ResultSet resultSet = statement.executeQuery("select " + column + " from " + table + ";");
        return resultSet.getString(1);
    }

    /**
     * Makes a new row with given values
     * If there are fewer values than columns in the table, then the rest of the columns will be filled with null values
     * @param table Which table to use
     * @param valueList Given values to make row with
     * @throws SQLException if a MySQl error occurs
     */
    public void newRow(String table, LinkedList valueList) throws SQLException { //insert into
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

    /**
     * Makes a new row with all null values
     * @param table Which table to use
     * @throws SQLException if a MySQl error occurs
     */
    public void newNullRow(String table) throws SQLException { //insert into
        int rows = getRowAmount(table);
        ResultSet forMetadata = statement.executeQuery("select * from " + table + ";");
        ResultSetMetaData metadata = forMetadata.getMetaData();
        String indexColumn = metadata.getColumnName(1);

        statement.execute("insert into " + table + " (" + indexColumn + ") values (" + (rows+1) + ");");
    }

    /**
     * Adds values to columns
     * If more columns than values are give, the remaining columns will be filled with null values
     * @param table Which table to use
     * @param columns Which columns to add to
     * @param values Which values to add to their corresponding columns
     * @throws SQLException if a MySQl error occurs
     */
    public void newColumnValues(String table, LinkedList columns, LinkedList values) throws SQLException { //insert into
        while(columns.size() >= values.size()){
            values.add(null);
        }

        String valueStr = "";
        for (int i = 0; i < values.size()-1; i++) {
            valueStr += values.get(i) + ", ";
        }
        valueStr += values.get(values.size()-1);

        String columnStr = "";
        for (int i = 0; i < columns.size()-1; i++) {
            columnStr += columns.get(i) + ", ";
        }
        columnStr += columns.get(columns.size()-1);

        statement.execute("insert into " + table + " (" + columnStr + ") values (" + valueStr + ");");
    }

    /**
     * Deletes a cell's contents
     * @param table Which table to use
     * @param columns which columns to check for conditions
     * @param conditionValues which values will be checked against the column values
     * @throws SQLException if a MySQl error occurs
     */
    public void deleteCell(String table, LinkedList columns, LinkedList conditionValues) throws SQLException { //delete

        while(columns.size() >= conditionValues.size()){
            conditionValues.add(null);
        }
        String conditions = "";
        for (int i = 0; i < columns.size()-1; i++) {
            conditions += columns.get(i) + "=" + conditionValues.get(i) + " and ";
        }
        conditions += columns.get(columns.size()-1) + "=" + conditionValues.get(columns.size()-1);

        statement.execute("delete from " + table + " where " + conditions + ";");
    }

    /**
     * Deletes a row's contents
     * @param table Which table to use
     * @param index The row's index where all the contents will be deleted
     * @throws SQLException if a MySQL error occurs
     */
    public void deleteRow(String table, int index) throws SQLException { //delete
        ResultSet forMetadata = statement.executeQuery("select * from " + table + ";");
        ResultSetMetaData metadata = forMetadata.getMetaData();
        String indexColumn = metadata.getColumnName(1);
        statement.execute("delete from " + table + " where " + indexColumn + "=" + index + ";");
    }

    /**
     * Deletes a table
     * @param table Which table to use
     * @throws SQLException if a MySQL error occurs
     */
    public void deleteAll(String table) throws SQLException { //delete
        statement.execute("delete from " + table + ";");
    }

    /**
     * Changes a certain row's values in certain columns
     * @param table Which table to use
     * @param columns Which columns to affect
     * @param values Which values to change to
     * @param index Which row index should be affected
     * @throws SQLException if a MySQL error occurs
     */
    public void changeRowValues(String table, LinkedList columns, LinkedList values, int index) throws SQLException { //update
        ResultSet forMetadata = statement.executeQuery("select * from " + table + ";");
        ResultSetMetaData metadata = forMetadata.getMetaData();
        String indexColumn = metadata.getColumnName(1);

        while(columns.size() >= values.size()){
            values.add(null);
        }
        String setValues = "";
        for (int i = 0; i < columns.size()-1; i++) {
            setValues += columns.get(i) + "=" + values.get(i) + ", ";
        }
        setValues += columns.get(columns.size()-1) + "=" + values.get(columns.size()-1);

        statement.execute("update " + table + " set " + setValues + " where " + indexColumn + "=" + index + ";");
    }

    /**
     * Changes a row to a single value
     * @param table Which table to use
     * @param value Which value to set everything to
     * @param index Which row to change
     * @throws SQLException if a MySQL error occurs
     */
    public void changeRow(String table, int value, int index) throws SQLException { //update
        ResultSet forMetadata = statement.executeQuery("select * from " + table + ";");
        ResultSetMetaData metadata = forMetadata.getMetaData();
        String indexColumn = metadata.getColumnName(1);

        statement.execute("update " + table + " set " + value + " where " + indexColumn + "=" + index + ";");
    }

    /**
     * Changes every value in a table to a value
     * @param table Which table to use
     * @param value Which value to set everything to
     * @throws SQLException if a MySQL error occurs
     */
    public void changeAll(String table, int value) throws SQLException { //update
        statement.execute("update " + table + " set " + value + ";");
    }

    /**
     * Closes the statement to save resources
     * @throws SQLException if a MySQL error occurs
     */
    public void closeStatement() throws SQLException {
        statement.close();
    }
}
