package org.zerock.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.dto.BoardDTO;
import org.zerock.dto.BoardListPagingDTO;
import org.zerock.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/board")
public class BoardContoller {
	
	private final BoardService boardService;
	
	// 게시물 목록 처리: BoardMapper에서 나온 BoardDTO 목록을 반환하도록 작성
//	@GetMapping("/list")
//	public void selectList(Model model) {
//		
//		log.info("=================================================");
//		log.info("/board/list 호출됨");
//		log.info("=================================================");
//		model.addAttribute("list", boardService.getList());
//	}
	// 게시물 목록 처리 + 페이징 처리: 현재 페이지 번화와 화면에 필요한 데이터의 개수를 파라미터로 전달받아서
	// Mapper의 list2()와 listCount()를 호출하도록 작성
	@GetMapping("/list")
	public void list(@RequestParam(name = "page", defaultValue = "1") int page,
			 @RequestParam(name = "size", defaultValue = "10") int size,
			 @RequestParam(name = "types", required = false) String types,
			 @RequestParam(name = "keyword", required = false) String keyword,
			 Model model) {
		
		log.info("page: " + page); // 현재 페이지 번호
		log.info("size: " + size); // 한 페이지당 크기(한 페이지에 출력되는 데이터 개수)
		
		model.addAttribute("dto", boardService.getList(page, size, types, keyword));
	}
	
	
	
	// 게시물 등록은 GET 방식과 POST 방식 모두를 이용해서 처리
	// GET: 게시물 등록에 필요한 입력 화면
	// POST: 게시물 등록 처리 후 게시물 목록으로 리다이렉트
	@GetMapping("/register")
	public void register() {
		log.info("=================================================");
		log.info("/board/register 호출됨");
		log.info("=================================================");
	}
	
	@PostMapping("/register")
	public String registerPost(BoardDTO dto, RedirectAttributes attr) {
		
		log.info("=================================================");
		log.info("/board/register POST 호출됨");
		log.info(dto);
		log.info("=================================================");
		
		Long bno = boardService.addBoard(dto);
		
		attr.addFlashAttribute("result", bno);
		
		return "redirect:/board/list";
	}
	
	// 경로의 마지막 값을 게시물의 번호로 활용
	@GetMapping("/read/{bno}")
	public String read(@PathVariable("bno") Long bno, Model model) {
		log.info("=================================================");
		log.info("/board/read 호출됨");
		log.info("=================================================");
		BoardDTO dto = boardService.getBoard(bno);
		model.addAttribute("board", dto);
		
		return "board/read";
	}
	
	// GET 방식으로 수정하려고 하는 게시물을 확인하고, POST 방식으로 수정이나 삭제를 처리
	@GetMapping("/modify/{bno}")
	public String modifyGet(@PathVariable("bno") Long bno, Model model) {
		log.info("=================================================");
		log.info("/board/modify GET 호출됨");
		log.info("=================================================");
		
		BoardDTO dto = boardService.getBoard(bno);
		model.addAttribute("board", dto);
		
		return "board/modify";
	}
	
	@PostMapping("/modify")
	public String modifyPost(BoardDTO dto) {
		log.info("=================================================");
		log.info("/board/modify POST 호출됨");
		log.info("=================================================");

		int result = boardService.modifyBoard(dto);
		
		return "redirect:/board/read/" + dto.getBno();
	}
	
	@PostMapping("/remove")
	public String removePost(BoardDTO dto) {
		log.info("=================================================");
		log.info("/board/remove POST 호출됨");
		log.info("=================================================");
		
		int result = boardService.deleteBoard(dto.getBno());
		
		return "redirect:/board/list";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
