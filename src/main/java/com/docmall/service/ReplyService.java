package com.docmall.service;

import java.util.List;

import com.docmall.domain.Criteria;
import com.docmall.domain.ReplyVO;

public interface ReplyService {

	public void register(ReplyVO vo);
	
	public List<ReplyVO> getListPage(Criteria cri, Long bno);
	
	public int countPaging(Long bno);
	
	public void modifyReply(ReplyVO vo);
	
	public void removeReply(Long rno);
}
