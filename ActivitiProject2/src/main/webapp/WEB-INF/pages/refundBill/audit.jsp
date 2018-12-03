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
    <!-- include summernote css/js -->
	<link href="${ctx}/static/css/summernote.css" rel="stylesheet" />
	<link href="${ctx}/static/css/icheck-green.css" rel="stylesheet" />
	<link href="${ctx}/static/css/bootstrap-dialog.min.css" rel="stylesheet" />
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
                                <h2>报销审批<small>审批报销数据</small></h2>
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
                                <form action="${ctx}/refundBill/saveAudit" method="POST" class="form-horizontal form-label-left" data-parsley-validate="data-parsley-validate">
                                	<input type="hidden" name="id" value="${refundBill.id}"/>
                                    <div class="item form-group">
                                        <label class="col-md-3 col-sm-3 col-xs-12 control-label" for="name">
                                          	  标题<span class="required">*</span>
                                        </label>
                                        <div class="col-md-6 col-sm-6 col-xs-12">
                                            ${refundBill.subject}
                                        </div>
                                    </div>
                                    <div class="item form-group">
                                        <label class="col-md-3 col-sm-3 col-xs-12 control-label" for="name">
                                          	  报销金额<span class="required">*</span>
                                        </label>
                                        <div class="col-md-6 col-sm-6 col-xs-12">
                                           ${refundBill.refundAmount}
                                        </div>
                                    </div>
                                    <div class="item form-group">
                                        <label class="col-md-3 col-sm-3 col-xs-12 control-label" for="name">
                                            	报销日期<span class="required">*</span>
                                        </label>
                                        <div class="col-md-6 col-sm-6 col-xs-12">
                                             ${refundBill.refundTime}
                                        </div>
                                        
                                    </div>
                                    <div class="item form-group">
                                        <label class="col-md-3 col-sm-3 col-xs-12 control-label" for="textarea">
                                           	 说明<span class="required">*</span>
                                        </label>
                                        <div class="col-md-6 col-sm-6 col-xs-12">
                                           ${refundBill.content}
                                        </div>
                                    </div>
                                    <%@ include file="approve.jsp" %>
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
    <!-- icheck -->
    <script src="${ctx}/static/js/icheck.min.js"></script>
    <script src="${ctx}/static/js/summernote.min.js"></script>
    <script src="${ctx}/static/js/summernote-lang/summernote-zh-CN.js"></script>
    <script src="${ctx}/static/js/bootstrap-dialog.min.js"></script>
	<script src="${ctx}/static/js/bootstrapGlobal.js"></script>
    <!-- Custom Theme Scripts -->
    <script src="${ctx}/static/js/custom.js"></script>
    <script type="text/javascript">
      $(function(){
    	  $('#advice').summernote({
          	placeholder: '请输入说明',
              tabsize: 2,
              minHeight: 300,             // set minimum height of editor
              lang: 'zh-CN',
              callbacks: {
                  onImageUpload: function(files) {
                    UploadFiles(files);
                    //$summernote.summernote('insertNode', imgNode);
                  }
               }
          });
      });
      
      function UploadFiles(files){
          //这里files是因为我设置了可上传多张图片，所以需要依次添加到formData中
              var formData = new FormData();
              for(f in files){
                  formData.append("file", files[f]);
              }
           
              $.ajax({
                  data: formData,
                  type: "POST",
                  url: "${ctx}/upload/uploadImages",
                  cache: false,
                  dataType:"json",
                  contentType: false,
                  processData: false,
                  success: function(imageUrl) {
                  	console.log(imageUrl);
                  	 for(i in imageUrl){
                  		 console.log(imageUrl[i])
                  	        $('#advice').summernote('editor.insertImage',"${ctx}"+imageUrl[i]);
                  	    }

               
                  },
                  error: function() {
                      console.log("uploadError");
                  }
              })
          }
    </script>
</body>
</html>