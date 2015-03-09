<%@ page import="org.motrice.coordinatrice.PastProcinst" %>
<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'pastProcinst.label', default: 'PastProcinst')}" />
    <title><g:message code="pastProcinst.query.label"/></title>
  </head>
  <body>
    <a href="#create-pastProcinst" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div id="create-pastProcinst" class="content scaffold-create" role="main">
      <h1><g:message code="pastProcinst.query.label"/></h1>
      <g:if test="${flash.message}">
	<div class="message" role="status">${flash.message}</div>
      </g:if>
      <g:hasErrors bean="${pastProcInst}">
	<ul class="errors" role="alert">
	  <g:eachError bean="${pastProcInst}" var="error">
	    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
	  </g:eachError>
	</ul>
      </g:hasErrors>
      <g:form action="filterlist" >
	<fieldset class="form">
	  <g:render template="filter"/>
	</fieldset>
	<fieldset class="buttons">
	  <g:submitButton name="create" class="save" value="${message(code: 'pastProcinst.list.details.label', default: 'Create')}" />
	</fieldset>
      </g:form>
    </div>
  </body>
</html>
