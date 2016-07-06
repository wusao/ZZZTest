/**
* Copy Right @ EA.Corp   
* @Title: SynchronizedTest.java 
* @Package personal.wade.test.thread 
* @Description:  
* @author Wade.wuchao
* @date 2016��1��18��
*/
package personal.wade.test.thread;

import personal.wade.util.DataStore;

/** 
* @ClassName: SynchronizedTest 
* @Description: 
* @author Wade.wuchao
* @date 2016��1��18�� 
*  
*/
public class SynchronizedTest {  

	private static Byte[] lockObj = new Byte[0];  
	
    public void opAccountMoney(UserTest user, int getMoney, String execDate) {  
    	synchronized(user){  
    	DataStore store = DataStore.getStore();  
          
//        UserTest user = store.getUserFromStore(account);  
    	user = store.getUserFromStore(user.getAccount()); 
        if (user != null) {  
  
            try {  
                user = get(user, getMoney);  
                store.putUserInStore(user);  
            } catch (Exception e) {  
                // TODO Auto-generated catch block  
                System.out.println(user.getName() + "����!");  
            }  
  
        }  
    	}
    }  
  
    public UserTest get(UserTest user, int money) throws Exception {  
        if (user.getMoney() >= money) {  
            sleepTest();  
            user.setMoney(user.getMoney() - money);  
            System.out.println("Thread.currentThread().getId() : "  
                    + Thread.currentThread().getId() + "      "  
                    + user.getName() + " ���: " + user.getMoney());  
        } else {  
            throw new Exception();  
        }  
        return user;  
    }  
  
    private void sleepTest() {  
        try {  
            Thread.currentThread().sleep(500);  
        } catch (InterruptedException e2) {  
            // TODO Auto-generated catch block  
            e2.printStackTrace();  
        }  
    }  
  
}  