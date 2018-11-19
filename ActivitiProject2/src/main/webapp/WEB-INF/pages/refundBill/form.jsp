<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglib.jsp" %>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报销管理</title>
</head>
<body>
 	<form:form action="${ctx}/refundBill/save" method="POST" modelAttribute="refundBill">
 		<form:hidden path="id"/>
 		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr>
		    <td height="30"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		      <tr>
		        <td height="24" bgcolor="#353c44"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		          <tr>
		            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
		              <tr>
		                <td width="6%" height="19" valign="bottom"><div align="center"><img src="${pageContext.request.contextPath }/images/tb.gif" width="14" height="14" /></div></td>
		                <td width="94%" valign="bottom">
		                	<span class="STYLE1">
		                			新增/修改报销单据
		                	</span>
		                	</td>
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
		    <td>
		    	<table>
		    		<tr>
		    			<td>标题：</td>
		    			<td>
		    				<form:input path="subject"/> 
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>说明：</td>
		    			<td>
		    				<form:textarea path="content"/> 
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>金额：</td>
		    			<td>
		    				<form:input path="refundAmount"/> 
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>报销时间：</td>
		    			<td>
		    				<form:input path="refundTime"/> 
		    			</td>
		    		</tr>
		    		<tr>
		    			<td></td>
		    			<td>
		    				<input type="submit" value="提交" />
		    			</td>
		    		</tr>
		    	
		    	</table>
		    </td>
		  </tr>
	</table>
	 	
	</form:form>
</body>
</html>