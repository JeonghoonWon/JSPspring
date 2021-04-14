package kr.or.ddit.listener;

import java.util.LinkedHashSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.Constants;
import kr.or.ddit.vo.MemberVO;

/**
 * listener 의 메서드를 보면 그 이벤트의 뭘 알수있어?
 * 
 * Application Lifecycle Listener implementation class CustomServletContextListener
 *
 */
//@WebListener  3.0 부터 사용 가능
public class CustomServletContextListener implements ServletContextListener {
	private static final Logger logger = 
			LoggerFactory.getLogger(CustomServletContextListener.class);
	
	 private ServletContext application;
	 
	 /**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent sce) { 
		logger.info("{}에서 어플리케이션 초기화 이벤트 처리", getClass().getName());
		
		//application scope 에 미리 방문자수 0 을 넣어둔다.
		application = sce.getServletContext();
		application.setAttribute(Constants.SESSIONCOUNTATTRNAME,0);
		application.setAttribute(Constants.USERLISTATTRNAME,new LinkedHashSet<MemberVO>());
		application.setAttribute("cPath", application.getContextPath());
	}
		
	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce) { 
    	logger.info("{}에서 어플리케이션 소멸 이벤트 처리", getClass().getName());
    	application = sce.getServletContext();
		application.setAttribute(Constants.SESSIONCOUNTATTRNAME,0);
    }

}
