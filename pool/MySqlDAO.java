package pool;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by Administrator on 2019/10/25 0025.
 */
public class MySqlDAO {
    public static Connection getConnection()throws Exception{
        String driverName = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/teaching";
        String userName = "root";
        String password = "";
        Class.forName(driverName);
        Connection con = DriverManager.getConnection(url, userName, password);
        return con;
    }
}
