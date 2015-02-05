<ul class="nav pull-right">
	<li class="dropdown dropdown-btn">
		
		<a class="dropdown-toggle" role="button" data-toggle="dropdown" data-target="#" href="#" tabindex="-1">
			<!-- TODO: integrate Springsource Security etc. and show User's name ... -->
    		<i class="icon-user"></i>
    		<g:message code="security.signin.label"/><b class="caret"></b>
		</a>
		<ul class="dropdown-menu" role="menu">
			<li class="fbconnect-container">
				<div style="text-align : center">
					<sec:ifNotGranted roles="ROLE_USER">
						<fb:login-button scope="email,publish_stream" onlogin="facebookLogin();" size="large">
							<g:message code="auth.login.facebook"/>
						</fb:login-button>
						<%-- Fake form to redirect to facebookLogin method in auth controller --%>
						<g:form name="facebookLogin" controller="auth" action="facebookLogin">
						</g:form>
					</sec:ifNotGranted>
					<sec:ifAllGranted roles="ROLE_USER">
					  Welcome <sec:username/> ! (<g:link uri="/j_spring_security_logout">Logout</g:link>)
					</sec:ifAllGranted>
				 </div>
			</li>
			
			<sec:ifNotGranted roles="ROLE_USER">
			
			<script type="text/javascript">

			var showButton = false;
			
			$(document).ready(function() { 
				
					$(".input_cred").change(function(){

						showButton = true;
						
						$("#password").parents("div.control-group").toggleClass("error", ($("#password").val().length<= 0));
						$("#password").parents("div.control-group").toggleClass("success", ($("#password").val().length> 0));


						$("#username").parents("div.control-group").toggleClass("error", ($("#username").val().length<= 0));
						$("#username").parents("div.control-group").toggleClass("success", ($("#username").val().length> 0));

						$(":input.input_cred").each(function( index ) {
							  if($(this).val().length == 0){
									showButton = false;
						 	   }
							});

						$("#sign-in").toggleClass("disabled", !showButton);
						
					});


					
					$(":input.input_cred").each(function( index ) {
						  if($(this).val().length == 0){
								showButton = false;
					 	   }
						});
					$("#sign-in").toggleClass("disabled", !showButton);

			});
									
		
			</script>
		
			<li class="divider"></li>
			<li class="form-container">
				<g:form controller="login" action="standardLogin" method="post" accept-charset="UTF-8">
					<div class="control-group">
						<div class="controls">
							<input class="input_cred" style="margin-bottom: 15px;" type="text" placeholder="Username" id="username" name="username">
						</div>
					</div>
					
					<div class="control-group">
						<div class="controls">
							<input class="input_cred" style="margin-bottom: 15px;" type="password" placeholder="Password" id="password" name="password">
						</div>
					</div>

					<input class="btn btn-primary btn-block" type="submit" id="sign-in" value="Sign in without Facebook">
				</g:form>
			</li>
			<li class="divider"></li>
			<li class="button-container">
				<!-- NOTE: the renderDialog MUST be placed outside the NavBar (at least for Bootstrap 2.1.1): see bottom of main.gsp -->
				<a href="${createLink(controller:'user', action: 'createUser')}" style="color : white;" class="btn btn-primary btn-block">${message(code: 'security.register.label', default: 'Register')}</a>
			</li>
			</sec:ifNotGranted>
		</ul>

	</li>
</ul>

<noscript>
<ul class="nav pull-right">
	<li class="">
		<g:link controller="user" action="show"><g:message code="default.user.unknown.label"/></g:link>
	</li>
</ul>
</noscript>
