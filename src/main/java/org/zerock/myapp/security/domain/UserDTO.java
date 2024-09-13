package org.zerock.myapp.security.domain;

import java.io.Serial;
import java.io.Serializable;

import org.zerock.myapp.security.annotation.VO;

import lombok.Data;

@VO
@Data
public class UserDTO implements Serializable{
	@Serial private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	
} // end class
