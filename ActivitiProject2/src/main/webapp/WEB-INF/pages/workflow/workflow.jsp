<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglib.jsp" %>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部署管理</title>
<script type="text/javascript" src="${ctx}/static/js/jquery.js"></script>
<script type="text/javascript">
function remove(deploymentId){
	if(confirm("是否删除？")) {
		var url ="remove?deploymentId="+deploymentId;
		$("form").attr("action",url);
		$("form").submit();
	}
	
}
</script>
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
		                <td width="94%" valign="bottom"><span class="STYLE1">流程定义信息列表</span></td>
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
		    	<div>
		    		<form:form action="" method="post" modelAttribute="processDefinitionBO">
		    			<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#a8c7ce">
		    				<tr>
		    					<td>流程编码：</td>
		    					<td>
		    						<form:input path="processKey"/>
		    					</td>
		    					<td>流程名称：</td>
		    					<td>
		    						<form:input path="processName"/>
		    					</td>
		    				</tr>
		    				<tr>
		    					<td colspan="4" style="text-align:center">
		    						<input type="submit" value="提交" />
		    					</td>
		    				</tr>
		    			</table>
		    		</form:form>
		    	</div>
		    	<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#a8c7ce" onmouseover="changeto()"  onmouseout="changeback()">
			      <tr>
			        <td width="12%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">ID</span></div></td>
			        <td width="18%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">名称</span></div></td>
			        <td width="10%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">流程定义的KEY</span></div></td>
			        <td width="10%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">流程定义的版本</span></div></td>
			        <td width="15%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">流程定义的规则文件名称</span></div></td>
			        <td width="15%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">流程定义的规则图片名称</span></div></td>
			        <td width="10%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">部署ID</span></div></td>
			        <td width="10%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">操作</span></div></td>
			      </tr>
			      <c:forEach items="${page.datas}" var="process" varStatus="status">
			        <tr>
				        <td height="20" bgcolor="#FFFFFF" class="STYLE6"><div align="center">${process.id}</div></td>
				        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">${process.name}</div></td>
				        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">${process.key}</div></td>
				        <td height="20" bgcolor="#FFFFFF" class="STYLE6"><div align="center">${process.version}</div></td>
				        <td height="20" bgcolor="#FFFFFF" class="STYLE6"><div align="center">${process.resourceName}</div></td>
				        <td height="20" bgcolor="#FFFFFF" class="STYLE6"><div align="center">${process.diagramResourceName}</div></td>
				        <td height="20" bgcolor="#FFFFFF" class="STYLE6"><div align="center">${process.deploymentId}</div></td>
				        <td height="20" bgcolor="#FFFFFF"><div align="center" class="STYLE21">
				        	<a target="_blank" href="viewImage?deploymentId=${process.deploymentId}&resourceName=${process.diagramResourceName}">查看流程图</a>
				        	<a href="javascript:remove('${process.deploymentId}')">删除</a>  
					 	</div></td>
				    </tr> 
			      </c:forEach>
			    </table>
		    </td>
		  </tr>
	</table>
	<hr>
	<br/>
	<!-- 发布流程 -->
	<form action="deploy" enctype="multipart/form-data" method="POST">
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr>
		    <td height="30"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		      <tr>
		        <td height="24" bgcolor="#353c44"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		          <tr>
		            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
		              <tr>
		                <td width="6%" height="19" valign="bottom"><div align="center"><img src="${pageContext.request.contextPath }/images/tb.gif" width="14" height="14" /></div></td>
		                <td width="94%" valign="bottom"><span class="STYLE1">部署流程定义</span></td>
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
		    	<div align="left" class="STYLE21">
					流程名称：<input type="text" name="filename" style="width: 200px;"/><br/>
					流程文件:<input type="file" name="file" style="width: 200px;"/><br/>
					<input type="submit" value="上传流程" class="button_ok"/>
				</div>
		    </td>
		  </tr>
	</table>
		
	</form>
</body>
</html>