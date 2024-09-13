package org.zerock.myapp.security.domain;

import java.io.Serial;
import java.io.Serializable;

import org.zerock.myapp.security.annotation.Identity;

import lombok.Value;
@Identity

@Value
public class Credential implements Serializable{
	@Serial private static final long serialVersionUID = 1L;

	private String username;
	private String role;
	
} // end class
