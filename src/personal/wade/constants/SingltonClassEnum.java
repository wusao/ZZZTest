/**
* Copy Right @ EA.Corp   
* @Title: singltonClassEnum.java 
* @Package personal.wade.constants 
* @Description:  
* @author Wade.wuchao
* @date 2015��12��2��
*/
package personal.wade.constants;

/** 
* @ClassName: SingltonClassEnum 
* @Description: 
* @author Wade.wuchao
* @date 2015��12��2�� 
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
