<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglib.jsp" %>

<img style="position:absolute;left:0;top:0;display:block;" src="${ctx}/workflow/viewImage?deploymentId=${deploymentId}&resourceName=${resourceName}" />
<img style="opacity:0;" src="${ctx}/workflow/viewImage?deploymentId=${deploymentId}&resourceName=${resourceName}" /> 
<div style="position:absolute;left:${local.x}px;top:${local.y}px;width:${local.width}px;height:${local.height}px;border:2px solid red;padding:5px;border-radius:5px;"></div>