/**
* Copy Right @ EA.Corp   
* @Title: MutilThreadSingelton.java 
* @Package personal.wade.test.singleton 
* @Description:  
* @author Wade.wuchao
* @date 2016��5��19��
*/
package personal.wade.test.singleton;

/** 
* @ClassName: MutilThreadSingelton 
* @Description: 
* @author Wade.wuchao
* @date 2016��5��19�� 
*  
*/
public class MutilThreadSingelton {

	public final static MutilThreadSingelton INSTANCE = new MutilThreadSingelton();
	
	private MutilThreadSingelton()
	{
		//Init Singelton Instance
	}
	
}
