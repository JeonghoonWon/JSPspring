package kr.or.ddit.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

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
	
	static {
		Properties properties = new Properties();	// 외부로 데이터를 뺄 수 있다.
		
		try(
			InputStream is = ConnectionFactory.class.getResourceAsStream("dbinfo.properties");
		) {
			properties.load(is);
			driverClassName = properties.getProperty("driverClassName");
			url = properties.getProperty("url");
			user = properties.getProperty("user");
			password = properties.getProperty("password");
			Class.forName(driverClassName);
			
		} catch (ClassNotFoundException | IOException e) {
			throw new RuntimeException(e);	
		}
	}
	
	public static Connection getConnection() throws SQLException{
//		String url = "jdbc:oracle:thin:@localhost:1521:xe";
//		String user = "WJH12";
//		String password = "java";
		return DriverManager.getConnection(url,user,password);
	}
}
