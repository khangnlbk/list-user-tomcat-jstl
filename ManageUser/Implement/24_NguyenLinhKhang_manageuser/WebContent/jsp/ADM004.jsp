<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="${pageContext.request.contextPath}/css/style.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/user.js"></script>
<title>ユーザ管理</title>
</head>
<body>
	<!-- Begin vung header -->
	<jsp:include page="Header.jsp"></jsp:include>

	<!-- End vung header -->

	<!-- Begin vung input-->
	<form 
		<c:choose>
			<c:when test="${userInfor.userId eq 0}">
				action="AddUserOK.do?key=${key}" 						
			</c:when>
			<c:otherwise>
				action="EditUserOK.do?key=${key}"	
			</c:otherwise>
		</c:choose> 
		 method="post" name="inputform">
		<table class="tbl_input" border="0" width="75%" cellpadding="0"
			cellspacing="0">
			<tr>
				<th align="left">
					<div style="padding-left: 100px;">
						情報確認<br></br>
						入力された情報をＯＫボタンクリックでＤＢへ保存してください 
					</div>
					<div style="padding-left: 100px;">&nbsp;</div>
				</th>
			</tr>
			<tr>
				<td align="left">
					<div style="padding-left: 100px;">
						<table border="1" width="70%" class="tbl_input" cellpadding="4"
							cellspacing="0">
							<tr>
								<td class="lbl_left" >アカウント名:</td>
								<td align="left">${fn:escapeXml(userInfor.loginName)}</td>
							</tr>
							<tr>
								<td class="lbl_left">グループ:</td>
								<td align="left">${fn:escapeXml(userInfor.groupName)}</td>
							</tr>
							<tr>
								<td class="lbl_left">氏名:</td>
								<td align="left">${fn:escapeXml(userInfor.fullName)}</td>
							</tr>
							<tr>
								<td class="lbl_left">カタカナ氏名:</td>
								<td align="left">${fn:escapeXml(userInfor.fullNameKana)}</td>
							</tr>
							<tr>
								<td class="lbl_left">生年月日:</td>
								<td align="left">${fn:escapeXml(fn:replace(userInfor.birthday, '-', '/'))}</td>
							</tr>
							<tr>
								<td class="lbl_left">メールアドレス:</td>
								<td align="left">${fn:escapeXml(userInfor.email)}</td>
							</tr>
							<tr>
								<td class="lbl_left">電話番号:</td>
								<td align="left">${fn:escapeXml(userInfor.tel)}</td>
							</tr>
							<tr>
								<th colspan="2"><a href="javascript:void(0)" 
									onclick="hiddenData();">日本語能力</a></th>
							</tr>
						</table>
					</div>
					<div id="levelJapan" style="padding-left: 100px; display: none;">
						<table border="1" width="70%" class="tbl_input" cellpadding="4"
							cellspacing="0">
							<tr>
								<td class="lbl_left">資格:</td>
								<td class="" align="left">${fn:escapeXml(userInfor.nameLevel)}</td>
							</tr>
							<c:choose>
								<c:when test="${userInfor.codeLevel eq '0'}">
									<tr>
										<td class="lbl_left">資格交付日:</td>
										<td align="left"></td>
									</tr>
									<tr>
										<td class="lbl_left">失効日:</td>
										<td align="left"></td>
									</tr>
									<tr>
										<td class="lbl_left">点数:</td>
										<td align="left"></td>
									</tr>
								</c:when>
								<c:otherwise>
									<tr>
										<td class="lbl_left">資格交付日:</td>
										<td class="" align="left">${fn:escapeXml(fn:replace(userInfor.startDate, '-', '/'))}</td>
									</tr>
									<tr>
										<td class="lbl_left">失効日:</td>
										<td class="" align="left">${fn:escapeXml(fn:replace(userInfor.endDate, '-', '/'))}</td>
									</tr>
									<tr>
										<td class="lbl_left">点数:</td>
										<td class="" align="left">${fn:escapeXml(userInfor.total)}</td>
									</tr>
								</c:otherwise>
							</c:choose>
						</table>
					</div>
				</td>
			</tr>
		</table>
		<div style="padding-left: 100px;">&nbsp;</div>
		<!-- Begin vung button -->
		<div style="padding-left: 45px;">
			<table border="0" cellpadding="4" cellspacing="0" width="300px">
				<tr>
					<th width="200px" align="center">&nbsp;</th>
					<td><input class="btn" type="submit" value="OK" /></td>
					<td><input class="btn" type="button" onclick="location.href='${pageContext.request.contextPath}/AddUserInput.do?action=BACK&key=${key}'" value="戻る" /></td>
				</tr>
			</table>
		</div>
		<!-- End vung button -->
	</form>
	<!-- End vung input -->

	<!-- Begin vung footer -->
	<jsp:include page="Footer.jsp"></jsp:include>
	<!-- End vung footer -->
</body>

</html>