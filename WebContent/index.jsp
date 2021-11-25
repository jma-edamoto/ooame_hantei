<%@ page language="java" contentType="text/html; charset=Shift_JIS" pageEncoding="Shift_JIS" %>
<%@ page import="jp.go.kishou.adess.oswy61.distinction.rain.*" %>
<%@ page import="java.util.*" %>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=Shift_JIS">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="-1">
<title>和歌山県 大雨基本パターン判定ツール Ver.<%=Version.getVersion()%></title>
</head>
<body>
<jsp:useBean id="serverInfo" class="jp.go.kishou.adess.oswy61.distinction.rain.bean.ServerBean" scope="page"/>
<table border="1" width="1200" height="800" bordercolor="#6090ef">
	<tr>
		<td align="center" valign="middle"><font size="+2" color="navy">和歌山県 大雨基本パターン判定ツール Ver.<%=Version.getVersion()%></font></td>
	</tr>
	<tr>
		<td align="center"><br><font size="+2">・使用モデルを選択してください。<br>・FT=39までの大雨判定を実施します。<br></font><br>
		<form action="ProcessServlet" method="POST">
		<font size="+2">使用モデル：</font>
		<c:choose>
			<c:when test="${fn:length(serverInfo.fileNameList) == 0}">
				<select name="abdir">
					<option value="0">モデルがありません。終了してください。</option>
				</select>
				<br>
			</c:when>
			<c:otherwise>
				<select name="abdir">
				<c:forEach var="filename" items="${serverInfo.fileNameList}">
					<option value="${serverInfo.dir.concat(filename)}">${filename }</option>
				</c:forEach>
			</select>
			<input type="submit" value="判定"><br>
			<table>
				<tr>
					<td align="center">
						<br><img src="image/haichizu.png"><br>
					</td>
				</tr>
			</table>
			</c:otherwise>
		</c:choose>
		<br>
		<input type="button" value="終了" onclick="window.close()"><br><br>
		</form>
		</td>
	</tr>
</table>
</body>
</html>