<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
   <c:import url="../common/menubar.jsp"/>
   
   <h1 align="center">게시글 상세보기</h1>
   <br>
   <table align="center" id="tb">
      <tr align="center" valign="middle">
         <th colspan="2">${ b.bId }번 글 상세보기 </th>
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
         <td>
            <c:if test="${ !empty b.originalFileName }">
               <a href="${ contextPath }/resources/buploadFiles/${b.renameFileName }" download="${ b.originalFileName }">
                  ${ b.originalFileName }
               </a>
            </c:if>
         </td>
      </tr>
      <tr>
         <td colspan="2" align="center">
            <c:url var="bupview" value="bupView.do">
               <c:param name="bId" value="${b.bId }"/>
            </c:url>   
            <c:url var="bdelete" value="bdelete.do">
               <c:param name="bId" value="${b.bId }"/>
            </c:url>
            <c:url var="blist" value="blist.do">
               <c:param name="currentPage" value="${ currentPage }"/>
            </c:url>
            
            <c:if test="${loginUser.id eq b.bWriter }">
               <a href="${bupview }">수정하기</a>
               <a href="${bdelete }">삭제하기</a>
            </c:if>
         </td>
      </tr>
   </table>
   
   <br>
   <br>
   
   
</body>
</html>