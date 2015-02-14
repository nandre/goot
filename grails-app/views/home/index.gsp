<html>

<head>
	<title><g:message code="default.welcome.title" args="[meta(name:'app.name')]"/> </title>
	<meta name="layout" content="kickstart" />
</head>

<body>

	<section id="intro" class="first">
		<h1 style="text-align : center;">Welcome to your brand new home.</h1>
		<sec:ifNotLoggedIn>
			<g:link style="display: block; margin: auto; padding-top : 40px; width; 150px; text-align: center;" controller="user" action="createUser"><button type="button" class="btn btn-large btn-info">Create your account !</button></g:link>
		</sec:ifNotLoggedIn>
	</section>


</body>

</html>
