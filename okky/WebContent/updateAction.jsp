<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="okky.OkkyDAO" %>
<%@ page import="okky.Okky" %>
<%@ page import="java.io.PrintWriter" %>
<% request.setCharacterEncoding("UTF-8");  %>
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
	}
	
	int okkyID = 0;
	if (request.getParameter("okkyID") != null) {
		okkyID = Integer.parseInt(request.getParameter("okkyID"));
	}
	if (okkyID == 0) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('유효하지 않은 글입니다.')");
		script.println("location.href = 'okky.jsp'");
		script.println("</script>");
	}
	Okky okky = new OkkyDAO().getOkky(okkyID);
	if (!userID.equals(okky.getUserID())){
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('권한이 없습니다.')");
		script.println("location.href = 'okky.jsp'");
		script.println("</script>");
	}
	
	
	else{
		if(request.getParameter("okkyTitle") == null || request.getParameter("okkyContent") == null || request.getParameter("okkyTitle").equals("") || request.getParameter("okkyContent").equals("")){
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("alert('입력이 안 된 사항이 있습니다.')");
				script.println("history.back()");
				script.println("</script>");
			} else{
				OkkyDAO okkyDAO = new OkkyDAO();
				int result = okkyDAO.update(okkyID, request.getParameter("okkyTitle"),request.getParameter("okkyContent"));
				if(result == -1){
					PrintWriter script = response.getWriter();
					script.println("<script>");
					script.println("alert('글 수정에 실패했습니다.')");
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