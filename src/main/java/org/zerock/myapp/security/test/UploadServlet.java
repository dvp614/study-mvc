package org.zerock.myapp.security.test;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;



@Log4j2
@NoArgsConstructor

//최대파일업로드크기 = 2MB제한
@MultipartConfig(location="C:/temp/upload", maxFileSize=1024 * 1024 * 2)	

@WebServlet("/Upload")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		log.trace("service(req, res) invoked.");

		// Step1. 요청(전송) 파라미터에 다국어문자가 깨지지 않도록 문자집합 지정
		req.setCharacterEncoding("utf8");
		
		// ============================================= //
		// Common-Step: 
		// ============================================= //
		// multiple 속성이 있는 <input type="file" name="uploadFile" multiple> 태그로
		// 업로드된 복수 개의 파일을 저장하는 로직
		// ============================================= //
		
		// Step2. 요청메시지의 Body에 들어있는 파일데이터를 얻어내기 위해서
		//        서블릿 컨테이너가 기본제공하는 업로드 기능을 이용합니다.
		
		// 2-2. 요청메시지의 Body에 들어있는 모든 멀티파트를 얻어냅니다.
		//      왱? "멀티파트(Multi-Part)"가 뭐지???
		Collection<Part> parts = req.getParts();
		parts.forEach(log::info);	// 획득한 모든 파트를 출력해봅니다.
		
		// 2-3. 2-2에서 얻어낸 컬렉션에서 각각의 파트를 끄집어내기 위해서
		//     Collection 인터페이스에 선언된 iterator() 메소드 호출로
		//     Iterator 타입의 열거객체(Iterator)를 얻어냅니다.
		Iterator<Part> iter = parts.iterator();
		
		// 2-4. 열거객체가 가지고 있는 모든 요소를 하나씩 순회(Traverse)합니다.
		while(iter.hasNext()) {	// 그 다음 요소가 있나요?
			Part part = iter.next(); // 있으면, 개별 파트를 얻어내고
			
			// 2-5. 첨부파일을 포함하고 있는 Part만 필터링
			String name = part.getName();	// 각 파트로부터, 파트이름을 획득
			
			// 2-6. 파트 이름이 "uploadFile"과 같다면...if 블록 수행
			if("uploadFile".equals(name)) {	// 파일데이터를 가지고 있는 Part이다!
				log.info("\t+ file size: {}", part.getSize());
				
				// 2-7. 각 파트로부터, 업로드된 파일의 이름을 획득
				String submittedFileName = part.getSubmittedFileName();
				
				// 2-8. 획득한 업로드 파일명으로, 실제 지정된 폴더에 파일 저장
				part.write(submittedFileName);
				
				// 2-9. 업로드에 사용된 임시파일 삭제
				part.delete();					
			} else  // 파트이름이 같지 않다면, 
				continue;	// Skip처리: 일반 Part 라면 제외시킴
		} // while	
		
		// ============================================= //
		// 아래의 일련의 코드는, 1개의 Part에 대한 다양한 정보획득 및 파일저장 로직
		// ============================================= //
				
		// ----------------------------------------------
		// Step.1 일반적인 전송파라미터의 값 획득
//		String writer = req.getParameter("writer");
//		log.info("\t+ writer: {}", writer);
		
		
		// ----------------------------------------------
		// Step.2 파일데이터가 저장된 멀티파트의 개별파트마다,
		//		  @MultipartConfig 어노테이션의 location 속성에 지정된 경로에
		//        첨부파일을 저장하자!
		
		// (1) 개별 파트부터 획득
//		Part part1 = req.getPart("uploadFile");
//		log.info("\t+ part1: {}", part1);
		
		// (2) 획득한 특정 파트의 각종 정보 획득 및 출력
//		String name = part1.getName();
//		long size = part1.getSize();
//		String contentType = part1.getContentType();
//		String submittedFileName = part1.getSubmittedFileName();
//		
//		log.info("\t+ 1. name: {}", name);
//		log.info("\t+ 2. size: {}", size);
//		log.info("\t+ 3. contentType: {}", contentType);
//		log.info("\t+ 4. submittedFileName: {}", submittedFileName);
		
//		Collection<String> headerNames = part1.getHeaderNames();
//		headerNames.forEach(log::info);				// using Method Reference
		
//		headerNames.forEach(h -> {					// using Lambda Expression
//			log.info("\t+ 5. header: {}", h);
//			log.info("\t+ 6. {} : {}", h, part1.getHeader(h));
//		});	// .forEach
		
		// (3) 파트에 저장된 파일 데이터를 디스크의 지정(@MultipartConfig)된 폴더에 저장
//		part1.write(submittedFileName);		// location=C:/temp/upload/{submittedFileName}
//		part1.delete(); 					// 디스크에 파일저장시 사용된 임시파일 삭제
		
		
	} // service

} // end class










