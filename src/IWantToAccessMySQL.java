package src;

import java.sql.SQLException;
import java.util.LinkedList;

public class IWantToAccessMySQL {
    public static void main(String[] args) throws SQLException {
        MySQLAccess mySQLAccess = new MySQLAccess("jdbc:mysql://localhost:3306/klaggle_tables", "java", "password");
        String table = "btd6_rounds";


        System.out.println(mySQLAccess.getRowAmount("btd6_rounds"));

        mySQLAccess.deleteRow(table, 141);

        //LinkedList btdStuff = new LinkedList<>();
        //btdStuff.add(141);
        //mySQLAccess.createRow(table, btdStuff);


        mySQLAccess.closeStatement();
    }
}
