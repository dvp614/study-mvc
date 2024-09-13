package org.zerock.myapp.view;

import java.io.IOException;
import java.io.Serial;

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
@WebServlet(SharedAttributes.LOGIN_URI)
public class LoginControllerServlet extends HttpServlet {
	@Serial private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		log.trace("service(req,res) invoked.");

		try {
			final String viewName = "login";
			req.setAttribute(SharedAttributes.VIEW_NAME, viewName);
			
			String requestURI = req.getRequestURI();
			
			
			// ROLE_USER, ROLE_ADMIN, ROLE_AUTHENTICATED, ROLE_ANONYMOUS
			String requiredRole = SharedAttributes.ROLE_ANONYMOUS;
			
			req.setAttribute(SharedAttributes.REQUEST_URI, requestURI);
			req.setAttribute(SharedAttributes.REQUIRED_ROLES, requiredRole);
		}catch(Exception e) {
			throw new IOException(e);
		} // try-catch
	} // service

} // end class
