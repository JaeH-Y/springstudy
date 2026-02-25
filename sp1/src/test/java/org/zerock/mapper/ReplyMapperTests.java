package org.zerock.mapper;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zerock.dto.BoardDTO;
import org.zerock.dto.ReplyDTO;

import lombok.extern.log4j.Log4j2;

@ExtendWith(SpringExtension.class)	// JUnit(Jupiter)에 스프링 테스트 프레임워크를 사용하도록 확장으로 등록하는 것
//이게 없으면? 그냥 순수 JUnit 테스트일 뿐, 스프링 환경에서 DI 등이 실행이 안됨
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")	// 테스트에 필요한 설정을 로딩하기 위해 설정
@Log4j2
public class ReplyMapperTests {
	
	@Autowired
	private ReplyMapper mapper;
	
	@Test
	public void testInsert() {
		Long bno = 1521L;
		
		// 새로운 댓글 생성
		ReplyDTO dto = ReplyDTO.builder().replyer("댓글 작성자")
				.replyText("댓글 내용")
				.bno(bno)
				.build();
		
		mapper.insert(dto);
		
		Long rno = dto.getRno();
		
		log.info("===========================================");
		log.info(rno);
		log.info("===========================================");
	}
	
	@Test
	public void testRead() {
		Long rno = 2L;
		
		ReplyDTO dto = mapper.read(rno);
		
		log.info("===========================================");
		log.info("작성자: " + dto.getReplyer());
		log.info("내용: " + dto.getReplyText());
		log.info("===========================================");
	}
	
	@Test
	public void testUpdate() {
		Long rno = 2L;
		String changedText = "DTO로 변경 된 댓글 내용";

		int result = mapper.update(ReplyDTO.builder().rno(rno).replyText(changedText).build());
		
		ReplyDTO dto = mapper.read(rno);
		
		log.info("===========================================");
		log.info("작성자: " + dto.getReplyer());
		log.info("내용: " + dto.getReplyText());
		log.info("===========================================");
	}
	
	@Test
	public void testDelete() {
		Long rno = 1L;
		
		int result = mapper.delete(rno);
		
		ReplyDTO dto = mapper.read(rno);
		
		log.info("===========================================");
		log.info("작성자: " + dto.getReplyer());
		log.info("내용: " + dto.getReplyText());
		log.info("삭제 여부: " + dto.isDelFlag());
		log.info("===========================================");
		
	}
	
	@Test
	public void testInserts() {
		Long[] bnos = {1521L, 1520L, 1519L};
		
		for(Long bno : bnos) {
			
			for(int i = 0; i < 10; i++) {
				ReplyDTO dto = ReplyDTO.builder()
						.bno(bno).
						replyText("Smaple Reply" + (i+1))
						.replyer("Replyer" + (i+1))
						.build();
				
				mapper.insert(dto);
			}
		}
		
	}
	
	@Test
	public void testListOfBoard() {
		Long bno = 1521L;
		
		List<ReplyDTO> list = mapper.repliesOfBoard(bno, 10, 10);
		
		log.info("===========================================");
		log.info("List Count: " + list.size());
		for(ReplyDTO dto : list) {
			log.info(dto);
		}
		log.info("===========================================");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
