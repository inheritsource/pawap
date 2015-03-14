<%@ page import="org.motrice.open311.O311Tenant" %>
<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'o311Tenant.label', default: 'O311Tenant')}" />
    <title><g:message code="default.edit.label" args="[entityName]" /></title>
  </head>
  <body>
    <a href="#edit-o311Tenant" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div id="edit-o311Tenant" class="content scaffold-edit" role="main">
      <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
      <g:if test="${flash.message}">
	<div class="message" role="status">${flash.message}</div>
      </g:if>
      <g:hasErrors bean="${o311TenantInst}">
	<ul class="errors" role="alert">
	  <g:eachError bean="${o311TenantInst}" var="error">
	    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
	  </g:eachError>
	</ul>
      </g:hasErrors>
      <g:form method="post" >
	<g:hiddenField name="id" value="${o311TenantInst?.id}" />
	<g:hiddenField name="version" value="${o311TenantInst?.version}" />
	<fieldset class="form">
	  <g:render template="form"/>
	</fieldset>
	<fieldset class="buttons">
	  <g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
	  <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" formnovalidate="" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
	</fieldset>
      </g:form>
    </div>
  </body>
</html>
