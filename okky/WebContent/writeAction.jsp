<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="okky.OkkyDAO" %>
<%@ page import="java.io.PrintWriter" %>
<% request.setCharacterEncoding("UTF-8");  %>
<jsp:useBean id="okky" class="okky.Okky" scope="page" />
<jsp:setProperty name="okky" property="okkyTitle"/>
<jsp:setProperty name="okky" property="okkyContent"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>웹 사이트</title>
</head>
<body>
	<%	
	String userID = null;
	if(session.getAttribute("userID") != null){
		userID = (String) session.getAttribute("userID");
	}
	if(userID == null){
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('로그인을 하세요.')");
		script.println("location.href = 'login.jsp'");
		script.println("</script>");
	}else{
		if(okky.getOkkyTitle() == null || okky.getOkkyContent() == null){
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("alert('입력이 안 된 사항이 있습니다.')");
				script.println("history.back()");
				script.println("</script>");
			} else{
				OkkyDAO okkyDAO = new OkkyDAO();
				int result = okkyDAO.write(okky.getOkkyTitle(), userID, okky.getOkkyContent());
				if(result == -1){
					PrintWriter script = response.getWriter();
					script.println("<script>");
					script.println("alert('글쓰기에 실패했습니다.')");
					script.println("history.back()");
					script.println("</script>");
				}
				else {
					PrintWriter script = response.getWriter();
					script.println("<script>");
					script.println("location.href = 'okky.jsp'");
					script.println("</script>");
				}
				
			}
			
	}
		
		
	%>
</body>
</html>