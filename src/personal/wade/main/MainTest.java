/**
* Copy Right @ EA.Corp   
* @Title: MainTest.java 
* @Package personal.wade.main 
* @Description:  
* @author Wade.wuchao
* @date 2015年10月14日
*/
package personal.wade.main;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeSet;
import java.util.Vector;
import java.util.function.Predicate;

import com.sun.jndi.url.iiopname.iiopnameURLContextFactory;

import personal.wade.template.validation.BankCardVal;
import personal.wade.template.validation.IDCardVal;
import personal.wade.test.data.BankCardData;
import personal.wade.test.thread.NewThreadCnt;
import personal.wade.test.thread.VolatileTest;
import personal.wade.util.StringUtil;
import personal.wade.util.StringUtils;

/** 
* @ClassName: MainTest 
* @Description: 
* @author Wade.wuchao
* @date 2015年10月14日 
*  
*/
public class MainTest {

	public static String strA = "abc";
	/** 
	 * <p><b>ParamContent</b></p> 
	 * @param args 
	 * void 
	  */
	public static void main(String[] args) {
		
/*		
		Thread threadObj = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("Hello ");
			}
		}){
			public void run(){
				super.run();
			System.out.println("World!");
			
			}
		};
		threadObj.start();
	*/
		int h= 1<<31;
//		int key = -1;
//		System.out.println((h = key) ^ (h >>> 16));
		for(int i = 0; i < 32; i++)
		{
			System.out.println(h = h >>> 1);
		}
		System.out.println(Integer.MAX_VALUE);
		System.out.println(Integer.MIN_VALUE);
//		String strC = "efg";
//		String strB = strC;
//		strA = null;
//		strC = null;
//		System.out.println(strA);
//		System.out.println(strC);
//		System.out.println(strB);
		/**
		 * Mail Testing Entrance
		 * <p><b>ParamContent</b></p> 
		 * @param args 
		 * void
		 */

			//Get Singleton Obj
/*			
		for (int i = 0; i < 10; i++) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						VolatileTest volatileTest = VolatileTest.getInstance();
						System.out.println(volatileTest);
						volatileTest.getCarObj().setWheelNum(volatileTest.getCarObj().getWheelNum() + 1);
						volatileTest.getCarObj().setWheelNum(volatileTest.getCarObj().getWheelNum() + 1);
						volatileTest.getCarObj().setWheelNum(volatileTest.getCarObj().getWheelNum() + 1);
						System.out.println(volatileTest);
					}
				}).start();
		}
	*/	
//		testStringToInteger();
//		testEnumGetValue();
//		testGetBankInfoList("","");
//		testListRemove();
//		for(String str : parseDepositSNList(",aaa,bbb,ccc,ddd,"))
//		System.out.println(str);
//		System.out.println(parseDepositSNList(",aaa,bbb,ccc,ddd,").size());
//		testBigDecimalToString();
//		System.out.println(TimeUtils.parseDateGroup(new Date(115, 9, 1)));
//		System.out.println(MD5.getMd5Uppercase32("123456789abc"));
//		System.out.println(EnumTest.ENUM_WUSAO);
//		testCalender();
//		testBankCardNO("6013822000645108518");
//		SingleOnsiteMsgBean singleOnsiteMsgBean = new SingleOnsiteMsgBean();
//		System.out.println(null == singleOnsiteMsgBean);
//		System.out.println(singleOnsiteMsgBean.getClass().equals(SingleOnsiteMsgBean.class));
//		
//		ShopWithdraw shopWithdrawObj = new ShopWithdraw();
//		shopWithdrawObj.setStatus(ShopDepositStatusEnum.AUDIT_PASSED.getValue());
//		
//		System.out.println(genWithdrawAuditMessage(shopWithdrawObj, singleOnsiteMsgBean));
//		singleOnsiteMsgBean =  (SingleOnsiteMsgBean) genWithdrawAuditMessage(shopWithdrawObj, singleOnsiteMsgBean);
//		System.out.println(singleOnsiteMsgBean);
//		
//		String msg_template = "{\"first\":{\"value\":\"【云微店测试推送微信消息，QQ：408789455】恭喜你购买成功！\",\"color\":\"#173177\"},\"orderID\":{\"value\":\"YWD20160316111220\",\"color\":\"#173177\"},\"orderMoneySum\":{\"value\":\"3000.00元\",\"color\":\"#173177\"},\"backupFieldName\":{\"value\":\"我想填的一个名称:\",\"color\":\"#173177\"},\"backupFieldData\":{\"value\":\"怡亚通xx店铺xx商品\",\"color\":\"#173177\"},\"remark\":{\"value\":\"如有问题请致电400-828-1878或直接在微信留言，小EA将第一时间为您服务！\",\"color\":\"#173177\"}}";
//		testIDCardNO();
//		testBankCardNO();
//		testAction2CHStr();
		
//		String a = null;
//		String b = null;
//		String c = null;
//		
//		System.out.println(a+b+c);
//		
//		System.out.println(SingltonClassEnum.A_SINGLTON.getInstance());
//		
//		System.out.println(SingltonClassEnum.valueOf("A_SINGLTON").getInstance());
		
//		testExceptionCatch();
		
//		Integer integerObj = new Integer(4);
//		System.out.println(integerObj.equals(ShopWithdrawStatusEnum.WITHDRAW_SUCCESS.getValue()));
//		System.out.println(integerObj.equals(ShopWithdrawStatusEnum.WITHDRAW_SUCCESS.getValue()) ? 
//				MTransferWithdrawStatusEnum.TRANSFER_SUCCESS.getValue() : MTransferWithdrawStatusEnum.TRANSFER_FAIL.getValue());
//		System.out.println((new BigDecimal(1).divide(new BigDecimal(1), 3, BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100)).doubleValue());

//	int a = 99;
//	Integer aobj = new Integer(a);
//	int b = 9999999;
//	Integer bobj = new Integer(b);
//	
//	System.out.println(a == aobj);
//	System.out.println(b == bobj);
//
//	System.out.println(4/3);
//	System.out.println(1003/3);
//	System.out.println((100%3.0 + 100/3.0 + 100*3.0));
//	System.out.println((100%3.0 + 100/3.0 + 100*3.0)/3.0);
	
	}

