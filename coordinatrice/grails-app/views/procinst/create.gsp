<%@ page import="org.motrice.coordinatrice.Procinst" %>
<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'procinst.label', default: 'Procinst')}" />
    <title><g:message code="default.create.label" args="[entityName]" /></title>
  </head>
  <body>
    <a href="#create-procinst" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div id="create-procinst" class="content scaffold-create" role="main">
      <h1><g:message code="procdef.start.process.label"/></h1>
      <g:if test="${flash.message}">
	<div class="message" role="status">${flash.message}</div>
      </g:if>
      <g:hasErrors bean="${procinstInst}">
	<ul class="errors" role="alert">
	  <g:eachError bean="${procinstInst}" var="error">
	    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
	  </g:eachError>
	</ul>
      </g:hasErrors>
      <g:form action="createinstance" >
	<fieldset class="form">
	  <g:hiddenField name="formConnection" value="${procInst?.msfd?.id}" />
	  <g:hiddenField name="formDef" value="${procInst?.formdef?.id}" />
	  <g:render template="procinst"/>
	</fieldset>
	<fieldset class="buttons">
	  <g:submitButton name="create" class="save" value="${message(code: 'procinst.create.label')}" />
	</fieldset>
      </g:form>
    </div>
  </body>
</html>
