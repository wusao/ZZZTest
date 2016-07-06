/**
* Copy Right @ EA.Corp   
* @Title: ThreadsCommunicatMain.java 
* @Package personal.wade.main 
* @Description:  
* @author Wade.wuchao
* @date 2016年5月27日
*/
package personal.wade.test.thread;

/** 
* @ClassName: ThreadsCommunicatMain 
* @Description: 
* @author Wade.wuchao
* @date 2016年5月27日 
*  
*/
public class ThreadsCommunicatMain {
//    static volatile int index = 1;
    static boolean flagSub = false;
    	private synchronized void doBussinessSub(int i){
    		if(!flagSub)
    		{
    			try {
					this.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		
    		int index = 1;
           	while(index != 10)
        		System.out.println("Sub i:"+i+" sub index: " + index++);
           	
           	flagSub = false;
           	
           	this.notify();
           	
    }
    	
    	private synchronized void doBussinessMain(int i)
    	{
    		if(flagSub){
    				try {
						this.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            }
            
            int index = 1;
           	while(index != 3)
        		System.out.println("Main i:"+i+" Main index: " + index++);
           	
           	flagSub = true;
           	this.notify();
    	}
    	
    	public void startDo()
    	{
            new Thread(new Runnable() {
                @Override
                public void run() {
                	for(int i=0; i< 3; i++)
                	{
                		doBussinessSub(i);
                	}
                }
            }).start();
            
            for(int i=0; i< 3; i++)
            {
            	doBussinessMain(i);
            }
    	}
    	
    public static void main(String[] args) {
    	ThreadsCommunicatMain tb = new ThreadsCommunicatMain();
    	tb.startDo();
    }
}