	private static String getStrA()
	{
		return strA;
	}
	
	private static void testAction2CHStr()
	{
		String CHStr = "刘亦菲";
		
		System.out.println("Length of <"+CHStr+"> : "+CHStr.length());
		
		System.out.println("mask name of <"+CHStr+"> : "+ StringUtils.maskName(CHStr));
	}
	
	private static void testIDCardNO()
	{

		String[] IDCardArray = new String[]{
				 "420983198988881111"
				,"431103198405176939"
				,"123456789012345678"
				,"441422199309080031"
				,"420983201511111111"
				,"441422199309080031"
				,"154235689706212347"
				,"360726198876766786"
				,"245156788690853688"
				,"111111111111111111"
				,"440301198654321456"
				,"362427198707270058"
				,"431023123456789101"
				,"362427198707270058"
				,"362427198707270058"
				,"430111234567890000"
				,"330719197906170054"
				,"420983198906111757"
				,"21101119760113871X"
				,"42098319890611175X"};
		for(String IDNO : IDCardArray)
			System.out.println(IDNO + "---" + IDCardVal.checkIDCardNO(IDNO));
	}
	
	private static String testStringModify(String souStr)
	{
		souStr = souStr + "XXX";
		return souStr;
	}
	
	private static void testBasicTypeDefaultValue()
	{
		char charBsc;
		boolean boolBsc;
		byte byteBsc;
		int intBsc;
		short shortBsc;
		long longBsc;
		float floatBsc;
		double doubleBsc;
		
//		System.out.println("Basic int default value:" + intBsc);
	}
	
