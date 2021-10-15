package com.docmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.docmall.domain.BoardVO;
import com.docmall.domain.Criteria;
import com.docmall.mapper.BoardMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service // 중간서비스 목적으로 사용하는 클래스는 어노테이션을 적용해야 한다.  boardServiceImpl이름으로 bean생성
public class BoardServiceImpl implements BoardService {

	@Setter(onMethod_ = @Autowired)  // 롬봄의 @Setter 어노테이션으로 의존성 주입
	private BoardMapper mapper;
	
	@Override
	public void register(BoardVO board) {
		
		log.info(board);
		
		mapper.insert(board);

	}

	@Override
	public List<BoardVO> getList() {
		// TODO Auto-generated method stub
		return mapper.getList();
	}

	@Override
	public BoardVO get(Long bno) {
		// TODO Auto-generated method stub
		return mapper.read(bno);
	}

	@Override
	public void modify(BoardVO board) {
		// TODO Auto-generated method stub
		mapper.update(board);
	}

	@Override
	public void remove(Long bno) {
		// TODO Auto-generated method stub
		mapper.delete(bno);
	}

	@Override
	public List<BoardVO> getListWithPaging(Criteria cri) {
		// TODO Auto-generated method stub
		return mapper.getListWithPaging(cri);
	}

	@Override
	public int getTotal(Criteria cri) {
		// TODO Auto-generated method stub
		return mapper.getTotalCount(cri);
	}

}
