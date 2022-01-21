package com.lsk.controller;

import org.lsk.domain.Member2DTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("member")
public class Member2Controller {
	
	// join2.jsp
	@GetMapping("join2")
	public void join2() {
		System.out.println("join2.jsp 띄우기 성공");
	}
	
	// join2.jsp에서 얻어진 데이터를 insert
	@PostMapping("join2")
	public void Postjoin2(Member2DTO m2dto) {
		System.out.println("신규회원 데이터 insert 성공");
	}
}
