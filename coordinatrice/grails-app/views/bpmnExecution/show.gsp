<%@ page import="org.motrice.coordinatrice.BpmnExecution" %>
<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'bpmnExecution.label', default: 'BpmnExecution')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
  </head>
  <body>
    <a href="#show-bpmnExecution" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div id="show-bpmnExecution" class="content scaffold-show" role="main">
      <h1><g:message code="default.show.label" args="[entityName]" /></h1>
      <g:if test="${flash.message}">
	<div class="message" role="status">${flash.message}</div>
      </g:if>
      <ol class="property-list bpmnExecution">
	<li class="fieldcontain">
	  <span id="flowElementName-label" class="property-label"><g:message code="bpmnExecution.flowElementName.label" default="Flow Element" /></span>
	  <span class="property-value" aria-labelledby="flowElementName-label"><g:fieldValue bean="${bpmnExecInst}" field="flowElementName"/></span>
	</li>
	<g:if test="${bpmnExecInst?.uuid}">
	  <li class="fieldcontain">
	    <span id="uuid-label" class="property-label"><g:message code="bpmnExecution.uuid.label" default="Uuid" /></span>
	    <span class="property-value" aria-labelledby="uuid-label"><g:fieldValue bean="${bpmnExecInst}" field="uuid"/></span>
	  </li>
	</g:if>
	<g:if test="${bpmnExecInst?.activityId}">
	  <li class="fieldcontain">
	    <span id="activityId-label" class="property-label"><g:message code="bpmnExecution.activityId.label" default="Activity Id" /></span>
	    <span class="property-value" aria-labelledby="activityId-label"><g:fieldValue bean="${bpmnExecInst}" field="activityId"/></span>
	  </li>
	</g:if>
	<g:if test="${bpmnExecInst?.ended}">
	  <li class="fieldcontain">
	    <span id="ended-label" class="property-label"><g:message code="bpmnExecution.ended.label" default="Ended" /></span>
	    <span class="property-value" aria-labelledby="ended-label"><g:formatBoolean boolean="${bpmnExecInst?.ended}" /></span>
	  </li>
	</g:if>
	<g:if test="${bpmnExecInst?.parentId}">
	  <li class="fieldcontain">
	    <span id="parentId-label" class="property-label"><g:message code="bpmnExecution.parentId.label" default="Parent Id" /></span>
	    <g:link action="show" id="${bpmnExecInst.parentId}"><span class="property-value" aria-labelledby="parentId-label"><g:fieldValue bean="${bpmnExecInst}" field="parentId"/></span></g:link>
	  </li>
	</g:if>
	<g:if test="${bpmnExecInst?.processInstanceId}">
	  <li class="fieldcontain">
	    <span id="processInstanceId-label" class="property-label"><g:message code="bpmnExecution.processInstanceId.label" default="Process Instance Id" /></span>
	    <span class="property-value" aria-labelledby="processInstanceId-label"><g:fieldValue bean="${bpmnExecInst}" field="processInstanceId"/></span>
	  </li>
	</g:if>
	<g:if test="${bpmnExecInst?.suspended}">
	  <li class="fieldcontain">
	    <span id="suspended-label" class="property-label"><g:message code="bpmnExecution.suspended.label" default="Suspended" /></span>
	    <span class="property-value" aria-labelledby="suspended-label"><g:formatBoolean boolean="${bpmnExecInst?.suspended}" /></span>
	  </li>
	</g:if>
      </ol>
      <g:form>
	<fieldset class="buttons">
	  <g:hiddenField name="id" value="${bpmnExecInst?.uuid}" />
	  <g:if test="${bpmnExecInst?.processInstanceId}">
	    <g:link class="show" controller="procinst" action="show" id="${bpmnExecInst.processInstanceId}"><g:message code="default.button.list.label" default="Process Instance"/></g:link>
	  </g:if>
	  <g:actionSubmit class="zap" action="signal" value="${message(code:'bpmnExecution.signal.label')}"/>
	</fieldset>
      </g:form>
    </div>
  </body>
</html>
