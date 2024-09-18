package com.in28minutes.springboot.myfirstwebapp.hello.SayHelloController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//Spring한테 bean이라고 정의해야하는데, 
//현재 우리가 만들고 있는 건 요청을 처리하는 웹UI 관련 컴포넌트인 Controller
@Controller
public class SayHelloController {
	
	//"say-hello" => "Hello! What are you learning today?!"
	//say-hello라는 url 클릭 시 Hello! What are you learning today?! 리턴
	
	//say-hello
	//http://localhost:8080/say-hello
	@RequestMapping("say-hello")
	@ResponseBody // 리턴한 것 그대로를 브라우저에 전송
	public String sayHello() {
		// Spring MVC는 리턴 시 해당하는 문자열의 뷰를 찾는데,,  
		// 문자열 그대로를 return하기 위해 @ResponseBody 추가
		return "Hello! What are you learning today?!";
	}
	
	@RequestMapping("say-hello-html")
	@ResponseBody // 리턴한 것 그대로를 브라우저에 전송
	public String sayHelloHtml() {
		// Spring MVC는 리턴 시 해당하는 문자열의 뷰를 찾는데,, 
		//html을 하드코딩 하는 건 비효율적이기 때문에 String Buffer를 사용하여 작업
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<title> My First HTML Page</title>");
		sb.append("</head>");
		sb.append("<body>");
		sb.append("My first html page with body by.Mindun");
		sb.append("</body>");
		sb.append("</html>");
				
		return sb.toString();
	}
	
	//sayHello.jsp의 뷰를 만들어 호출할 예정
	//"say-hello-jsp" -> sayHello.jsp
	//jsp폴더 : /src/main/resources/META-INF/resources/WEB-INF/jsp/sayHello.jsp
	//		   /src/main/resources/META-INF/resources/WEB-INF/jsp/sayHello.jsp
	@RequestMapping("say-hello-jsp")
	//@ResponseBody // 리턴한 것 그대로를 브라우저에 전송 나는!!view호출한거라 해당 어노테이션 제거
	public String sayHelloJsp() {
		// Spring MVC는 리턴 시 해당하는 문자열의 뷰를 찾는데,,  
		// 문자열 그대로를 return하기 위해 @ResponseBody 추가
		return "sayHello";
	}
	
}
