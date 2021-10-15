package com.docmall.mapper;

import java.util.List;

import com.docmall.domain.BoardVO;
import com.docmall.domain.Criteria;

public interface BoardMapper {

	// 메서드가 호출이 되면, mapper xml파일의 메서드명과 동일한 id의 쿼리가 호출이 되어진다.
	
	// 게시글 저장
	public void insert(BoardVO board);
	
	// 게시물목록. 페이징, 검색기능이 없음.
	public List<BoardVO> getList();
	
	// 게시물목록. 페이징기능 추가
	public List<BoardVO> getListWithPaging(Criteria cri);
	
	// 테이블 전체 데이터 개수
	public int getTotalCount(Criteria cri);
	
	// 게시물조회(상세보기)
	public BoardVO read(Long bno);
	
	// 게시물수정하기
	public void update(BoardVO board);
	
	// 게시물삭제하기
	public void delete(Long bno);
}
