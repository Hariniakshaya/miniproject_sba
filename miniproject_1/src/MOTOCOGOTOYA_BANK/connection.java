package MOTOCOGOTOYA_BANK;
import java.sql.Connection;
import java.sql.DriverManager;

public class connection {
	static Connection con; // Global Connection Object
    public static Connection getConnection()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/motocogotoya","root","Harini@3");       
            }
        catch (Exception e) {
            System.out.println("Connection Failed!");
        }
 
        return con;

}
}
