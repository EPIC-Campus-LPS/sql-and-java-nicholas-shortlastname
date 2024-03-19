package src;

import java.sql.SQLException;
import java.util.LinkedList;

public class IWantToAccessMySQL {
    public static void main(String[] args) throws SQLException {
        MySQLAccess mySQLAccess = new MySQLAccess("jdbc:mysql://localhost:3306/klaggle_tables", "java", "password");
        String table = "btd6_rounds";


        System.out.println(mySQLAccess.getColumnAmount(table));
        System.out.println(mySQLAccess.getRowAmount(table));
        System.out.println(mySQLAccess.getCellIndex(table, "Red", 20));

        LinkedList testLinkedList = new LinkedList<>();
        testLinkedList.add(200); testLinkedList.add(420420); testLinkedList.add(123456789);

        mySQLAccess.newRow(table, testLinkedList);
        mySQLAccess.newNullRow(table);

        LinkedList columnLinkedList = new LinkedList<>();
        columnLinkedList.add("RBE"); columnLinkedList.add("Round"); columnLinkedList.add("Red");

        mySQLAccess.newColumnValues(table, columnLinkedList, testLinkedList);

        mySQLAccess.deleteCell(table, columnLinkedList, testLinkedList);
        mySQLAccess.newRow(table, testLinkedList);
        mySQLAccess.deleteRow(table, 250);

        LinkedList secondLinkedListValues = new LinkedList<>();
        testLinkedList.add(300); testLinkedList.add(987654321);

        LinkedList thirdLinkedListValues = new LinkedList<>();
        thirdLinkedListValues.add(987654321);
        LinkedList thirdLinkedListColumns = new LinkedList<>();
        thirdLinkedListColumns.add("RBE");

        mySQLAccess.newRow(table, secondLinkedListValues);
        mySQLAccess.deleteCell(table, thirdLinkedListColumns, thirdLinkedListValues);
        mySQLAccess.deleteRow(table, 300);

        LinkedList fourthLinkedListValues = new LinkedList<>();
        fourthLinkedListValues.add(1111111111);

        mySQLAccess.newRow(table, secondLinkedListValues);
        mySQLAccess.changeRowValues(table, thirdLinkedListColumns, fourthLinkedListValues, 300);

        mySQLAccess.changeRow(table, 6, 300);


        mySQLAccess.closeStatement();
    }
}
