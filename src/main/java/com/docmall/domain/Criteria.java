package com.docmall.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 페이징,검색 정보를 저장하는 목적

@ToString
@Setter
@Getter
public class Criteria {

	// 페이징 기능을 위한 필드(변수)
	private int pageNum;  // 1	 2	3	4	5 클릭한 번호가 파라미터로 전송될 때 사용하는 용도
	private int amount;   // 매페이지마다 출력될 게시물 개수
	
	// 검색기능을 위한 필드(변수)
	private String type;  // 검색종류. "T", "C","W", "TC", "TW", "TWC"
	private String keyword;  // 검색어
	
	
	public Criteria() {
		this(1, 10);
	}

	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
	
	// 검색기능에 사용할 메서드. type필드의 정보를 String[]배열로 참조하는 기능 메서드."T"   "TWC" -> new String[] {"T", "W", "C"}
	public String[] getTypeArr() {
		return type == null? new String[] {} :type.split("");
	}
	
	
}
