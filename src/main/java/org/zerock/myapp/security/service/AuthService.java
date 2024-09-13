package org.zerock.myapp.security.service;

import java.sql.Date;
import java.util.Objects;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.zerock.myapp.security.domain.Credential;
import org.zerock.myapp.security.domain.UserVO;
import org.zerock.myapp.security.util.SharedAttributes;

import lombok.extern.log4j.Log4j2;

@Log4j2

public class AuthService {
	public void redirectTo(HttpServletResponse res, String redirectURI) throws Exception {
		log.trace("redirectTo(res, {}) invoked.", redirectURI);

		Objects.requireNonNull(res);
		Objects.requireNonNull(redirectURI);

		res.sendRedirect(redirectURI);
		log.info("\t+ Re-Directed to the {}", redirectURI);
	} // redirectTo

	public HttpSession createSession(HttpServletRequest req) throws Exception {
		log.trace("createSession(req) invoked.");

		Objects.requireNonNull(req);

		HttpSession oldSession = req.getSession(false);
		this.destroySession(oldSession);

		HttpSession newSession = req.getSession(true);
//		HttpSession newSession = req.getSession();

		String sessionId = newSession.getId();
		Date creationTime = new Date(newSession.getCreationTime());

		log.info("\t+ New Sessin(%s) Is Created At (%s)".formatted(sessionId, creationTime));

		return newSession;
	} // createSession

	public void destroySession(HttpSession session) {
		log.trace("destroySession(session) invoked.");

		if (session != null) {
			String sessionId = session.getId();
			Date creationTime = new Date(session.getCreationTime());

			session.invalidate();

			log.info("\t+ Current Session(%s) Created At (%s) Destroyed.".formatted(sessionId, creationTime));
		} // if
	} // destorySession

	public Credential createCredential(UserVO vo) throws Exception {
		log.trace("createCredential({}) invoked.", vo);

		Credential credential = null;

		if (vo != null) { // 인증에 성공한 경우에만, Model인 VO가 null 이 아님
			credential = new Credential(vo.getUsername(), vo.getRole());
		} // if

		log.info("\t+ credential : {}", credential);

		return credential;
	} // createCredential
	
	// 현재 세션의 금고상자안에 신분증 존재여부 확인
	public boolean isAuthenticated(HttpSession session) throws Exception{
		log.trace("isAuthenticated({}) invoked.", session);
		
		// Step1. 매개변수가 null인지 아닌지 체크
		if(session == null) return false;
		
		// Step2. Session Scope에 신분증 존재여부 확인
		Credential credential = this.getCredential(session);
		return(credential != null);
	} // isAuthenticated
	
	// 현재 금고상자에서 Credential을 얻어내고, 여기서 권한명을 추출해서,
	// 보호된 자원이 요구하는 권한들과 비교하여, 권한매칭이 되면 true 반환, 안되면 false 반환
	public boolean isAuthorized(HttpSession session, Set<String> requiredRoles) throws Exception{
		log.trace("isAuthorized({}, {}) invoked.", session, requiredRoles);
		
		if(session == null) return false;    // 인가 실패
	
		Credential credential = this.getCredential(session);
		if(credential == null) return false; // 인가 실패
		
		// 권한비교
		String myRole = credential.getRole();
		if(myRole == null || "".equals(myRole)) return false; // 인가 실패
		
		return requiredRoles.contains(myRole); // 인가 실패 또는 성공 반환
	} // isAuthorized

	public Credential getCredential(HttpSession session) throws Exception{
		log.trace("getCredential({}) invoked.", session);
		
		// 매개변수가 null이면, null을 반환, 아니면 그냥 통과
		Objects.<HttpSession>requireNonNullElse(session, null);
		
		Object obj = session.getAttribute(SharedAttributes.CREDENTIAL);
		log.info("\t+ obj : {}", obj);
		
		// 결과값을 반환
		if(obj instanceof Credential credential) return credential;
		else return null;
	} // getCredential
	
} // end class
