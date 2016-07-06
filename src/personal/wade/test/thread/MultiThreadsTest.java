/**
* Copy Right @ EA.Corp   
* @Title: MultiThreadsTest.java 
* @Package personal.wade.test.thread 
* @Description:  
* @author Wade.wuchao
* @date 2016年1月18日
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
* @date 2016年1月18日 
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
        
             // 构造Runner   
        TestRunnable runner1 = new TestRunnable() {   
            @Override   
            public void runTest() throws Throwable {   
                Date now = new Date();  
                test.opAccountMoney(user1 , 1000 , df.format(now));  
            }   
        };   
          
        // 构造Runner   
        TestRunnable runner2 = new TestRunnable() {   
            @Override   
            public void runTest() throws Throwable {   
                Date now = new Date();  
                test.opAccountMoney(user2 , 1000 , df.format(now));  
            }   
        };   
      
          
        int runnerCount = 30;   
        //Rnner数组，想当于并发多少个。   
        TestRunnable[] trs = new TestRunnable[runnerCount];   
          
        int i = 0;  
        for (; i < runnerCount; i++) {  
            if(i%2 == 0){  
                trs[i] = runner1;   
            }else{  
                trs[i] = runner2;   
            }  
        }   
        // 用于执行多线程测试用例的Runner，将前面定义的单个Runner组成的数组传入   
        MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(trs);   
        try {   
            // 开发并发执行数组里定义的内容   
            long start = (new Date()).getTime();  
            mttr.runTestRunnables();  
            System.out.println("花费时间:" + ((new Date()).getTime() - start) + " 毫秒");  
              
        } catch (Throwable e) {   
            e.printStackTrace();   
        }   
    }  
       
}  