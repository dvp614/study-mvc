package org.zerock.myapp.security.controller;

import java.io.IOException;
import java.io.Serial;
import java.util.Set;

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

@WebServlet(SharedAttributes.AUTHORIZE_URI)
public class AuthorizeControllerServlet extends HttpServlet {
	@Serial private static final long serialVersionUID = 1L;

	// 인가 = 인증을 기반으로 + 권한이 적절한지를 체크
	// 원리 => (1) 현재 요청을 보낸 브라우저에 할당된 세션을 얻어내고(req.getSession(fales))
	//         (2) 얻어낸 세션으로 Session Scope(=금고상자)에서 Credential를 얻음
	//             * 중요 : Credential 객체안에 필드로, 인증성공한 아이디 + 권한명이 포함됨
	//	       (3) Credential에 저장되어 있는 권한명을 추출한다
	//         (4) (3)에서 얻어낸 권한명과 요청을 보낸 보호된 자원이 필요로 하는 권한들과
	//             매칭되는지를 확인(if 매칭되면 -> 인가성공, else -> 인가실패)
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		log.trace("serviece (req,res) invoked.");
	
		try { 
			// Step1. 인가체크 수행
			HttpSession session = req.getSession(false);
			log.trace("\t+ session : {}", session);
			

			@SuppressWarnings("unchecked")
			Set<String> requiredRoles = (Set<String>) req.getAttribute(SharedAttributes.REQUIRED_ROLES);
			log.trace("\t+ requiredRoles : {}", requiredRoles);
			
			// AuthService 의 isAuthorized 미소드 호출로 처리 위임
			AuthService service = new AuthService();
			boolean isAuthorized = service.isAuthorized(session, requiredRoles);
			log.trace("\t+ isAuthorized : {}", isAuthorized);
			
			// Step4. 인가에 성공하면, View Resolver로 포워드 수행
			RequestDispatcher dispatcher;
			if(isAuthorized) { // 인가체크에 성공
				dispatcher = req.getRequestDispatcher(SharedAttributes.VIEW_RESOLVER_URI);
			} else { // 인가체크에 실패
				dispatcher = req.getRequestDispatcher(SharedAttributes.FORBIDDEN_URI);
			} // if-else
			
			dispatcher.forward(req,res);
		}catch(Exception e) {
			throw new ServletException(e);
			
		} // try-catch
	} // service

} // end class
