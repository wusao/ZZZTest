/**
* Copy Right @Author   
* @Title: ObjectFinalizeTest.java 
* @Package personal.wade.test.javasrc 
* @Description:  
* @author Wade.wuchao
* @mail: wusaole@163.com
* @date 2016��5��25��
*/
package personal.wade.test.javasrc;


/**
 * @ClassName: ObjectFinalizeTest
 * @Description:
 * @author Wade.wuchao
 * @date 2016��5��25��
 * 
 */
public class ObjectFinalizeTest {

	Test main; // ��¼Test������finalize��ʱ���ڻָ��ɴ���

	public ObjectFinalizeTest(Test t)

	{
		main = t; // ����Test ����
	}

	@Override
	protected void finalize()
	{

		main.ref = this;// �ָ��������ñ�����ɴ�

		System.out.println("This is finalize"); // ���ڲ���finalizeֻ����һ��

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

		test.ref = null; // MyObject����Ϊ���ɴ����finalize��������

		int i = 0;
		
		System.gc(); //����gc ���գ����ǲ�������ִ�� finalize���ȴ�JVM����
		
		while(test.ref == null)
		{
			//��������finalize ����Զ���ڴ�ѭ���в�����
			i = i + 1;
			System.out.println(i); //GCִ��ǰ���˶��ٴ�++����
		}
		
		if(test.ref != null)
			System.out.println("My Object������");
		
		System.out.println(test.ref);
	}

}
