package com.docmall.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ReplyVO {

	// rno, bno, reply, replyer, replyDate, updateDate
	
	private Long rno;
	private Long bno;
	
	private String reply;
	private String replyer;
	private Date replyDate;
	private Date updateDate;
}
