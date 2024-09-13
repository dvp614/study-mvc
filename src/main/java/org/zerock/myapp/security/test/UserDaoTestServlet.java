package org.zerock.myapp.security.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zerock.myapp.security.dao.UserDAO;
import org.zerock.myapp.security.domain.UserDTO;
import org.zerock.myapp.security.domain.UserVO;

import lombok.Cleanup;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@NoArgsConstructor
@Log4j2

@WebServlet("/userDaoTest")
public class UserDaoTestServlet extends HttpServlet {
	@Serial private static final long serialVersionUID = 1L;
	
	private UserDAO userDAO;

	@PostConstruct
	void postConstruct() {
		log.trace("postConstruct() invoked.");
		
		try {this.userDAO = new UserDAO(); } catch(Exception _ignored) {}
		
		Objects.requireNonNull(this.userDAO);
		log.info("\t+ this.userDAO : {}", this.userDAO);
	} // postConstruct
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		log.trace("servied(req,res) invoked.");
		
		try {
			String username = req.getParameter("username");
			String password= req.getParameter("password");
			
			UserDTO dto = new UserDTO();
			dto.setUsername(username);
			dto.setPassword(password);
			
			log.info("\t+ dto : {}", dto);
			
			res.setCharacterEncoding("utf8");
			res.setContentType("text/html; chatset=utf8");
			
			@Cleanup PrintWriter out = res.getWriter();
			out.println("<h3>UserDAOTest</h3>");
			out.println("<hr>");
			
			UserVO vo = this.userDAO.selectByUserName(dto);
			log.info("\t+ vo : {}", vo);
			
			out.println("<p>%s</p>".formatted(vo));
			
			out.flush();
		}catch(Exception e) {
			throw new ServletException(e);
		}// try-catch
		
	} // service

} // end class
