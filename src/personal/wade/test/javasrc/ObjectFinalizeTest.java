/**
* Copy Right @Author   
* @Title: ObjectFinalizeTest.java 
* @Package personal.wade.test.javasrc 
* @Description:  
* @author Wade.wuchao
* @mail: wusaole@163.com
* @date 2016年5月25日
*/
package personal.wade.test.javasrc;


/**
 * @ClassName: ObjectFinalizeTest
 * @Description:
 * @author Wade.wuchao
 * @date 2016年5月25日
 * 
 */
public class ObjectFinalizeTest {

	Test main; // 记录Test对象，在finalize中时用于恢复可达性

	public ObjectFinalizeTest(Test t)

	{
		main = t; // 保存Test 对象
	}

	@Override
	protected void finalize()
	{

		main.ref = this;// 恢复本对象，让本对象可达

		System.out.println("This is finalize"); // 用于测试finalize只运行一次

	}

	@Override
	public String toString()
	{
		return "This is ObjectFinalizeTest to String";
	}
}

class Test {

	ObjectFinalizeTest ref;

	public static void main(String[] args) {

		Test test = new Test();

		test.ref = new ObjectFinalizeTest(test);

		test.ref = null; // MyObject对象为不可达对象，finalize将被调用

		int i = 0;
		
		System.gc(); //调用gc 回收，但是不会立马执行 finalize，等待JVM调度
		
		while(test.ref == null)
		{
			//若不调度finalize 将永远处于此循环中不结束
			i = i + 1;
			System.out.println(i); //GC执行前做了多少次++操作
		}
		
		if(test.ref != null)
			System.out.println("My Object还活着");
		
		System.out.println(test.ref);
	}

}
