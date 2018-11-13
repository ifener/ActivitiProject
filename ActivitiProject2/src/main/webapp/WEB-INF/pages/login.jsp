<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglib.jsp" %>
<html>
<head>
	<title>登录页面</title>
	<link href="${ctx}/static/css/public.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
		if(parent != window){
			parent.location.href = window.location.href;
		}
		function func_login(){
			document.forms[0].submit();
		}
	</script>
</head>
<body style="text-align:center;">
	<form action="doLogin" method="post">
		<table width="100%"  height="100%" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td bgcolor="#1075b1">&nbsp;</td>
		  </tr>
		  <tr>
		    <td height="608" background="${ctx}/static/images/login_03.gif"><table width="847" border="0" align="center" cellpadding="0" cellspacing="0">
		      <tr>
		        <td height="318" background="${ctx}/static/images/login_04.gif">&nbsp;</td>
		      </tr>
		      <tr>
		        <td height="84"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		          <tr>
		            <td width="381" height="84" background="${ctx}/static/images/login_06.gif">&nbsp;</td>
		            <td width="200" valign="middle">
		            <table width="100%" border="0" cellspacing="0" cellpadding="0">
		              <tr>
		                <td width="55" height="24" valign="bottom"><div align="right"><span class="STYLE3">用户名</span></div></td>
		                <td><input type="text" name="loginId" /></td>
		              </tr>
                      <tr>
		                <td width="55" height="24" valign="bottom"><div align="right"><span class="STYLE3">密码</span></div></td>
		                <td><input type="password" name="loginPassword" /></td>
		              </tr>		              
		            </table>
		            
		            </td>
		            <td width="26"><img src="${ctx}/static/images/login_08.gif" width="26" height="84"></td>
		            <td width="67" background="${ctx}/static/images/login_09.gif">
		            <table width="100%" border="0" cellspacing="0" cellpadding="0">
	              		<tr>
	               			<td height="25"><div align="center" style="cursor:hand" onclick="func_login()"><img src="${ctx}/static/images/dl.gif" width="57" height="20"></div></td>
	              		</tr>
			            </table>
            		</td>
		            <td width="211" background="${ctx}/static/images/login_10.gif">&nbsp;</td>
		          </tr>
		        </table></td>
		      </tr>
		      <tr>
		        <td height="206" background="${ctx}/static/images/login_11.gif">&nbsp;</td>
		      </tr>
		    </table></td>
		  </tr>
		  <tr>
		    <td bgcolor="#152753">&nbsp;</td>
		  </tr>
		</table>
	</form>
	<script type="text/javascript">
		document.getElementById("username").focus();
	</script>
</body>
</html>