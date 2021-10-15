package com.docmall.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  // setter,getter, toString 메서드
@AllArgsConstructor // 모든 필드를 이용한 생성자메서드
@NoArgsConstructor  // 기본생성자 메서드
public class SampleVO {
	private Integer mno;
	private String firstName;
	private String lastName;
}
