
<%@ page import="com.goot.data.Comment" %>
<%@ page import="com.goot.utils.CommentType" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'comment.label', default: 'Comment')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>

<body>
	
<section id="list-comment" class="first">

	<table class="table table-bordered">
		<thead>
			<tr>
			
				<g:sortableColumn property="type" title="${message(code: 'comment.type.label', default: 'Type')}" />
				
				<g:sortableColumn property="content" title="${message(code: 'comment.content.label', default: 'Content')}" />
			
			</tr>
		</thead>
		<tbody>
		<g:each in="${commentInstanceList}" status="i" var="commentInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td><g:link action="show" id="${commentInstance.id}">${fieldValue(bean: commentInstance, field: "type")}</g:link></td>
			
				
				<g:if test="${commentInstance.type ==  CommentType.IMAGE.getCode()}">
					<td><img style="width : 50px;" src="${fieldValue(bean: commentInstance, field: "content")}" ></img></td>
				</g:if>
				<g:else>
					<td>${fieldValue(bean: commentInstance, field: "content")}</td>
				</g:else>
			
			</tr>
		</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<bs:paginate total="${commentInstanceTotal}" />
	</div>
</section>

</body>

</html>
