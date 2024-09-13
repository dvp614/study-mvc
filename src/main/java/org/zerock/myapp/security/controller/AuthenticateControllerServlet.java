package org.zerock.myapp.security.controller;

import java.io.IOException;
import java.io.Serial;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.zerock.myapp.security.service.AuthService;
import org.zerock.myapp.security.util.SharedAttributes;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@NoArgsConstructor
@Log4j2

@WebServlet(SharedAttributes.AUTHENTICATE_URI)
public class AuthenticateControllerServlet extends HttpServlet {
	@Serial private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		log.trace("serviece (req,res) invoked.");

		try {
			// Step1. Get current HTTP session if present.
			HttpSession session = req.getSession(false);
			
			// Step2. Authenticate current web browser.
			AuthService authService = new AuthService();
			
			boolean isAuthenticated = authService.isAuthenticated(session);
			log.info("\t+ isAuthenticated: {}", isAuthenticated);
			
			// Step3. If Not authenticated, Re-direct to the `SharedAttributes.LOGIN_URI`".
			//			    If authenticated & do authorize, forward to the `SharedAttributes.AUTHORIZE_URI`.
			//				If authenticated & do Not authorize, forward to the `SharedAttributes.VIEW_RESOLVER_URI`.
			if(!isAuthenticated) {		// if Not authenticated.
				res.sendRedirect(SharedAttributes.LOGIN_URI);
				log.info("\t+ Re-direction to the {} Done.", SharedAttributes.LOGIN_URI);
			} else {							// if authenticated.
				Boolean doAuthorize = (Boolean) req.getAttribute(SharedAttributes.DO_AUTHORIZE);
				
				RequestDispatcher dispatcher;
				if(doAuthorize) {
					// (1) forward to the SharedAttributes.AUTHORIZE_URI.
					dispatcher = req.getRequestDispatcher(SharedAttributes.AUTHORIZE_URI);
				} else {
					// (2) forward to the SharedAttributes.VIEW_RESOLVER_URI.
					dispatcher = req.getRequestDispatcher(SharedAttributes.VIEW_RESOLVER_URI);
				} // if-else
				
				dispatcher.forward(req, res);
			} // if-else
			log.info("Done.");
		} catch(Exception e) {
			throw new ServletException(e);
		} // try-catch
	} // service

} // end class
