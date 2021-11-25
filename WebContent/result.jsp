<%@ page language="java" contentType="text/html; charset=Shift_JIS" pageEncoding="Shift_JIS" %>
<%@ page import="jp.go.kishou.adess.oswy61.distinction.rain.*" %>
<%@ page import="java.io.*,java.util.*,java.text.*"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=Shift_JIS">
<link rel="stylesheet" type="text/css" href="common.css">
<title>�a�̎R�� ��J��{�p�^�[������c�[�� Ver.<%=Version.getVersion()%></title>
</head>
<body>
<jsp:useBean id="judge" class="jp.go.kishou.adess.oswy61.distinction.rain.bean.JudgeDataBean" scope="session"/>
<c:catch var="exception">
<table border="1" width="2280" height="800" bordercolor="#6090ef">
<!-- MSM�̗\�񎞊Ԃ��ύX���ꂽ�ꍇ�́AFTsetting.properties��ҏW����ƂƂ��ɁA��s�O��width��K���ɑ傫�����Ă��������B -->
	<tr>
		<td valign="middle"><font size="+2" color="navy">�a�̎R�� ��J��{�p�^�[������c�[�� Ver.<%=Version.getVersion()%></font></td>
	</tr>
	<tr>
	<td align="center"><br>
	<font size="+2">���茋�ʂ͎��̒ʂ�ł��B</font><br><br>
	<table border="1" cellspacing="0">
		<tr>
			<td width="16" rowspan="15" bgcolor="#55ffaa">�쐼���n����</td>
			<td rowspan="2" bgcolor="#55ffaa">${fn:substring(judge.fname ,0,16)}</td>
			<td width="160" bgcolor="#55ffaa">FT</td>
			<c:forEach begin="0" end="${judge.ft}" var="i">
				<td bgcolor="#55ffaa">${i}</td>
			</c:forEach>
			<td width="160" bgcolor="#55ffaa">FT</td>
		</tr>

		<tr>
			<td width="160" bgcolor="#55ffaa">JST</td>
			<c:forEach var="jst" items="${judge.jst}">
				<td bgcolor="#55ffaa">${jst}</td>
			</c:forEach>
			<td width="160" bgcolor="#55ffaa">JST</td>
		</tr>

		<tr>
			<td rowspan="5" bgcolor="#55ffaa">�̈楓��_<br>�i5�_�͒��ӕ�A<br>6�E7�E8�_�͌x��j</td>
			<td width="160" bgcolor="#55ffaa"><a href="image/nairiku.png" target="_blank">�����^</a></td>
			<c:forEach var="point" items="${judge.sw_pNairiku}" varStatus="status">
				<td bgcolor="${Display.getBgcolor(point, 'SW')}">
					<font color="${Display.getColor(point, 'SW')}">
						${Display.getPoint(point,judge.sw_flag[status.index])  }
					</font>
				</td>
			</c:forEach>
			<td width="160" bgcolor="#55ffaa">�����^</td>
		</tr>

		<tr>
			<td width="160" bgcolor="#55ffaa"><a href="image/kichu.png" target="_blank">�I���A�c�ӁE�����K�^</a></td>
			<c:forEach var="point" items="${judge.sw_pKichu}" varStatus="status">
				<td bgcolor="${Display.getBgcolor(point, 'SW')}">
					<font color="${Display.getColor(point, 'SW')}">
						${Display.getPoint(point,judge.sw_flag[status.index])  }
					</font>
				</td>
			</c:forEach>
			<td width="160" bgcolor="#55ffaa">�I���A�c�ӁE�����K�^</td>
		</tr>

		<tr>
			<td width="160" bgcolor="#55ffaa"><a href="image/hokubu.png" target="_blank">�k���^</a></td>
			<c:forEach var="point" items="${judge.sw_pHokubu}" varStatus="status">
				<td bgcolor="${Display.getBgcolor(point, 'SW')}">
					<font color="${Display.getColor(point, 'SW')}">
						${Display.getPoint(point,judge.sw_flag[status.index])  }
					</font>
				</td>
			</c:forEach>
			<td width="160" bgcolor="#55ffaa">�k���^</td>
		</tr>

		<tr>
			<td width="160" bgcolor="#55ffaa"><a href="image/engan.png" target="_blank">���݌^</a></td>
			<c:forEach var="point" items="${judge.sw_pEngan}" varStatus="status">
				<td bgcolor="${Display.getBgcolor(point, 'SW')}">
					<font color="${Display.getColor(point, 'SW')}">
						${Display.getPoint(point,judge.sw_flag[status.index])  }
					</font>
				</td>
			</c:forEach>
			<td width="160" bgcolor="#55ffaa">���݌^</td>
		</tr>

		<tr>
			<td width="160" bgcolor="#55ffaa"><a href="image/tanabe.png" target="_blank">�c�ӥ�����K���݌^</a></td>
			<c:forEach var="point" items="${judge.sw_pTanabe}" varStatus="status">
				<td bgcolor="${Display.getBgcolor(point, 'SW')}">
					<font color="${Display.getColor(point, 'SW')}">
						${Display.getPoint(point,judge.sw_flag[status.index])  }
					</font>
				</td>
			</c:forEach>
			<td width="160" bgcolor="#55ffaa">�c�ӥ�����K���݌^</td>
		</tr>

		<tr>
			<td rowspan="7" bgcolor="#55ffaa">�v�f<br>�i���F��1�_�A<br>��F��2�_�j</td>
			<td width="160" bgcolor="#55ffaa">950hPa����[kt]</td>
			<c:forEach var="val" items="${judge.sw_ws950}" varStatus="status">
				<td bgcolor="${Display.getBgcolor(judge.swSpeed950Score[status.index])}">${Display.getValue(val)}</td>
			</c:forEach>
			<td width="160" bgcolor="#55ffaa">950hPa����[kt]</td>
		</tr>

		<tr>
			<td width="160" bgcolor="#55ffaa">K-index</td>
			<c:forEach var="val" items="${judge.sw_kindex}" varStatus="status">
				<td bgcolor="${Display.getBgcolor(judge.swKIndexScore[status.index])}">${Display.getValue(val)}</td>
			</c:forEach>
			<td width="160" bgcolor="#55ffaa">K-index</td>
		</tr>

		<tr>
			<td width="160" bgcolor="#55ffaa">SSI</td>
			<c:forEach var="val" items="${judge.sw_ssi}" varStatus="status">
				<td bgcolor="${Display.getBgcolor(judge.swSsiScore[status.index])}">${Display.getValue(val)}</td>
			</c:forEach>
			<td width="160" bgcolor="#55ffaa">SSI</td>
		</tr>

		<tr>
			<td width="160" bgcolor="#55ffaa">�~����</td>
			<c:forEach var="val" items="${judge.sw_tpw}" varStatus="status">
				<td bgcolor="${Display.getBgcolor(judge.swTpwScore[status.index])}">${Display.getValue(val)}</td>
			</c:forEach>
			<td width="160" bgcolor="#55ffaa">�~����</td>
		</tr>

		<tr>
			<td width="160" bgcolor="#55ffaa">850hPa��������</td>
			<c:forEach var="val" items="${judge.sw_ept850}" varStatus="status">
				<td bgcolor="${Display.getBgcolor(judge.swEpt850Score[status.index])}">${Display.getValue(val)}</td>
			</c:forEach>
			<td width="160" bgcolor="#55ffaa">850hPa��������</td>
		</tr>

		<tr>
			<td width="160" bgcolor="#55ffaa">950hPa��������</td>
			<c:forEach var="val" items="${judge.sw_ept950}" varStatus="status">
				<td bgcolor="${Display.getBgcolor(judge.swEpt950Score[status.index])}">${Display.getValue(val)}</td>
			</c:forEach>
			<td width="160" bgcolor="#55ffaa">950hPa��������</td>
		</tr>

		<tr>
			<td width="160" bgcolor="#55ffaa">�������ʍ�<br>950hPa-850hPa</td>
			<c:forEach var="val" items="${judge.sw_eptdif}" varStatus="status">
				<td bgcolor="${Display.getBgcolor(judge.swEptDiffScore[status.index])}">${Display.getValue(val)}</td>
			</c:forEach>
			<td width="160" bgcolor="#55ffaa">�������ʍ�<br>950hPa-850hPa</td>
		</tr>

		<tr>
			<td bgcolor="#55ffaa">�i55mm�ȏ�͒��ӕ�A<br>65mm�ȏ�͌x��j</td>
			<td width="160" bgcolor="#55ffaa">��A���J��</td>
			<c:forEach var="val" items="${judge.sw_r1}" varStatus="status">
				<td bgcolor="${Display.getBgcolor(val)}">
					<font color="${Display.getColor(val)}">${Display.getValue(val)}</font>
				</td>
			</c:forEach>
			<td width="160" bgcolor="#55ffaa">��A���J��</td>
		</tr>
	</table>

	<br>

	<table border="1" cellspacing="0">
		<tr>
			<td width="16" rowspan="13" bgcolor="#aad5ff">�쓌���n����</td>
			<td rowspan="2" bgcolor="#aad5ff">${fn:substring(judge.fname ,0,16)}</td>
			<td width="160" bgcolor="#aad5ff">FT</td>
			<c:forEach begin="0" end="${fn:length(judge.jst) - 1}" var="i">
				<td bgcolor="#aad5ff">${i}</td>
			</c:forEach>
			<td width="160" bgcolor="#aad5ff">FT</td>
		</tr>

		<tr>
			<td width="160" bgcolor="#aad5ff">JST</td>
			<c:forEach var="jst" items="${judge.jst}">
				<td bgcolor="#aad5ff">${jst}</td>
			</c:forEach>
			<td width="160" bgcolor="#aad5ff">JST</td>
		</tr>

		<tr>
			<td rowspan="2" bgcolor="#aad5ff">�̈楓��_<br>�i5�_�͒��ӕ�A<br>6�E7�_�͌x��j</td>
			<td width="160" bgcolor="#aad5ff"><a href="image/higashi.png" target="_blank">���^</a></td>
			<c:forEach var="point" items="${judge.se_pHigashi}" varStatus="status">
				<td bgcolor="${Display.getBgcolor(point, 'SE')}">
					<font color="${Display.getColor(point, 'SE')}">
						${Display.getPoint(point,judge.se_flag[status.index])  }
					</font>
				</td>
			</c:forEach>
			<td width="160" bgcolor="#aad5ff">���^</td>
		</tr>

		<tr>
			<td width="160" bgcolor="#aad5ff"><a href="image/nanto.png" target="_blank">�쓌�^</a></td>
			<c:forEach var="point" items="${judge.se_pNanto}" varStatus="status">
				<td bgcolor="${Display.getBgcolor(point, 'SE')}">
					<font color="${Display.getColor(point, 'SE')}">
						${Display.getPoint(point,judge.se_flag[status.index])  }
					</font>
				</td>
			</c:forEach>
			<td width="160" bgcolor="#aad5ff">�쓌�^</td>
		</tr>

		<tr>
			<td rowspan="8" bgcolor="#aad5ff">�v�f<br>�i���F��1�_�j</td>
			<td width="160" bgcolor="#aad5ff">700hPa��k��[kt]</td>
			<c:forEach var="val" items="${judge.se_v700}" varStatus="status">
				<td bgcolor="${Display.getBgcolor(judge.seV700Score[status.index])}">${Display.getValue(val)}</td>
			</c:forEach>
			<td width="160" bgcolor="#aad5ff">700hPa��k��[kt]</td>
		</tr>

		<tr>
			<td width="160" bgcolor="#aad5ff">850hPa������[kt]</td>
			<c:forEach var="val" items="${judge.se_u850}" varStatus="status">
				<td bgcolor="${Display.getBgcolor(judge.seU850Score[status.index])}">${Display.getValue(val)}</td>
			</c:forEach>
			<td width="160" bgcolor="#aad5ff">850hPa������[kt]</td>
		</tr>

		<tr>
			<td width="160" bgcolor="#aad5ff">K-index</td>
			<c:forEach var="val" items="${judge.se_kindex}" varStatus="status">
				<td bgcolor="${Display.getBgcolor(judge.seKIndexScore[status.index])}">${Display.getValue(val)}</td>
			</c:forEach>
			<td width="160" bgcolor="#aad5ff">K-index</td>
		</tr>

		<tr>
			<td width="160" bgcolor="#aad5ff">SSI</td>
			<c:forEach var="val" items="${judge.se_ssi}" varStatus="status">
				<td bgcolor="${Display.getBgcolor(judge.seSsiScore[status.index])}">${Display.getValue(val)}</td>
			</c:forEach>
			<td width="160" bgcolor="#aad5ff">SSI</td>
		</tr>

		<tr>
			<td width="160" bgcolor="#aad5ff">�~����</td>
			<c:forEach var="val" items="${judge.se_tpw}" varStatus="status">
				<td bgcolor="${Display.getBgcolor(judge.seTpwScore[status.index])}">${Display.getValue(val)}</td>
			</c:forEach>
			<td width="160" bgcolor="#aad5ff">�~����</td>
		</tr>

		<tr>
			<td width="160" bgcolor="#aad5ff">850hPa��������</td>
			<c:forEach var="val" items="${judge.se_ept850}" varStatus="status">
				<td bgcolor="#ffffff">${Display.getValue(val)}</td>
			</c:forEach>
			<td width="160" bgcolor="#aad5ff">850hPa��������</td>
		</tr>

		<tr>
			<td width="160" bgcolor="#aad5ff">950hPa��������</td>
			<c:forEach var="val" items="${judge.se_ept950}" varStatus="status">
				<td bgcolor="${Display.getBgcolor(judge.seEpt950Score[status.index])}">${Display.getValue(val)}</td>
			</c:forEach>
			<td width="160" bgcolor="#aad5ff">950hPa��������</td>
		</tr>

		<tr>
			<td width="160" bgcolor="#aad5ff">�������ʍ�<br>950hPa-850hPa</td>
			<c:forEach var="val" items="${judge.se_eptdif}" varStatus="status">
				<td bgcolor="${Display.getBgcolor(judge.seEptDiffScore[status.index])}">${Display.getValue(val)}</td>
			</c:forEach>
			<td width="160" bgcolor="#aad5ff">�������ʍ�<br>950hPa-850hPa</td>
		</tr>

		<tr>
			<td bgcolor="#aad5ff">�i55mm�ȏ�͒��ӕ�A<br>65mm�ȏ�͌x��j</td>
			<td width="160" bgcolor="#aad5ff">��A���J��</td>
			<c:forEach var="val" items="${judge.se_r1}" varStatus="status">
				<td bgcolor="${Display.getBgcolor(val)}">
					<font color="${Display.getColor(val)}">${Display.getValue(val)}</font>
				</td>
			</c:forEach>
			<td width="160" bgcolor="#aad5ff">��A���J��</td>
		</tr>
	</table>

	<br>
		<c:set var="info" value="${judge}" scope="session" />
		<font size="+2">���茋�ʂ�pdf�t�@�C���ɕۑ����邱�Ƃ��ł��܂��B</font><br><br>
		<form action="PDFCreatorServlet" method="post">
			<input type="submit" value="pdf�o��">
			<input type="button" value="�߂�" onClick="history.back()">
			<input type="button" value="�I��" onClick="window.close()"><br><br>
		</form>
	</td>
	</tr>
</table>
</c:catch>
<c:if test="${exception != null}">
	�G���[���������܂���
</c:if>

</body>
</html>