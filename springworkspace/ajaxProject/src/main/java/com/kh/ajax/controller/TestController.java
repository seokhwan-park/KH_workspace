package com.kh.ajax.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.ajax.model.vo.User;
@Controller
public class TestController 
{
   /**
    * 1. ServletOutputStream을 이용한 방식
    * @param name
    * @param age
    * @param response
    * @throws IOException 
    */
//   @RequestMapping("test1.do")
//   public void test1Method(String name, int age, HttpServletResponse response) throws IOException
//   {
//      PrintWriter out = response.getWriter();
//      
//      if(name.equals("신사임당") && age == 47) {
//    	  out.print("ok");
//      }else {
//    	  out.print("fail");
//      }
//   }
	
	
	
	/**
	 * 2. @ResponseBody를 이용한 방식
	 * 	  반환형이 String일 경우 뷰명 반환
	 *   하지만 @ResponseBody가 붙으면 String반환은 해당 요청을 보낸 뷰에 전달할 데이터로 변환
	 * @param name
	 * @param age
	 * @param response
	 * @return
	 */
	@ResponseBody // 결과를 response 객체에 담아서 보내주세요 즉, 원래는 string으로 view로 가야하는데 해당 @를 써주면 data로 이동한다.
	@RequestMapping("test1.do")
	   public String test1Method(String name, int age) {
	      // httpSevlet을 선언하지 않아도 responsebody가 대체해준다.
		
	      if(name.equals("신사임당") && age == 47) {
	    	  return "ok";
	      }else {
	    	  return "fail";
	      }
	   }
	
	
	
	/**
	 * 3. @ResponseBody를 사용
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value="test2.do", produces="application/json;charset=utf-8")
	public String test2Method() throws UnsupportedEncodingException {
		// DB에서 조회해온 결과를 가져왔다고 가정하고 User 객체를 하나 생성
		User user = new User("user01", "pass01", "홍길동", 20, "user01@test.com", "01012345678");
		
		
		// response.setContentType("application/json; charset=UTF-8");	// 원래 이렇게 지정안해주면 문자열로 인식
		// @ResponseBody 어노테이션을 추가하게 되면 위의 코드를 생략해도 된다.
		// 다만 charset은 별도로 수정해주어야 한다.
		
		// JSON 객체에 담아서 보내주기
		// JSON 사용을 위해서 pom.xml json-simple라이브러리를 추가했기 때문에 import가능
		// key값과 vo객체의 변수명은 왠만해서는 맞추어주면 좋다.
		JSONObject job = new JSONObject();	
		job.put("userId", user.getUserId());
		job.put("userPwd", user.getUserPwd());
//		job.put("userName", URLEncoder.encode(user.getUserName(),"UTF-8"));
		// response부분에서 설정해줘도 되고, request에서 설정해주어도 된다.
		job.put("userName", user.getUserName());
		job.put("age", user.getAge());
		job.put("email", user.getEmail());
		job.put("phone", user.getPhone());
		
		//return job;	// 반환형이 String이 아니어서 오류
		return job.toJSONString(); // String 타입이지만, jsonstring이어서 key, value형태~!!!!
		
		
	}
	
	
	
	/**
	 * 4. @ResponseBody를 쓰지않고 Json 객체 보내기
	 * @return
	 * @throws IOException 
	 * @throws UnsupportedEncodingException
	 */
//	@RequestMapping("test2.do")
//	public void test2Method(HttpServletResponse response) throws IOException {		// HttpServletResponse 추가!!!!!!!!!
//		// DB에서 조회해온 결과를 가져왔다고 가정하고 User 객체를 하나 생성
//		User user = new User("user01", "pass01", "홍길동", 20, "user01@test.com", "01012345678");
//		
//		response.setContentType("application/json); charset=UTF-8");
//		
//		// JSON 객체에 담아서 보내주기
//		// JSON 사용을 위해서 pom.xml json-simple라이브러리를 추가했기 때문에 import가능
//		// key값과 vo객체의 변수명은 왠만해서는 맞추어주면 좋다.
//		JSONObject job = new JSONObject();	
//		job.put("userId", user.getUserId());
//		job.put("userPwd", user.getUserPwd());
//		job.put("userName", URLEncoder.encode(user.getUserName(),"UTF-8"));
//		job.put("age", user.getAge());
//		job.put("email", user.getEmail());
//		job.put("phone", user.getPhone());
//
//
//		PrintWriter out = response.getWriter();
//		out.print(job);
//		
//	}
}



























