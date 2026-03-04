package org.zerock.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.dto.BoardDTO;
import org.zerock.dto.ProductDTO;

import lombok.extern.log4j.Log4j2;

@ExtendWith(SpringExtension.class)	// JUnit(Jupiter)에 스프링 테스트 프레임워크를 사용하도록 확장으로 등록하는 것
//이게 없으면? 그냥 순수 JUnit 테스트일 뿐, 스프링 환경에서 DI 등이 실행이 안됨
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")	// 테스트에 필요한 설정을 로딩하기 위해 설정
@Log4j2
@Transactional
public class ProductMapperTests {
	
	@Autowired
	private ProductMapper mapper;
	
	// 테스트에서 @Transactional의 기본 동작
	// 1. 테스트 시작 -> 트랜잭션 시작
	// 2. 테스트 종료 -> 무조건 Rollback
	// 즉, 테스트는 DB를 더럽히지 않는 것이 기본 철학
	@Commit	// 테스트 트랜잭션을 ROLLBACK 하지 말고 COMMIT 하게 하는 어노테이션(실제 DB 반영)
	@Test
	public void testInsert() {
		ProductDTO dto = ProductDTO.builder()
				.pName("TEST PRODUCT")
				.pDesc("TEST DESCRIPTION")
				.pPrice(15000)
				.writer("TEST ADMIN")
				.build();
		
		// 상품 테이블에 등록
		mapper.insert(dto);
		
		log.info("pno: " + dto.getPNo());
		// 상품 이미지 테이블에 등록
		dto.addImage(UUID.randomUUID().toString(), "TEST_IMAGE1.jpg");
		dto.addImage(UUID.randomUUID().toString(), "TEST_IMAGE2.jpg");
		dto.addImage(UUID.randomUUID().toString(), "TEST_IMAGE3.jpg");
		
		mapper.insertImage(dto);
	}
	
	@Test
	public void testSelectOne() {
		Long pno = 1L;
		
		ProductDTO dto = mapper.selectOne(pno);
		
		log.info("================================================");
		log.info("dto: " + dto);
		log.info("================================================");
	}
	
	@Commit
	@Test
	public void testUpdateOne() {
		Long no = 1L;
		String pName = "NEW TEST PRODUCT";
		String pDesc = "NEW TEST DESCRIPTION";
		int pPrice = 20000;
		 
		ProductDTO dto = ProductDTO.builder()
					.pNo(no)
					.pName(pName)
					.pDesc(pDesc)
					.pPrice(pPrice)
					.build();
		 
		dto.addImage(UUID.randomUUID().toString(), "NEW_TEST_FILENAME1.jpg");
		dto.addImage(UUID.randomUUID().toString(), "NEW_TEST_FILENAME2.jpg");
		dto.addImage(UUID.randomUUID().toString(), "NEW_TEST_FILENAME3.jpg");
		dto.addImage(UUID.randomUUID().toString(), "NEW_TEST_FILENAME4.jpg");
		 
		// 1. 기존 이미지 모두 삭제
		int delImgResult = mapper.deleteImage(dto.getPNo()); 
		// 2. 상품 정보 수정
		int updateProResult = mapper.updateOne(dto);
		// 3. 상품 이미지 갱신(삽입)
		int updateImgResult = mapper.insertImage(dto);
		 
	}
	
	
	@Test
	@Commit
	public void createDummyData() {
		for(int i = 0; i < 45; i++) {
			ProductDTO dto = ProductDTO.builder()
					.pName("TEST PRODUCT" + (i+1))
					.pDesc("TEST DESCRIPTION" + (i+1))
					.writer("TEST ADMIN" + (i / 10))
					.pPrice(20000 + (i*100))
					.build();
			
			// 상품 테이블에 등록
			mapper.insert(dto);
			
			// 상품 이미지 테이블에 등록
			dto.addImage(UUID.randomUUID().toString(), (i+1) + "TEST_IMAGE_1.jpg");
			dto.addImage(UUID.randomUUID().toString(), (i+1) + "TEST_IMAGE_2.jpg");
			dto.addImage(UUID.randomUUID().toString(), (i+1) + "TEST_IMAGE_3.jpg");
			
			mapper.insertImage(dto);
		}
	}
	
	@Test
	public void testList() {
		int limit = 10;
		int offset = 0;
		mapper.selectList(limit, offset).forEach(item -> log.info(item));
		
		log.info(mapper.selectListCount());
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
