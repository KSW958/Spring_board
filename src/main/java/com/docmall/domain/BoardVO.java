package com.docmall.domain;

import java.util.Date;

import lombok.Data;

// Board + VO(Value Object)

@Data
public class BoardVO {

	// tbl_board테이블의 컬럼명과 매핑되는 클래스의 필드명을 일치시킨다.
	// 일치하지 않으면, 중간작업이 필요하다.
	// bno, title, content, writer, regdate, updatedate
	
	private Long bno;
	private String title;
	private String content;
	private String writer;
	private Date regdate;  // "2021/08/06" 문자열 형식이 자동형변환
	private Date updatedate;
		
}
