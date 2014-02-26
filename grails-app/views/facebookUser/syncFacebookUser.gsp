<html>
<head>
 <meta charset="utf-8">
	 <g:if test="${isLoggedWithFacebook}">
	 	<script type="text/javascript" src="${resource(dir:'js/login',file:'success.js')}"></script>
	 </g:if>
	 <g:else>
		<script type="text/javascript" >
		    var loc = window.location.toString();
		    var x = loc.search("#");
			
		    if (x > 0) {

			    <%-- 
					Facebook sends the authentication answer with an anchor (#, fragment)
					That's why we get the fragment in javascript to reload the page and 
					manage the anchor as parameters. It's send through server side then.
				--%>
				
		        var out = loc.replace(".gsp#","?");

		        alert(out);
		        
		        window.location = out
		
		    } else { 
				alert('Your facebook authentication failed');
			}
		</script>
	 </g:else>
</head>
<body>
	<p>Success !</p>
</body>
</html>