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
				action="AddUserValidate.do" 
			</c:when>
			<c:otherwise>				
				action="EditUserValidate.do?userId=${userInfor.userId}" 
			</c:otherwise>
		</c:choose>
		method="post" name="inputform">
		<input type="hidden" name="action" value="VALIDATE" />
		<table class="tbl_input" border="0" width="75%" cellpadding="0"
			cellspacing="0">
			<tr>
				<th align="left">
					<div style="padding-left: 100px;">会員情報編集</div>
				</th>
			</tr>
			<c:forEach items="${listErr}" var="err">
				<tr>
					<td class="errMsg">
						<div style="padding-left: 120px">${err}</div>
					</td>
				</tr>
			</c:forEach>
			<tr>
				<td class="errMsg">
					<div style="padding-left: 120px">&nbsp;</div>
				</td>
			</tr>
			<tr>
				<td align="left">
					<div style="padding-left: 100px;">
						<table border="0" width="100%" class="tbl_input" cellpadding="4"
							cellspacing="0">
							<tr>
								<td class="lbl_left"><font color="red">*</font> アカウント名:</td>
								<td align="left"><input class="txBox" type="text"
									name="loginName" value="${fn:escapeXml(userInfor.loginName)}"
									size="15" onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" maxlength="15"
									<c:if test="${userInfor.userId ne 0}">readonly="true"</c:if> /></td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font> グループ:</td>
								<td align="left"><select name="groupId">
										<option value="0">選択してください</option>
										<c:forEach items="${listMstGroup}" var="mstGroup">
											<option value="${mstGroup.groupId}"
												<c:if test="${mstGroup.groupId eq userInfor.groupId}">selected="selected"</c:if>>
												<c:choose>
													<c:when test="${fn:length(mstGroup.groupName) gt 25}">
														<c:out
															value="${fn:substring(mstGroup.groupName, 0, 22)}..."></c:out>
													</c:when>
													<c:otherwise>
														${fn:escapeXml(mstGroup.groupName)}
													</c:otherwise>
												</c:choose>
											</option>
										</c:forEach>
								</select> <span>&nbsp;&nbsp;&nbsp;</span></td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font> 氏名:</td>
								<td align="left"><input class="txBox" type="text"
									name="fullName" value="${fn:escapeXml(userInfor.fullName)}"
									size="30" onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" maxlength="255"/></td>
							</tr>
							<tr>
								<td class="lbl_left">カタカナ氏名:</td>
								<td align="left"><input class="txBox" type="text"
									name="fullNameKana"
									value="${fn:escapeXml(userInfor.fullNameKana)}" size="30"
									onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" maxlength="255"/></td>
							</tr>

							<!-- chia userInfor.birthday thành mảng có 3 phần tử -->
							<c:set var="birthDay"
								value="${fn:split(userInfor.birthday, '-')}" />
							<tr>
								<td class="lbl_left"><font color="red">*</font> 生年月日:</td>
								<td align="left"><select name="yearBirthday">
										<c:forEach items="${listYear}" var="year">
											<option value="${year}"
												<c:if test="${year eq birthDay[0]}">selected="selected"</c:if>>${year}</option>
										</c:forEach>
								</select>年 <select name="monthBirthday">
										<c:forEach items="${listMonth}" var="month">
											<option value="${month}"
												<c:if test="${month eq birthDay[1]}">selected="selected"</c:if>>${month}</option>
										</c:forEach>
								</select>月 <select name="dayBirthday">
										<c:forEach items="${listDay}" var="day">
											<option value="${day}"
												<c:if test="${day eq birthDay[2]}">selected="selected"</c:if>>${day}</option>
										</c:forEach>
								</select>日</td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font> メールアドレス:</td>
								<td align="left"><input class="txBox" type="text"
									name="email" value="${fn:escapeXml(userInfor.email)}" size="30"
									onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" maxlength="100"/></td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font>電話番号:</td>
								<td align="left"><input class="txBox" type="text"
									name="tel" value="${fn:escapeXml(userInfor.tel)}" size="30"
									onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" maxlength="14"/></td>
							</tr>
							<c:if test="${userInfor.userId eq 0}">
								<tr>
									<td class="lbl_left"><font color="red">*</font> パスワード:</td>
									<td align="left"><input class="txBox" type="password"
										name="password" value="" size="30"
										onfocus="this.style.borderColor='#0066ff';"
										onblur="this.style.borderColor='#aaaaaa';" maxlength="15"/></td>
								</tr>
								<tr>
									<td class="lbl_left">パスワード（確認）:</td>
									<td align="left"><input class="txBox" type="password"
										name="confirmPassword" value="" size="30"
										onfocus="this.style.borderColor='#0066ff';"
										onblur="this.style.borderColor='#aaaaaa';" maxlength="15"/></td>
								</tr>
							</c:if>
							<tr>
								<th align="left" colspan="2"><a href="javascript:void(0)"
									onclick="hiddenData();">日本語能力</a></th>
							</tr>
						</table>
					</div>
					<div id="levelJapan" style="padding-left: 100px; display: none;">
						<table border="0" width="100%" class="tbl_input" cellpadding="4"
							cellspacing="0">
							<tr>
								<td class="lbl_left">資格:</td>
								<td align="left"><select name="codeLevel">
										<option value="0">選択してください</option>
										<c:forEach items="${listMstJapan}" var="mstJapan">
											<option value="${mstJapan.codeLevel}"
												<c:if test="${mstJapan.codeLevel eq userInfor.codeLevel}">selected="selected"</c:if>>
												<c:choose>
													<c:when test="${fn:length(mstJapan.nameLevel) gt 25}">
														<c:out
															value="${fn:substring(mstJapan.nameLevel, 0, 22)}..."></c:out>
													</c:when>
													<c:otherwise>
														${fn:escapeXml(mstJapan.nameLevel)}
													</c:otherwise>
												</c:choose>
											</option>
										</c:forEach>
								</select></td>
							</tr>
							<c:set var="startDateJapan"
								value="${fn:split(userInfor.startDate, '-')}" />
							<tr>
								<td class="lbl_left">資格交付日:</td>
								<td align="left"><select name="yearStartDateJapan">
										<c:forEach items="${listYear}" var="year">
											<option value="${year}"
												<c:if test="${year eq startDateJapan[0]}">selected="selected"</c:if>>${year}</option>
										</c:forEach>
								</select>年 <select name="monthStartDateJapan">
										<c:forEach items="${listMonth}" var="month">
											<option value="${month}"
												<c:if test="${month eq startDateJapan[1]}">selected="selected"</c:if>>${month}</option>
										</c:forEach>
								</select>月 <select name="dayStartDateJapan">
										<c:forEach items="${listDay}" var="day">
											<option value="${day}"
												<c:if test="${day eq startDateJapan[2]}">selected="selected"</c:if>>${day}</option>
										</c:forEach>
								</select>日</td>
							</tr>
							<!-- chia userInfor.endDateJapan thành mảng có 3 phần tử -->
							<c:set var="endDateJapan"
								value="${fn:split(userInfor.endDate, '-')}" />
							<tr>
								<td class="lbl_left">失効日:</td>
								<td align="left"><select name="yearEndDateJapan">
										<c:forEach items="${listYearEndDateJapan}" var="year">
											<option value="${year}"
												<c:if test="${year eq endDateJapan[0]}">selected="selected"</c:if>>${year}</option>
										</c:forEach>
								</select>年 <select name="monthEndDateJapan">
										<c:forEach items="${listMonth}" var="month">
											<option value="${month}"
												<c:if test="${month eq endDateJapan[1]}">selected="selected"</c:if>>${month}</option>
										</c:forEach>
								</select>月 <select name="dayEndDateJapan">
										<c:forEach items="${listDay}" var="day">
											<option value="${day}"
												<c:if test="${day eq endDateJapan[2]}">selected="selected"</c:if>>${day}</option>
										</c:forEach>
								</select>日</td>
							</tr>
							<tr>
								<td class="lbl_left">点数:</td>
								<td align="left"><input class="txBox" type="text"
									name="total" value="${fn:escapeXml(userInfor.total)}" size="5"
									onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" maxlength="3"/></td>
							</tr>
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
					<td><input class="btn" type="submit" value="確認" /></td>
					<td><input class="btn" type="button"
						<c:choose>
						<c:when test="${userInfor.userId eq 0}">
							onclick="location.href='${pageContext.request.contextPath}/listuser.do?action=BACK'" 							
						</c:when>
						<c:otherwise>
							onclick="location.href='${pageContext.request.contextPath}/ViewDetailUser.do?userId=${userInfor.userId}'"
						</c:otherwise>
					</c:choose>
						value="戻る" /></td>
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