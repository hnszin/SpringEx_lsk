package org.lsk.service;

import org.lsk.domain.Member2DTO;
import org.lsk.mapper.Member2Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Member2ServiceImpl implements Member2Service{
	@Autowired
	public Member2Mapper m2mapper;

	// 회원가입하기 설계
	@Override
	public void insert(Member2DTO m2dto) {
		m2mapper.insert(m2dto);
	}

	@Override
	public Member2DTO login(Member2DTO m2dto) {
		System.out.println("서비스1");
		return m2mapper.login(m2dto);
	}
}
