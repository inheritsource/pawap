<%@ page import="org.motrice.migratrice.MigItem" %>
<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
      <g:set var="entityName" value="${message(code: 'migItem.label', default: 'MigItem')}" />
      <title><g:message code="default.create.label" args="[entityName]" /></title>
  </head>
  <body>
    <a href="#create-migItem" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div id="create-migItem" class="content scaffold-create" role="main">
      <h1><g:message code="default.create.label" args="[entityName]" /></h1>
      <g:if test="${flash.message}">
	<div class="message" role="status">${flash.message}</div>
      </g:if>
      <g:hasErrors bean="${migItemInst}">
	<ul class="errors" role="alert">
	  <g:eachError bean="${migItemInst}" var="error">
	    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
	  </g:eachError>
	</ul>
      </g:hasErrors>
      <g:form action="save"  enctype="multipart/form-data">
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
