/**
* Copy Right @ EA.Corp   
* @Title: DataStore.java 
* @Package personal.wade.test.thread 
* @Description:  
* @author Wade.wuchao
* @date 2016年1月18日
*/
package personal.wade.util;

import java.util.HashMap;
import java.util.Map;

import personal.wade.test.thread.UserTest;

/** 
* @ClassName: DataStore 
* @Description: 
* @author Wade.wuchao
* @date 2016年1月18日 
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
      
    // 为了保证数据的同步,对DataStore进行单例设计且对并发操作进行同步  
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
     * 缓存中有查询的对象就从缓存中取出,没有就查询数据库 
     * 当缓存中没有查询对象,避免并发时多次查询数据库,对其进行同步 
     */  
    public UserTest getUserFromStore(String account){  
        UserTest user = mapCache.get(account);  
        if(user == null){  
            //为避免死锁情况产生,不要用字符串对象作为对象锁  
            synchronized (lockObj2) {  
                user = mapCache.get(account);  
                if(user == null){  
                    //数据库查询 db.query(account)  
                    if("zhangsan".equalsIgnoreCase(account)){  
                        user =new UserTest(1L , "zhangsan"  , 10000 ,"张三" );  
                    }  
                    if("lisi".equalsIgnoreCase(account)){  
                        user = new UserTest(2L , "lisi"  , 10000 ,"李四" );  
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