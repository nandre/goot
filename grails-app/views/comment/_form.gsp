<%@ page import="com.goot.data.Comment" %>



			<div class="control-group fieldcontain ${hasErrors(bean: commentInstance, field: 'type', 'error')} required">
				<label for="type" class="control-label"><g:message code="comment.type.label" default="Type" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<g:field type="number" name="type" required="" value="${commentInstance.type}"/>
					<span class="help-inline">${hasErrors(bean: commentInstance, field: 'type', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: commentInstance, field: 'content', 'error')} ">
				<label for="content" class="control-label"><g:message code="comment.content.label" default="Content" /></label>
				<div class="controls">
					<g:textField name="content" value="${commentInstance?.content}"/>
					<span class="help-inline">${hasErrors(bean: commentInstance, field: 'content', 'error')}</span>
				</div>
			</div>

