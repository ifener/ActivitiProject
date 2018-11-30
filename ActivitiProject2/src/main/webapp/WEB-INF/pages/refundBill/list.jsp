<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglib.jsp" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>报销管理</title>
    <jsp:include page="/WEB-INF/pages/headerjs.jsp"></jsp:include>
     <!-- bootstrap-daterangepicker -->
    <link href="${ctx}/static/css/daterangepicker.css" rel="stylesheet">
     <!-- bootstrap-datetimepicker -->
     <link href="${ctx}/static/css/bootstrap-datetimepicker/bootstrap-datetimepicker.css" rel="stylesheet">
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
                                <h2>报销列表<small>查询报销数据</small></h2>
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
                            	<form:form action="list" modelAttribute="refundBillBO" class="form-horizontal form-label-left">
                            		<div class="panel panel-default">
										<div class="panel-body">
											<div class="form-group">
												<div class="col-xs-2 control-label">报销日期</div>       
												<div class="col-xs-4">
									                <div class="control-group">
						                              <div class="controls">
						                                <div class="input-prepend input-group">
						                                  <span class="add-on input-group-addon"><i class="glyphicon glyphicon-calendar fa fa-calendar"></i></span>
						                                  <input type="text" name="dateRange" id="dateRange" class="form-control" value="" />
						                                </div>
						                              </div>
						                            </div>
												</div>
												<div class="col-xs-2 control-label">标题</div>       
												<div class="col-xs-4">
									                <form:input path="refundBill.subject" cssClass="form-control col-md-7 col-xs-12"/>
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
					                          <th>申请人</th>
					                          <th>标题</th>
					                          <th>报销金额</th>
					                          <th>操作</th>
					                        </tr>
					                      </thead>
					                      <tbody>
					                      	<c:forEach items="${page.datas}" var="refundBill" varStatus="status">
						                        <tr>
						                          <th scope="row">${status.index+1}</th>
						                          <td>${refundBill.user.employeeName}</td>
						                          <td>${refundBill.subject}</td>
						                          <td>${refundBill.refundAmount}</td>
						                          <td><a href="view/${refundBill.id}">查看</a></td>
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
            <jsp:include page="/WEB-INF/pages/footer.jsp"></jsp:include>
        </div>
    </div>

    <jsp:include page="/WEB-INF/pages/footerjs.jsp"></jsp:include>
    <!-- Parsley -->
    <script src="${ctx}/static/js/parsleyjs/parsley.min.js"></script>
    <script src="${ctx}/static/js/parsleyjs/i18n/zh_cn.js"></script>
    <script src="${ctx}/static/js/moment/moment.min.js"></script>
    <script src="${ctx}/static/js/daterangepicker.js"></script>
    <!-- bootstrap-datetimepicker -->    
    <script src="${ctx}/static/js/bootstrap-datetimepicker/bootstrap-datetimepicker.min.js"></script>

    <!-- Custom Theme Scripts -->
    <script src="${ctx}/static/js/custom.js"></script>
    <!-- Parsley -->
    <script src="${ctx}/static/js/parsleyjs/parsley.min.js"></script>
    <script src="${ctx}/static/js/bootstrap-paginator.js"></script>
    <script>
      $(function(){
    	  $('#dateRange').daterangepicker({
			  locale: {
				format: 'YYYY-MM-DD'
			  }
		  });
      });
    </script>
</body>
</html>