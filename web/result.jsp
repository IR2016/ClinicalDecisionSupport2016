<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Result</title>
<!-- Latest compiled and minified CSS -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
		<link rel="stylesheet" type="text/css" href="style.css">
       
                <style>
                    tt {
                        background: yellow;
                    }
                </style>
       
</head>
<body style="background:white;">
<%@page import="java.util.HashMap" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List"%>
<%@page import="org.json.*"%>

    <%
        String newQuery = (String)request.getAttribute("query");
        System.out.println("===in request=="+ newQuery);
        if(newQuery == null){
            newQuery = "Search for...";
        }
    %>

        <div class="searchBar" style="width:800px;margin:auto;padding-top:30px">
            
            <!--query input-->
            <form action="QueryServlet" method="post">
                    <div class="row">
                            <div class="col-xs-9" style="padding-right: 0">
                          <input id="freeText" name="freeText" type="text" class="form-control customize-input" placeholder="Search for..." value="<%=newQuery%>">
                        </div>
                        <div class="col-xs-3" style="padding-left: 0">   
                                    <button class="btn customize-btn" type="submit">Search</button>
                            </div>
                    </div>
            </form> 

	 </div><!--searchbar-->
         
         <div id="content" style="width:1200px;margin:auto;">

	<%
		if(request.getAttribute("res")!=null){
			String result = (String)request.getAttribute("res");
			JSONArray jsonArray = new JSONArray(result);
			
                    for (int i = 0; i < jsonArray.length(); i++) {
		        JSONObject object = jsonArray.getJSONObject(i);
		        out.println("====== "+ (i+1) +" =======");
//		        out.println("title: " + object.getString("title"));
//		        out.println("abst: " + object.getString("abst"));
		        // out.println("keywords: " + object.getString("keywords"));
//		        out.println("content: " + object.getString("content"));
                        String link = (i+1)+ "TITLE: " + object.getString("title");
                        out.println("<h4><a href='QueryServlet?id="+ i +"'>" + link + "</a></h4>");
                        out.println("<p>" +"ABSTRACT: " + object.getString("abst") + "</p>");
		    }
		}
	%>
        </div>
       
            
        </script>
        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
   	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous">
   	</script>
        
        <script>
            
            String.prototype.splice = function(idx, rem, str) {
                return this.slice(0, idx) + str + this.slice(idx + Math.abs(rem));
            };
            
            $(document).ready(function(){
                
                var searchBox = $('#freeText');
                var word = searchBox.val();
                if (word) {
                    word = word.split(' ').join('|')
                    highlightByWord(word);
                }
                
                
            })
            
            function highlightByWord(word) {
                console.log(word);
                var open = '<tt>';
                var close = '</tt>';
//                var len = word.length;
                
                
                
                var $ps = $('p');
                $.each($ps, function(){
                    var offset = 0;
                    var regex = new RegExp(word, 'gi'); //!!!
                    var content = $(this).text();
                    var newContent = content;
                    var match;
                    console.log($(this).get(0));
                    while ((match = regex.exec(content)) != null) {
                        var len = match[0].length //!!!
                        console.log('find it at ' + match.index);
                        var start = match.index;
                        newContent = newContent.splice(start + offset, 0, open);
                        offset += 4;
                        var end = start + len;
                        newContent = newContent.splice(end + offset, 0, close);
                        offset += 5;
                    }
                    $(this).html(newContent);
                })
                
            }
        </script>
        
</body>
</html>