package src;

import java.sql.SQLException;

public class IWantToAccessMySQL {
    public static void main(String[] args) throws SQLException {
        SimpleMySQLAccess mySQLAccess = new SimpleMySQLAccess("jdbc:mysql://localhost:3306/klaggle_tables", "java", "password");
        String table = "btd6_rounds";

        System.out.println(mySQLAccess.returnStatement("select * from btd6_rounds;", table));


        mySQLAccess.closeStatement();
    }
}
