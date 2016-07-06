/**
* Copy Right @ EA.Corp   
* @Title: singltonClassEnum.java 
* @Package personal.wade.constants 
* @Description:  
* @author Wade.wuchao
* @date 2015年12月2日
*/
package personal.wade.constants;

/** 
* @ClassName: SingltonClassEnum 
* @Description: 
* @author Wade.wuchao
* @date 2015年12月2日 
*  
*/
public enum SingltonClassEnum {

	A_SINGLTON;
	
	private String classAInstance = new String("This is a singlton!");
	
	public String getInstance()
	{
		return this.classAInstance;
	}
}
