/**
* Copy Right @ EA.Corp   
* @Title: MultiThreadsTest.java 
* @Package personal.wade.test.thread 
* @Description:  
* @author Wade.wuchao
* @date 2016��1��18��
*/
package personal.wade.test.thread;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;  
import net.sourceforge.groboutils.junit.v1.TestRunnable;
import personal.wade.util.DataStore;

import org.junit.Test;  
/** 
* @ClassName: MultiThreadsTest 
* @Description: 
* @author Wade.wuchao
* @date 2016��1��18�� 
*  
*/
public class MultiThreadsTest {  
    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
      
    @Test  
    public void multiWSReqTest() throws Exception {  
          
        final String account1 = "zhangsan";  
        final String account2 = "lisi";  
        
        SynchronizedTest test = new SynchronizedTest();  
        
        DataStore store = DataStore.getStore();  
        final UserTest user1 = store.getUserFromStore(account1);  
        final UserTest user2 = store.getUserFromStore(account2); 
        
             // ����Runner   
        TestRunnable runner1 = new TestRunnable() {   
            @Override   
            public void runTest() throws Throwable {   
                Date now = new Date();  
                test.opAccountMoney(user1 , 1000 , df.format(now));  
            }   
        };   
          
        // ����Runner   
        TestRunnable runner2 = new TestRunnable() {   
            @Override   
            public void runTest() throws Throwable {   
                Date now = new Date();  
                test.opAccountMoney(user2 , 1000 , df.format(now));  
            }   
        };   
      
          
        int runnerCount = 30;   
        //Rnner���飬�뵱�ڲ������ٸ���   
        TestRunnable[] trs = new TestRunnable[runnerCount];   
          
        int i = 0;  
        for (; i < runnerCount; i++) {  
            if(i%2 == 0){  
                trs[i] = runner1;   
            }else{  
                trs[i] = runner2;   
            }  
        }   
        // ����ִ�ж��̲߳���������Runner����ǰ�涨��ĵ���Runner��ɵ����鴫��   
        MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(trs);   
        try {   
            // ��������ִ�������ﶨ�������   
            long start = (new Date()).getTime();  
            mttr.runTestRunnables();  
            System.out.println("����ʱ��:" + ((new Date()).getTime() - start) + " ����");  
              
        } catch (Throwable e) {   
            e.printStackTrace();   
        }   
    }  
       
}  