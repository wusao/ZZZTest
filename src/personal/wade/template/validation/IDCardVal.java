/**
* Copy Right @ EA.Corp   
* @Title: IDCardVal.java 
* @Package personal.wade.template.validation 
* @Description:  
* @author Wade.wuchao
* @date 2015��11��17��
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
* @date 2015��11��17�� 
*  
*/
public class IDCardVal {

    /**
     * ���֤������֤ 1������Ľṹ ������ݺ�������������룬��ʮ��λ���ֱ������һλУ������ɡ�����˳�������������Ϊ����λ���ֵ�ַ�룬
     * ��λ���ֳ��������룬��λ����˳�����һλ����У���롣 2����ַ��(ǰ��λ����
     * ��ʾ�������ס����������(�С��졢��)�������������룬��GB/T2260�Ĺ涨ִ�С� 3�����������루����λ��ʮ��λ��
     * ��ʾ�������������ꡢ�¡��գ���GB/T7408�Ĺ涨ִ�У��ꡢ�¡��մ���֮�䲻�÷ָ����� 4��˳���루��ʮ��λ��ʮ��λ��
     * ��ʾ��ͬһ��ַ������ʶ������Χ�ڣ���ͬ�ꡢͬ�¡�ͬ�ճ������˱ඨ��˳��ţ� ˳�����������������ԣ�ż�������Ů�ԡ� 5��У���루��ʮ��λ����
     * ��1��ʮ��λ���ֱ������Ȩ��͹�ʽ S = Sum(Ai * Wi), i = 0, ... , 16 ���ȶ�ǰ17λ���ֵ�Ȩ���
     * Ai:��ʾ��iλ���ϵ����֤��������ֵ Wi:��ʾ��iλ���ϵļ�Ȩ���� Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2 
     * ��2������ģ Y = mod(S = Sum(Ai * Wi), 11) ��3��ͨ��ģ�õ���Ӧ��У���� Y: 0 1 2 3 4 5 6 7 8 9 10 У����: 1 0 X 9 8 7 6 5 4 3 2
     */
	
	private static final Logger  logger = LoggerFactory.getLogger(IDCardVal.class);
	
    private static final String[] ValCodeArr = { "1", "0", "X", "9", "8", "7", "6", "5", "4",
            "3", "2" };
    
    private static final String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
            "9", "10", "5", "8", "4", "2" };
	
    /**
     * ���ܣ����õ�������
     * 
     * @return Hashtable ����
     */
    private static Hashtable<String, String> GetAreaCode() {
        Hashtable<String, String> hashtable = new Hashtable<String, String>();
        hashtable.put("11", "����");
        hashtable.put("12", "���");
        hashtable.put("13", "�ӱ�");
        hashtable.put("14", "ɽ��");
        hashtable.put("15", "���ɹ�");
        hashtable.put("21", "����");
        hashtable.put("22", "����");
        hashtable.put("23", "������");
        hashtable.put("31", "�Ϻ�");
        hashtable.put("32", "����");
        hashtable.put("33", "�㽭");
        hashtable.put("34", "����");
        hashtable.put("35", "����");
        hashtable.put("36", "����");
        hashtable.put("37", "ɽ��");
        hashtable.put("41", "����");
        hashtable.put("42", "����");
        hashtable.put("43", "����");
        hashtable.put("44", "�㶫");
        hashtable.put("45", "����");
        hashtable.put("46", "����");
        hashtable.put("50", "����");
        hashtable.put("51", "�Ĵ�");
        hashtable.put("52", "����");
        hashtable.put("53", "����");
        hashtable.put("54", "����");
        hashtable.put("61", "����");
        hashtable.put("62", "����");
        hashtable.put("63", "�ຣ");
        hashtable.put("64", "����");
        hashtable.put("65", "�½�");
        hashtable.put("71", "̨��");
        hashtable.put("81", "���");
        hashtable.put("82", "����");
        hashtable.put("91", "����");
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
			//����lenientΪfalse. ����SimpleDateFormat��ȽϿ��ɵ���֤���ڣ�����2007/02/29�ᱻ���ܣ���ת����2007/03/01
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
