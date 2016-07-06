/**
* Copy Right @ EA.Corp   
* @Title: Entrance.java 
* @Package personal.wade.test.versionlock 
* @Description:  
* @author Wade.wuchao
* @date 2016年5月31日
*/
package personal.wade.test.versionlock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;

import personal.wade.util.DBConnection;

/** 
* @ClassName: Entrance 
* @Description: 
* @author Wade.wuchao
* @date 2016年5月31日 
*  
*/
public class Entrance {

	static CountDownLatch latch = new CountDownLatch(100);
	
	/** 
	 * <p><b>ParamContent</b></p> 
	 * @param args 
	 * void 
	  */
	public static void main(String[] args) {
		
		initData();
		
		System.out.println(System.currentTimeMillis());
		
		HighConcurrenceService serviceImpl = new TransactionalHighConcurrenceServiceImpl();
//		HighConcurrenceService serviceImpl = new VersionNumHighConcurrenceServiceImpl();
		
		for(int i=0; i<10000; i++)
		{
			new Thread(
					new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							for(int j = 0; j< 10; j++)
							{
								serviceImpl.updHighConcurrenceRecord(j);
							}
						 latch.countDown();		
						}
					}
					).start();
		}
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("end ....");
		
	}

	private static void initData()
	{
		PreparedStatement ps = null;
		Connection conn = null;
		// TODO Auto-generated method stub
		try {
			conn = DBConnection.getConnection();
			
			if(null != conn && !conn.isClosed())
				System.out.println("Succeeded connecting to the Database!");
			else {
				return;
			}
			// 要执行的SQL语句
			String sql = "delete from counter";
			// statement用来执行SQL语句
			ps = conn.prepareStatement(sql);
			Integer result = ps.executeUpdate();
			
			// 要执行的SQL语句
			sql = "insert into counter(versionNum, countNum) value(1, 1)";
			// statement用来执行SQL语句
			ps = conn.prepareStatement(sql);
			result = ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
				try {
					if(null != ps)
						ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					if(null != conn)
						conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
}
