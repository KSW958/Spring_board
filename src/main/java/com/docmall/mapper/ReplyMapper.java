package com.docmall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.docmall.domain.Criteria;
import com.docmall.domain.ReplyVO;

public interface ReplyMapper {
	
	public void insert(ReplyVO vo);
	
	// Criteria cri (검색필드 제외한 페이징정보), bno - 게시물 글번호
	public List<ReplyVO> getListWithPaging(@Param("cri") Criteria cri, @Param("bno") Long bno);
	
	public int getCountByBno(Long bno);
	
	public void update(ReplyVO vo);
	
	public void delete(Long rno);
}
