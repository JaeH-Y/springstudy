package org.zerock.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.extern.log4j.Log4j2;

@ExtendWith(SpringExtension.class)	// JUnit(Jupiter)에 스프링 테스트 프레임워크를 사용하도록 확장으로 등록하는 것
//이게 없으면? 그냥 순수 JUnit 테스트일 뿐, 스프링 환경에서 DI 등이 실행이 안됨
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")	// 테스트에 필요한 설정을 로딩하기 위해 설정
@Log4j2
public class TestServiceTests {
	
	@Autowired
	private TestService ts;
	
	@Test
	public void testAll() {
		String str = "rkskekfkakqktkdkwkckzkxkvkgkdkdidjdudhdydndbdmdlzhdtladmsepzhdskrhvkxtladmsepvkxsksek";
//		String str = "rkske";
		ts.insertAll(str);
	}
}
