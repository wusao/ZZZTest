/**
* Copy Right @ EA.Corp   
* @Title: UserTest.java 
* @Package personal.wade.test.thread 
* @Description:  
* @author Wade.wuchao
* @date 2016年1月18日
*/
package personal.wade.test.thread;

import java.io.Serializable;

/** 
* @ClassName: UserTest 
* @Description: 
* @author Wade.wuchao
* @date 2016年1月18日 
*  
*/
public class UserTest implements Serializable {  
	  
    /** 
     *  
     */  
    private static final long serialVersionUID = 3232456789876543L;  
      
    private Long id;  
    private String account;  
    private Integer money;  
    private String name;  
      
    public UserTest(Long id, String account, Integer money, String name) {  
        super();  
        this.id = id;  
        this.account = account;  
        this.money = money;  
        this.name = name;  
    }  
    public UserTest() {  
        super();  
        // TODO Auto-generated constructor stub  
    }  
    public Long getId() {  
        return id;  
    }  
    public void setId(Long id) {  
        this.id = id;  
    }  
    public String getAccount() {  
        return account;  
    }  
    public void setAccount(String account) {  
        this.account = account;  
    }  
    public Integer getMoney() {  
        return money;  
    }  
    public void setMoney(Integer money) {  
        this.money = money;  
    }  
    public String getName() {  
        return name;  
    }  
    public void setName(String name) {  
        this.name = name;  
    }  
}  
