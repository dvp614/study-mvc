package org.zerock.myapp.security.controller;

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


@Log4j2
@NoArgsConstructor

@WebServlet(SharedAttributes.VIEW_URI)
public class ViewServlet extends HttpServlet {
	@Serial private static final long serialVersionUID = 1L;

	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		log.trace(">>> service(req, res) invoked.");

		try {
			// Step1. Get transfered resolved view path from request scope.
			String resolvedViewPath = (String) req.getAttribute(SharedAttributes.RESOLVED_VIEW_PATH);
			log.info("\t+ resolvedViewPath: {}", resolvedViewPath);

			// Step2. Invoke resolved view path.
			RequestDispatcher dispatcher = req.getRequestDispatcher(resolvedViewPath);
			dispatcher.forward(req, res);
			
			log.info("\t+ Done.");
		} catch(Exception e) {
			throw new ServletException(e);
		} // try-catch
	} // service

} // end class

