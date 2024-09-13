package org.zerock.myapp.security.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.zerock.myapp.security.domain.UserDTO;
import org.zerock.myapp.security.domain.UserVO;
import org.zerock.myapp.security.util.SharedAttributes;

import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;

@Log4j2
//@NoArgsConstructor
public class UserDAO {
//	@Resource(name =) // XX, injection
	private DataSource dataSource;

	
	public UserDAO() throws Exception {
		log.trace("Default Constructor() invoked.");
		Context ctx = new InitialContext();
		Objects.requireNonNull(ctx);

//		Object obj = ctx.lookup("java:comp/env/jdbc/h2/test");
		Object obj = ctx.lookup(SharedAttributes.JNDI_PREFIX + SharedAttributes.DATA_SOURCE);
		if (obj instanceof DataSource dataSource) {
			this.dataSource = dataSource;
		} // if

		log.info("\t+ this.database : {}", this.dataSource);
	} // Default Constructor

	
	public UserVO selectByUserName(UserDTO dto) throws Exception {
		log.trace("selectByUserName({}) invoked. ", dto);
		Objects.requireNonNull(dto);

		if (dto.getUsername() == null || "".equals(dto.getUsername())) {
			throw new IllegalArgumentException("username is null or empty");
		} // if

		@Cleanup
		Connection conn = this.dataSource.getConnection();
		log.info("\t+ conn : {}", conn);

		String sql = "SELECT * FROM t_user WHERE username = ? and password = ?";

		@Cleanup
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, dto.getUsername());
		pstmt.setString(2, dto.getPassword());

		@Cleanup
		ResultSet rs = pstmt.executeQuery();

		if (rs.next()) {
			String username = rs.getString("username");
			String password = rs.getString("password");
			String role = rs.getString("role");

			return new UserVO(username, password, role);

		} else {			// if Not found.
			return null;
		} // if-else
		
	} // selectByUserName

} // end class
