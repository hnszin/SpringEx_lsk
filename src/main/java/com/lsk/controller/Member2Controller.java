package com.lsk.controller;

import javax.servlet.http.HttpSession;

import org.lsk.domain.Member2DTO;
import org.lsk.service.Member2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysql.cj.Session;

@Controller
@RequestMapping("member")
public class Member2Controller {
	@Autowired
	Member2Service m2service;
	
	// join2.jsp
	@GetMapping("join2")
	public void join2() {
		System.out.println("join2.jsp 띄우기 성공");
	}
	
	// join2.jsp에서 얻어진 데이터를 insert
	@PostMapping("join2")
	public void Postjoin2(Member2DTO m2dto) {
		System.out.println("Postjoin2"+m2dto);
		m2service.insert(m2dto); 
	}
	
	@GetMapping("login2")
	public void login2() {
		System.out.println("login2.jsp 띄우기 성공");
	}
	
	// login2.jsp에서 얻어진 데이터를 select
	@PostMapping("login2")
	public String Postlogin2(Member2DTO m2dto,HttpSession session) {
		
		Member2DTO login = m2service.login(m2dto);
		session.setAttribute("login", login);
		if(session.getAttribute("login")!=null) {
			return "redirect:/";
		} else {
			return "redirect:/member/login2";
		}
	}
}
