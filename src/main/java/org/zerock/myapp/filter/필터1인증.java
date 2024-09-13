package org.zerock.myapp.filter;

import java.io.IOException;
import java.io.Serial;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor
@WebFilter( "/3" )
public class 필터1인증 extends HttpFilter implements Filter {
	@Serial private static final long serialVersionUID = 1L;


	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) 
			throws IOException, ServletException {
		log.info("(1) 여기는 전처리 코드가 수행되는 부분입니다.");
		
		chain.doFilter(req, res);
		
		//(2) 후처리 코드
		log.info("(2) 여기는 후처리 코드가 수행되는 부분입니다.");
	}
}
