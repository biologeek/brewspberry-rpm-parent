<%@page import="net.brewspberry.business.beans.Levure"%>
<%@page import="net.brewspberry.business.beans.Houblon"%>
<%@page import="net.brewspberry.business.beans.Malt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>

<head>
<title>BrewViewer</title>

<link href="https://code.jquery.com/jquery-3.0.0.min.js" rel="stylesheet">
<!-- Bootstrap -->
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet"
	media="screen">
<link href="assets/styles.css" rel="stylesheet" media="screen">
<link href="vendors/jGrowl/jquery.jgrowl.css" rel="stylesheet"
	media="screen">

<!--/.fluid-container-->
<link href="vendors/datepicker.css" rel="stylesheet" media="screen">
<link href="vendors/uniform.default.css" rel="stylesheet" media="screen">
<link href="vendors/chosen.min.css" rel="stylesheet" media="screen">

<link href="vendors/wysiwyg/bootstrap-wysihtml5.css" rel="stylesheet"
	media="screen">

<script src="vendors/jquery-1.9.1.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<script src="vendors/jquery.uniform.min.js"></script>
<script src="vendors/chosen.jquery.min.js"></script>
<script src="vendors/bootstrap-datepicker.js"></script>

<script src="vendors/wysiwyg/wysihtml5-0.3.0.js"></script>
<script src="vendors/wysiwyg/bootstrap-wysihtml5.js"></script>

<script src="vendors/wizard/jquery.bootstrap.wizard.min.js"></script>

<script src="https://code.angularjs.org/1.3.0/angular.min.js"></script>
<script src="https://code.angularjs.org/1.3.0/angular-messages.min.js"></script>


<script type="text/javascript"
	src="vendors/jquery-validation/dist/jquery.validate.min.js"></script>
<script src="assets/form-validation.js"></script>

<script src="assets/scripts.js"></script>



<%
	List<Malt> maltList = (List<Malt>) request.getAttribute("maltList");
		List<Houblon> hopList = (List<Houblon>) request
		.getAttribute("hopList");
		List<Levure> yeastList = (List<Levure>) request
		.getAttribute("yeastList");
%>
<script>
	jQuery(document).ready(function() {
		FormValidation.init();
	});

	$(function() {
		$(".datepicker").datepicker();
		$(".uniform_on").uniform();
		$(".chzn-select").chosen();
		$('.textarea').wysihtml5();

		$('#rootwizard').bootstrapWizard(
				{
					onTabShow : function(tab, navigation, index) {
						var $total = navigation.find('li').length;
						var $current = index + 1;
						var $percent = ($current / $total) * 100;
						$('#rootwizard').find('.bar').css({
							width : $percent + '%'
						});
						// If it's the last tab then hide the last button and
						// show the finish instead
						if ($current >= $total) {
							$('#rootwizard').find('.pager .next').hide();
							$('#rootwizard').find('.pager .finish').show();
							$('#rootwizard').find('.pager .finish')
									.removeClass('disabled');
						} else {
							$('#rootwizard').find('.pager .next').show();
							$('#rootwizard').find('.pager .finish').hide();
						}
					}
				});
		$('#rootwizard .finish').click(function() {
			alert('Finished!, Starting over!');
			$('#rootwizard').find("a[href*='tab1']").trigger('click');
		});
	});
</script>

<script type="text/javascript">
			

	
	function cleanUpSpecificPart(spec){
		
		$("#"+spec).empty();
		
	}
	
	
	</script>

<script type="text/javascript">
jQuery(document).ready(function(){
	console.log('Top success : ${topSuccess}');
	// Adds success field if ingredient is created
	if (${topSuccess} == '1') {
		
		console.log('Prepending content')
		$('#content').prepend ('<div class=\"alert alert-success\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button><h4>Success</h4>The operation completed successfully</div>');
	}
	});
</script>

<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
<script src="vendors/modernizr-2.6.2-respond-1.1.0.min.js"></script>


</head>

