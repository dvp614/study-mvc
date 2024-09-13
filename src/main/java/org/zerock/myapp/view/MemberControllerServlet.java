package org.zerock.myapp.view;

import java.io.IOException;
import java.io.Serial;

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
@WebServlet(SharedAttributes.MEMBER_URI)
public class MemberControllerServlet extends HttpServlet {
	@Serial private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		log.trace("service(req,res) invoked.");

		try {
			String model = "Transfer Success";
			String viewName = "member";
			
			req.setAttribute(SharedAttributes.MODEL, model);
			req.setAttribute(SharedAttributes.VIEW_NAME, viewName);
			
			String requestURI = req.getRequestURI();
			

			// ROLE_USER, ROLE_ADMIN, ROLE_AUTHENTICATED, ROLE_ANONYMOUS
			String requiredRole = SharedAttributes.ROLE_AUTHENTICATED;
			
			req.setAttribute(SharedAttributes.REQUEST_URI, requestURI);
			req.setAttribute(SharedAttributes.REQUIRED_ROLES, requiredRole);

			// Step3. Determine Whether Doing Authentication.
			boolean doAuthenticate = true;
			boolean doAuthorize = false;
			
			req.setAttribute(SharedAttributes.DO_AUTHENTICATE, doAuthenticate);
			req.setAttribute(SharedAttributes.DO_AUTHORIZE, doAuthorize);
			
			// Step4. Do Authentication or View Resolving with SharedAttributes.AUTHENTICATE.
			RequestDispatcher dispatcher;
			
			if(doAuthenticate) {
				// (1) forward to the SharedAttributes.AUTHENTICATE_URI.
				dispatcher = req.getRequestDispatcher(SharedAttributes.AUTHENTICATE_URI);
			} else {
				// (2) forward to the SharedAttributes.VIEW_RESOLVER_URI.
				dispatcher = req.getRequestDispatcher(SharedAttributes.VIEW_RESOLVER_URI);
			} // if-else
			
			dispatcher.forward(req, res);
			
			
			log.info("Done.");
		} catch(Exception e) {
			throw new ServletException(e);
		} // try-catch
	} // service

} // end class
