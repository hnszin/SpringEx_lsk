package org.lsk.service;

import java.util.ArrayList;

import org.lsk.domain.AttachDTO;
import org.lsk.domain.BoardDTO;
import org.lsk.domain.Criteria;
import org.lsk.domain.ReplyDTO;
import org.lsk.mapper.AttachMapper;
import org.lsk.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Boardservice의 메서드를 반드시 구현해야함


@Service
public class BoardServiceImpl implements BoardService{
	@Autowired
	private BoardMapper bmapper;
	@Autowired
	private AttachMapper amapper;
	
	// 게시판 글쓰기 설계된 것을 구현
	@Transactional
	public void write(BoardDTO b) {
		// 제목과 내용을 board테이블에 insert
		bmapper.insertSelectKey(b);
		
		// 파일명, 파일경로, 파일타입, uuid값을 attach테이블에 insert
//		amapper.insert(board);
		// BoardDTO에 있는 AttachList를 가져와서 반복문으로 실행하여 attach 변수에 저장
		b.getAttachList().forEach(attach->{
			// BoardDTO의 bno값을 가져(board.getbno())와서 AttachDTO에 bno에 저장(attach.setBno)
			attach.setBno(b.getBno());
			amapper.insert(attach);
		});
	}
	
	// 게시판 목록리스트 설계된 것을 구현
	public ArrayList<BoardDTO> list(Criteria cri) {
		// ArrayList 동적배열
		return bmapper.list(cri);
	}
	
	// 게시판 목록리스트에서 제목을 클릭했을 때 내용이 나오는 상세페이지 설계된 것을 구현
	@Transactional 
	// 두개의 메서드를 동시에 실행
	public BoardDTO detail(BoardDTO b) {
		bmapper.cntupdate(b);
		return bmapper.detail(b);
	}
		
	// 게시글 수정하기를 눌렀을 때, 내용이 나오는 상세페이지 설계된 것을 구현
	public void modify(BoardDTO b) {
		bmapper.modify(b);
	}
	
	// 게시판 글 삭제 설계된 것을 구현
	public void delete(BoardDTO b) {
		bmapper.delete(b);
	}
	
	// 게시판 페이지에 쓰일 데이터 건수
	public int getTotalCount(Criteria cri) {
		return bmapper.getTotalCount(cri);
	};
	
	//
	public ArrayList<AttachDTO> fileList(int bno){
		return amapper.fileList(bno);
	}

	
}
