package org.zerock.mapper;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zerock.dto.BoardDTO;

import lombok.extern.log4j.Log4j2;

@ExtendWith(SpringExtension.class)	// JUnit(Jupiter)에 스프링 테스트 프레임워크를 사용하도록 확장으로 등록하는 것
//이게 없으면? 그냥 순수 JUnit 테스트일 뿐, 스프링 환경에서 DI 등이 실행이 안됨
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")	// 테스트에 필요한 설정을 로딩하기 위해 설정
@Log4j2
public class BoardMapperTests {
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Test
	public void testInsert() {
		BoardDTO boardDTO = BoardDTO.builder()
				.title("title")
				.content("content")
				.writer("user00")
				.build();
		
		int insertCount = boardMapper.insert(boardDTO);
		
		log.info("-----------------------------------------");
		log.info("insertCount: " + insertCount);
	}
	
	@Test
	public void testInsert2() {
		BoardDTO boardDTO = BoardDTO.builder()
				.title("title2")
				.content("content2")
				.writer("user02")
				.build();
		
		int insertCount = boardMapper.insert(boardDTO);
		
		log.info("-----------------------------------------");
		log.info("insertCount2: " + insertCount);
		log.info("=========================================");
		log.info("BNO: " + boardDTO.getBno());
	}
	
	@Test
	public void testSelectOne() {
		Long bno = 2L;
		
		BoardDTO dto = boardMapper.selectOne(bno);
		
		log.info("=========================================");
		log.info("BoardDTO: " + dto);
		log.info("=========================================");
	}
	
	@Test
	public void testRemoveOne() {
		Long bno = 2L;
		
		int result = boardMapper.removeOne(bno);
		
		log.info("=========================================");
		log.info("Remove Result: " + result);
		log.info("=========================================");
	}
	
	@Test
	public void testUpdateOne() {
		BoardDTO dto = BoardDTO.builder()
				.bno(2L)
				.title("update Title")
				.content("update Content")
				.delFlag(false)
				.build();
		
		int result = boardMapper.updateOne(dto);
		
		log.info("=========================================");
		log.info("Update Result: " + result);
		log.info("=========================================");
	}
	
	@Test
	public void testSelectList() {
		List<BoardDTO> list = new ArrayList<BoardDTO>();
		list = boardMapper.selectList();
		
		log.info("------------------------------------------------------------");
		log.info("BoardList");
		list.stream().forEach(b -> log.info(b.toString()));
		log.info("------------------------------------------------------------");
	}
	
	@Test
	public void testList2() {
		int page = 2;
		
		// 계산
		int count = 10;
		int skip = (page - 1) * count;
		
		List<BoardDTO> dtoList = boardMapper.list2(skip, count);
		
		dtoList.stream().forEach(board -> log.info(board));
	}
	
	@Test
	public void testSearch() {
		int page = 2;
		
		// 계산
		int count = 10;
		int skip = (page - 1) * count;
		
		String[] types = {"T", "C", "W"};
		String keyword = "테스트";
		
		boardMapper.listSearch(skip, count, types, keyword);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
