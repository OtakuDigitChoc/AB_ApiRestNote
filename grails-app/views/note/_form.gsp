<%@ page import="note.Note" %>



<div class="fieldcontain ${hasErrors(bean: noteInstance, field: 'content', 'error')} required">
	<label for="content">
		<g:message code="note.content.label" default="Content" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="content" required="" value="${noteInstance?.content}"/>

</div>

