
<%@ page import="com.gliiim.data.Comment" %>
<%@ page import="com.gliiim.utils.CommentType" %>
<!doctype html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'comment.label', default: 'Comment')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>

<body>

<section id="show-comment" class="first">

	<table class="table">
		<tbody>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="comment.type.label" default="Type" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: commentInstance, field: "type")}</td>
				
			</tr>

			
			<g:if test="${commentInstance.type ==  CommentType.IMAGE.getCode()}">
				<tr class="prop">
					<td valign="top" class="name"><g:message code="comment.preview.label" default="Preview" /></td>
					
					<td valign="top" class="value"><img style="width : 100px;" src="${fieldValue(bean: commentInstance, field: "content")}"><img></td>
					
				</tr>
			</g:if>
			<g:else>
				<tr class="prop">
					<td valign="top" class="name"><g:message code="comment.content.label" default="Content" /></td>
					
					<td valign="top" class="value">${fieldValue(bean: commentInstance, field: "content")}</td>	
				</tr>
			</g:else>
		
		</tbody>
	</table>
</section>

</body>

</html>
