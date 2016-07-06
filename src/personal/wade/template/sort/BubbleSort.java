/**
* Copy Right @ EA.Corp   
* @Title: BubbleSort.java 
* @Package personal.wade.template 
* @Description:  
* @author Wade.wuchao
* @date 2015年10月26日
*/
package personal.wade.template.sort;

/**
 * @ClassName: BubbleSort
 * @Description: Implement of bubble sort
 * @author Wade.wuchao
 * @date 2015年10月26日
 * 
 */
public class BubbleSort {

	public static int[] bubbleSort(int[] a) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < (a.length - i) - 1; j++) {
				if (a[j] > a[j + 1]) {
					int temp = a[j];
					a[j] = a[j + 1];
					a[j + 1] = temp;
				}
			}
		}
		return a;
	}
}
