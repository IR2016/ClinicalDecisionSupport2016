<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">

		<title>index</title>
		<!-- Latest compiled and minified CSS -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
		<link rel="stylesheet" type="text/css" href="style.css">
	</head>

	<body>
		

    <div class = "container">
    	<div class ="main">	
	    	
	    	<div class="header">
	    		<h1>CLINICAL DECISION SUPPORT SYSTEM</h1>
	    		<h3>Evidence-based Search for Better Dignosis and Treatment</h3>
	    	</div><!--header-->
			
			<div class="searchBar">
				<!--tab-->
				<ul class="nav nav-tabs nav-justified">
					<li role="presentation" class="customize-tab"><a href="index.jsp">Free Text</a></li>
				  	<li role="presentation" class="customize-tab inactive"><a href="test.jsp">Test Topic</a></li>
				</ul>

				<!--query input-->
				<form action="QueryServlet" method="post">
					<div class="row">
						<div class="col-xs-9" style="padding-right: 0">
					      <input name="freeText" type="text" class="form-control customize-input" placeholder="Search for...">
					    </div>
					    <div class="col-xs-3" style="padding-left: 0">   
							<button class="btn customize-btn" type="submit">Search</button>
						</div>
					</div>
	   			</form> 

	   		</div><!--searchbar-->
   		</div><!--main-->

		<!--footer-->
	   	<footer class="footer">
	      <div class="footer_container">

	        <p id="by">By Jie Song, Shi Qiu, Hanwei Cheng<p>
	        <p class="text-muted">&copy; 2016 IS2140 Information Storage & Retrieval &middot; <a href="#">Privacy</a>
	                            &middot; <a href="#">Terms</a></p>
	      </div>
	    </footer>

   	</div> <!--container-->
    
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
   	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous">
   	</script>
	</body>
</html>