	private static void testBankCardNO()
	{

		String[] cardIdArray = BankCardData.BANK_CARD_ARR;
		for(String cardId : cardIdArray)
			System.out.println(cardId + "---" + BankCardVal.checkBankCard(cardId));
	}
	
	private static void testBankCardNO(String bankCardNo)
	{

			System.out.println(bankCardNo + "---" + BankCardVal.checkBankCard(bankCardNo));
	}
	
	private static void testStringToInteger()
	{
		String intValue = "999";
		String etdIntValue = String.valueOf(Integer.MAX_VALUE + 1);
		String negIntValue = "-100";
		
		System.out.println("-------Test Integer.getInteger(Str)--------");
		System.out.println(Integer.getInteger(intValue));
		System.out.println(Integer.getInteger(etdIntValue));
		System.out.println(Integer.getInteger(negIntValue));
		
		System.out.println("-------Test Integer.valueOf(Str)--------");
		System.out.println(Integer.valueOf(intValue));
		System.out.println(Integer.valueOf(etdIntValue));
		System.out.println(Integer.valueOf(negIntValue));

		System.out.println("-------Test Integer.parseInt(Str)--------");
		System.out.println(Integer.parseInt(intValue));
		System.out.println(Integer.parseInt(etdIntValue));
		System.out.println(Integer.parseInt(negIntValue));
	}
	
	private static List<String> parseDepositSNList(String depositSNStr)
	{
		if(StringUtil.isBlank(depositSNStr))
		{	return null;  }
		
		ArrayList<String> restList = new ArrayList<String>();
		
		if(depositSNStr.contains(","))
		{	
			for(String str : Arrays.asList(depositSNStr.split(",")))
				restList.add(str);
		}else {
			restList.add(depositSNStr);
		}
		
//		restList.removeIf(str -> StringUtil.isBlank(str));
		restList.removeIf(new Predicate<String>() {
            public boolean test(String lang) {
                return lang.equals("aaa");
            }
        });
		return restList;
	}
	
	private static void testListRemove()
	{
		
		TreeSet set = new TreeSet();   
		ArrayList list = new ArrayList();   
		Vector vector = new Vector();   
		  
		for (int i = 0; i < 10; i++) {   
		set.add(i);   
		list.add(i);   
		vector.add(i);   
		}   
		System.out.println("初始化后set里的值为:" + set.toString());   
		System.out.println("初始化后list里的值为:" + list.toString());   
		System.out.println("初始化后vector里的值为:" + vector.toString());   
		  
		for (int i = 0; i < 5; i++) {   
		set.remove(i);   
		list.remove(i);   
		vector.remove(i);   
		  
		}   
		System.out.println("此时set的值为" + set.toString());   
		System.out.println("此时后list的值为" + list.toString());   
		System.out.println("此时后vector的值为" + vector.toString());  
	}
	
	private static void testCalender()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		
		System.out.println("now.YEAR: " + calendar.get(Calendar.YEAR));   
		System.out.println("now.MONTH: " + calendar.get(Calendar.MONTH));  
		System.out.println("now.DATE: " + calendar.get(Calendar.DATE));  
		System.out.println("now.HOUR: " + calendar.get(Calendar.HOUR));  
		System.out.println("now.HOUR_OF_DAY: " + calendar.get(Calendar.HOUR_OF_DAY));  
		System.out.println("now.MINUTE: " + calendar.get(Calendar.MINUTE));  
		System.out.println("now.SECOND: " + calendar.get(Calendar.SECOND));  
	}
	
	private static void testExceptionCatch()
	{
		try{
			throwSQLExpt();
		}catch(SQLException e)
		{
			if(e.getMessage().equals("It is a SQL Exception Been throw"))
			System.out.println("Equals"+e.getMessage());
			else {
				System.out.println(e.getMessage());
			}
		}catch(Exception e)
		{
			System.out.println("Have not Catch SQL Excpt");
		}
	}
	
	private static void throwSQLExpt() throws SQLException
	{
		throw new SQLException("It is a SQL Exception Been throw");
	}
	
}
