<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglib.jsp" %>
<h2>审批历程</h2>
<table class="table table-striped table-bordered">
	<thead>
		<tr>
			<th>序号</th>
			<th>节点</th>
			<th>审批人</th>
			<th>审批时间</th>
			<th>审批结果</th>
			<th>审批意见</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${comments!=null}">
			<c:forEach items="${comments}" var="comment" varStatus="status">
				<tr>
					<td>${status.index+1}</td>
					<td>${comment.taskName}</td>
					<td>${comment.comment.userId}</td>
					<td>
						<fmt:formatDate value="${comment.comment.time}" pattern="yyyy-MM-dd hh:mm:ss"/>
					</td>
					<td>${comment.comment.type}</td>
					<td>${comment.comment.fullMessage}</td>
				</tr>
			</c:forEach>
		</c:if>
	</tbody>
</table>

