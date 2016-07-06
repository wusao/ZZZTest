/**
* Copy Right @ EA.Corp   
* @Title: ThreadTest.java 
* @Package personal.wade.test 
* @Description:  
* @author Wade.wuchao
* @date 2015��11��9��
*/
package personal.wade.test;

import java.security.Timestamp;

/** 
* @ClassName: ThreadTest 
* @Description: 
* @author Wade.wuchao
* @date 2015��11��9�� 
*  
*/
public class ThreadTest {
	
	public static int times = 0;
	
	    public static void main(String[] args) {  
	        Runner1 runner1 = new Runner1();  
	        Runner2 runner2 = new Runner2();
	        
	        Runner1 runner1_syn = new Runner1();
	        //Thread(Runnable target) �����µ� Thread ����  
	        Thread thread1 = new Thread(runner1);  
	        Thread thread2 = new Thread(runner2);  
	        
	        Thread thread1_syn = new Thread(runner1_syn);
	        
	        thread1.start();  
//	        thread2.start();  
	        thread1_syn.start();
//	        thread1.run();  
//	        thread2.run();  
	    }  
	}  
	  
	class Runner1 implements Runnable { // ʵ����Runnable�ӿڣ�jdk��֪���������һ���߳�  
		
		public static int times = 0;

		
		public void run() {  
			
			synchronized(this){
		        if(times == 50)
		        {
		        	try {
		        		System.out.println("-----------Parse Thread 1--------------");  
						wait(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
			}
			
	        for (int i = 0; i < 100; i++) {  
	            System.out.println("����Runner1����״̬��������������������" + i + "--->" + times);  
	            times=i;
	        }  
	    }  
	}		
	  
	class Runner2 implements Runnable { // ʵ����Runnable�ӿڣ�jdk��֪���������һ���߳�  
	    public void run() {  
	        for (int i = 0; i < 100; i++) {  
	            System.out.println("����Runner2����״̬==========" + i);  
	        }  
	    }  
}
