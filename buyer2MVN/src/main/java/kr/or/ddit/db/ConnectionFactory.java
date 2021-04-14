package kr.or.ddit.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 * Factory Object[Method] Pattern
 * 	: 객체 생성을 전담하는 객체를 운영하는 구조.
 *
 */
public class ConnectionFactory {
	private static String driverClassName;
	private static String url;
	private static String user;
	private static String password;
	private static String connectionMessage;
	private static DataSource ds; //상위타입
	
	static {
//		Properties properties = new Properties(); //비휘발성으로 운영하는 api 특징을 갖고있다.
//		try(
//			InputStream is = ConnectionFactory.class.getResourceAsStream("dbinfo.properties"); //navigator에서 같은 위치에 있음을 확인할 수 있다.
//		) { //단 한번 실행하는 구조로 변경
//			properties.load(is);
			ResourceBundle bundle = ResourceBundle.getBundle("kr.or.ddit.db.dbinfo", Locale.ENGLISH);
			
			driverClassName = bundle.getString("driverClassName");
			url = bundle.getString("url");
			user = bundle.getString("user");
			password = bundle.getString("password");
			connectionMessage = bundle.getString("connectionMessage");
			
			int initialSize = Integer.parseInt(bundle.getString("initialSize"));
			int maxTotal = Integer.parseInt(bundle.getString("maxTotal"));
			long maxWait = Long.parseLong(bundle.getString("maxWait"));
			
			ds = new BasicDataSource();
			((BasicDataSource)ds).setDriverClassName(driverClassName);
			((BasicDataSource)ds).setUrl(url);
			((BasicDataSource)ds).setUsername(user);
			((BasicDataSource)ds).setPassword(password);
			
			((BasicDataSource)ds).setMaxTotal(initialSize);
			((BasicDataSource)ds).setInitialSize(maxTotal);
			((BasicDataSource)ds).setMaxWaitMillis(maxWait); 
			
			
//			ds = new OracleDataSource(); //드라이버를 따로 로딩할 필요가 없어졌다.
//			((OracleDataSource)ds).setURL(url); //구현체가 가진 setter
//			((OracleDataSource)ds).setUser(user);
//			((OracleDataSource)ds).setPassword(password); 
			
//			Class.forName(driverClassName);
//		} catch (IOException e) {
//			throw new RuntimeException(e); //예외발생, Unchecked Exception
//		} 
	}
	
	public static Connection getConnection() throws SQLException{
//	 	return DriverManager.getConnection(url, user, password);
		System.out.println(connectionMessage);
		return ds.getConnection();
	}
	
}
