package com.lsk.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
//@RequestMapping("upload2")
public class Upload2Controller {
	
	// uploadAjax2.jsp 화면 띄우기
	@GetMapping("uploadAjax2")
	public void uploadAjaxForm() {
		System.out.println("uploadAjax2 화면 띄우기 완료");
	}
	
	// uploadAjax2.jsp의 파일 업로드 Controller
	@PostMapping("uploadAjax2Action")
	public ResponseEntity<String> uploadAjax2Post(MultipartFile[] uploadFile) {
		
		System.out.println("uploadAjax2Action 파일업로드 완료");
		
		// 파일 업로드 폴더 경로 지정
		String uploadFolder = "C:\\upload";
		
		// uploadFile의 매개변수에 저장된 배열을 multipartFile에 대입하여 순서대로 출력
		for(MultipartFile multipartFile : uploadFile) {
			System.out.println("Upload File Name : "+multipartFile.getOriginalFilename());
			System.out.println("Upload File Name : "+multipartFile.getSize());
			
			String uploadFileName = multipartFile.getOriginalFilename();
			
			//File : 파일을 입/출력 담당 클래스
			//uploadFolder에 저장되어 있는 경로로 '실제 파일명으로 저장'
			File saveFile = new File(uploadFolder, uploadFileName);
//			System.out.println("aaaaaaaaaaaa");
			try {
				multipartFile.transferTo(saveFile);
//				System.out.println("bbbbbbbbbb");
			} catch (IOException e) {
				e.printStackTrace();
			} // .try
		}// .for
		return new ResponseEntity<>("aaaaaaaaaaaa",HttpStatus.OK);
				
	}// .uploadAjax2Post
}
