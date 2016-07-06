/**
* Copy Right @ EA.Corp   
* @Title: VersionNumHighConcurrenceServiceImpl.java 
* @Package personal.wade.test.versionlock 
* @Description:  
* @author Wade.wuchao
* @date 2016年5月31日
*/
package personal.wade.test.versionlock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import personal.wade.util.DBConnection;

/** 
* @ClassName: VersionNumHighConcurrenceServiceImpl 
* @Description: 
* @author Wade.wuchao
* @date 2016年5月31日 
*  
*/
public class VersionNumHighConcurrenceServiceImpl implements HighConcurrenceService {

	/**
	 * Title: updHighConcurrenceRecord 
	 * @see personal.wade.test.versionlock.HighConcurrenceService#updHighConcurrenceRecord() 
	  */
	@Override
	public void updHighConcurrenceRecord(int times) {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			conn = DBConnection.getConnection();
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			conn.setAutoCommit(true);
			
			ps = conn.prepareStatement("select versionNum, countNum from counter");
			rs = ps.executeQuery();
			rs.next();
			Integer versionNum = rs.getInt(1);
			Integer countNum = rs.getInt(2);
			
//			System.out.println("versionNum:" + versionNum);
//			System.out.println("countNum:" + countNum);
			Integer newVersionNum = versionNum + 1;
			countNum = countNum + 2;
			
			ps = conn.prepareStatement("update counter set versionNum = " + newVersionNum + ", countNum = "+ countNum+", lastUpdateTime=CURRENT_TIMESTAMP where versionNum="+versionNum);
			Integer result = ps.executeUpdate();
			int i = 1;
			while(result != 1)
			{
				System.out.println("VersionNum: Thread:"+ Thread.currentThread().getId() + " Times " + times + " update record fail, try times " + i );
				i++;
				
//				Thread.sleep(100);
				
				ps = conn.prepareStatement("select versionNum, countNum from counter");
				rs = ps.executeQuery();
				if(rs.next())
				{
				versionNum = rs.getInt(1);
				countNum = rs.getInt(2);
				}else
					continue;
//				System.out.println("versionNum:" + versionNum);
//				System.out.println("countNum:" + countNum);
				newVersionNum = versionNum + 1;
				countNum = countNum + 2;
				
				ps = conn.prepareStatement("update counter set versionNum = " + newVersionNum + ", countNum = "+ countNum+", lastUpdateTime=CURRENT_TIMESTAMP where versionNum="+versionNum);
				result = ps.executeUpdate();
			}
			
//			System.out.println("VersionNum: Thread:"+ Thread.currentThread().getId() + " Times " + times + " update record success, try times total " + i );
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
			try {
				rs.close();
				ps.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
//			System.out.println(System.currentTimeMillis());
		}
//		catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
	}

}