<body class="wysihtml5-supported" ng-app="ingredientApp">

	<jsp:include page="tpl/header.jsp"></jsp:include>


	<div class="container-fluid" ng-controller="ingredientController">

		<div class="row-fluid">
			<jsp:include page="tpl/sidebar.jsp"></jsp:include>
			<!--/span-->
			<div class="span9 main-block" id="content">
			
			<div class="row-fluid">
	            <div class="alert alert-success" ng-show="successfulAdding">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
	                <h4>Success</h4>
	            	The operation completed successfully</div>
	            	<div class="navbar">
	                	<div class="navbar-inner">
	                        <ul class="breadcrumb">
	                            <i class="icon-chevron-left hide-sidebar"><a href='#' title="Hide Sidebar" rel='tooltip'>&nbsp;</a></i>
	                            <i class="icon-chevron-right show-sidebar" style="display:none;"><a href='#' title="Show Sidebar" rel='tooltip'>&nbsp;</a></i>
	                            <li>
	                                <a href="#">Dashboard</a> <span class="divider">/</span>	
	                            </li>
	                            <li>
	                                <a href="#">Settings</a> <span class="divider">/</span>	
	                            </li>
	                            <li class="active">Tools</li>
	                        </ul>
	                	</div>
	            	</div>
	            	<div class="alert alert-error" ng-show="unsuccessfulAdding">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
	                <h4>Failure</h4>
	            	Something went wrong : {{ errorMessage }}</div>
	            	<div class="navbar">
	                	<div class="navbar-inner">
	                        <ul class="breadcrumb">
	                            <i class="icon-chevron-left hide-sidebar"><a href='#' title="Hide Sidebar" rel='tooltip'>&nbsp;</a></i>
	                            <i class="icon-chevron-right show-sidebar" style="display:none;"><a href='#' title="Show Sidebar" rel='tooltip'>&nbsp;</a></i>
	                            <li>
	                                <a href="#">Dashboard</a> <span class="divider">/</span>	
	                            </li>
	                            <li>
	                                <a href="#">Settings</a> <span class="divider">/</span>	
	                            </li>
	                            <li class="active">Tools</li>
	                        </ul>
	                	</div>
	            	</div>
	        	</div>
				<div class="block">
					<div class="navbar navbar-inner block-header">
						<div class="muted pull-left">Ajouter un ingr�dient</div>
					</div>
					
					<div class="block-content collapse in">
						<div class="span12">
							<form name="addIngredient" ng-submit="submitToIngredientService()" class="form-horizontal">
								<fieldset>
									<legend>Ingrédient</legend>

									<div class="control-group">
										<label class="control-label" for="date01">Type of
											ingredient:</label>
										<div class="controls">

											<select name="ingredient_type" id="ingredient_type"
												class="hop-select"
												ng-change="updatePageWithSpecificFields();" ng-model="ingredientTypeSelection">
												<option value="default" selected="selected"></option>
												<c:forEach items="${ingredientTypes}" var="ing">
													<option value="${ing.key}"
														<c:if test="${ing.key == ingredientType}"> selected="selected" </c:if>
														${ing.value}</option>
												</c:forEach>
												<div ng-messages="addIngredient.ingredient_type.$error" ng-messages-include="tpl/error_mmessages.html"></div>
											</select>
										</div>
									</div>


									<div class="control-group">
										<label class="control-label" for="date01">Description
											:</label>
										<div class="controls">
											<input type="text" name="ing_description" class="span6" ng-model="ing_description"
												id="typeahead" data-provide="typeahead" data-items="4"
												value="${ing_fdescription_val}">
											<div ng-messages="addIngredient.ing_description.$error" ng-messages-include="tpl/error_mmessages.html"></div>
												
										</div>
									</div>
									<div id="malt-specific-parameters" ng-show="malt_specific_parameters_show" style="display:none">
										<div class="control-group">
											<label class="control-label" for="date01">Malt cereal :</label>
											<div class="controls">
												<select name="smal_cereale" ng-model="smal_cereale" id="select01" class="hop-select">\
													${maltSpecificCerealMenuOptions}
												</select>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label" for="date01">Type of malt :</label>
											<div class="controls">
												<select name="smal_type" ng-model="smal_type" id="select01" class="hop-select">
													${maltTypeSpecificMenuOptions}
												</select>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label" for="date01">Color of malt (EBC) :</label>
												<div class="controls">
													<input type="text" name="smal_couleur" ng-model="smal_couleur" class="span6"
														id="typeahead" data-provide="typeahead" data-items="4"
															value="${smal_couleur_val}">
												</div>
										</div>
									</div>
									<div id="hop-specific-parameters" ng-show="hop_specific_parameters_show" style="display:none">
										<div class="control-group">
											<label class="control-label" for="date01">Hop variety :</label>
											<div class="controls">
												<input type="text" name="shbl_variete" ng-model="shbl_variete" class="span6"
													id="typeahead" data-provide="typeahead" data-items="4"
														value="${shbl_variete_val}">
											</div>
										</div>
										<div class="control-group">
											<label class="control-label" for="date01">Alpha acids :</label>
											<div class="controls">
												<input type="text" name="shbl_acide_alpha" ng-model="shbl_acide_alpha" class="span6"
												id="typeahead" data-provide="typeahead" data-items="4"
												value="${shbl_acide_alpha_val}">
											</div>
										</div>
										<div class="control-group">
											<label class="control-label" for="date01">Aromas :</label>
											<div class="controls">
												<input type="text" name="shbl_aromes" ng-model="shbl_aromes" class="span6"
													id="typeahead" data-provide="typeahead" data-items="4"
													value="${shbl_aromes_val}">
											</div>
										</div>
										<div class="control-group">
											<label class="control-label" for="date01">Use :</label>
											<div class="controls">
												<select name="shbl_type" id="select01" ng-model="shbl_type" class="hop-select">
													<option value="default" selected="selected"></option>
													${hopTypeSpecificMenuOptions}
												</select>
											</div>
										</div>
									</div>		
									<div id="yeast-specific-parameters" ng-show="yeast_specific_parameters_show" style="display:none">									
										<div class="control-group">
										<label class="control-label" for="date01">Yeast species :</label>
											<div class="controls">
												<select name="yeast_specie" id="select01" ng-model="yeast_specie" class="hop-select">
													<option value="default" selected="selected"></option>
													<option value="Saccharomyces cerevisiae">Saccharomyces cerevisiae</option>
													<option value="Saccharomyces uvarum">Saccharomyces uvarum</option>
												</select>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label" for="date01">Floculation :</label>
											<div class="controls">
												<select name="slev_floculation" ng-model="slev_floculation" id="select01" class="hop-select">
													<option value="default" selected="selected"></option>
													<option value="faible">Low</option>
													<option value="moyenne">Medium</option>
													<option value="forte">High</option>
												</select>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label" for="date01">Aromas :</label>
											<div class="controls">
												<input type="text" name="slev_aromes" ng-model="slev_aromes" class="span6"
													id="typeahead" data-provide="typeahead" data-items="4"
														value="${slev_aromes_val}">
											</div>
										</div>
									</div>
									<br /> 
									
									<input type="hidden" name="ingredient_id" value="${ingredient_id}" />

									<div class="control-group">
										<label class="control-label" for="date01">Provider :</label>
										<div class="controls">
											<input type="text" name="ing_fournisseur" ng-model="ing_fournisseur" class="span6"
												id="typeahead" data-provide="typeahead" data-items="4"
												value="${ing_fournisseur_val}">
											<div ng-messages="addIngredient.ing_fournisseur.$error" ng-messages-include="tpl/error_mmessages_add_ingredient.html"></div>
										</div>
									</div>

									<div class="form-actions">
										<button type="submit" class="btn btn-primary">Save
											changes</button>
										<button type="reset" class="btn">Cancel</button>
									</div>
								</fieldset>
							</form>

						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
	<!-- TODO : Compléter avec la page interface.html du template -->

	<jsp:include page="tpl/footer.jsp"></jsp:include>


</body>
</html>