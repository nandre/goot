<html>
	<head>
		<title><g:message code="facebookUser.welcomePage.title"/></title>
		<meta name="layout" content="kickstart" />
		<g:set var="layout_nomainmenu"		value="${true}" scope="request"/>
		<g:set var="layout_nosecondarymenu"	value="${true}" scope="request"/>
	</head>

<body>
	<content tag="header">
		<!-- Empty Header -->
	</content>
	
  	<section id="Welcome" class="">
		<div class="big-message">
			<div class="container">
				<h1>
					<g:message code="facebookUser.welcomePage.h1"/>
				</h1>
				<h2>
					<g:message code="facebookUser.welcomePage.h2"/>
				</h2>
				<p>
					<g:message code="facebookUser.welcomePage.p"/>
				</p>
				
				<div class="actions">
					<a href="${createLink(uri: '/')}" class="btn btn-large btn-primary">
						<i class="icon-chevron-left icon-white"></i>
						<g:message code="error.button.backToHome"/>
					</a>					
				</div>
			</div>
		</div>
	</section>
  
  
  </body>
</html>