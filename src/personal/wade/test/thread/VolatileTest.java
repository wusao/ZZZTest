/**
* Copy Right @ EA.Corp   
* @Title: VolatileTest.java 
* @Package personal.wade.test.thread 
* @Description:  
* @author Wade.wuchao
* @date 2016年5月20日
*/
package personal.wade.test.thread;

/** 
* @ClassName: VolatileTest 
* @Description: 
* @author Wade.wuchao
* @date 2016年5月20日 
*  
*/
public class VolatileTest {

	private volatile static VolatileTest volatileTest = new VolatileTest();
	private Car carObj;
	
	private VolatileTest()
	{
		this.carObj = new Car("Bike", 0);
	}
	
	public Car getCarObj()
	{
		return carObj;
	}
	
	public static VolatileTest getInstance()
	{
		return volatileTest;
	}
	
	@Override
	public String toString()
	{
		return this.carObj.toString();
	}
	
	public class Car{
		private String carName;
		private Integer wheelNum;
		
		public Car(String carName, Integer wheelNum)
		{
			this.setCarName(carName);
			this.setWheelNum(wheelNum);
		}
		
		@Override
		public String toString()
		{
			StringBuffer sb = new StringBuffer();
			sb.append("carName:");
			sb.append(this.carName);
			sb.append("\r\n");
			sb.append("wheelNum:");
			sb.append(this.wheelNum);
			return sb.toString();
		}
		
		/**
		 * @return the carName
		 */
		public String getCarName() {
			return carName;
		}
		/**
		 * @param carName the carName to set
		 */
		public void setCarName(String carName) {
			this.carName = carName;
		}
		/**
		 * @return the wheelNum
		 */
		public Integer getWheelNum() {
			return wheelNum;
		}
		/**
		 * @param wheelNum the wheelNum to set
		 */
		public void setWheelNum(Integer wheelNum) {
			this.wheelNum = wheelNum;
		}
		
		
	}
	
}
