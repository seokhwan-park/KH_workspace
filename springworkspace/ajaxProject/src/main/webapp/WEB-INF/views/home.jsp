<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
   <title>Home</title>
   <script src="http://code.jquery.com/jquery-3.5.1.min.js"></script>
</head>
<body>
   <h1 align="center">Spring에서의 AJAX 사용 테스트</h1>

   <ol>
      <li>
         서버쪽 컨트롤러 값 보내기
         <button id="btn1">테스트</button>
         <div id="d1"></div>
      </li>
      <script>
         $(function()
            {
               $("#btn1").on("click", function()
                  {
                     $.ajax(
                     {
                        url:"test1.do",
                        data:{name:"신사임당", age:47},
                        type:"post",
                        success:function(data)
                        {
                           console.log(data);
                           if(data=="ok"){
                        	   $("#d1").text("전송성공");
                           }else{
                        	   $("#d1").text("전송실패");
                           }
                        },
                        /* error:function(request, jqXHR, exception)
                        {
                           // jqXHR : 기본 XHMLHttpRequest 객체의 래퍼
                           alert("error code : " + request.status + "\n" + "message : " + request.responseText + "error : " + errorData);
                        } */
                        error:function(request,jqXHR,exception){
                        	var msg="";
                        	if(request.status==0){
                        		msg='Not Connect. \n Verify Network.';
                        	}else if(request.status == 404){
                        		msg='Requested page not found [404]';
                        	}else if(request.status == 500){
                        		msg='Internal Server Error-> code error [500]'
                        	}else if(request.status == 'parseerror'){
                        		msg='Requested JSON parse failed';
                        	}else if(exception == 'timeout'){
                        		msg='Time out error';
                        	}else if(exception == 'abort'){
                        		msg='Ajax request abprted';
                        	}else{
                        		msg='Error. \n' + jqXHR.responseText;
                        	}
                        	alert(msg);
                        }
                     });
                  });
            });
      </script>
   

		<li>컨트롤러에서 리턴하는 JSON 객체 받아서 출력하기
			<button id="btn2">테스트</button>
			<div id="d2"></div>
		</li>
		<script>
			$(function(){
				$("#btn2").on("click",function(){
					$.ajax({
						url:"test2.do",
						dataType:"json",	// 컨트롤러에서 ajax로 넘겨주는 형식 지정!
						success:function(data){
							console.log(data);
							console.log(decodeURIComponent(data.userName));
							
							$("#d2").html("아이디 : data.userId" + data.userId
									+ "<br> 이름 : " + decodeURIComponent(data.userName)
									+ "<br> 이메일 : " + data.email
									+ "<br> 전화번호 : " + data.phone
									+ "<br> 나이 : " + data.age);
							
						},
						error:function(request,status,errorData){
							alert("error code : " + request.status + "\n" + "message : " + request.responseText + "error : " + errorData);
						}
					});
				});
			});
		</script>
   
   
   </ol>
</body>
</html>