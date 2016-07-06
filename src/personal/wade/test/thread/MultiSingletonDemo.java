/**
* Copy Right @ EA.Corp   
* @Title: MultiSingletonDemo.java 
* @Package personal.wade.test.thread 
* @Description:  
* @author Wade.wuchao
* @date 2016��5��23��
*/
package personal.wade.test.thread;

/** 
* @ClassName: MultiSingletonDemo 
* @Description: 
* @author Wade.wuchao
* @date 2016��5��23�� 
*  
*/
public class MultiSingletonDemo {
		private static volatile MultiSingletonDemo instance = null;

		private MultiSingletonDemo() {
		}

		public static MultiSingletonDemo getInstance() {
			if (instance == null) {
				synchronized (MultiSingletonDemo.class) {
					if (instance == null) {
						instance = new MultiSingletonDemo();
					}
				}
			}
			return instance;
		}
}
