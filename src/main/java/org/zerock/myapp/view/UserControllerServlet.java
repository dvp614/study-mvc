package org.zerock.myapp.view;

import java.io.IOException;
import java.io.Serial;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zerock.myapp.security.util.SharedAttributes;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@NoArgsConstructor
@Log4j2
@WebServlet(SharedAttributes.USER_URI)
public class UserControllerServlet extends HttpServlet {
	@Serial private static final long serialVersionUID = 1L;


	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		log.trace("service(req,res) invoked.");

		try {
			final String viewName = "user";
			req.setAttribute(SharedAttributes.VIEW_NAME, viewName);
			
			// ROLE_USER, ROLE_ADMIN, ROLE_AUTHENTICATED, ROLE_ANONYMOUS
			String requiredRole = SharedAttributes.ROLE_USER;

			String requestURI = req.getRequestURI();
			req.setAttribute(SharedAttributes.REQUEST_URI, requestURI);
			req.setAttribute(SharedAttributes.REQUIRED_ROLES, requiredRole);
			
			boolean doAuthenticate = true;
			boolean doAuthorize = true;
			
			req.setAttribute(SharedAttributes.DO_AUTHENTICATE, doAuthenticate);
			req.setAttribute(SharedAttributes.DO_AUTHORIZE, doAuthorize);
			
			// Step3. 나를 호출하는데 필요한 권한정보도 설정에서 넘겨줍니다.
			Set<String> requiredRoles = Set.<String>of("ROLE_USER", "ROLE_ADMIN");
			
			req.setAttribute(SharedAttributes.REQUIRED_ROLES, requiredRoles);
			
			RequestDispatcher dispatcher;
			
			if(doAuthenticate) { // 인증체크가 필요하면
				// (1) forward to the SharedAttributes.AUTHENTICATE_URI.
				dispatcher = req.getRequestDispatcher(SharedAttributes.AUTHENTICATE_URI);
			} else { // 인증체크마저도 필요없다면
				// (2) forward to the SharedAttributes.VIEW_RESOLVER_URI.
				dispatcher = req.getRequestDispatcher(SharedAttributes.VIEW_RESOLVER_URI);
			} // if-else
			
			dispatcher.forward(req, res);
			
			log.info("Done.");
		}catch(Exception e) {
			throw new IOException(e);
		} // try-catch
	} // service

} // end class
