<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglib.jsp" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>报销查看</title>
    <jsp:include page="/WEB-INF/pages/headerjs.jsp"></jsp:include>
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
                                <h2>报销查看<small>查看报销数据</small></h2>
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
                                <form:form action="${ctx}/refundBill/save" method="POST" modelAttribute="refundBill" class="form-horizontal form-label-left" data-parsley-validate="data-parsley-validate">
                                    <div class="item form-group">
                                        <label class="col-md-3 col-sm-3 col-xs-12" for="name">
                                          	  标题<span class="required">*</span>
                                        </label>
                                        <div class="col-md-6 col-sm-6 col-xs-12">
                                            ${refundBill.subject}
                                        </div>
                                    </div>
                                    <div class="item form-group">
                                        <label class="col-md-3 col-sm-3 col-xs-12" for="name">
                                          	  报销金额<span class="required">*</span>
                                        </label>
                                        <div class="col-md-6 col-sm-6 col-xs-12">
                                           ${refundBill.refundAmount}
                                        </div>
                                    </div>
                                    <div class="item form-group">
                                        <label class="col-md-3 col-sm-3 col-xs-12" for="name">
                                            	报销日期<span class="required">*</span>
                                        </label>
                                        <div class="col-md-6 col-sm-6 col-xs-12">
                                             ${refundBill.refundTime}
                                        </div>
                                        
                                    </div>
                                    <div class="item form-group">
                                        <label class="col-md-3 col-sm-3 col-xs-12" for="textarea">
                                           	 说明<span class="required">*</span>
                                        </label>
                                        <div class="col-md-8 col-sm-8 col-xs-12">
                                           ${refundBill.content}
                                        </div>
                                    </div>
                                    
                                    <div class="item form-group">
									  	 <div class="col-xs-12">
									         <c:import url="/workflow/hiscomments">
									         	<c:param name="processKey" value="${processKey}"></c:param>
									         	<c:param name="bizId" value="${refundBill.id}"></c:param>
									         </c:import>
									     </div>
									</div>
  
                                    <div class="form-group">
                                        <div class="col-md-12 col-sm-12 col-xs-12 text-right">
                                            <button type="button" onclick="window.history.back();" class="btn btn-success">返回</button>
                                        </div>
                                    </div>
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
</body>
</html>