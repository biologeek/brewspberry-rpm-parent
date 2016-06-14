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
<div class="errormessages">
	<c:forEach begin="0" end="${errors.size()}" var="err">
	
		<c:if test="${err.getSeverity() < 3">
			<div class="alert alert-danger">
			    <a class="close" data-dismiss="alert" href="#">&times;</a>
			    <strong>Error!</strong> ${err.getError()}.
			</div>
		</c:if>
	
	</c:forEach>
</div>
<form action="register" method="post">
	<fieldset name="Login info">
		<div class="group">
		    <label>Username</label>
		  	<input type="text" name="username" id="username"><span class="highlight"></span><span class="bar"></span>
		  	<span id="v_username"></span> 
		</div>
		<div class="group">
		    <label>Password</label>
		    <input type="password" name="password" id="password"><span class="highlight"></span><span class="bar"></span>
		  	<span id="v_password"></span> 
		</div>
		
		<div class="group">
		    <label>Repeat password</label>
		    <input type="password" id="repeat_password"><span class="highlight"></span><span class="bar"></span>
		  	<span id="v_repeat_password"></span> 
		</div>
	</fieldset>
  
  
  		<fieldset name="User info">
			  
		<div class="group">
		    <label>First name</label>
		  	<input type="text" name="first_name" id="first_name"><span class="highlight"></span><span class="bar"></span>
		  	<span id="v_first_name"></span> 
		</div>
		<div class="group">
		    <label>Last name</label>
		  	<input type="text" name="last_name" id="last_name"><span class="highlight"></span><span class="bar"></span>
		  	<span id="v_last_name"></span> 
		</div>
		
		<div class="group">
		    <label>Age</label>
		  <select name="age">
			<c:forEach begin="0" end="100" var="a">
		  		<option value="${a}">${a}</option>
		  	</c:forEach>
		  </select>
		  </span><span class="bar"></span>
		  	<span id="v_age"></span> 
		</div>
		
		<div class="group">
		    <label>Birthday</label>
		  <select name="birthday">
			
		  </select>
		  </span><span class="bar"></span>
		  	<span id="v_birthday"></span> 
		</div>
 		</fieldset>
 
	  <input type="hidden" name="formType" id="formType" value="registration">
	  <input type="submit" class="button buttonBlue" />
	  <div class="ripples buttonRipples"><span class="ripplesCircle">
	    </span>
	</div>
</form>

<a href="register"> Pas encore inscrit ?</a>

</body>
</html>
