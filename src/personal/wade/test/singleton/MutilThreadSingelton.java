/**
* Copy Right @ EA.Corp   
* @Title: MutilThreadSingelton.java 
* @Package personal.wade.test.singleton 
* @Description:  
* @author Wade.wuchao
* @date 2016年5月19日
*/
package personal.wade.test.singleton;

/** 
* @ClassName: MutilThreadSingelton 
* @Description: 
* @author Wade.wuchao
* @date 2016年5月19日 
*  
*/
public class MutilThreadSingelton {

	public final static MutilThreadSingelton INSTANCE = new MutilThreadSingelton();
	
	private MutilThreadSingelton()
	{
		//Init Singelton Instance
	}
	
}
