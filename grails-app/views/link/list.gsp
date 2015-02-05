
<%@ page import="com.goot.data.Link" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'link.label', default: 'Link')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>

<body>
	
<section id="list-link" class="first">

	<table class="table table-bordered">
		<thead>
			<tr>
			
				<th><g:message code="link.reporter.label" default="Reporter" /></th>
			
				<g:sortableColumn property="url" title="${message(code: 'link.url.label', default: 'Url')}" />
			
			</tr>
		</thead>
		<tbody>
		<g:each in="${linkInstanceList}" status="i" var="linkInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td>${linkInstance?.reporter?.email}</td>
			
				<td><g:link action="show" id="${linkInstance.id}">${fieldValue(bean: linkInstance, field: "url")}</g:link></td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<bs:paginate total="${linkInstanceTotal}" />
	</div>
</section>

</body>

</html>
