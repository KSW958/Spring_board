package com.docmall.service;

import java.util.List;

import com.docmall.domain.BoardVO;
import com.docmall.domain.Criteria;

public interface BoardService {

	public void register(BoardVO board);
	
	public List<BoardVO> getList();
	
	public List<BoardVO> getListWithPaging(Criteria cri);
	
	public int getTotal(Criteria cri);
	
	public BoardVO get(Long bno);
	
	public void modify(BoardVO board);
	
	public void remove(Long bno);
}
