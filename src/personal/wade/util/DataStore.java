/**
* Copy Right @ EA.Corp   
* @Title: DataStore.java 
* @Package personal.wade.test.thread 
* @Description:  
* @author Wade.wuchao
* @date 2016��1��18��
*/
package personal.wade.util;

import java.util.HashMap;
import java.util.Map;

import personal.wade.test.thread.UserTest;

/** 
* @ClassName: DataStore 
* @Description: 
* @author Wade.wuchao
* @date 2016��1��18�� 
*  
*/
public class DataStore {  
    private static Map<String , UserTest>  mapCache = new HashMap<String, UserTest>();  
  
    private static DataStore store;  
      
    private static Byte[] lockObj1 = new Byte[0];  
    private static Byte[] lockObj2 = new Byte[0];  
      
    private DataStore() {  
        super();  
        // TODO Auto-generated constructor stub  
    }  
      
    // Ϊ�˱�֤���ݵ�ͬ��,��DataStore���е�������ҶԲ�����������ͬ��  
    public static DataStore getStore(){  
        if(store == null){  
            synchronized (lockObj1) {  
                if(store == null){  
                    store = new DataStore();  
                }  
            }  
        }  
        return store;  
    }  
      
    public void putUserInStore(UserTest user){  
        mapCache.put(user.getAccount(), user);  
        //DB.saveOrUpdate(user);  
    }  
      
    /* 
     * �������в�ѯ�Ķ���ʹӻ�����ȡ��,û�оͲ�ѯ���ݿ� 
     * ��������û�в�ѯ����,���Ⲣ��ʱ��β�ѯ���ݿ�,�������ͬ�� 
     */  
    public UserTest getUserFromStore(String account){  
        UserTest user = mapCache.get(account);  
        if(user == null){  
            //Ϊ���������������,��Ҫ���ַ���������Ϊ������  
            synchronized (lockObj2) {  
                user = mapCache.get(account);  
                if(user == null){  
                    //���ݿ��ѯ db.query(account)  
                    if("zhangsan".equalsIgnoreCase(account)){  
                        user =new UserTest(1L , "zhangsan"  , 10000 ,"����" );  
                    }  
                    if("lisi".equalsIgnoreCase(account)){  
                        user = new UserTest(2L , "lisi"  , 10000 ,"����" );  
                    }  
                    putUserInStore(user);  
                    /*** if continue do no synchronized somethings need : ***/  
                    //lockObj2.notify();  
                    //do something................  
                }  
            }  
        }  
        return user;  
    }  
}  