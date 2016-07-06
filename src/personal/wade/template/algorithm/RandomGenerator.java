/**
* Copy Right @ EA.Corp   
* @Title: RandomGenerator.java 
* @Package personal.wade.template.algorithm 
* @Description:  
* @author Wade.wuchao
* @date 2015年12月10日
*/
package personal.wade.template.algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/** 
* @ClassName: RandomGenerator 
* @Description: Math related random data generation 
* @author Wade.wuchao
* @date 2015年12月10日 
*  
*/
public class RandomGenerator {

	/**
	 * Generate N records random data in required limit area with no repeat
	 * <p><b>ParamContent</b></p>
	 * @param recordsNumber : the size of result number list, must >= 0
	 * @param limitStart : Start of limited area, null means start from 1 (include)
	 * @param limitEnd : End of limited area (include)
	 * @return List<Integer> with size() = recordsNumber
	 * ,return null if input recordsNumber > (limitEnd - limitStart +1) or recordsNumber < 0
	 */
	public static List<Integer> genNoRptRdmInLmt(Integer recordsNumber, Integer limitStart, Integer limitEnd, int option)
	{
		Integer NEGATIVE_HANDLER = 0;
		
		//Default start form 0
		if(null == limitStart)
		{	limitStart = 1;	}
		
		//Required recordsNum bigger than area (issue with no repeat)
		if(null == limitEnd || recordsNumber < 0 || recordsNumber > (limitEnd - limitStart +1))
		{	return null;	}
		
		List<Integer> retRecords = new ArrayList<Integer>(recordsNumber);
		
//		if(recordsNumber <= (limitEnd - limitStart)/2)
//			for(int i = recordsNumber; i > 0 ; i--)
//			{
//				retRecords.add(genOneNoRptRecord(retRecords, limitStart, limitEnd+1));
//			}
//		else {
//			genNoRptAlternate(retRecords,recordsNumber, limitStart, limitEnd+1);
//		}
		
		/**
		 * Slowest, Normal way
		 */
		if(option == 1)
			for(int i = recordsNumber; i > 0 ; i--)
			{
				retRecords.add(genOneNoRptRecord(retRecords, limitStart, limitEnd+1));
			}
		
		/**
		 * Quickest, Complexest, Alternate way 
		 */
		if(option == 2)
		{
			genNoRptAlternate(retRecords,recordsNumber, limitStart, limitEnd+1);
			randomOrder(retRecords);
		}
		
		/**
		 * a little adjustment with Normal way
		 */
		if(option == 3)
		{
			for(int i = limitEnd; i >= limitStart ; i--)
			{
				retRecords.add(i);
			}
			for(int i = recordsNumber; i > 0 ; i--)
			{
				retRecords.add(genOneNoRptRecord(retRecords, limitStart, limitEnd+1));
			}
			randomOrder(retRecords);
		}
		
		/**
		 * 
		 */
		if(option == 4)
		{
			
		}
		return retRecords;
	}
	
	/**
	 * Normal way to generate no repeat records
	 * <p><b>ParamContent</b></p> 
	 * @param srcList
	 * @param limitStart
	 * @param limitEnd
	 * @return 
	 * Integer
	 */
	private static Integer genOneNoRptRecord(List<Integer> srcList, Integer limitStart, Integer limitEnd)
	{
		java.util.Random random = new java.util.Random();
		while(true)
		{	
			Integer newRecord = random.nextInt(limitEnd-limitStart);
//			System.out.println(limitStart + "--->" +  limitEnd + "--->" +  newRecord);
			if(srcList.contains(newRecord + limitStart) == false)
			{
				return newRecord + limitStart; 
			}
		}
	}
	
	/**
	 * Alternate way to generate Random No Repeat records
	 * <p><b>ParamContent</b></p> 
	 * @param srcList
	 * @param recordsNumber
	 * @param limitStart
	 * @param limitEnd 
	 * void
	 */
	private static void genNoRptAlternate(List<Integer> srcList, Integer recordsNumber, Integer limitStart, Integer limitEnd)
	{
		if(recordsNumber > srcList.size() && limitEnd > limitStart)
		{
			Integer newRecord = genOneNoRptRecord(srcList, limitStart, limitEnd);
			srcList.add(newRecord);
			genNoRptAlternate(srcList, recordsNumber, limitStart, newRecord);
			genNoRptAlternate(srcList, recordsNumber, newRecord + 1, limitEnd);
		}
	}
	
	private static <T> void randomOrder(List<T> srcList)
	{
		int size = srcList.size();
		java.util.Random random = new java.util.Random();
		for(int i = 0; i<size; i++)
		{
			swap(srcList, i, random.nextInt(size)%size);
		}
	}
	
	private static <T> void swap(List<T> srcList, Integer indexFrom, Integer indexTo)
	{
		T tmp = srcList.get(indexFrom);
		srcList.set(indexFrom, srcList.get(indexTo));
		srcList.set(indexTo, tmp);
	}
	
	
	public static void main(String[] args)
	{
		Long timeStart = System.currentTimeMillis();
		System.out.println("Option 1--->"+genNoRptRdmInLmt(1000, 1, 2000, 1));
//		for(int i=0; i<1000; i++)
//		System.out.println(new Random().nextInt(1));
		Long timeEnd = System.currentTimeMillis();
		System.out.println(timeEnd - timeStart);
		
		timeStart = System.currentTimeMillis();
		System.out.println("Option 2--->"+genNoRptRdmInLmt(1000, 1, 2000, 2));
//		for(int i=0; i<1000; i++)
//		System.out.println(new Random().nextInt(1));
		timeEnd = System.currentTimeMillis();
		System.out.println(timeEnd - timeStart);
		
		timeStart = System.currentTimeMillis();
		System.out.println("Option 3--->"+genNoRptRdmInLmt(1000, 1, 2000, 3));
//		for(int i=0; i<1000; i++)
//		System.out.println(new Random().nextInt(1));
		timeEnd = System.currentTimeMillis();
		System.out.println(timeEnd - timeStart);
	}
}
