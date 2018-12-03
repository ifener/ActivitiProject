<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglib.jsp" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>报销审批</title>
    <jsp:include page="/WEB-INF/pages/headerjs.jsp"></jsp:include>
     <!-- bootstrap-daterangepicker -->
    <link href="${ctx}/static/css/daterangepicker.css" rel="stylesheet" />
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
                                <h2>报销审批列表<small></small></h2>
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
                            	<form:form action="findTaskList" modelAttribute="refundBillBO" class="form-horizontal form-label-left">
                            		<div class="panel panel-default">
										<div class="panel-body">
											<div class="form-group">
												<div class="col-xs-2 control-label">报销日期</div>       
												<div class="col-xs-4">
									                <div class="control-group">
						                              <div class="controls">
						                                <div class="input-prepend input-group">
						                                  <span class="add-on input-group-addon"><i class="glyphicon glyphicon-calendar fa fa-calendar"></i></span>
						                                  <form:input path="dateRange" cssClass="form-control col-md-7 col-xs-12"/>
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
										        <td><div align="center"><span class="STYLE10">序号</span></div></td>
										        <td><div align="center"><span class="STYLE10">申请人</span></div></td>
										        <td><div align="center"><span class="STYLE10">申请日期</span></div></td>
										        <td><div align="center"><span class="STYLE10">标题</span></div></td>
										        <td><div align="center"><span class="STYLE10">金额</span></div></td>
										        <td><div align="center"><span class="STYLE10">状态</span></div></td>
										        <td><div align="center"><span class="STYLE10">操作</span></div></td>
										    </tr>
					                      </thead>
					                      <tbody>
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
    <script src="${ctx}/static/js/moment/moment.min.js"></script>
    <script src="${ctx}/static/js/daterangepicker.js"></script>

    <!-- Custom Theme Scripts -->
    <script src="${ctx}/static/js/custom.js?id=1"></script>
    <!-- paginator -->
    <script src="${ctx}/static/js/bootstrap-paginator.js"></script>
    <script>
      $(function(){
    	  $('input[name="dateRange"]').daterangepicker({
    		  autoUpdateInput: false,
              locale: {
                  cancelLabel: 'Clear'
              }
          });
    	  
    	  $('input[name="dateRange"]').on('apply.daterangepicker', function(ev, picker) {
              $(this).val(picker.startDate.format('YYYY-MM-DD') + ' - ' + picker.endDate.format('YYYY-MM-DD'));
          });

    	  $('input[name="dateRange"]').on('cancel.daterangepicker', function(ev, picker) {
              $(this).val('');
          });
      });
    </script>
</body>
</html>