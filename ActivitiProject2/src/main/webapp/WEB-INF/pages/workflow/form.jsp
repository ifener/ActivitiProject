<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglib.jsp" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>流程部署</title>
    <jsp:include page="/WEB-INF/pages/headerjs.jsp"></jsp:include>
    <!-- bootstrap-daterangepicker -->
    <link href="${ctx}/static/css/daterangepicker.css" rel="stylesheet">
     <!-- bootstrap-datetimepicker -->
     <link href="${ctx}/static/css/bootstrap-datetimepicker/bootstrap-datetimepicker.css" rel="stylesheet">
     
     <link href="${ctx}/static/css/beautyfileupload.css" rel="stylesheet" />
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
                                <h2>流程部署<small>上传流程ZIP并部署</small></h2>
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
                                <form action="deploy" method="POST" enctype="multipart/form-data" class="form-horizontal form-label-left" data-parsley-validate="data-parsley-validate">
                                    <div class="item form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-12">
                                          	  流程名称<span class="required">*</span>
                                        </label>
                                        <div class="col-md-6 col-sm-6 col-xs-12">
                                           <input type="text" class="form-control col-md-7 col-xs-12" name="name" />
                                        </div>
                                    </div>
                                    <div class="item form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-12">
                                          	  流程文件<span class="required">*</span>
                                        </label>
                                        <div class="col-md-6 col-sm-6 col-xs-12">
                                            <input type="file" name="file" id="file" /> 
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                                            <button type="submit" class="btn btn-success">上传</button>
                                        </div>
                                    </div>
                                </form>
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
    <!-- Parsley -->
    <script src="${ctx}/static/js/parsleyjs/parsley.min.js"></script>
    <script src="${ctx}/static/js/parsleyjs/i18n/zh_cn.js"></script>

    <!-- Custom Theme Scripts -->
    <script src="${ctx}/static/js/custom.js"></script>
    <script src="${ctx}/static/js/beautyfileupload.js"></script>
    <script>
        $(function(){
            $('#refundTime').datetimepicker({
                allowInputToggle: true,
                format: 'YYYY-MM-DD hh:mm:ss',
                ignoreReadonly: true,
                collapse:true
            });
            
            $("#file").beautyfileupload({
            	allowExtensions:[".zip"],
            	allowExtensionErrorHandler:function(){
            		$(this).val('');
            		alert("文件格式不正确");
            	},
            	okHandler:function(e){
            		alert("ok"+this)
            	}
            });
        });
    </script>
</body>
</html>