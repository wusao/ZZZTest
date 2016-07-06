/**
* Copy Right @ EA.Corp   
* @Title: TransactionalHighConcurrenceServiceImpl.java 
* @Package personal.wade.test.versionlock 
* @Description:  
* @author Wade.wuchao
* @date 2016年5月31日
*/
package personal.wade.test.versionlock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import personal.wade.util.DBConnection;

/** 
* @ClassName: TransactionalHighConcurrenceServiceImpl 
* @Description: 
* @author Wade.wuchao
* @date 2016年5月31日 
*  
*/
public class TransactionalHighConcurrenceServiceImpl implements HighConcurrenceService {

	/**
	 * Title: updHighConcurrenceRecord 
	 * @see personal.wade.test.versionlock.HighConcurrenceService#updHighConcurrenceRecord() 
	  */
	@Override
	public  void updHighConcurrenceRecord(int times) {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			conn = DBConnection.getConnection();
//			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			conn.setAutoCommit(false);
			
			ps = conn.prepareStatement("select versionNum, countNum from counter for update");
			rs = ps.executeQuery();
			rs.next();
			Integer versionNum = rs.getInt(1);
			Integer countNum = rs.getInt(2);
			
//			System.out.println("versionNum:" + versionNum);
//			System.out.println("countNum:" + countNum);
			versionNum = versionNum + 1;
			countNum = countNum + 2;
			ps = conn.prepareStatement("update counter set versionNum = " +versionNum+ ", countNum = "+ countNum+", lastUpdateTime=CURRENT_TIMESTAMP");
			Integer result = ps.executeUpdate();
//			if(result != 1)
////				System.out.println("Transactional: Thread:"+ Thread.currentThread().getId() + " Times " + times + " update record fail");
//			else {
////				System.out.println("Transactional: Thread:"+ Thread.currentThread().getId() + " Times " + times + " update record success");
//			}
			conn.commit();
			
//			rs.close();
//			ps.close();
//			conn.close();
			
			System.out.println(System.currentTimeMillis());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			
			try {
				if(null != rs)
					rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
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
