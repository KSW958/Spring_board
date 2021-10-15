package com.docmall.domain;

import lombok.Getter;
import lombok.ToString;

// 페이징 기능 목적 - 페이징로직
// jsp페이지에서 [참고1]의 형태로 출력을 하고자 할때, 필요한 정보를 구성하는 기능

@Getter
@ToString
public class PageDTO {
	
	/*
	한 페이지에 5개씩 출력
	전체데이타 개수 : 일정패턴. 25, 50, 75, 100
	전체데이타 개수 61
	
	총 페이지수 = 전체데이타 개수(61) / 페이지 출력건수(5)
	
	[참고1] 
	한 블럭에 페이지 번호를 5개씩 사용 
	 		1	2	3	4	5	[next]             - 1block
	[prev]	6	7	8	9	10	[next]			   - 2block 
	[prev] 	11	12	13	14	15					   - 3block    endPage = 13
	  
	 */
	
	private int startPage;  // 블럭에서 첫번째 페이지 정보
	private int endPage;    // 블럭에서 마지막 페이지 정보
	private boolean prev, next;  // 이전,다음 표시 여부를 가지고 있는 값 
	
	private int total; // 게시판 테이블의 전체 데이타개수
	
	private Criteria cri; // 페이징,검색 정보가 저장

	public PageDTO(int total, Criteria cri) {
//		super(); 생략가능
		
		// 로직을 만들어서, 위의 4개필드에 필요한 정보를 저장한다.
		
		this.total = total;
		this.cri = cri;
		
		this.endPage = (int) (Math.ceil(cri.getPageNum() / 10.0)) * 10;  // 1 block에서 페이지번호를 선택하게되면.
		//  this.endPage = (int) (1.0) * 5; 
	    //  this.endPage = (int) (2.0) * 5; 
	    //  this.endPage = (int) (3.0) * 5; 
		
		this.startPage = this.endPage - 9;
		
		// 총 데이타 개수에 따라서 위의 공식이 적용이 되지않는다.(예외)
		// 아래구문에서 조건식 true인 경우에는 3block에서 endPage의 값이 정확한 값이 아니기때문에, 실제 데이터의 총개수를 구하여, endPage를 구하자.
		// 전체데이타 개수에 따라서 실제 마지막페이지를 구한다.
		// total = 61.0~65.0 / 5 = Math.ceil()을 적용하면 13 
		int realEnd = (int) (Math.ceil((total * 1.0) / cri.getAmount()));
		// int realEnd = (int) (Math.ceil((61.0) / 5)));
		
		// 아래구문에서 조건식 true인 경우에는 3block에서 endPage의 값이 정확한 값이 아니기때문에, 실제 데이터의 총개수를 구하여, endPage를 구하자.
		if (realEnd <= this.endPage) {
			this.endPage = realEnd;
		}
		
		this.prev = this.startPage > 1;  // 2block에서 startPage 값이 6
		
		this.next = this.endPage < realEnd;
		
	}
	
	
}
