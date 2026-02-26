package org.zerock.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.dto.ReplyDTO;
import org.zerock.dto.ReplyListPagingDTO;
import org.zerock.service.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/replies")
public class ReplyController {
	
	private final ReplyService replyS;

	// 뒤에 더 붙는 URL이 없는 경우 Mappting 사용법
	// @PostMapping("/")
	// @PostMapping("")
	@PostMapping	// 권장
	public ResponseEntity<Map<String, Long>> addReply(ReplyDTO dto) {
		log.info(dto);
		
		replyS.addReply(dto);
		
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("rno", dto.getRno());
		
		return ResponseEntity.ok(map);
	}
	
	@GetMapping("/{bno}/list")
	public ResponseEntity<ReplyListPagingDTO> replyListOfBoard(
			@PathVariable("bno") Long bno,
			@RequestParam(name= "page", defaultValue= "1") int page,
			@RequestParam(name= "size", defaultValue= "10") int size) {

		log.info("bno: " + bno);
		log.info("page: " + page);
		log.info("size: " + size);
		
		return ResponseEntity.ok(replyS.listOfBoard(bno, page, size));
	}
	
	@GetMapping("/{rno}")
	public ResponseEntity<ReplyDTO> getOneReply(@PathVariable("rno") Long rno){
		
		return ResponseEntity.ok(replyS.getOneReply(rno));
	}
	
	@DeleteMapping("/{rno}")
	public ResponseEntity<Map<String, String>> deleteReply(@PathVariable("rno") Long rno){
		log.info("rno: " + rno);
		
		replyS.removeReply(rno);
		
		Map<String, String> map = new HashMap<>();
		map.put("deleteResult", "deleted");
		
		return ResponseEntity.ok(map);
	}
	
	@PutMapping("/{rno}")
	public ResponseEntity<Map<String, String>> updateReply(
			@PathVariable("rno") Long rno, ReplyDTO dto){
		
		log.info("rno: " + rno);
		log.info("replyDTO: " + dto);
		
		replyS.modifyReply(dto);
		
		Map<String, String> map = new HashMap<>();
		map.put("updateResult", "modified");
		
		return ResponseEntity.ok(map);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
