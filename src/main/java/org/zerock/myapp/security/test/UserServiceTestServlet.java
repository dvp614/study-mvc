package org.zerock.myapp.security.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zerock.myapp.security.domain.UserDTO;
import org.zerock.myapp.security.domain.UserVO;
import org.zerock.myapp.security.service.UserService;

import lombok.Cleanup;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@NoArgsConstructor
@Log4j2

@WebServlet("/userServiceTest")
public class UserServiceTestServlet extends HttpServlet {
	@Serial private static final long serialVersionUID = 1L;
	
	private UserService userService = new UserService();

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		log.trace("service(req,res) invoked.");
		
		try {
			String username = req.getParameter("username");
			String password= req.getParameter("password");
			
			UserDTO dto = new UserDTO();
			dto.setUsername(username);
			dto.setPassword(password);
			
			log.info("\t+ dto : {}", dto);
			
			res.setCharacterEncoding("utf8");
			res.setContentType("text/html; charset=utf8");
			
			@Cleanup PrintWriter out = res.getWriter();
			out.println("<h3>UserServiceTest</h3>");
			out.println("<hr>");
			
			UserVO vo = this.userService .findByUsername(dto);
			log.info("\t+ vo : {}", vo);
			
			out.println("<p>%s</p>".formatted(vo));
			
			out.flush();
		}catch(Exception e) {
			throw new ServletException(e);
		}
	} // service

} // end class
