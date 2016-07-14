<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="net.brewspberry.business.beans.Brassin"%>
<%@page import="java.lang.Math"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	Brassin brassin = (Brassin) request.getAttribute("brew");
%>

<!-- Bootstrap -->
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet"
	media="screen">
<link href="assets/styles.css" rel="stylesheet" media="screen">
<link href="vendors/jGrowl/jquery.jgrowl.css" rel="stylesheet"
	media="screen">

<script src="https://code.jquery.com/jquery-2.2.2.min.js"></script>

<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/1.0.2/Chart.min.js"></script>
<script src="https://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>

<script type="text/javascript"
	src="http://momentjs.com/downloads/moment-with-locales.min.js">
	
</script>
<script type="text/javascript" src="js/graphRefresher.js"></script>

<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
<script src="vendor/modernizr-2.6.2-respond-1.1.0.min.js"></script>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Brassin n°${brassin.getBra_id()}</title>
</head>

<body class="wysihtml5-supported">

	<jsp:include page="tpl/header.jsp"></jsp:include>


	<div class="container-fluid">

		<div class="row-fluid">
			<jsp:include page="tpl/sidebar.jsp"></jsp:include>
			<!--/span-->
			<div class="span9" id="content">

				<div class="block">
					<div class="navbar navbar-inner block-header">
						<div class="muted pull-left"
							style="font-weight: bold; text-align: center;">Stocks</div>
					</div>
				</div>

				<div class="block">
					<div class="navbar navbar-inner block-header">
						<div class="muted pull-left" style="font-size: 10px;">

							<table>

								<tr>
									<th>Ingredient</th>
									<th>Unitary Value</th>
									<th>Unitary Weight</th>
									<th colspan="2">Total</th>
								</tr>

								<tr>
									<th></th>
									<th></th>
									<th></th>
									<th>kg/L</th>
									<th>€</th>
								</tr>
								<c:forEach begin="0" end="${counters.size()}" var="counter">

									<tr>
										<td>${(AbstractIngredient) (counter.getCpt_product()).getIng_desc()}</td>
										<td>${(AbstractIngredient) (counter.getCpt_product()).getIng_unitary_price()}</td>
										<td>${(AbstractIngredient) (counter.getCpt_product()).getIng_unitary_weight()}</td>
										<td>${counter.getCpt_value()}</td>
										<td>${counter.getStd_stock_value()}</td>
									</tr>

								</c:forEach>
							</table>

						</div>
					</div>
				</div>
			</div>
		</div>
</body>