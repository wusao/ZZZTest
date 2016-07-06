/**
* Copy Right @ EA.Corp   
* @Title: SelectionSort.java 
* @Package personal.wade.template 
* @Description:  
* @author Wade.wuchao
* @date 2015年10月26日
*/
package personal.wade.template.sort;

/**
 * @ClassName: SelectionSort
 * @Description: Implement of Selection Sort
 * @author Wade.wuchao
 * @date 2015年10月26日
 * 
 */
public class SelectionSort {

	public static void main(String[] args) {
		int[] arr = { 1, 4, 7, 2, 5, 8, 3, 6, 9 };
		selecitonSort(arr);
		for (int i : arr)
			System.out.print(i + ",");
	}

	public static void selecitonSort(int[] a) {
		for (int i = 0; i < a.length; i++) {
			int max = a[0];
			int count = 0;
			int k = a.length - i - 1;
			for (int j = 0; j < a.length - i; j++) {
				if (max < a[j]) {
					max = a[j];
					count = j;
				}
			}
			a[count] = a[k];
			a[k] = max;
		}
	}
}
