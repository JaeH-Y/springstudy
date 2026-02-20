package org.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.dto.SampleDTO;
import org.zerock.service.HelloService;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Controller	// 해당 클래스의 객체가 스프링에서 빈(Bean)으로 관리되는 대상임을 지정
@RequiredArgsConstructor	// 생성자를 통한 의존성 주입을 할 수 있도록
@ToString
@Log4j2
@RequestMapping("/sample")	// '/sample'로 시작하는 요청을 HelloController가 처리한다는 것을 의미
public class HelloController {

	private final HelloService helloS;
	
//	@Autowired // 생성자가 하나만 있을 때는 생략 가능
//	public HelloController(HelloService _helloS) {
//		this.helloS = _helloS;
//	}
	
//	@RequestMapping(value = "/ex1", method = RequestMethod.GET)	// 옛날 방식
	// 스프링 4.3 이상에서는 @GetMapping, @PostMapping, @PutMapping, @DeleteMaping 등 지원
	@GetMapping("/ex1")
	public void ex1() {
		log.info("/sample/ex1");
		
		helloS.hello1();
	}
	// 메소드가 void이면 스프링이 요청 URL 기반으로 뷰 이름을 추론
	// 즉, 사용된 요청 경로 = 뷰 이름
	
	@GetMapping("/ex2")
	public String ex2() {
		log.info("/sample/ex2");
		
		String name = helloS.hello2("Hong Gil dong");
		return "sample/success";
	}
	
	@GetMapping("/ex3")
	public String ex3() {
		log.info("/sample/ex3");
		
		return "redirect:/sample/ex3re";
		// 브라우저에게 /sample/ex3re로 이동하라는 메세지를 전송
	}
	
	@GetMapping("/ex4")
	public void ex4(@RequestParam(name = "n1", defaultValue = "1") int num,
//					@RequestParam("name") String name)	// 추가 속성 값이 없이 name 속성만 있는 경우 속성명 생략 가능
					@RequestParam(name = "name", required = false) String name) {
		
		//required 속성: 필수 여부 지정, 파라미터 없어도 괜찮은지 아닌지.
		log.info("/sample/ex4");
		log.info("num: " + num);
		log.info("name: " + name);
	}
	
	@GetMapping("/ex5")
	public void ex5(SampleDTO dto) {
		log.info("/sample/ex5");
		log.info(dto);
	}
	
	@GetMapping("/ex6")
	public void ex6(Model model) {
		model.addAttribute("name", "Hong Gil Dong");
		model.addAttribute("age", 16);
		
		log.info(model.getAttribute("name"));
		// 참고: 뷰 렌더링 직전에 request attribute로 복사됨
	}
	
	@GetMapping("/ex7")
	public String ex7(RedirectAttributes attr) {
		// 리다이렉트 시 새로운 요청으로 데이터를 전달하는 2가지 방법
		attr.addAttribute("name", "Hong"); // 쿼리스트링으로 만들어 데이터 전달
		attr.addFlashAttribute("age", 16);	// 1회성 데이터 전달용
		// 참고: 내부적으로 세션에 임시 저장 후, 다음 요청에서 Model에 자동 주입되고 즉시 제거됨
		
		return "redirect:/sample/ex8";
	}
	
	@GetMapping("/ex8")
	public void ex8(@RequestParam("name") String name, Model model) {
		
		model.addAttribute("name", name);
		log.info("redirected");
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
