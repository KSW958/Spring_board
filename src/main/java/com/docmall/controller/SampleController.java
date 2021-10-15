package com.docmall.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.docmall.domain.SampleVO;
import com.docmall.domain.Ticket;

import lombok.extern.log4j.Log4j;

/*
 Controller 클래스를 생성시, 1)Rest방식 일경우에는  @RestController 2)일반적인 jsp용도일 경우에는 @Controller 어노테이션을 지정한다.  
 */

@RestController  //  @RestController = @Controller + @ResponseBody(리턴값을 데이터로 클라이언트에게 보내주는 역활)
@RequestMapping("/sample")
@Log4j
public class SampleController {
	
	
	// 서버에서 REST방식으로 데이터를 클라이언트에게 보내는 데이터의 유형(포맷)
	
	
	// 브라우저에게 명시적으로 보내는 데이타의 MIME를 지정하여, 정보를 보낸다.
	@GetMapping(value = "/getText", produces = "text/plain; charset=UTF-8")
	public String getText() {
		
		return "hello";  // 
	}
	
	
	// 자바객체를 JSON(jackson-databind) or XML(jackson-dataformat-xml)로 변환하는 작업
	// MediaType 클래스 : 스프링에서 MIME을 다룰때 사용하는 클래스
	// 주소1> http://localhost:7070/sample/getSample : xml응답, 주소2> http://localhost:7070/sample/getSample.json : json응답
	@GetMapping(value = "/getSample", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE})
	public SampleVO getSample() {
		
		return new SampleVO(112, "스타", "로드");
	}
	
	//List컬렉션 형태의 데이터를 리턴타입
	@GetMapping(value = "/getList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<SampleVO> getList(){
		
		List<SampleVO> voList = new ArrayList<SampleVO>();
		
		for(int i=0; i<9; i++) {
			voList.add(new SampleVO(i, i + "First", i + "Last"));
		}
		
		return voList;
	
	}
	
	// Map컬렉션 형태의 데이터를 리턴타입
	@GetMapping(value = "/getMap", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, SampleVO> getMap(){
		
		Map<String, SampleVO> map = new HashMap<String, SampleVO>();
		map.put("First", new SampleVO(111, "이순신", "거북선"));
		
		return map;
	}
	
	
	/*위의 구문은 ResponseEntity<T> 클래스를 사용안함  */
	
	
	// Rest방식? View(Jsp) 에 해당하는 화면을 제공하는 것이아니라, 데이타 그 자체를 클라이언트에게 전송하는 특징을 갖고있다. 데이터를 자바스크립트에서 사용하여 처리하게된다.
	// ResponseEntity<T> : 데이터 + HTTP상태코드 + HTTP 헤더의 상태메시지 를 사용하여, 클라이언트에게 정보를 전달하는 기능을 제공.
	@GetMapping(value = "/check", params = {"height", "weight"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<SampleVO> check(Double height, Double weight) {
		
		ResponseEntity<SampleVO> result = null;
		
		SampleVO vo = new SampleVO(000, "" + height, "" + weight);
		
		if(height < 150) {
			result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo);
		}else {
			result = ResponseEntity.status(HttpStatus.OK).body(vo);
		}

		return result;
	}
	
	// 배열을 리턴값으로 사용
	// @PathVariable : URL매핑에 해당하는 주소의 일부분을 데이터값으로 사용하고자 할때 사용.
	@GetMapping(value =  "/product/{cat}/{pid}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)  // 상품기능/카테고리명/상품코드   주소: product/bags/1234
	public String[] getpath(@PathVariable("cat") String cat, @PathVariable("pid") Integer pid) {
		
		
		return new String[] {"category: " + cat, "productid: " + pid};
	}
	
	
	// @RequestBody : JSON 포맷의 데이터를 서버로 보내면, @RequestBody 가 사용하고자 하는 파라미터 타입으로 변환을 자동으로 해준다.
	// version 업이 되면서 생략이 가능.
	@PostMapping("/ticket")
	public Ticket convert(@RequestBody Ticket ticket) {
		
		log.info("convert.... ticket" + ticket);
		
		return ticket;
	}
	
	
	
	
	
	
	
	
	
}
