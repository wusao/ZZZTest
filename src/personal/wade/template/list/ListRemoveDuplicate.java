/**
* Copy Right @ EA.Corp   
* @Title: ListRemoveDuplicate.java 
* @Package personal.wade.template.list 
* @Description:  Remove Duplicate Element from List
* @author Wade.wuchao
* @date 2015年12月2日
*/
package personal.wade.template.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/** 
* @ClassName: ListRemoveDuplicate 
* @Description: Remove Duplicate Element from List
* @author Wade.wuchao
* @date 2015年12月2日 
*  
*/
public class ListRemoveDuplicate {

	/** 
	 * <p><b>ParamContent</b></p> 
	 * @param args 
	 * void 
	  */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Integer[] intArr = {0, 1, 2, 3, 4, 5, 0, 2, 4, 5};
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(intArr));
		
		removeDuplicateWithSet(list);
	}

	public static <T> void removeDuplicate(List<T> list) {  
		   for ( int i = 0 ; i < list.size() - 1 ; i ++ ) {  
		     for ( int j = list.size() - 1 ; j > i; j -- ) {  
		       if (list.get(j).equals(list.get(i))) {  
		         list.remove(j);  
		       }   
		      }   
		    }   
		    System.out.println(list);  
		}
	
	public static <T> void removeDuplicateWithSet(List<T> list) {  
	      HashSet<T> h = new HashSet<T>(list);  
	      list.clear();
	      list.addAll(h);  
	      System.out.println(list);  
	}   
	
	public static <T> void removeDuplicateWithOrder(List<T> list) {  
	     Set<T> set = new HashSet<T>();  
	      List<T> newList = new ArrayList<T>();  
	   for (Iterator<T> iter = list.iterator(); iter.hasNext();) {  
	          T element = iter.next();  
	          if (set.add(element))  
	             newList.add(element);  
	       }   
	      list.clear();  
	      list.addAll(newList);  
	     System.out.println( " remove duplicate " + list);  
	}  	
}
