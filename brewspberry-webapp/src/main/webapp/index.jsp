<!DOCTYPE html>
<html>

<head>
<title>BrewViewer</title>
<!-- Bootstrap -->
<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet"
	media="screen">
<link href="assets/styles.css" rel="stylesheet" media="screen">
<link href="vendors/jGrowl/jquery.jgrowl.css" rel="stylesheet" />
<link href="css/connection/style.css" rel="stylesheet"
	media="screen">
<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
<script src="js/vendor/modernizr-2.6.2-respond-1.1.0.min.js"></script>
<script src="js/connection/index.js"></script>

</head>


<body>

    <hgroup>
  <h1>Brewspberry connection page</h1>

</hgroup>
<c:forEach begin="0" end="${errors.size()}" var="err">

	<c:if test="${err.getSeverity() < 3}">
		<div class="alert alert-danger">
		    <a class="close" data-dismiss="alert" href="#">&times;</a>
		    <strong>Error!</strong> ${err.getError()}.
		</div>
	</c:if>

</c:forEach>
<form action="user.do" method="post">
  <div class="group">
     <label>Username</label>
    <input type="text" name="username" id="username"><span class="highlight"></span><span class="bar"></span>
  </div>
  <div class="group">
      <label>Password</label>
    <input type="password" name="password" id="password"><span class="highlight"></span><span class="bar"></span>

  </div>
  <input type="hidden" name="formType" id="formType" value="connection">
  <input type="submit" class="button buttonBlue" />
  <div class="ripples buttonRipples"><span class="ripplesCircle">
    </span></div>
</form>

<a href="register"> Pas encore inscrit ?</a>

</body>
</html>
