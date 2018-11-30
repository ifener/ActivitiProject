<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglib.jsp" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>流程管理</title>
    <jsp:include page="/WEB-INF/pages/headerjs.jsp"></jsp:include>
    <!-- bootstrap dialog -->
    <link href="${ctx}/static/css/bootstrap-dialog.min.css" rel="stylesheet">s
</head>
<body class="nav-md">
    <div class="container body">
        <div class="main_container">
        	<jsp:include page="/WEB-INF/pages/left.jsp"></jsp:include>
            <jsp:include page="/WEB-INF/pages/top.jsp"></jsp:include>
            
            <!-- page content -->
            <div class="right_col" role="main">
            	<div class="row">
            		<div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2>流程管理<small>查询流程定义数据</small></h2>
                                <ul class="nav navbar-right panel_toolbox">
                                    <li>
                                        <a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                    </li>
                                    <li class="dropdown">
                                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
                                            <ul class="dropdown-menu" role="menu">
                                                <li><a href="#">Settings 1</a></li>
                                                <li><a href="#">Settings 2</a></li>
                                            </ul>
                                    </li>
                                    <li>
                                        <a class="close-link"><i class="fa fa-close"></i></a>
                                    </li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                            	<form:form action="list" modelAttribute="processDefinitionBO" class="form-horizontal form-label-left">
                            		<div class="panel panel-default">
										<div class="panel-body">
											<div class="form-group">
												<div class="col-xs-2 control-label">流程编码</div>       
												<div class="col-xs-4">
									            	<form:input path="processKey" cssClass="form-control col-md-7 col-xs-12"/>
												</div>
												<div class="col-xs-2 control-label">流程名称</div>       
												<div class="col-xs-4">
									                <form:input path="processName" cssClass="form-control col-md-7 col-xs-12"/>
												</div>
											</div>
											
											<div class="form-group">
												<div class="col-md-12 col-sm-12 col-xs-12" style="text-align:center;">
		                                            <button class="btn btn-primary" type="reset">重置</button>
		                                            <button type="submit" class="btn btn-success">查询</button>
		                                        </div>
											</div>
										</div>
									</div>
									<table class="table table-striped table-bordered">
					                    <thead>
					                        <tr>
					                          <th>序号</th>
					                          <th>名称</th>
					                          <th>流程KEY</th>
					                          <th>流程版本</th>
					                          <th>流程文件名称</th>
					                          <th>流程图片名称</th>
					                          <th>部署ID</th>
					                          <th>操作</th>
					                        </tr>
					                      </thead>
					                      <tbody>
					                      	<c:forEach items="${page.datas}" var="process" varStatus="status">
						                        <tr>
						                          <th scope="row">${status.index+1}</th>
						                          <td>${process.name}</td>
						                          <td>${process.key}</td>
						                          <td>${process.version}</td>
						                          <td>${process.resourceName}</td>
						                          <td>${process.diagramResourceName}</td>
						                          <td>${process.deploymentId}</td>
						                          <td>
						                          	<a href="javascript:viewImage('${process.deploymentId}','${process.diagramResourceName}');">查看流程图</a>
				        							<a href="javascript:remove('${process.deploymentId}')">删除</a>  
						                          </td>
						                        </tr>
					                        </c:forEach>
					                      </tbody>
					                </table>
					                <%@ include file="/WEB-INF/pages/paginator.jsp" %>
                            	</form:form>
                            </div>
                        </div>
                    </div>
            	</div>
            </div>
            <!-- /page content -->
        </div>
    </div>

    <jsp:include page="/WEB-INF/pages/footerjs.jsp"></jsp:include>
    <script src="${ctx}/static/js/bootstrap-paginator.js"></script>
    <script src="${ctx}/static/js/bootstrap-dialog.min.js"></script>
    <script>
      function viewImage(deploymentId,resourceName){
    	 var url = "${ctx}/workflow/viewImageBase64?deploymentId="+deploymentId+"&resourceName="+resourceName;
    	 BootstrapDialog.show({
    		 title: '查看流程图',
             message: $('<div></div>').load(url),
             size:BootstrapDialog.SIZE_WIDE
         });
      }
    </script>
</body>
</html>