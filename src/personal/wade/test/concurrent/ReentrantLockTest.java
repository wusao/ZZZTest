/**
* Copy Right @ EA.Corp   
* @Title: ReentrantLockTest.java 
* @Package personal.wade.test.concurrent 
* @Description:  
* @author Wade.wuchao
* @date 2016��6��2��
*/
package personal.wade.test.concurrent;

import java.util.concurrent.locks.ReentrantLock;

/** 
* @ClassName: ReentrantLockTest 
* @Description: 
* @author Wade.wuchao
* @date 2016��6��2�� 
*  
*/
public class ReentrantLockTest {
	  
	    public static void main(String[] args) {  
	        Long t_start = System.currentTimeMillis();  
	  
	        User_lock u = new User_lock("����", 100);  
	        Thread_lock t1 = new Thread_lock("�߳�A", u, 20);  
	        Thread_lock t2 = new Thread_lock("�߳�B", u, -60);  
	        Thread_lock t3 = new Thread_lock("�߳�C", u, -80);  
	        Thread_lock t4 = new Thread_lock("�߳�D", u, -30);  
	        Thread_lock t5 = new Thread_lock("�߳�E", u, 100);  
	        Thread_lock t6 = new Thread_lock("�߳�F", u, 50);  
	  
	        t1.start();  
	        t2.start();  
	        t3.start();  
	        t4.start();  
	        t5.start();  
	        t6.start();  
	  
	        /** 
	         * ���´������ڼ���ʱ�䣬��Ȼ�������������Ҳ����Ҫһ���ʱ�䣬�����������Ч����Ӱ�� 
	         */  
	        boolean flag = true;  
	        while (flag) {  
	            if (Thread_lock.activeCount() == 1) {  
	                Long t_end = System.currentTimeMillis();  
	                System.out.println("��ǰʱ�䣺" + (t_end - t_start));  
	                flag = false;  
	            }  
	        }  
	    }  
	}  


	class Thread_lock extends Thread {  
	    private User_lock u;  
	    private int y = 0;  
	  
	    Thread_lock(String name, User_lock u, int y) {  
	        super(name); // �̵߳�����  
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
	     * ҵ�񷽷� 
	     *  
	     * @param x 
	     *            ���x��Ԫ 
	     */  
	    public synchronized void operBySyncMethod(int x) {  
	        try{  
	            Thread.sleep(100);// ���ã���������ʱ��  
	            this.cash += x;  
	            System.out.println(Thread.currentThread().getName() + "  ���н���With Sync Method�����ӡ�"  
	                    + x + "������ǰ�û���Ϣ��" + toString());  
	        } catch (InterruptedException e) {  
	            e.printStackTrace();  
	        }  
	  
	        try{  
	            Thread.sleep(100);// ���ã���������ʱ��  
	            this.code = "����(2)";  
	        } catch (InterruptedException e) {  
	            e.printStackTrace();  
	        }  
	  
	    }  
	 
	    /** 
	     * ҵ�񷽷� 
	     *  
	     * @param x 
	     *            ���x��Ԫ 
	     */  
	    public void operBySyncObj(int x) {  
	    	 synchronized(cash){ 
		        try{  
		            Thread.sleep(100);// ���ã���������ʱ��  
		            this.cash += x;  
		            System.out.println(Thread.currentThread().getName() + "  ���н���With Sync Object�����ӡ�"  
		                    + x + "������ǰ�û���Ϣ��" + toString());  
		        } catch (InterruptedException e) {  
		            e.printStackTrace();  
		        }  
	    	 }
	        synchronized(code){ 
		        try{  
		            Thread.sleep(100);// ���ã���������ʱ��  
		            this.code = "����(2)";  
		        } catch (InterruptedException e) {  
		            e.printStackTrace();  
		        }  
	        }
	    }  

	    /** 
	     * ҵ�񷽷� 
	     *  
	     * @param x 
	     *            ���x��Ԫ 
	     */  
	    public  void operByReetrantLock(int x) {  
	        myLock1.lock();  
	        try{  
	            Thread.sleep(100);// ���ã���������ʱ��  
	            this.cash += x;  
	            System.out.println(Thread.currentThread().getName() + "  ���н���With ReetrantLock�����ӡ�"  
	                    + x + "������ǰ�û���Ϣ��" + toString());  
	        } catch (InterruptedException e) {  
	            e.printStackTrace();  
	        } finally {  
	            myLock1.unlock();  
	        }  
	          
	        myLock2.lock();  
	        try{  
	            Thread.sleep(100);// ���ã���������ʱ��  
	            this.code = "����(2)";  
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
