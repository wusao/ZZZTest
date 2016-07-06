/**
* Copy Right @ EA.Corp   
* @Title: BankCardVal.java 
* @Package personal.wade.template.validation 
* @Description:  
* @author Wade.wuchao
* @date 2015年11月17日
*/
package personal.wade.template.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import personal.wade.util.StringUtil;


/** 
* @ClassName: BankCardVal 
* @Description: Validate whether a Bank Card ID is legal
* @author Wade.wuchao
* @date 2015年11月17日 
*  
*/
public class BankCardVal {
	
	private static final Logger  logger = LoggerFactory.getLogger(BankCardVal.class);
	
	private static final String[] cardHearderArr = {
			"4",
			"30","35", "36", "37",
			"51", "52", "53", "54", "55", "58",
			"60", "62", "63", "68",
			"84", "87",
			"90", "95", "96", "99",
			"103"};
	
	/**
	 * Validate whether bank cardNO is legal
	 * <p><b>Rule</b></p>
	 * <pre>
	 * 1. Length must bigger than 12 and All consist of number
	 * 2. Start with "4, 51, 52, 53, 54, 55, 62"
	 * 3. Luhm validation
	 * </pre>
     * @param bankCardNO
     * @return
     */
    public static boolean checkBankCard(String bankCardNO) 
    {
    	if(StringUtil.isBlank(bankCardNO) || bankCardNO.length() < 12)
    	{
    		logger.info("Bank card length is not valid");
    		return false;
    	}
    	
    	if(StringUtil.isNumeric(bankCardNO) == false)
    	{
    		logger.info("Bank card must consist of number");
    		return false;
    	}
    	
        if(checkLuhm(bankCardNO) == false)
        {
    		logger.info("Bank card validation code is illegal ");
             return false;
         }
    	
    	if(checkCardHead(bankCardNO.substring(0, 2)) == false)
    	{
    		logger.info("Bank card hearder is illegal ");
    		return false;
    	}
    	
        return true;
    }
   
    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     * @param prePartStr
     * @return
     */
    private static boolean checkLuhm(String bankCardNO){
    	
    	String prePartStr = bankCardNO.substring(0, bankCardNO.length()-1);
    	
        char[] chs = prePartStr.trim().toCharArray();
        
        int luhmSum = 0;
        
        for(int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if(j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;           
        }
        
        char luhmChar = (luhmSum % 10 == 0) ? '0' : (char)((10 - luhmSum % 10) + '0');
        
        return luhmChar == bankCardNO.charAt(bankCardNO.length()-1);
    }

    private static boolean checkCardHead(String cardHeaderStr)
    {
//    	if(cardHeaderStr.startsWith(cardHearderArr[0]))
//    	{
//    		return true;
//    	}
    	
    	for(int i = 0; i < cardHearderArr.length;i++)
    	{
    		if(cardHeaderStr.startsWith(cardHearderArr[i]))
    			return true;
    	}
    	
    	return false;
    }
    
    public static void main(String[] args)
    {
    	System.out.println(checkBankCard("6228480402564890018"));
    }
    
//	public static void main(String[] args) {
//	    String reg = "18632116655";
//	    String data = JsonUtil.toJson(reg);
//	    String post = HttpClient.post("http://192.168.199.127:8067/openapi/rest/hometop/homeinfo", AESUtil.encrypt(data));
//	    System.out.println(post);
//	    System.out.println(AESUtil.decrypt(post));
//	}
    
}
