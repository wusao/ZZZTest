/**
* Copy Right @ EA.Corp   
* @Title: Zuhe.java 
* @Package personal.wade.bean 
* @Description:  
* @author Wade.wuchao
* @date 2015年12月3日
*/
package personal.wade.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
/** 
* @ClassName: Zuhe 
* @Description: 
* @author Wade.wuchao
* @date 2015年12月3日 
*  
*/

public class Zuhe {

     public static List<String> list = new ArrayList<String>();
     
     public static void lottery(int a[], int start_index, int end_index, int needed_balls, Set<Integer> already_chosen) {
           if (needed_balls == 0) {
                 list.add(already_chosen.toString());
                 return;
           }
           for (int i = start_index; i <= end_index - needed_balls + 1; i++) {
                 already_chosen.add(a[i]);
                 lottery(a, i + 1, end_index, needed_balls - 1, already_chosen);
                 already_chosen.remove(a[i]);
           }

     }
     public static void main(String[] args) {
        lottery(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10} , 0, 9, 8,
        new HashSet<Integer>());
        System.out.println(list.size());
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
     }
}
