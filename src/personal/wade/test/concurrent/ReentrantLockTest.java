/**
* Copy Right @ EA.Corp   
* @Title: ReentrantLockTest.java 
* @Package personal.wade.test.concurrent 
* @Description:  
* @author Wade.wuchao
* @date 2016年6月2日
*/
package personal.wade.test.concurrent;

import java.util.concurrent.locks.ReentrantLock;

/** 
* @ClassName: ReentrantLockTest 
* @Description: 
* @author Wade.wuchao
* @date 2016年6月2日 
*  
*/
public class ReentrantLockTest {
	  
	    public static void main(String[] args) {  
	        Long t_start = System.currentTimeMillis();  
	  
	        User_lock u = new User_lock("张三", 100);  
	        Thread_lock t1 = new Thread_lock("线程A", u, 20);  
	        Thread_lock t2 = new Thread_lock("线程B", u, -60);  
	        Thread_lock t3 = new Thread_lock("线程C", u, -80);  
	        Thread_lock t4 = new Thread_lock("线程D", u, -30);  
	        Thread_lock t5 = new Thread_lock("线程E", u, 100);  
	        Thread_lock t6 = new Thread_lock("线程F", u, 50);  
	  
	        t1.start();  
	        t2.start();  
	        t3.start();  
	        t4.start();  
	        t5.start();  
	        t6.start();  
	  
	        /** 
	         * 以下代码用于计算时间，当然，它本身的运行也会需要一点点时间，但与分析运行效率无影响 
	         */  
	        boolean flag = true;  
	        while (flag) {  
	            if (Thread_lock.activeCount() == 1) {  
	                Long t_end = System.currentTimeMillis();  
	                System.out.println("当前时间：" + (t_end - t_start));  
	                flag = false;  
	            }  
	        }  
	    }  
	}  


	class Thread_lock extends Thread {  
	    private User_lock u;  
	    private int y = 0;  
	  
	    Thread_lock(String name, User_lock u, int y) {  
	        super(name); // 线程的名称  
	        this.u = u;  
	        this.y = y;  
	    }  
	  
	    public void run() {  
	        u.operBySyncMethod(y);  
//	        u.operBySyncObj(y);
//	        u.operByReetrantLock(y);
	    }  
	}  
	  
	class User_lock {  
	    private String code;  
	    private Integer cash;  
	    private ReentrantLock myLock1 = new ReentrantLock();  
	    private ReentrantLock myLock2 = new ReentrantLock();  
	  
	    User_lock(String code, int cash) {  
	        this.code = code;  
	        this.cash = cash;  
	    }  
	  
	    public String getCode() {  
	        return code;  
	    }  
	  
	    public void setCode(String code) {  
	        this.code = code;  
	    }  
	  
	    /** 
	     * 业务方法 
	     *  
	     * @param x 
	     *            添加x万元 
	     */  
	    public synchronized void operBySyncMethod(int x) {  
	        try{  
	            Thread.sleep(100);// 作用：增加运行时间  
	            this.cash += x;  
	            System.out.println(Thread.currentThread().getName() + "  运行结束With Sync Method，增加“"  
	                    + x + "”，当前用户信息：" + toString());  
	        } catch (InterruptedException e) {  
	            e.printStackTrace();  
	        }  
	  
	        try{  
	            Thread.sleep(100);// 作用：增加运行时间  
	            this.code = "张三(2)";  
	        } catch (InterruptedException e) {  
	            e.printStackTrace();  
	        }  
	  
	    }  
	 
	    /** 
	     * 业务方法 
	     *  
	     * @param x 
	     *            添加x万元 
	     */  
	    public void operBySyncObj(int x) {  
	    	 synchronized(cash){ 
		        try{  
		            Thread.sleep(100);// 作用：增加运行时间  
		            this.cash += x;  
		            System.out.println(Thread.currentThread().getName() + "  运行结束With Sync Object，增加“"  
		                    + x + "”，当前用户信息：" + toString());  
		        } catch (InterruptedException e) {  
		            e.printStackTrace();  
		        }  
	    	 }
	        synchronized(code){ 
		        try{  
		            Thread.sleep(100);// 作用：增加运行时间  
		            this.code = "张三(2)";  
		        } catch (InterruptedException e) {  
		            e.printStackTrace();  
		        }  
	        }
	    }  

	    /** 
	     * 业务方法 
	     *  
	     * @param x 
	     *            添加x万元 
	     */  
	    public  void operByReetrantLock(int x) {  
	        myLock1.lock();  
	        try{  
	            Thread.sleep(100);// 作用：增加运行时间  
	            this.cash += x;  
	            System.out.println(Thread.currentThread().getName() + "  运行结束With ReetrantLock，增加“"  
	                    + x + "”，当前用户信息：" + toString());  
	        } catch (InterruptedException e) {  
	            e.printStackTrace();  
	        } finally {  
	            myLock1.unlock();  
	        }  
	          
	        myLock2.lock();  
	        try{  
	            Thread.sleep(100);// 作用：增加运行时间  
	            this.code = "张三(2)";  
	        } catch (InterruptedException e) {  
	            e.printStackTrace();  
	        } finally {  
	            myLock2.unlock();  
	        }  
	  
	    }  
	    
	    @Override  
	    public String toString() {  
	        return "User{" + "code='" + code + '\'' + ", cash=" + cash + '}';  
	    }  
}
