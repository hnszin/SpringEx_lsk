
package com.lsk.controller;
import java.util.ArrayList;

import org.lsk.domain.AttachDTO;
import org.lsk.domain.BoardDTO;
import org.lsk.domain.Criteria;
import org.lsk.domain.PageDTO;
import org.lsk.service.BoardService;
import org.lsk.service.BoardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("board")
public class BoardController {
	
	@Autowired
	private BoardServiceImpl service;
	// private BoardService service = new BoardService();
	
	// 글쓰기 화면으로
	@GetMapping("write")
	public void GetWrite() {
		System.out.println("board/write");
	}
	
	//글쓰기 버튼을 클릭하면
	@PostMapping("write")
	public String PostWrite(BoardDTO b){
		
		//System.out.println("aaaa");
		System.out.println("BoardDTO="+b);
		
		// 메모리 사용이 효율적이지 않기에 참조변수를 직접 입력하지 않고 model을 사용함.
		// sql 테이블을 작성하고 활용해서 model을 만듦, table == model(class 객체)
		
		service.write(b); // Presentation Business를 연결하는 호출부
		// System.out.println("write post..."+b.toString());
		return "redirect:/board/list";
	}
	
	// 게시판 목록 리스트
	@GetMapping("list")
	public void GetList(Criteria cri, Model model) {
		System.out.println("list 연결 확인");
		model.addAttribute("list", service.list(cri));
		int totalCount = service.getTotalCount(cri);
		model.addAttribute("pageMaker", new PageDTO(cri, totalCount));
	}
	
	// 게시판 목록 리스트에서 제목을 클릭하면...
	@GetMapping("detail")
	public void detail(BoardDTO b, Model model) {
		model.addAttribute("detail", service.detail(b));
	}
	
	// 게시판 상세페이지에서 이미지를 출력하기 위한 select한 결과를 javascript로...
	@GetMapping(value="fileList/{bno}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ArrayList<AttachDTO>> fileList(@PathVariable int bno){
		System.out.println("fileList");
		return new ResponseEntity<>(service.fileList(bno),HttpStatus.OK);
	}
	
	// 글 수정화면으로...
	@GetMapping("modify")
	public void GetModify(BoardDTO b, Model model) {
		model.addAttribute("detail", service.detail(b));
		System.out.println("board/modify");
	}
	
	// 글 수정 버튼 클릭 후 상세페이지화면으로...
	@PostMapping("modify")
	public String PostModify(BoardDTO b, RedirectAttributes rttr) {
		System.out.println(b);
		service.modify(b);
		rttr.addAttribute("bno", b.getBno());
		return "redirect:/board/detail";
	}
	
	// 글 삭제 버튼 클릭 후 목록 리스트 페이지로 이동
	@GetMapping("delete")
	public String delete(BoardDTO b) {
		System.out.println(b);
		service.delete(b);
		return "redirect:/board/list";
	}
	
}

	