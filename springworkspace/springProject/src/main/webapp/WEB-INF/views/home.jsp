<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
</head>
<body>
	<c:import url="common/menubar.jsp" />
	<!-- ajax를 통해서 게시글 TOP 5 목록 불러오기  -->
	<h1 align="center">게시글 TOP5 목록</h1>
	<table align="center" border="1" cellspacing="0" width="700" id="tb">
		<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>번날짜</th>
				<th>조회수</th>
				<th>첨부파일</th>
			</tr>
		</thead>
		<tbody></tbody>
	</table>
	<script type="text/javascript">
		$(function() {
			topList();
			setInterval(function() {
				topList();
			}, 3000);
		
		});
		$(function() {
			topList();
		});
		function topList() {
			$.ajax({
						url : "topList.do",
						dataType : "JSON",
						success : function(data) {
							console.log(data);
							$tableBody = $("#tb tbody");
							$tableBody.html("");
							for ( var i in data) {
								var $tr = $("<tr>");
								var $bId = $("<td>").text(data[i].bId);
								var $bTitle = $("<td>").text(data[i].bTitle);
								var $bWriter = $("<td>").text(data[i].bWiter);
								var $bCreateDate = $("<td>").text(
										data[i].bCreateDate);
								var $bCount = $("<td>").text(data[i].bCount);
								var $bFile = $("<td>").text(" ");
								if (data[i].originalFileName != null) {
									$bFile = $("<td>").text("◎");
								}
								$tr.append($bId);
								$tr.append($bTitle);
								$tr.append($bWriter);
								$tr.append($bCreateDate);
								$tr.append($bCount);
								$tr.append($bFile);

								$tableBody.append($tr);
							}
						},
						error : function(request, status, error) {
							alert("code:" + request.status + "\n"
									+ "message : " + request.responseText
									+ "\n" + "error : " + error);

						}
					});
		}
	</script>
</body>
</html>
