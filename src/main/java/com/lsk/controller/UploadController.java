package com.lsk.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.lsk.domain.AttachDTO;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnailator;

@Controller
public class UploadController {
	
	// upload.jsp 화면 띄우기
	@GetMapping("upload")
	public void uploadForm() {
		System.out.println("파일업로드 화면...");
	}
	
	// uploadAjax.jsp 화면 띄우기
	@GetMapping("uploadAjax")
	public void uploadAjaxForm() {
		System.out.println("파일업로드 화면...");
	}
	
	// upload.jsp의 form태그를 이용해서 파일 업로드 Controller
	@PostMapping("uploadAction")
	public void uploadAction(MultipartFile[] uploadFile) { 
		//MultipartFile에 있는 메서드 **MultipartFile을 추가하면 DTO가 필요없음
//		//사용자가 업로드한 실제 파일 이름 
//		System.out.println("MultipartFile = "+uploadFile[0].getOriginalFilename());
//		//사용자가 업로드한 실제 파일 크기
//		System.out.println("MultipartFile = "+uploadFile[0].getSize());
//		//사용자가 업로드한 실제 파일 양식
//		System.out.println("MultipartFile = "+uploadFile[0].getContentType());
//		//사용자가 업로드한 실제 파일 이름 
//		System.out.println("MultipartFile = "+uploadFile[1].getOriginalFilename());
//		//사용자가 업로드한 실제 파일 크기
//		System.out.println("MultipartFile = "+uploadFile[1].getSize());
//		//사용자가 업로드한 실제 파일 양식
//		System.out.println("MultipartFile = "+uploadFile[1].getContentType());
		
		//파일 업로드할 경로 지정
		String uploadFolder="C:\\upload";
			
		for(MultipartFile multiparFile : uploadFile) { // uploadFile의 매개변수에 저장된 배열을 multipartFile에 대입하여 순서대로 출력
			System.out.println(multiparFile);
			//사용자가 업로드한 실제 파일 이름 
			System.out.println("MultipartFile = "+multiparFile.getOriginalFilename());
			//사용자가 업로드한 실제 파일 크기
			System.out.println("MultipartFile = "+multiparFile.getSize());
			//사용자가 업로드한 실제 파일 양식
			System.out.println("MultipartFile = "+multiparFile.getContentType());
			
			//File : 파일을 입/출력 담당 클래스
			//uploadFolder에 저장되어 있는 경로로 '실제 파일명으로 저장'
			File saveFile = new File(uploadFolder,multiparFile.getOriginalFilename());
			
			//transferTo() 라는 메소드를 사용해서 '원하는 위치에 저장'하는데, 이것을 사용하기위해선 예외처리를 해주어야 함.
			try {
				multiparFile.transferTo(saveFile);
			} catch (Exception e) {
				e.printStackTrace();
			}// end try
		}// for문 end
	}
	
	// 년/월/일 폴더의 생성하기 위한 폴더 이름 추출하여 리턴
	private String getFolder() {
		//현재날짜를 추출(Tue Jan 18 09:34:09 KST 2022)
		Date date = new Date();
		//2022-01-18(yyyy-mm-dd형식으로 변경(:포맷))
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//month는 대문자로 하여야함. 소문자로 할 시 String타입으로 인식.
		//2022-01-18 -> 2022\01\18 (폴더를 만들기 위하여 \)
		String str=sdf.format(date);
		
		return str.replace("-", File.separator);// - 표시를 \(separator)로 바꿔라. 
	}
	
