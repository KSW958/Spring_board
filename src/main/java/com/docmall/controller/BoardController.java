package com.docmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.docmall.domain.BoardVO;
import com.docmall.domain.Criteria;
import com.docmall.domain.PageDTO;
import com.docmall.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Controller // 컨트롤러 기능을 담당하는 클래스는 어노테이션 필수
@Log4j  // 롬복의 로그기능 어노테이션
@RequestMapping("/board/*")  // 매핑주소 어노테이션. 공통경로, view(jsp)파일이 관리되는 폴더명과 동일해야 한다.
@AllArgsConstructor  // 클래스의 필드를 이용하여 생성자메서드 생성
public class BoardController {

	private BoardService service;  // 생성자메서드를 이용하여, 자동으로 의존성주입이 이루어진다.
	
	//1)글쓰기 폼 주소 - /board/register
	@GetMapping("/register")
	public void register() {
		
		log.info("글쓰기 폼");
	}
	
	//2)글저장 주소 - /board/register
	@PostMapping("/register")
	public String register(BoardVO board) {
		
		log.info(board);
		
		// DB저장작업
		service.register(board);
		
		return "redirect:/board/list";  // 주소 이동
	}
	
	//3)리스트 주소 - /board/list
	// /board/list 주소를 처음 요청시,호출되는 메서드의 파라미터가 참조형이면, 스프링의 자동으로 객체생성을 해준다.
	// jsp페이지에 페이징, 검색기능 추가
	// 처음주소 : /board/list, 두번째 이후주소: board/list?pageNum=4&amount=10
	@GetMapping("/list")
	public void list(Criteria cri, Model model) {
		
		log.info("list: " + cri); // 페이징정보, 검색정보 같이 출력
		
		// cri: pageNum=1, amount=10
		List<BoardVO> list = service.getListWithPaging(cri);
		
		
		// 테이블의 전체 데이타 개수 불러오는 작업(조건이 포함되는 경우)
		int total = service.getTotal(cri);
		
		log.info("total: " + total);
		
		
		model.addAttribute("pageMaker", new PageDTO(total, cri)); // list.jsp에서 "pageMaker"이름으로 테이블을의 모든데이타를 참조
		model.addAttribute("list", list); // list.jsp에서 "list"이름으로 테이블을의 모든데이타를 참조
	}
	
	//4)게시물조회 - /board/get?bno=글번호, 수정폼 주소 -  /board/modify?bno=글번호
	// get()메서드 호출시 파라미터의 값은 뷰(jsp)에서 자동으로 참조가 가능하다.
	@GetMapping({"/get", "/modify"})
	public void get(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri,Model model) {
		
		log.info("get of modify" + bno);
		
		BoardVO board = service.get(bno);
		
		model.addAttribute("board", board);
	}
	
	//5)수정폼  주소 - /board/modify.  4번으로 대체
	
	
	//6)수정하기
	@PostMapping("/modify")  // 날짜 데이터 포맷 : 2021/08/06 사용( "-" 사용하지말고 "/" 구분자로 사용)
	public String modify(BoardVO board, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		
		log.info(board);
		
		service.modify(board);
		rttr.addFlashAttribute("result", "success"); // list.jsp에서 "result"를 키로 참조하는 경우
		
		// RedirectAttributes 인터페이스 기능? "redirect:/board/list"  주소로 이동시 쿼리스트링 형태로 페이징,검색정보를 파라미터로 보내준다.
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		
		return "redirect:/board/list";  // board/list?pageNum=1&amount=10&type=W&keyword=나그네
	}
	
	//7)삭제하기
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		
		service.remove(bno);
		
		
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		
		return "redirect:/board/list";
		
	}
	
}
