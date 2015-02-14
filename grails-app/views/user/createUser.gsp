<html>

<head>
	<title><g:message code="createUser.title" args="[meta(name:'app.name')]"/></title>
	<meta name="layout" content="kickstart" />
	<script>

		var showButton = true;
		
		$(document).ready(function() { 
			
				$(".span3").change(function(){

					showButton = true;

					console.log("showButton : " + showButton);
					$("#firstName").parents("div.control-group").toggleClass("error", ($("#firstName").val().length <= 0));
					$("#firstName").parents("div.control-group").toggleClass("success", ($("#firstName").val().length > 0));
				

					$("#lastName").parents("div.control-group").toggleClass("error", ($("#lastName").val().length<= 0));
					$("#lastName").parents("div.control-group").toggleClass("success", ($("#lastName").val().length> 0));
		

					$("#email").parents("div.control-group").toggleClass("error", ($("#email").val().length<= 0));
					$("#email").parents("div.control-group").toggleClass("success", ($("#email").val().length> 0));


					$("#password1").parents("div.control-group").toggleClass("error", ($("#password1").val().length<= 0));
					$("#password1").parents("div.control-group").toggleClass("success", ($("#password1").val().length> 0));
				

					$("#password2").parents("div.control-group").toggleClass("error", ($("#password2").val().length<= 0));
					$("#password2").parents("div.control-group").toggleClass("success", ($("#password2").val().length> 0));

	
					$("#secretQuestion").parents("div.control-group").toggleClass("error", ($("#secretQuestion").val().length<= 0));
					$("#secretQuestion").parents("div.control-group").toggleClass("success", ($("#secretQuestion").val().length> 0));


					$("#secretAnswer").parents("div.control-group").toggleClass("error", ($("#secretAnswer").val().length<= 0));
					$("#secretAnswer").parents("div.control-group").toggleClass("success", ($("#secretAnswer").val().length> 0));

					console.log("showButton before loop: " + showButton);
					
					$(":input.registration").each(function( index ) {
						console.log("values : " + $(this).val());
						  if($(this).val().length == 0){
								showButton = false;
								console.log("showButton in loop : " + showButton);
								$("#submit-registration-button").attr('disabled', 'disabled');
					 	   };
						});

					if(showButton){
						console.log("showButton at the end: " + showButton);
						$("#submit-registration-button").removeAttr('disabled');
					}
					console.log("showButton at the very end : " + showButton);
					$("#submit-registration-button").toggleClass("disabled", !showButton);
					
				});


				
				$(":input.registration").each(function( index ) {
					  if($(this).val().length == 0){
							showButton = false;
							$("#submit-registration-button").attr('disabled', 'disabled');
				 	   };
					});

				if(showButton){
					$("#submit-registration-button").removeAttr('disabled');
				}
				$("#submit-registration-button").toggleClass("disabled", !showButton);
	
			});
	
	</script>
</head>
<body>
	<g:if test="${!registrationDone}">
		<section id="createUserForm">
			<div>
				<g:form controller="user" action="createUser" class="form-horizontal" method="post" name="register_form">
				<div class="modal-header">
					<h3><g:message code="security.register.title"/></h3>
				</div>
				<g:renderErrors bean="${userInst}" as="list" />
				<div class="modal-body">
					<div class="control-group">
						<label class="control-label" for="firstName">${message(code: 'security.firstname.label', default: 'Firstname')}</label>
						<div class="controls">
							<input type="text" class="span3 registration ${hasErrors(bean:userInst, field:'firstName', 'error')}" name="firstName" id="firstName" placeholder="${message(code: 'security.firstname.label', default: 'Firstname')}">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="lastName">${message(code: 'security.lastname.label', default: 'Lastname')}</label>
						<div class="controls">
							<input type="text" class="span3 registration ${hasErrors(bean:userInst, field:'lastName', 'error')}" name="lastName" id="lastName" placeholder="${message(code: 'security.lastname.label', default: 'Lastname')}">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="email">${message(code: 'security.email.label', default: 'Email')}</label>
						<div class="controls">
							<input type="text" class="span3 registration ${hasErrors(bean:userInst, field:'email', 'error')}" name="email" id="email" placeholder="${message(code: 'security.email.label', default: 'Email')}">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="password1">${message(code: 'security.password.label', default: 'Password')}</label>
						<div class="controls">
							<input type="password" class="span3 registration ${hasErrors(bean:userInst, field:'password', 'error')}" name="password" id="password1" placeholder="${message(code: 'security.password.label', default: 'Password')}">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="password2">${message(code: 'security.password.confirm.label', default: 'Confirm')}</label>
						<div class="controls">
							<input type="password" class="span3 registration ${hasErrors(bean:userInst, field:'password2', 'error')}" name="password2" id="password2" placeholder="${message(code: 'security.password.confirm.label', default: 'Confirm')}">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="secretQuestion">${message(code: 'security.question.label', default: 'Secret question')}</label>
						<div class="controls">
							<input type="text" class="span3 registration ${hasErrors(bean:userInst, field:'secretQuestion', 'error')}" name="secretQuestion" id="secretQuestion" placeholder="${message(code: 'security.question.label', default: 'Secret question')}">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="secretAnswer">${message(code: 'security.answer.label', default: 'Secret answer')}</label>
						<div class="controls">
							<input type="text" class="span3 registration ${hasErrors(bean:userInst, field:'secretAnswer', 'error')}" name="secretAnswer" id="secretAnswer" placeholder="${message(code: 'security.answer.label', default: 'Secret answer')}">
						</div>
					</div>
					<%--<div class="control-group">
						<div class="controls">--%>
							<label class="checkbox" for="agreement">
								<%--<input type="checkbox" value="" name="agreement" id="agreement" >--%>
								${message(code: 'security.agreement.label', default: 'By registering, I confirm I have read and agree with the Terms of Use.')}<%--
							</label>
						</div>
					</div>
				--%></div>
				<div>
					<button type="submit" id="submit-registration-button" class="btn btn-primary"><g:message code="security.register.label"/></button>
				</div>
				</g:form>
			</div>
		</section>
	</g:if>
	<g:else>
		<section id="registrationDone">
			<div class="modal-header">
				<h3><g:message code="security.register.done" default="Your account has just been created !"/></h3>
			</div>
		</section>
	</g:else>

</body>

</html>
