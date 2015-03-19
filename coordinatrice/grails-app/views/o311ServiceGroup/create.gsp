<%@ page import="org.motrice.open311.O311ServiceGroup" %>
<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'o311ServiceGroup.label', default: 'O311ServiceGroup')}" />
    <title><g:message code="default.create.label" args="[entityName]" /></title>
  </head>
  <body>
    <a href="#create-o311ServiceGroup" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div id="create-o311ServiceGroup" class="content scaffold-create" role="main">
      <h1><g:message code="o311ServiceGroup.create.label" args="[jurisdictionInst]" /></h1>
      <g:if test="${flash.message}">
	<div class="message" role="status">${flash.message}</div>
      </g:if>
      <g:hasErrors bean="${serviceGroupInst}">
	<ul class="errors" role="alert">
	  <g:eachError bean="${serviceGroupInst}" var="error">
	    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
	  </g:eachError>
	</ul>
      </g:hasErrors>
      <g:form action="save" >
	<g:hiddenField name="jurisdiction.id" value="${jurisdictionInst?.id}" />
	<fieldset class="form">
	  <g:render template="form"/>
	</fieldset>
	<fieldset class="buttons">
	  <g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
	</fieldset>
      </g:form>
    </div>
  </body>
</html>
