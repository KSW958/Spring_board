package com.docmall.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.docmall.domain.Criteria;
import com.docmall.domain.PageDTO;
import com.docmall.domain.ReplyVO;
import com.docmall.service.ReplyService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@RequestMapping("/replies/")
@RestController  // json or xml형식의 데이터포맷을 서버로부터 받고자 할때 사용.   @RestController = @Controller + @ResponseBody
@Log4j
@AllArgsConstructor
public class ReplyController {

	private ReplyService service;
	
	//댓글등록 주소 /replies/new 
	// consumes : 클라이언트로부터 전송되어 온 데이타 포맷을 정의.(즉 다른 포맷의 데이타가 전송되면, 에러발생)
	// produces : 서버에서 클라이언트로 응답하는 데이타 포맷의 정의.
	// , consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE} 생략이 가능
	@PostMapping(value = "/new", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> create(@RequestBody ReplyVO vo){
		
		ResponseEntity<String> entity = null;
		
		service.register(vo);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	// 댓글목록(페이지기능포함) : 1)댓글목록데이타 + 2)페이징데이타
	// REST URI 표현:   /게시물번호/페이징번호  예> /replies/1048576/1
	@GetMapping(value = "/{bno}/{page}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<Map<String, Object>> listPage(@PathVariable("bno") Long bno, @PathVariable("page") Integer page)
	{
		ResponseEntity<Map<String, Object>> entity = null;
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		//1)댓글목록데이타
		Criteria cri = new Criteria(page, 5);
//		cri.setPageNum(page);
		
		List<ReplyVO> list = service.getListPage(cri, bno);
		
		//2)페이징정보
		int total = service.countPaging(bno);
		PageDTO pageMaker = new PageDTO(total, cri);
		
		// map컬렉션에 2개의 정보추가
		map.put("list", list); // 1)댓글목록추가
		map.put("pageMaker", pageMaker); //2)페이징정보추가
		
		entity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		
		return entity;
	}
	
	// 댓글 수정
	@RequestMapping(value = "/modify/{rno}", method = {RequestMethod.PUT, RequestMethod.PATCH})
	public ResponseEntity<String> update(@PathVariable("rno") Long rno, @RequestBody ReplyVO vo){
		
		ResponseEntity<String> entity = null;
		
		vo.setRno(rno);
		service.modifyReply(vo);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	@DeleteMapping("/delete/{rno}")
	public ResponseEntity<String> delete(@PathVariable("rno") Long rno){
		
		ResponseEntity<String> entity = null;
		
		service.removeReply(rno);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	
	// 댓글 삭제
}
