package personal.wade.util;

import com.alibaba.druid.pool.DruidDataSource;

public class ConnectionPoolManager {
	public static final String url = "jdbc:mysql://127.0.0.1:3306/test";
	public static final String name = "com.mysql.jdbc.Driver";
	public static final String user = "root";
	public static final String password = "root";

	// druidÁ¬½Ó³ØÊµÀý
	public static volatile DruidDataSource dataSource = null;

	public String getConnectionPoolKey() {
		return "druid";
	}
	
	private ConnectionPoolManager(){
		dataSource = new DruidDataSource();
		dataSource.setMaxActive(100);
		dataSource.setMaxIdle(30);
		dataSource.setMinIdle(20);
		dataSource.setInitialSize(10);
		dataSource.setPoolPreparedStatements(true);
		dataSource.setTestOnBorrow(false);
		dataSource.setTestOnReturn(false);
		dataSource.setMinEvictableIdleTimeMillis(30);
		dataSource.setMaxWaitThreadCount(10000);
		dataSource.setDriverClassName(name);
		dataSource.setUrl(url);
		dataSource.setUsername(user);
		dataSource.setPassword(password);
	}

	public static DruidDataSource getDataSource() {
		if (dataSource == null) {
			synchronized(ConnectionPoolManager.class){
				if(dataSource == null){
					new ConnectionPoolManager();
				}
			}
		}
		return dataSource;
	}
	
	

}
