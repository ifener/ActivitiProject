<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglib.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>任务管理</title>
<style type="text/css">
table.list td{border:1px solid #cdc;}
</style>
</head>
<body>
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr>
		    <td height="30"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		      <tr>
		        <td height="24" bgcolor="#353c44"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		          <tr>
		            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
		              <tr>
		                <td width="6%" height="19" valign="bottom"><div align="center"><img src="${pageContext.request.contextPath }/images/tb.gif" width="14" height="14" /></div></td>
		                <td width="94%" valign="bottom"><span class="STYLE1">待审批的报销单</span></td>
		              </tr>
		            </table></td>
		            <td><div align="right"><span class="STYLE1">
		              </span></div></td>
		          </tr>
		        </table></td>
		      </tr>
		    </table></td>
		  </tr>
		  <tr>
		    <td><table class="list" width="100%" border="0" cellpadding="0" cellspacing="1" onmouseover="changeto()"  onmouseout="changeback()">
		      <tr>
		        <td><div align="center"><span class="STYLE10">序号</span></div></td>
		        <td><div align="center"><span class="STYLE10">申请人</span></div></td>
		        <td><div align="center"><span class="STYLE10">申请日期</span></div></td>
		        <td><div align="center"><span class="STYLE10">标题</span></div></td>
		        <td><div align="center"><span class="STYLE10">金额</span></div></td>
		        <td><div align="center"><span class="STYLE10">状态</span></div></td>
		        <td><div align="center"><span class="STYLE10">操作</span></div></td>
		      </tr>
		      
		      <c:if test="${page!=null}">
		      	<c:forEach items="${page.datas}" var="item" varStatus="status">
		      		<tr>
				       <td>${status.index+1}</td>
				       <td>${item.bizObj.user.employeeName}</td>
				       <td>${item.bizObj.refundTime}</td>
				       <td>${item.bizObj.subject}</td>
				       <td>${item.bizObj.refundAmount}</td>
				       <td>${item.bizObj.auditDesc}</td>
				       <td>
				       	   <a href="audit/${item.bizObj.id}">审批</a>
				       </td>
				    </tr> 
		      	</c:forEach>
		      </c:if>
		        
		      
		    </table></td>
		  </tr>
	</table>
</body>
</html>