/**
* Copy Right @ EA.Corp   
* @Title: ThreadsCommunicatMain.java 
* @Package personal.wade.main 
* @Description:  
* @author Wade.wuchao
* @date 2016年5月27日
*/
package personal.wade.main;

/** 
* @ClassName: ThreadsCommunicatMain 
* @Description: 
* @author Wade.wuchao
* @date 2016年5月27日 
*  
*/
public class SimpleMain {
    static volatile int index = 0;
    static boolean flagSub = false;
    	private synchronized void doBussinessSub(){
    		
        		System.out.println("Sub "+ Thread.currentThread().getName()+" sub index: " + index);
        		index++;
        		if(index == 5)
        		{
        			this.notify();
        		}
    }
    	
    	private synchronized void doBussinessMain()
    	{
    		if(index != 5){
                try {
    				this.wait();
    			} catch (InterruptedException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
            }
            
        		System.out.println("Hello Main!!! "+" Main index: " + index);
    	}
    	
    	public void startDo()
    	{

    	for(int i=1; i< 6; i++)
    	{
            new Thread(new Runnable() {
                @Override
                public void run() {

                		doBussinessSub();
                	}
            }).start();
        }
    	
            	doBussinessMain();
    	}
    	
    public static void main(String[] args) {
    	SimpleMain tb = new SimpleMain();
    	tb.startDo();
    }
    
//    public static void main(String[] args,String a) {
//    	System.out.println("AAA");
//    }
}