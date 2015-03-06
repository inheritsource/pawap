<%@ page import="org.motrice.coordinatrice.BpmnTask" %>
<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
      <g:set var="entityName" value="${message(code: 'bpmnTask.label', default: 'BpmnTask')}" />
      <title><g:message code="bpmnTask.complete.label" args="[entityName]" /></title>
  </head>
  <body>
    <a href="#edit-bpmnTask" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div id="edit-bpmnTask" class="content scaffold-edit" role="main">
      <h1><g:message code="bpmnTask.complete.label" args="[entityName]" /></h1>
      <g:if test="${flash.message}">
	<div class="message" role="status">${flash.message}</div>
      </g:if>
      <g:hasErrors bean="${bpmnTaskInst}">
	<ul class="errors" role="alert">
	  <g:eachError bean="${bpmnTaskInst}" var="error">
	    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
	  </g:eachError>
	</ul>
      </g:hasErrors>
      <g:form method="post" >
	<g:hiddenField name="id" value="${bpmnTaskInst?.uuid}" />
	<g:hiddenField name="formConnection" value="${bpmnTaskInst?.mafd?.id}" />
	<g:hiddenField name="formDef" value="${bpmnTaskInst?.formdef?.id}" />
	<fieldset class="form">
	  <g:render template="taskinst"/>
	</fieldset>
	<fieldset class="buttons">
	  <g:actionSubmit class="zap" action="completeAction" value="${message(code: 'bpmnTask.complete.label')}" />
	  <g:link class="show" action="show" id="${bpmnTaskInst?.uuid}"><g:message code="bpmnTask.show.label" default="Task"/></g:link>
	</fieldset>
      </g:form>
    </div>
  </body>
</html>
