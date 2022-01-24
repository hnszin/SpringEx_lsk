package org.lsk.mapper;

import org.lsk.domain.Member2DTO;

public interface Member2Mapper {
	// 회원가입하기 설계
	public void insert(Member2DTO m2dto);
	
	// 로그인하기 설계
	public Member2DTO login(Member2DTO M2dto);
}
