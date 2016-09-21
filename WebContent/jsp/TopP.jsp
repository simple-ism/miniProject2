<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div>등록제품</div><div id="sum1"></div><div>개</div>
	<div>낮은가격</div><div>높은가격</div><div>상품명</div><div>신상품</div>
	<hr>
	<div id="product"></div>
	<div id="paging">
		<div><a href=""><img src="${pageContext.request.contextPath}/images/btn_page_first.gif"/></a></div>
		<div><a href=""><img src="${pageContext.request.contextPath}/images/btn_page_prev.gif"/></a></div>
		<div id="input1"></div>
		<div><a href=""><img src="${pageContext.request.contextPath}/images/btn_page_next.gif"/></a></div>
		<div><a href=""><img src="${pageContext.request.contextPath}/images/btn_page_last.gif"/></a></div>
	</div>
<script>
	var page=1;
	(function(page){
		$.ajax({
			url:"/mini2Project/jsp/TopP.do",
			data: {'page' : page},	
			dataType: "json"
			
		})
		.done(function(result){
			var html="";
			html += 
		})
	})();

</script>
</body>
</html>


