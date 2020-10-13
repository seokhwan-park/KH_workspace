<%@page import="com.kh.spring.member.model.vo.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:import url="../common/menubar.jsp" />

	<h1 align="center">게시글 상세보기</h1>
	<br>
	<table align="center" id="tb" style="border: 1px solid black;">
		<tr align="center" valign="middle">
			<th colspan="2">${ b.bId }번글상세보기</th>
		</tr>
		<tr>
			<td>제목</td>
			<td>${ b.bTitle }</td>
		</tr>
		<tr>
			<td>작성자</td>
			<td>${ b.bWriter }</td>
		</tr>
		<tr>
			<td>내용</td>
			<td>${ b.bContent }</td>
		</tr>
		<tr>
			<td>첨부파일</td>
			<td><c:if test="${ !empty b.originalFileName }">
					<a href="${ contextPath }/resources/buploadFiles/${b.renameFileName }" download="${ b.originalFileName }"> ${ b.originalFileName } </a>
				</c:if></td>
		</tr>
		<tr>
			<td colspan="2" align="center"><c:url var="bupview" value="bupView.do">
					<c:param name="bId" value="${ b.bId }" />
				</c:url> <c:url var="bdelete" value="bdelete.do">
					<c:param name="bId" value="${ b.bId }" />
				</c:url> <c:url var="blist" value="blist.do">
					<c:param name="currentPage" value="${ currentPage }" />
				</c:url> <c:if test="${ loginUser.id eq b.bWriter }">
					<a href="${ bupview }">수정하기</a>
					<a href="${ bdelete }">삭제하기</a>
				</c:if> <a href="${ blist }">목록으로</a></td>
		</tr>

	</table>

	<br>
	<br>

	   <!-- Ajax를 이용해서 댓글 등록 -->
   <table align="center" width="500" border="1" cellspacing="0">
         <tr>
            <td><textarea cols="55" rows="3" id="rContent"></textarea></td>
            <td>
               <button id="rSubmit">등록하기</button>
            </td>
         </tr>
   </table>
   
   <!-- 댓글 목록보기 -->
   <table align="center" width="500" border="1" cellspacing="0" id="rtb">
         <thead>
            <tr>
               <td colspan="3"><b id="rCount"></b></td>
            </tr>
         </thead>
         <tbody>
         </tbody>
   </table>
	<script type="text/javascript">
         $(function(){
            getReplyList();
            
            // 댓글 등록
            $("#rSubmit").on("click",function(){
               var rContent=$("#rContent").val();
               var refBid = ${b.bId};
               var rWriter = "<%=((Member) session.getAttribute("loginUser")).getId()%>";   
               // 필요한 데이터들은 가져와서 쓸 수 있다.
               
               $.ajax({
                  url:"addReply.do",
                  data:{
                     rContent:rContent,
                     refBid:refBid,
                     rWriter:rWriter
                  },
                  type:"post",
                  success:function(data){           // 댓글등록 성공 시 : succecc, 댓글 등록 실패 시 : fail
                     if(data == "success"){
                        getReplyList();         // 등록 성공 시 다시 댓글 리스트 불러오기
                        $("#rContent").val("");   // 댓글 등록시에 사용한 댓글 내용 초기화
                     }
                  }, error:function(){
                     console.log("전송 실패");    
                  }
               });
            });
         });
         
         function getReplyList(){
            var bId = ${ b.bId };
            
            $.ajax({
               url:"rList.do",
               data:{ bId:bId },
               dataType:"json",
               success:function(data){
               //   console.log(data);
                  $tableBody = $("#rtb tbody");
                  $tableBody.html("");
                  
                  // 분기처리해야하기 위해 변수를 초기화 안해주고 밖으로 밴다.
                  var $tr;
                  var $rWriter;
                  var $rContent;
                  var $rCreateDate;
               
                  $("#rCount").text("댓글 (" + data.length +")"); // 댓글(0) 갯수
                  if(data.length > 0){  // 해당 게시글 댓글이 존재할 경우
                     for(var i in data){
                        $tr = $("<tr>");
                        $rWriter = $("<td width='100'>").text(data[i].rWriter);
                        $rContent= $("<td>").text(data[i].rContent);
                        $rCreateDate=$("<td width='100'>").text(data[i].rCreateDate);
                        
                        $tr.append($rWriter);
                        $tr.append($rContent);
                        $tr.append($rCreateDate);
                        $tableBody.append($tr);
                     }
                  }else{
                     $tr = $("<tr>");
                     $rContent = $("<td colspan='3'>").text("등록된 댓글이 없습니다.");
                     
                     $tr.append($rContent);
                     $tableBody.append($tr);
                  }
               },error:function(){
                  console.log("전송실패");
               }
            });
         }
   </script>






</body>
</html>