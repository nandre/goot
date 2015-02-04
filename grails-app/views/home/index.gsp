<html>

<head>
	<title><g:message code="default.welcome.title" args="[meta(name:'app.name')]"/> </title>
	<meta name="layout" content="kickstart" />
</head>

<body>

	<section id="intro" class="first">
		<h1>Welcome to your brand new universe.</h1>
		<p>
			Let's start together to share our vision of what sharing should be. 
		</p>
		<h2>Don't knock at the door, this house is yours </h2>
		<p>
			No matter what it looks like now, you'll be the best making it turn into something marvellous.
		</p>
		<sec:ifNotLoggedIn>
			<g:link style="display: block; margin: auto; padding-top : 40px; width; 150px; text-align: center;" controller="user" action="createUser"><button type="button" class="btn btn-large btn-info">Create your account !</button></g:link>
		</sec:ifNotLoggedIn>
	</section>


</body>

</html>
