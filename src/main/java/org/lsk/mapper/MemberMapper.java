package org.lsk.mapper;

import org.lsk.domain.MemberDTO;

public interface MemberMapper {
	// 회원가입하기 설계
	public void insert(MemberDTO mdto);
	
	// 로그인하기 설계
	public MemberDTO login(MemberDTO mdto);
}