	// 썸네일 이미지 생성을 할 것 인지 안 할것인지에 대해 판단하는 메서드(사용자가 업로드 한 파일이 이미지이면 생성, 엑셀이나 한글 등 이미지가 아닌 것을 올리면 생성안함)
	private boolean checkImage(File file) {
		
		try {
			String contentType=Files.probeContentType(file.toPath());	// probeContentType : 파일의 타입을 알아내는 메서드
			return contentType.startsWith("image");	//그 파일의 타입이 image이면 true, 그렇지않으면 false
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	// uploadAjax.jsp의 form태그를 이용해서 파일 업로드 Controller
	@PostMapping(value="uploadAjax",produces={MediaType.APPLICATION_JSON_VALUE})
	//ResponseEntity : 사용자의 HttpRequest에 대한 응답 데이터를 포함하는 클래스
	public ResponseEntity<ArrayList<AttachDTO>> uploadAjaxAction(MultipartFile[] uploadFile) {
		//AttachDTO에 저장되는 값이 여러파일에 대한 값이면 배열로 처리가 되어야하므로 ArrayList타입이 되어야 함.
		ArrayList<AttachDTO>list = new ArrayList();
		//파일업로드 할 경로 지정
		String uploadFolder="C:\\upload";
		
		//폴더 생성						(기존 폴더,		현재폴더)를 결합
		File uploadPath = new File(uploadFolder,getFolder());
		
		//업로드 경로 저장
		String uploadFolderPath=getFolder();
		
		System.out.println("uploadPath="+uploadPath);
		
		//현재 만들려고 하는 폴더가 없으면
		if(uploadPath.exists()==false) {
			//폴더 생성
			uploadPath.mkdirs();//make directories : 폴더를 만들다
		}
		for(MultipartFile multiparFile : uploadFile) { // uploadFile의 매개변수에 저장된 배열을 multipartFile에 대입하여 순서대로 출력
			
			//UploadController에 있는 uploadAjaxAction에서 AttachDTO를 사용해서 값을 저장해야되는데,
			//이럴 경우 UploadController에 AttachDTO가 없으면 사용을 할수가 없다.
			//그래서 UploadController에 AttachDTO를 포함시켜서 사용해야함.
			AttachDTO attachdto = new AttachDTO();
			
			System.out.println(multiparFile);
			//사용자가 업로드한 실제 파일 이름 
			System.out.println("MultipartFile = "+multiparFile.getOriginalFilename());
			//사용자가 업로드한 실제 파일 크기
			System.out.println("MultipartFile = "+multiparFile.getSize());
			//사용자가 업로드한 실제 파일 양식
			System.out.println("MultipartFile = "+multiparFile.getContentType());
			//실제 파일명을 저장
			String uploadFileName = multiparFile.getOriginalFilename();
			//실제 파일명(uploadFileName)을 AttachDTO클래스(attachdto)에 fileName에 저장(setFileName) 
			attachdto.setFileName(uploadFileName);
			
			// 중복이 되지않는 문자열을 생성
			UUID uuid = UUID.randomUUID();
			// UUID + "_" + getOriginalFilname()의 조합으로 파일명을 생성
			uploadFileName=uuid.toString()+"_"+uploadFileName;
			
			//File : 파일을 입/출력 담당 클래스
			//uploadFolder에 저장되어 있는 경로로 '실제 파일명으로 저장'
			File saveFile = new File(uploadPath,uploadFileName);
			
			//transferTo() 라는 메소드를 사용해서 '원하는 위치에 저장'하는데, 이것을 사용하기위해선 예외처리를 해주어야 함.
			try {
				//saveFile변수에 저장되어있는 폴더명으로 파일을 보내라.
				multiparFile.transferTo(saveFile);
				
				//실제 파일명(uploadFolderPath)을 AttachDTO클래스(attachdto)에 fileName에 저장(setUploadPath) 
				attachdto.setUploadPath(uploadFolderPath); //for문 안에 있어야 반복이되면서 배열이 완성됨. 굳이 try안에 넣을 필요 없음!
				
				//uuid값(UUID)을 AttachDTO클래스(attachdto)에 uploadPath에 저장(setUploadPath) 
				attachdto.setUuid(uuid.toString()); //setUuid가 String타입이므로 uuid의 toString메서드를 가져옴.
				
				//이미지파일이면
				if(checkImage(saveFile)) {
					//FileType값(image)을 AttachDTO클래스(attachdto)에 uploadPath에 저장(setImage)
					attachdto.setImage(true);
					//썸네일 파일을 생성하기 전에 썸네일 파일 이름을 추출 FileOutputStream : 파일을 밖으로 내보내다
					FileOutputStream thumnail = new FileOutputStream(new File(uploadPath,"s_"+uploadFileName));
					//썸네일 파일을 생성함
					Thumbnailator.createThumbnail(multiparFile.getInputStream(),thumnail,100,100);
					//썸네일 종료(메모리 공간 환수)
					thumnail.close();
				}
				list.add(attachdto);
			} catch (Exception e) {
				e.printStackTrace();
			}// end try
		}// for문 end
		//통신상태가 정상적(HttpStatus.OK)이면 ArrayList(list)에 저장되어 있는 값을 uploadAjax.js에 있는 ajax에 success로 보내라 (웹브라우저에 보내라.)
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	//파일업로드한 파일타입이 이미지일 때 웹브라우저에 이미지를 띄우기 위한 컨트롤러
	@GetMapping("display")
	//getFile()은 문자열로 파일의 경로가 포함된 fileName을 매개변수 받고 byte[](미진수)를 전송
	public ResponseEntity<byte[]> getFile(String fileName) {
		System.out.println("url주소를 통한 fileName="+fileName);
		
		File file = new File("C:\\upload\\"+fileName);
		System.out.println("file="+file);
		
		ResponseEntity<byte[]> result = null;
		
		//byte[]로 이미지 파일의 데이터를 전송할 때 브라우저에 보내는 MIME타입이 파일의 종류(.jpg, .png, .xls, .ppt)에 따라 달라진다. 
		//이 부분을 해결하기 위해서 probeContentType()을 이용해서 적절한 MIME타입 데이터를 Http의 헤더 메세지에 포함할 수 있도록 처리
		HttpHeaders header = new HttpHeaders();
		try {
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),header,HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//파일업로드한 파일타입이 이미지가 아닐때(.xls, .ppt, .txt) 웹브라우저를 통해 다운로드 받을 수 있게하는 컨트롤러
	//댓글쓰기를 하기위한 RequestMapping
	//Mapping을 할 때 우리가 원하는 데이터 타입을 강제함으로써 오류상황을 줄일 수 있다.
	//consumes : 들어오는 데이터 타입 정의(생략가능)
	//produces : 반환하는 데이터 타입 정의(생략가능)
	//*생략을 하게 되면, 웹브라우저가 알아서 타입을 판단*
	//웹브라우저가 이 파일은 download해야하는 파일입니다.라는 것을 인지할 수 있도록 반환이 되어야 함.
	//그러기 위해서는 APPLICATION_OCTETE_STREAM_VALUE 타입으로 반환데이터 타입을 선언함.
	@GetMapping(value="download", produces=MediaType.APPLICATION_OCTET_STREAM_VALUE) //타입이 다운로드 받을 수 있는 타입으로 옵션을 추가해야함.
	public ResponseEntity<Resource> downloadFile(String fileName) {
		System.out.println("download fileName="+fileName);
		
		Resource resource = new FileSystemResource("C:\\upload\\"+fileName);
		
		System.out.println("download resource="+resource);
		
		String resourceName = resource.getFilename();
		
		HttpHeaders header = new HttpHeaders();
		try {
			header.add("Content-Disposition", "attachment; filename="+new String(resourceName.getBytes("UTF-8"),"ISO-8859-1"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<Resource>(resource,header,HttpStatus.OK);
		
	}
	
}
