package kr.or.ddit.listener;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.Constants;
import kr.or.ddit.vo.MemberVO;

/**
 * Application Lifecycle Listener implementation class CustomSessionaddListener
 *
 */
@WebListener
public class CustomSessionaddListener implements HttpSessionAttributeListener {
	private static final Logger logger = 
			LoggerFactory.getLogger(CustomSessionaddListener.class);
	

    public void attributeAdded(HttpSessionBindingEvent event)  { 
    	HttpSession session = event.getSession();
		ServletContext application = session.getServletContext();
		MemberVO authMember =(MemberVO) session.getAttribute("authMember");
    	
		logger.info("session authMember : {}" , authMember.getMem_id());
		
    }
    
    public void attributeRemoved(HttpSessionBindingEvent event)  {
    	
    }

    public void attributeReplaced(HttpSessionBindingEvent event)  {
    	
    }
	
}
