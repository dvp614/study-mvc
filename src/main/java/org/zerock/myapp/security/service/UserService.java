package org.zerock.myapp.security.service;

import org.zerock.myapp.security.dao.UserDAO;
import org.zerock.myapp.security.domain.UserDTO;
import org.zerock.myapp.security.domain.UserVO;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor
public class UserService {
	
	// Impoertant :
	//		The `@PostContruct` & `@PreDestory` annotations do work
	//      Only in the java *Servlet class.
	
	// 수행할 비지니스 로직 : 사용자 정보 조회
	// 비지니스 로직의 수행경과로 발생하는 데이터를 우리는 MVC패턴에서
	// 얘기하는 "Model"이라고 합니다.
	// 조회된 사용자 정보를 가지고 있는게, DAO가 반환한 VO객체이고,
	// 이것이 곧 여기서 구현되는 비지니스로직의 수행결과 발생한 데이터 즉,
	// "Model"이 되는 것입니다. 오해마실것은, MVC패턴에서, Model은 무조건
	// VO객체는 절대 아닙니다.
	public UserVO findByUsername(UserDTO dto) throws Exception{
		log.trace("findByUsername({}) invoked.", dto);
		
		UserDAO dao = new UserDAO();
		
		UserVO vo = dao.selectByUserName(dto);
		log.info("\t+ vo : {}", vo);
		return vo;
	} // findByUsername
} // end class
