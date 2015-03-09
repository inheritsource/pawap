<%@ page import="org.motrice.coordinatrice.BpmnTask" %>
<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'bpmnTask.label', default: 'BpmnTask')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
  </head>
  <body>
    <a href="#show-bpmnTask" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div id="show-bpmnTask" class="content scaffold-show" role="main">
      <h1><g:message code="default.show.label" args="[entityName]" /></h1>
      <g:if test="${flash.message}">
	<div class="message" role="status">${flash.message}</div>
      </g:if>
      <ol class="property-list bpmnTask">
	<li class="fieldcontain">
	  <span id="name-label" class="property-label"><g:message code="bpmnTask.nameid.label" default="Name"/></span>
	  <span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${bpmnTaskInst}" field="name"/>&nbsp;&nbsp;(<g:fieldValue bean="${bpmnTaskInst}" field="uuid"/>)</span>
	</li>
	<g:if test="${bpmnTaskInst?.definitionKey}">
	  <li class="fieldcontain">
	    <span id="definitionKey-label" class="property-label"><g:message code="bpmnTask.definitionKey.label" default="Definition Key" /></span>
	    <span class="property-value" aria-labelledby="definitionKey-label"><g:fieldValue bean="${bpmnTaskInst}" field="definitionKey"/></span>
	  </li>
	</g:if>
	<li class="fieldcontain">
	  <span id="owner-label" class="property-label"><g:message code="bpmnTask.ownerassignee.label" default="Own/Ass" /></span>
	  <span class="property-value" aria-labelledby="owner-label"><g:ownerassignee owner="${bpmnTaskInst?.owner}" assignee="${bpmnTaskInst?.assignee}"/></span>
	</li>
	<g:if test="${bpmnTaskInst?.description}">
	  <li class="fieldcontain">
	    <span id="description-label" class="property-label"><g:message code="bpmnTask.description.label" default="Description" /></span>
	    <span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${bpmnTaskInst}" field="description"/></span>
	  </li>
	</g:if>
	<g:if test="${bpmnTaskInst?.assignee}">
	  <li class="fieldcontain">
	    <span id="assignee-label" class="property-label"><g:message code="bpmnTask.assignee.label" default="Assignee" /></span>
	    <span class="property-value" aria-labelledby="assignee-label"><g:fieldValue bean="${bpmnTaskInst}" field="assignee"/></span>
	  </li>
	</g:if>
	<g:if test="${bpmnTaskInst?.dueTime}">
	  <li class="fieldcontain">
	    <span id="dueTime-label" class="property-label"><g:message code="bpmnTask.dueTime.label" default="Due Time" /></span>
	    <span class="property-value" aria-labelledby="dueTime-label"><g:formatDate date="${bpmnTaskInst?.dueTime}" /></span>
	  </li>
	</g:if>
	<g:if test="${bpmnTaskInst?.createdTime}">
	  <li class="fieldcontain">
	    <span id="createdTime-label" class="property-label"><g:message code="bpmnTask.createdTime.label" default="Created Time" /></span>
	    <span class="property-value" aria-labelledby="createdTime-label"><g:formatDate date="${bpmnTaskInst?.createdTime}" /></span>
	  </li>
	</g:if>
	<g:if test="${bpmnTaskInst?.priority}">
	  <li class="fieldcontain">
	    <span id="priority-label" class="property-label"><g:message code="bpmnTask.priority.label" default="Priority" /></span>
	    <span class="property-value" aria-labelledby="priority-label"><g:fieldValue bean="${bpmnTaskInst}" field="priority"/></span>
	  </li>
	</g:if>
	<g:if test="${bpmnTaskInst?.executionId}">
	  <li class="fieldcontain">
	    <span id="executionId-label" class="property-label"><g:message code="bpmnTask.executionId.label" default="Execution Id" /></span>
	    <span class="property-value" aria-labelledby="executionId-label"><g:fieldValue bean="${bpmnTaskInst}" field="executionId"/></span>
	  </li>
	</g:if>
	<g:if test="${bpmnTaskInst?.processInstanceId}">
	  <li class="fieldcontain">
	    <span id="processInstanceId-label" class="property-label"><g:message code="bpmnTask.processInstanceId.label" default="Process Instance Id" /></span>
	    <span class="property-value" aria-labelledby="processInstanceId-label"><g:fieldValue bean="${bpmnTaskInst}" field="processInstanceId"/></span>
	  </li>
	</g:if>
	<li class="fieldcontain">
	  <span id="suspended-label" class="property-label"><g:message code="bpmnTask.suspended.label" default="Suspended" /></span>
	  <g:set var="imgfile"><g:disabled flag="${bpmnTaskInst.suspended}"/></g:set>
	  <span class="property-value" aria-labelledby="suspended-label"><g:img dir="images/silk" file="${imgfile}"/></span>
	</li>
      </ol>
      <g:form>
	<fieldset class="buttons">
	  <g:hiddenField name="id" value="${bpmnTaskInst?.id}" />
	  <g:link class="edit" action="edit" id="${bpmnTaskInst?.uuid}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
	  <g:link class="edit" action="complete" id="${bpmnTaskInst?.uuid}"><g:message code="bpmnTask.complete.label" default="Complete" /></g:link>
	  <g:if test="${bpmnTaskInst?.processInstanceId}">
	    <g:link class="show" controller="procinst" action="show" id="${bpmnTaskInst.processInstanceId}"><g:message code="default.button.list.label" default="Process Instance"/></g:link>
	  </g:if>
	</fieldset>
      </g:form>
    </div>
  </body>
</html>
