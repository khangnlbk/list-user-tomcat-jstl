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
<title>ユーザ管理</title>
</head>
<body>
	<!-- Begin vung header -->
	<jsp:include page="Header.jsp"></jsp:include>
	<!-- End vung header -->

	<!-- Begin vung dieu kien tim kiem -->
	<form action="listuser.do" method="get" name="mainform">
		<table class="tbl_input" border="0" width="90%" cellpadding="0"
			cellspacing="0">
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>会員名称で会員を検索します。検索条件無しの場合は全て表示されます。</td>
			</tr>
			<tr>
				<td width="100%">
					<table class="tbl_input" cellpadding="4" cellspacing="0">

						<tr>
							<td class="lbl_left">氏名:</td>
							<td align="left"><input class="txBox" type="text"
								name="fullName" value="${fn:escapeXml(fullName)}" size="20"
								onfocus="this.style.borderColor='#0066ff';"
								onblur="this.style.borderColor='#aaaaaa';" /></td>
							<td></td>
						</tr>
						<tr>
							<td class="lbl_left">グループ:</td>
							<td align="left" width="80px"><select name="groupId">
									<option value="0">全て</option>
									<c:forEach items="${listMstGroup}" var="mstGroup">
										<option value="${mstGroup.groupId}"<c:if test="${mstGroup.groupId eq groupId}">selected="selected"</c:if>>											
											<c:choose>
												<c:when test="${fn:length(userInfor.groupName) gt 25}">
													<c:out value="${fn:substring(mstGroup.groupName, 0, 22)}..."></c:out>
												</c:when>
												<c:otherwise>
													${fn:escapeXml(mstGroup.groupName)}
												</c:otherwise>
											</c:choose>
										</option>
									</c:forEach>
							</select></td>
							<td align="left"><input class="btn" type="submit" value="検索" />
								<input type="hidden" name="action" value="SEARCH" /> <input
								class="btn" type="button" onclick="location.href='${pageContext.request.contextPath}/AddUserInput.do'" value="新規追加" /></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>
	<!-- End vung dieu kien tim kiem -->

	<!-- Begin vung hien thi danh sach user -->
	<c:choose>
		<c:when test="${totalRecords eq 0}">
			<center>
				<c:out value="${MSG005}"></c:out>
			</center>
		</c:when>
		<c:otherwise>
			<table class="tbl_list" border="1" cellpadding="4" cellspacing="0"
				width="80%">
				<tr class="tr2">
					<th align="center" width="3%">ID</th>
					<!-- Tạo URL dùng chung cho trường hợp SORT -->
					<c:url value="listuser.do" var="urlSORT">
						<c:param name="action" value="SORT" />
						<c:param name="fullName" value="${fullName}" />
						<c:param name="groupId" value="${groupId}" />
						<c:param name="currentPage" value="${currentPage}" />
					</c:url>
					<th align="left" width="20%">
						氏名 
						<a href="${urlSORT}&sortType=FULLNAME&sortByFullName=
						<c:choose>
							<c:when test="${sortByFullName eq 'DESC'}">
								ASC
							</c:when>
							<c:otherwise>
								DESC
							</c:otherwise>
						</c:choose>">
						<c:choose>
							<c:when test="${sortByFullName eq 'ASC'}">
								▲▽
							</c:when>
							<c:otherwise>
								△▼
							</c:otherwise>
						</c:choose>
						</a>
					</th>

					<th align="left" width="10%">生年月日</th>
					<th align="left" width="13%">グループ</th>
					<th align="left" width="14%">メールアドレス</th>
					<th align="left" width="10%">電話番号</th>
					<th align="left" width="15%">
						日本語能力 
						<a href="${urlSORT}&sortType=CODELEVEL&sortByCodeLevel=
						<c:choose>
							<c:when test="${sortByCodeLevel eq 'DESC'}">
								ASC
							</c:when>
							<c:otherwise>
								DESC
							</c:otherwise>
						</c:choose>
						">
						<c:choose>
							<c:when test="${sortByCodeLevel eq 'ASC'}">
								▲▽
							</c:when>
							<c:otherwise>
								△▼
							</c:otherwise>
						</c:choose>
						</a>
					</th>
					<th align="left" width="10%">
						失効日 
						<a href="${urlSORT}&sortType=ENDDATE&sortByEndDate=
						<c:choose>
							<c:when test="${sortByEndDate eq 'DESC'}">
								ASC
							</c:when>
							<c:otherwise>
								DESC
							</c:otherwise>
						</c:choose>
						">
						<c:choose>
							<c:when test="${sortByEndDate eq 'ASC'}">
								▲▽
							</c:when>
							<c:otherwise>
								△▼
							</c:otherwise>
						</c:choose>
					</a>
					</th>
					<th align="left" width="5%">点数</th>
				</tr>

				<!-- Hiển thị danh sách user -->
				<c:forEach items="${listUsers}" var="userInfor">
					<tr>
						<td align="right"><a href="${pageContext.request.contextPath}/ViewDetailUser.do?userId=${userInfor.userId}">${userInfor.userId}</a></td>
						<c:choose>
							<c:when test="${fn:length(userInfor.fullName) gt 25}">
								<td><c:out value="${fn:substring(userInfor.fullName, 0, 22)}..."></c:out></td>
							</c:when>
							<c:otherwise>
								<td>${fn:escapeXml(userInfor.fullName)}</td>
							</c:otherwise>
						</c:choose>
						<td align="center">${fn:replace(userInfor.birthday, '-', '/')}
						</td>
						<td>${fn:escapeXml(userInfor.groupName)}</td>
						<c:choose>
							<c:when test="${fn:length(userInfor.email) gt 25}">
								<td><c:out value="${fn:substring(userInfor.email, 0, 22)}..."></c:out></td>
							</c:when>
							<c:otherwise>
								<td>${fn:escapeXml(userInfor.email)}</td>
							</c:otherwise>
						</c:choose>
						<td>${fn:escapeXml(userInfor.tel)}</td>						
						<c:choose>
							<c:when test="${fn:length(userInfor.nameLevel) gt 25}">
								<td><c:out value="${fn:substring(userInfor.nameLevel, 0, 22)}..."></c:out></td>
							</c:when>
							<c:otherwise>
								<td>${fn:escapeXml(userInfor.nameLevel)}</td>
							</c:otherwise>
						</c:choose>

						<td align="center">
							${fn:replace(userInfor.endDate, '-', '/')}</td>
						<td align="right">${userInfor.total}</td>
					</tr>
				</c:forEach>
			</table>
		</c:otherwise>
	</c:choose>
	<!-- End vung hien thi danh sach user -->

	<!-- Begin vung paging -->
	<c:url value="listuser.do" var="urlPAGING">
		<c:param name="action" value="PAGING" />
		<c:param name="fullName" value="${fullName}" />
		<c:param name="groupId" value="${groupId}" />
		<c:param name="sortType" value="${sortType}" />
		<c:param name="sortByFullName" value="${sortByFullName}" />
		<c:param name="sortByCodeLevel" value="${sortByCodeLevel}" />
		<c:param name="sortByEndDate" value="${sortByEndDate}" />
	</c:url>
	
	<c:set var="size" value="${listPaging.size() }"></c:set>
	<c:if test="${size > 0 }">
		<table>
			<tr>
				<td class="lbl_paging"><c:set var="startPage"
						value="${listPaging.get(0) }"></c:set> 
						<c:set var="endPage" value="${listPaging.get(size - 1)}">
						</c:set> <c:if test="${currentPage > limitPage}">
						<a
							href="${urlPAGING}&currentPage=${startPage - limitPage}">&#60;&#60;</a>
					</c:if> <c:forEach items="${listPaging}" var="page">
						<c:choose>
							<c:when test="${paging == currentPage}">
								<c:out value="${page}"></c:out>
							</c:when>
							<c:otherwise>
								<a href="${urlPAGING}&currentPage=${page}">${page}</a>
							</c:otherwise>
						</c:choose>
					</c:forEach> <c:if test="${endPage < totalPage }">
						<a href="${urlPAGING}&currentPage=${endPage + 1}">&#62;&#62;</a>
					</c:if></td>
			</tr>
		</table>
	</c:if>

	<!-- End vung paging -->

	<!-- Begin vung footer -->
	<jsp:include page="Footer.jsp"></jsp:include>
	<!-- End vung footer -->

</body>

</html>