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

@NoArgsConstructor
@Log4j2

@WebServlet(SharedAttributes.VIEW_RESOLVER_URI)
public class ViewResolverServlet extends HttpServlet {
	@Serial private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		log.trace("serviece (req,res) invoked.");
		
		try { // 핵심로직 : 인증체크 => 현재 요청을 보낸 브라우저에 할당된 세션금고상자안에
			String viewName = (String) req.getAttribute(SharedAttributes.VIEW_NAME);
			String resolvedViewPath = SharedAttributes.VIEW_PREFIX + viewName + SharedAttributes.VIEW_SUFFIX;
			
			log.info("\t+ viewName: {}, resolvedViewPath: {}", viewName, resolvedViewPath);
			
			// Step2. Set resolved view path to the request scope.
			req.setAttribute(SharedAttributes.RESOLVED_VIEW_PATH, resolvedViewPath);
			
			// Step2. Request Forwading to the SharedAttributes.VIEW_URI with resolved view path.
			RequestDispatcher dispatcher = req.getRequestDispatcher(SharedAttributes.VIEW_URI);
			dispatcher.forward(req, res);
			
			log.info("\t+ Done.");
		}catch(Exception e) {
			throw new ServletException(e);
			
		} // try-catch
	} // service

} // end class
