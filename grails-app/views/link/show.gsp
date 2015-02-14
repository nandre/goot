
<%@ page import="com.gliiim.data.Link" %>
<!doctype html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'link.label', default: 'Link')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>

<body>

<section id="show-link" class="first">

	<table class="table">
		<tbody>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="link.reporter.label" default="Reporter" /></td>
				
				<td valign="top" class="value"><g:link controller="user" action="show" id="${linkInstance?.reporter?.id}">${linkInstance?.reporter?.encodeAsHTML()}</g:link></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="link.comments.label" default="Comments" /></td>
				
				<td valign="top" style="text-align: left;" class="value">
					<ul>
					<g:each in="${linkInstance.comments}" var="c">
						<li><g:link controller="comment" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></li>
					</g:each>
					</ul>
				</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="link.url.label" default="Url" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: linkInstance, field: "url")}</td>
				
			</tr>
		
		</tbody>
	</table>
</section>

</body>

</html>
