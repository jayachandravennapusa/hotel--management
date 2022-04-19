package sqlacess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class MySqlAcess {
	private static Connection connection=null;
	private static Statement statement=null;
	static {
		try {
		connection=DriverManager.getConnection("jdbc:mysql://localhost/receptionproject","root","chandradeep76");
		statement=connection.createStatement();
		}catch(Exception e) {
			e.printStackTrace();
		}
		}
	  public static Connection getConnection()
	    {
	        return connection;
	    }
	  public static Statement getStatement() {
		  return statement;
	  }
}
