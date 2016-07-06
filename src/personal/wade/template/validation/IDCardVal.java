/**
* Copy Right @ EA.Corp   
* @Title: IDCardVal.java 
* @Package personal.wade.template.validation 
* @Description:  
* @author Wade.wuchao
* @date 2015年11月17日
*/
package personal.wade.template.validation;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import personal.wade.util.StringUtil;


/** 
* @ClassName: IDCardVal 
* @Description: Validate whether ID card is legal
* @author Wade.wuchao
* @date 2015年11月17日 
*  
*/
public class IDCardVal {

    /**
     * 身份证号码验证 1、号码的结构 公民身份号码是特征组合码，由十七位数字本体码和一位校验码组成。排列顺序从左至右依次为：六位数字地址码，
     * 八位数字出生日期码，三位数字顺序码和一位数字校验码。 2、地址码(前六位数）
     * 表示编码对象常住户口所在县(市、旗、区)的行政区划代码，按GB/T2260的规定执行。 3、出生日期码（第七位至十四位）
     * 表示编码对象出生的年、月、日，按GB/T7408的规定执行，年、月、日代码之间不用分隔符。 4、顺序码（第十五位至十七位）
     * 表示在同一地址码所标识的区域范围内，对同年、同月、同日出生的人编定的顺序号， 顺序码的奇数分配给男性，偶数分配给女性。 5、校验码（第十八位数）
     * （1）十七位数字本体码加权求和公式 S = Sum(Ai * Wi), i = 0, ... , 16 ，先对前17位数字的权求和
     * Ai:表示第i位置上的身份证号码数字值 Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2 
     * （2）计算模 Y = mod(S = Sum(Ai * Wi), 11) （3）通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10 校验码: 1 0 X 9 8 7 6 5 4 3 2
     */
	
	private static final Logger  logger = LoggerFactory.getLogger(IDCardVal.class);
	
    private static final String[] ValCodeArr = { "1", "0", "X", "9", "8", "7", "6", "5", "4",
            "3", "2" };
    
    private static final String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
            "9", "10", "5", "8", "4", "2" };
	
    /**
     * 功能：设置地区编码
     * 
     * @return Hashtable 对象
     */
    private static Hashtable<String, String> GetAreaCode() {
        Hashtable<String, String> hashtable = new Hashtable<String, String>();
        hashtable.put("11", "北京");
        hashtable.put("12", "天津");
        hashtable.put("13", "河北");
        hashtable.put("14", "山西");
        hashtable.put("15", "内蒙古");
        hashtable.put("21", "辽宁");
        hashtable.put("22", "吉林");
        hashtable.put("23", "黑龙江");
        hashtable.put("31", "上海");
        hashtable.put("32", "江苏");
        hashtable.put("33", "浙江");
        hashtable.put("34", "安徽");
        hashtable.put("35", "福建");
        hashtable.put("36", "江西");
        hashtable.put("37", "山东");
        hashtable.put("41", "河南");
        hashtable.put("42", "湖北");
        hashtable.put("43", "湖南");
        hashtable.put("44", "广东");
        hashtable.put("45", "广西");
        hashtable.put("46", "海南");
        hashtable.put("50", "重庆");
        hashtable.put("51", "四川");
        hashtable.put("52", "贵州");
        hashtable.put("53", "云南");
        hashtable.put("54", "西藏");
        hashtable.put("61", "陕西");
        hashtable.put("62", "甘肃");
        hashtable.put("63", "青海");
        hashtable.put("64", "宁夏");
        hashtable.put("65", "新疆");
        hashtable.put("71", "台湾");
        hashtable.put("81", "香港");
        hashtable.put("82", "澳门");
        hashtable.put("91", "国外");
        return hashtable;
    }
	
	/**
	 * Validation whether input <code>cardNO</code> is a legal Chinese ID card NO
	 * <p><b>Rule</b></p>
	 * <pre>
	 * <b>Basic</b>
	 * 1. Length must be 15 or 18
	 * 2. Only last char can be 'X', others should be numeric
	 * 3. Contain birthday format must as YYYYMMDD
	 * <b>Validation Code</b>
	 * Last char in ID card NO is a validation code, calculate by the front numbers
	 * ValCodeArr_Index = mod(S = Sum(Ai * Wi), 11)
	 * ValCodeArr = { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" }
	 * </pre>
	 * @param cardNO
	 * @return 
	 * boolean
	 */
	public static boolean checkIDCardNO(String cardNO)
	{
		if(StringUtil.isBlank(cardNO))
			return false;
		
		if(!checkBasicRule(cardNO))
			return false;
		
		if(!checkLastCode(cardNO))
			return false;
		
		return true;
	}
	
	/**
	 * Check Three Basic rules
	 * <p><b>ParamContent</b></p> 
	 * @param cardNO
	 * @return 
	 * boolean
	 */
	private static boolean checkBasicRule(String cardNO)
	{
		if(cardNO.length() != 15 && cardNO.length() != 18)
		{
			logger.info("ID Card must 15 or 18 char long");
			return false;
		}
		
		//Main part except last char
		String infoPart = cardNO.length() == 18 ? cardNO.substring(0, 17)
												: cardNO.substring(0, 6) +"19"+ cardNO.substring(6, 15);
		//1. Rule
		if(!StringUtil.isNumeric(infoPart))
		{
			logger.info("ID Card must consist of number except last char");
			return false;
		}
		//2. Rule
		if(!validateDatePart(infoPart.substring(6, 14)))
		{
			logger.info("ID Card must contain a legal birthday date");
			return false;
		}
		//3. Rule
		if(null == GetAreaCode().get(infoPart.substring(0,2)))
		{
			logger.info("ID Card must with legal area start");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Check last validation code
	 * <p><b>ParamContent</b></p> 
	 * @param cardNO
	 * @return 
	 * boolean
	 */
	private static boolean checkLastCode(String cardNO)
	{
		if(cardNO.length() == 15)
			return true;
		
	   int TotalmulAiWi = 0;
	   
	   for (int i = 0; i < 17; i++) 
	   {
	        TotalmulAiWi = TotalmulAiWi
	                 + Integer.parseInt(String.valueOf(cardNO.charAt(i)))
	                 * Integer.parseInt(Wi[i]);
	    }
	    int modValue = TotalmulAiWi % 11;
	    String strVerifyCode = ValCodeArr[modValue];

	    if (strVerifyCode.equalsIgnoreCase(String.valueOf(cardNO.charAt(17))) == false) 
	    {
	       logger.info("ID Card not have a legal validate code");
	       return false;
	     }
	    
		return true;
	}
	
	private static boolean validateDatePart(String dateStr)
	{
		try
		{
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			//设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
			format.setLenient(false);
			Date date = format.parse(dateStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			
			if(calendar.after(new Date()) || calendar.get(Calendar.YEAR) < 1900)
				return false;
			
			return true;
		}
		catch(Exception e){
			logger.info(e.getMessage());
			return false;
		}
	}
	
	
	public static void main(String args[])
	{
		IDCardVal.checkIDCardNO("420983198906111757");
	}
}
