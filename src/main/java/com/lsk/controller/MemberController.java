package com.lsk.controller;

import javax.servlet.http.HttpSession;

import org.lsk.domain.MemberDTO;
import org.lsk.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("member")
public class MemberController {
	@Autowired
	MemberService mservice;
	
	// 회원가입 화면
	@GetMapping("join")
	public void write() {
		System.out.println("join.jsp 띄우기 완료");
	}
	
	// 회원가입 화면을 통해 얻어진 데이터 insert
	@PostMapping("join")
	public void PostMember(MemberDTO mdto) {
		System.out.println("PostMember"+mdto);
		mservice.insert(mdto);
	}
	
	// 로그인 화면 이동
	@GetMapping("login")
	public void login(){
		System.out.println("login.jsp 띄우기 완료");
	}
	
	// 로그인 화면을 통해 얻어진 데이터를 활용하여 select
	@PostMapping("login")
	public String PostLogin(MemberDTO mdto,HttpSession session) {
		MemberDTO login=mservice.login(mdto);
		
		// MemberDTO에 있는 MemberDTO [id='abcd, password=1234, name=정자바]를 세션 영역에 login이라는 변수에 저장
		// 세션객체(sesiion)에  login변수에 login값을 저장(setAttribute)
		// session : 로그인했을때 정보가 저장된채로 다른 페이지를 이동하더라도 로그인정보가 홈페이지에 계속 유지 ,하지만 우리가 만든 게시판은 새로고침할때마다 데이터가 리셋됐는데 게시판과 달리 데이터가 홈페이지에 계속 유지되도록 하는 기능
		session.setAttribute("login", login);
		
		// session.invalidate(); 로그아웃
		// session영역에 login이라는 변수에 값이 있으면 로그인 된 채로
		if(session.getAttribute("login")!=null) {
			return "redirect:/";
		}else {// session영역에 login이라는 변수에 값이 없으면 (null)
			// 다시 로그인 할 수 있게 로그인 페이지로 이동
			return "redirect:/member/login";
		}
		
	}
}
