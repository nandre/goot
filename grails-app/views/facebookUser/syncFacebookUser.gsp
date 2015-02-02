<html>
<head>
 <meta charset="utf-8">
	 <g:if test="${isLoggedWithFacebook}">
	 	<g:javascript src="${resource(dir:'js/login',file:'success.js')}" />
		<script type="text/JavaScript">
			window.onload = function(){
				//also send JSESSIONID to chrome extension
				postToChromeExtension("${JSESSIONID}");
			}
		</script>
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
				
		        var out = loc.replace(".gsp?#","?");
		        
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