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
@WebServlet(SharedAttributes.FORBIDDEN_URI)
public class ForbiddenControllerServlet extends HttpServlet {
	@Serial private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		log.trace("service(req,res) invoked.");
		
		try {
			final String viewName = "forbidden";
			req.setAttribute(SharedAttributes.VIEW_NAME, viewName);
			

			// Step3. Forward to the SharedAttributes.VIEW_RESOLVER_URI
			//				with No authentication & No authorization.
			RequestDispatcher dispatcher = req.getRequestDispatcher(SharedAttributes.VIEW_RESOLVER_URI);
			dispatcher.forward(req, res);
			
			log.info("Done.");
		}catch(Exception e) {
			throw new IOException(e);
		} // try-catch
	} // service

} // end class
