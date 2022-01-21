package org.lsk.service;

import org.lsk.domain.MemberDTO;
import org.lsk.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{
	@Autowired
	private MemberMapper mmapper;
	
	// 회원가입하기 설계된 것을 구현
	@Override
	public void insert(MemberDTO mdto) {
		mmapper.insert(mdto);
	};
	
	// 로그인하기 설계된 것을 구현
	@Override
	public MemberDTO login(MemberDTO mdto) {
		return mmapper.login(mdto);
	};
	
}
