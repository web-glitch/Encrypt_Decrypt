import java.sql.*;
public class DAO {
	
		public static Connection connect()
		{
			Connection conn=null;
			try
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","aec","1234");
			
		}catch(ClassNotFoundException e)
		{
			e.printStackTrace();
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			
			
	}
		return conn;

}
}
