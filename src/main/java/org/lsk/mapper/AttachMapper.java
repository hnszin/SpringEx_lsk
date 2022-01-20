package org.lsk.mapper;

import java.util.ArrayList;

import org.lsk.domain.AttachDTO;

public interface AttachMapper {
	//게시판 글쓰기 할 때 파일과 관련되어 있는 attach테이블에 insert
	public void insert(AttachDTO board);
	
	//게시판 상세페이지에 업로드된 이미지를 뿌리기 위한 데이터list
	public ArrayList<AttachDTO> fileList(int bno);
}
