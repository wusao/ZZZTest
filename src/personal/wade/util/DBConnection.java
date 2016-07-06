/**
* Copy Right @ EA.Corp   
* @Title: DBConnection.java 
* @Package personal.wade.util 
* @Description:  
* @author Wade.wuchao
* @date 2016年5月31日
*/
package personal.wade.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.alibaba.druid.pool.DruidDataSource;

/** 
* @ClassName: DBConnection 
* @Description: 
* @author Wade.wuchao
* @date 2016年5月31日 
*  
*/
public class DBConnection {

	/** 
	 * <p><b>ParamContent</b></p> 
	 * @param args 
	 * void 
	  */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Connection conn = getConnection();
			
			if(null != conn && !conn.isClosed())
				System.out.println("Succeeded connecting to the Database!");
			else {
				return;
			}
			// 要执行的SQL语句
			PreparedStatement ps = conn.prepareStatement("select versionNum, countNum from counter");
			ResultSet rs = ps.executeQuery();
			rs.next();
			System.out.println(rs.getInt("versionNum"));
			
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static Connection getSimpleConnection()
	{
		// 连续数据库
		try {
			Connection conn;
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/test", "root", "root");
			return conn;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public static Connection getConnection()
	{
		
		DruidDataSource ds = ConnectionPoolManager.getDataSource();
		try {
			Connection conn = ds.getConnection();
			return conn;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
}
