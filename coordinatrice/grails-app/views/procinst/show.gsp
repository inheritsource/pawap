<%@ page import="org.motrice.coordinatrice.Procinst" %>
<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'procinst.label', default: 'Procinst')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
  </head>
  <body>
    <a href="#show-procinst" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div id="show-procinst" class="content scaffold-show" role="main">
      <h1><g:message code="default.show.label" args="[entityName]" /></h1>
      <g:if test="${flash.message}">
	<div class="message" role="status">${flash.message}</div>
      </g:if>
      <ol class="property-list procinst">
	<g:if test="${procInst?.processInstanceId}">
	  <li class="fieldcontain">
	    <span id="processInstanceId-label" class="property-label"><g:message code="procinst.processInstanceId.label" default="Process Instance Id" /></span>
	    <span class="property-value" aria-labelledby="processInstanceId-label"><g:fieldValue bean="${procInst}" field="processInstanceId"/></span>
	  </li>
	</g:if>
	<g:if test="${procInst?.procdef}">
	  <li class="fieldcontain">
	    <span id="procdef-label" class="property-label"><g:message code="procinst.procdef.label" default="Procdef" /></span>
	    <span class="property-value" aria-labelledby="procdef-label"><g:link controller="procdef" action="show" id="${procInst?.procdef?.uuid}">${procInst?.procdef?.encodeAsHTML()}</g:link></span>
	  </li>
	</g:if>
	<g:if test="${procInst?.activityId}">
	  <li class="fieldcontain">
	    <span id="activityId-label" class="property-label"><g:message code="procinst.activityId.label" default="Activity Id" /></span>
	    <span class="property-value" aria-labelledby="activityId-label"><g:fieldValue bean="${procInst}" field="activityId"/></span>
	  </li>
	</g:if>
	<g:if test="${procInst?.flowElementName}">
	  <li class="fieldcontain">
	    <span id="flowElementName-label" class="property-label"><g:message code="procinst.flowElementName.label" default="Flow Element" /></span>
	    <span class="property-value" aria-labelledby="flowElementName-label"><g:fieldValue bean="${procInst}" field="flowElementName"/></span>
	  </li>
	</g:if>
	<g:if test="${procInst?.businessKey}">
	  <li class="fieldcontain">
	    <span id="businessKey-label" class="property-label"><g:message code="procinst.businessKey.label" default="Business Key" /></span>
	    <span class="property-value" aria-labelledby="businessKey-label"><g:fieldValue bean="${procInst}" field="businessKey"/></span>
	  </li>
	</g:if>
	<li class="fieldcontain">
	  <span id="suspended-label" class="property-label"><g:message code="procinst.suspended.label" default="Suspended" /></span>
	  <span class="property-value" aria-labelledby="suspended-label"><g:formatBoolean boolean="${procInst?.suspended}" /></span>
	</li>
	<li class="fieldcontain">
	  <span id="ended-label" class="property-label"><g:message code="procinst.ended.label" default="Ended" /></span>
	  <span class="property-value" aria-labelledby="ended-label"><g:formatBoolean boolean="${procInst?.ended}" /></span>
	</li>
	<li class="fieldcontain">
	  <span id="executions-label" class="property-label"><g:message code="procinst.executions.label" default="Executions" /></span>
	  <span class="property-value" aria-labelledby="executions-label">${execList.size()}</span>
	</li>
      </ol>
      <g:form>
	<fieldset class="buttons">
	  <g:hiddenField name="id" value="${procInst?.id}" />
	  <g:link class="edit" action="edit" id="${procInst?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
	  <g:link class="show" controller="bpmnExecution" action="list" id="${procInst?.processInstanceId}"><g:message code="default.button.list.label" default="List Executions"/></g:link>
	</fieldset>
      </g:form>
    </div>
  </body>
</html>
