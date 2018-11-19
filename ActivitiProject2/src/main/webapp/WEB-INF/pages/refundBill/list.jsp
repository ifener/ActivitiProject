<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglib.jsp" %>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>请假管理</title>
</head>
<body>
 	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr>
		        <td height="20" bgcolor="#FFFFFF" class="STYLE10" colspan="8"><div align="left">
					<a href="${pageContext.request.contextPath }/refundBill/add">报销申请新增</a>
				</div></td>
		  </tr> 
		  <tr>
		    <td><table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#a8c7ce" onmouseover="changeto()"  onmouseout="changeback()">
		      <tr>
		        <td bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">ID</span></div></td>
		        <td bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">申请人</span></div></td>
		        <td bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">申请金额</span></div></td>
		        <td bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">标题</span></div></td>
		        <td bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">说明</span></div></td>
		        <td bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">时间</span></div></td>
		        <td bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">状态</span></div></td>
		        <td bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">操作</span></div></td>
		      </tr>
		      <c:if test="${page.datas!=null}">
        		 <c:forEach items="${page.datas}" var="process" varStatus="status">
        			 <tr>
        			 	<td height="20" bgcolor="#FFFFFF" class="STYLE6"><div align="center">${process.id}</div></td>
			            <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">${process.user.employeeName }</div></td>
				        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">${process.refundAmount}</div></td>
				        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">${process.subject}</div></td>
				        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">${process.content}</div></td>
				        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">${process.refundTime}</div></td>
				        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">${process.auditDesc}</div></td>
				        <td height="20" bgcolor="#FFFFFF"><div align="center" class="STYLE21">
			        	<a href="load/${process.id}" >修改</a>
						<a href="remove/${process.id}">删除</a>
						<a href="${pageContext.request.contextPath }/workflowAction_startProcess.action?id=1" >申请请假</a>
					</div></td>
        			 </tr>
        		  </c:forEach>
        	  </c:if>
		    </table></td>
		  </tr>
	</table>
</body>
</html>