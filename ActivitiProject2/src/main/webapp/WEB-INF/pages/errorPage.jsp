<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglib.jsp" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>错误页面</title>
    <!-- Animate.css -->
    <link href="${ctx}/static/css/animate.min.css" rel="stylesheet">
     <!-- Bootstrap core CSS -->
     <link href="${ctx}/static/css/bootstrap.min.css" rel="stylesheet">

     <link href="${ctx}/static/fonts/css/font-awesome.min.css" rel="stylesheet">
     <link href="${ctx}/static/css/animate.min.css" rel="stylesheet">
     <!-- Custom Theme Style -->
     <link href="${ctx}/static/css/custom.min.css" rel="stylesheet">
 
     <!-- NProgress -->
     <link href="${ctx}/static/css/nprogress.css" rel="stylesheet">
 
     <!-- jQuery -->
     <script src="${ctx}/static/js/jquery.min.js"></script>
     <!-- Bootstrap -->
     <script src="${ctx}/static/js/bootstrap.min.js"></script>
  </head>

  <body class="login">
    <div>
      <a class="hiddenanchor" id="signup"></a>
      <a class="hiddenanchor" id="signin"></a>

      <div class="login_wrapper">
        <div class="animate form login_form">
          <section class="login_content">
           ${exception}
          </section>
        </div>
      </div>
    </div>
  </body>
</html>
