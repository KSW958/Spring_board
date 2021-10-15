package com.docmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.docmall.domain.Criteria;
import com.docmall.domain.ReplyVO;
import com.docmall.mapper.ReplyMapper;

import lombok.Setter;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper;
	
	@Override
	public void register(ReplyVO vo) {
		
		mapper.insert(vo);
		
	}

	@Override
	public List<ReplyVO> getListPage(Criteria cri, Long bno) {
		// TODO Auto-generated method stub
		return mapper.getListWithPaging(cri, bno);
	}

	@Override
	public int countPaging(Long bno) {
		// TODO Auto-generated method stub
		return mapper.getCountByBno(bno);
	}

	@Override
	public void modifyReply(ReplyVO vo) {
		
		mapper.update(vo);
		
	}

	@Override
	public void removeReply(Long rno) {
		
		mapper.delete(rno);
	}

}
