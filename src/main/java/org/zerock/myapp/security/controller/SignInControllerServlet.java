package org.zerock.myapp.security.controller;

import java.io.IOException;
import java.io.Serial;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.zerock.myapp.security.domain.Credential;
import org.zerock.myapp.security.domain.UserDTO;
import org.zerock.myapp.security.domain.UserVO;
import org.zerock.myapp.security.service.AuthService;
import org.zerock.myapp.security.service.UserService;
import org.zerock.myapp.security.util.SharedAttributes;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@NoArgsConstructor
@Log4j2

@WebServlet(SharedAttributes.SIGNIN_URI)
public class SignInControllerServlet extends HttpServlet {
	@Serial private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		log.trace("serviece (req,res) invoked.");
		
		// 1 request 객체로부터 매핑정보를 추출
		HttpServletMapping mapping = req.getHttpServletMapping();
		log.info("\t+ matchValue : {}, patter : {}, sevletName : {}, mappingMatch : {}",
				mapping.getMatchValue(),
				mapping.getPattern(),
				mapping.getServletName(),
				mapping.getMappingMatch());
		
		try {
			// 2-1 비지니스 계층에서 로그인구현에 필요한 서비스
			UserService userService = new UserService();
			AuthService authService = new AuthService();
			
			HttpSession newSession = authService.createSession(req);
			
			// 2-3 로그인(인증) 처리에 필요한 모든 전송 파라미터 수집
			final String username = req.getParameter("username");
			final String password = req.getParameter("password");
			
			// 2-4 수집된 모든 전송파라미터를 DTO로 저장
			UserDTO dto = new UserDTO();
			dto.setUsername(username);
			dto.setPassword(password);
			log.info("\t+ dto : {}", dto);
			
			// 2-5 DTO로 전송된 정보로 사용자를 찾아냈다면,
			//     (1) 인증에 성공한 것이고
			//     (2) 인증된 사용자에 대한 신원정보 => Credential을 생성
			//     (3) 생성한 Credential객체를, Session Scope에 저장합니다.
			
			UserVO vo = userService.findByUsername(dto);
			Credential credential = authService.createCredential(vo);
			newSession.setAttribute(SharedAttributes.CREDENTIAL, credential);
			
			if(vo==null) {
				authService.redirectTo(res, SharedAttributes.LOGIN_URI);
			}else {
				authService.redirectTo(res, SharedAttributes.MEMBER_URI);
			}
		}catch(Exception e) {
			throw new ServletException(e);
			
		} // try-catch
	} // service

} // end class
