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

    <title>登录</title>
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
 
     <script type="text/javascript">
		if(parent != window){
			parent.location.href = window.location.href;
		}
		function func_login(){
			document.forms[0].submit();
		}
	</script>
  </head>

  <body class="login">
    <div>
      <a class="hiddenanchor" id="signup"></a>
      <a class="hiddenanchor" id="signin"></a>

      <div class="login_wrapper">
        <div class="animate form login_form">
          <section class="login_content">
            <form action="doLogin" method="post">
              <h1>登录系统</h1>
              <div>
                <input type="text" name="loginId" class="form-control" placeholder="用户名" required="" />
              </div>
              <div>
                <input type="password" name="loginPassword" class="form-control" placeholder="密码" required="" />
              </div>
              <div>
                <button class="btn btn-default submit" type="button" onclick="func_login();">登录</button>
                <a class="reset_pass" href="#"></a>
              </div>

              <div class="clearfix"></div>

              <div class="separator">
                <p class="change_link">
                  <a href="#signup" class="to_register"></a>
                </p>

                <div class="clearfix"></div>
                <br />

                <div>
                  <h1><i class="fa fa-paw"></i>Power BY Ken Ngai</h1>
                </div>
              </div>
            </form>
          </section>
        </div>

        <div id="register" class="animate form registration_form">
          <section class="login_content">
            <form>
              <h1>Create Account</h1>
              <div>
                <input type="text" class="form-control" placeholder="Username" required="" />
              </div>
              <div>
                <input type="email" class="form-control" placeholder="Email" required="" />
              </div>
              <div>
                <input type="password" class="form-control" placeholder="Password" required="" />
              </div>
              <div>
                <a class="btn btn-default submit" href="index.html">Submit</a>
              </div>

              <div class="clearfix"></div>

              <div class="separator">
                <p class="change_link">Already a member ?
                  <a href="#signin" class="to_register"> Log in </a>
                </p>

                <div class="clearfix"></div>
              </div>
            </form>
          </section>
        </div>
      </div>
    </div>
  </body>
</html>
