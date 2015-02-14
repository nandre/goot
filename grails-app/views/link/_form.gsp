<%@ page import="com.gliiim.data.Link" %>



			<div class="control-group fieldcontain ${hasErrors(bean: linkInstance, field: 'reporter', 'error')} required">
				<label for="reporter" class="control-label"><g:message code="link.reporter.label" default="Reporter" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<g:select id="reporter" name="reporter.id" from="${com.gliiim.User.list()}" optionKey="id" required="" value="${linkInstance?.reporter?.id}" class="many-to-one"/>
					<span class="help-inline">${hasErrors(bean: linkInstance, field: 'reporter', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: linkInstance, field: 'comments', 'error')} ">
				<label for="comments" class="control-label"><g:message code="link.comments.label" default="Comments" /></label>
				<div class="controls">
					<g:select name="comments" from="${com.gliiim.data.Comment.list()}" multiple="multiple" optionKey="id" size="5" value="${linkInstance?.comments*.id}" class="many-to-many"/>
					<span class="help-inline">${hasErrors(bean: linkInstance, field: 'comments', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: linkInstance, field: 'url', 'error')} ">
				<label for="url" class="control-label"><g:message code="link.url.label" default="Url" /></label>
				<div class="controls">
					<g:textField name="url" value="${linkInstance?.url}"/>
					<span class="help-inline">${hasErrors(bean: linkInstance, field: 'url', 'error')}</span>
				</div>
			</div>

