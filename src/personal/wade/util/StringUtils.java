package personal.wade.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class StringUtils {
	
	/**
	 * Used for mask a name string
	 */
	private static final char NAME_MASK_CHAR = '*';
	
	
	public static boolean isEmpty(String str){
		if(null==str||"".equals(str.trim())){
			return true;
		}
		return false;
	}
	
	public static String replaceString(String value, String oldString, String newString)
	   {
	     if ((value == null) || (newString == null) || (oldString == null))
	       return null;
	     StringBuffer sbf = new StringBuffer();
	     sbf.append(value);
	     int len = newString.length();
	     int l = oldString.length();
	     int pos = 0 - len;
	     String temp = "";
	     if (value.equals(""))
	       return value;
	     do
	     {
	       temp = sbf.toString();
	       pos = temp.indexOf(oldString, pos + len);
	       if (pos >= 0)
	         sbf.replace(pos, pos + l, newString);
	     }
	     while (pos >= 0);
	     String aa = new String(sbf);
	     return aa;
	   }
	 
	   public static String null2EmptyString(String src)
	   {
	     String s = src;
	     if (StringUtil.isBlank(src))
	       s = "";
	     return s.trim();
	   }
	   
		
		/**
		 * Split ***SNStr for potential SQL injection
		 * <p><b>notice</b></p>
		 * This method will remove all string with value 'undefined', if your expected result contains 'undefined', Do not use this method
		 * <p><b>ParamContent</b></p> 
		 * batchSNStr: withdraw_sn, deposit_sn, transfer_sn list string separate by ','  Sample:<code>"W2015102414362620349848,W2015102414362799223008"</code>
		 * <pre>
		 * <b>Example:</b>
		 * null  ---> null
		 * " "   ---> null
		 * "W2015102414362799223008"  ---> List<String>{W2015102414362799223008}
		 * "W2015102414362620349848,W2015102414362799223008"  ---> List<String>{"W2015102414362799223008", "W2015102414362799223008"}
		 * </pre>
		 * @param batchSNStr 
		 * @return List<String>
		 * 
		 */
		public static List<String> splitSNList(String batchSNStr)
		{
			if(StringUtil.isBlank(batchSNStr))
			{	return null;  }
			
			List<String> restList = new ArrayList<String>();
			
			if(batchSNStr.contains(","))
			{	
				restList = new ArrayList<String>(Arrays.asList(batchSNStr.trim().split(",")));
			}else {
				restList.add(batchSNStr);
			}
			//Remove 'undefined' String parameter to avoid unpredictable issue
			Iterator<String> it = restList.iterator();
			while(it.hasNext())
			{
				String tempStr = it.next();
				if(StringUtil.isBlank(tempStr) || tempStr.equals("undefined"))
					it.remove();
			}
			
			return restList;
		}
		
		/**
		 * Mask Bank Card Name Str with NAME_MASK_CHAR(*)
		 * @param nameStr
		 * @return 
		 * String
		 */
		public static String maskName(String nameStr)
		{
			if(StringUtil.isEmpty(nameStr))
			{
				return nameStr;
			}
			
			nameStr = nameStr.trim();
			
			if(nameStr.length() <= 1)
			{
				return nameStr;
			}
			
			char[] nameCharArray = nameStr.toCharArray();
			StringBuffer sb = new StringBuffer().append(nameCharArray[0]);
			//Currently Use a common way to mask, last_name + ** for all case
			sb.append("**");
			
//			for(int i = 1; i < nameCharArray.length ; i++)
//			{
//				//Potential Logic To change Mask Rule
//				nameCharArray[i] = NAME_MASK_CHAR;
//				
//				sb.append(nameCharArray[i]);
//			}
			
			return sb.toString();
		}
		
		
		public static void main(String[] args)
		{
			System.out.println(splitSNList(",undefined,W2015102414362620349848,W2015102414362799223008,undefined,,"));
		}
}
