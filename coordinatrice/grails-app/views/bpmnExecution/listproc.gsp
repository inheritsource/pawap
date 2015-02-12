<%@ page import="org.motrice.coordinatrice.BpmnExecution" %>
<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'bpmnExecution.label', default: 'BpmnExecution')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
  </head>
  <body>
    <a href="#list-bpmnExecution" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div id="list-bpmnExecution" class="content scaffold-list" role="main">
      <h1><g:message code="bpmnExecution.listproc.label" args="[procInst?.procdef, procInst?.processInstanceId]"/></h1>
      <g:if test="${flash.message}">
	<div class="message" role="status">${flash.message}</div>
      </g:if>
      <table>
	<thead>
	  <tr>
	    <g:sortableColumn property="label" title="${message(code: 'bpmnExecution.label.label', default: 'Execution')}" />
	    <g:sortableColumn property="activityId" title="${message(code: 'bpmnExecution.activityId.label', default: 'Activity')}" />
	    <g:sortableColumn property="suspended" title="${message(code: 'bpmnExecution.suspended.label', default: 'Active')}" />
	    <g:sortableColumn property="ended" title="${message(code: 'bpmnExecution.ended.label', default: 'Cont')}" />
	  </tr>
	</thead>
	<tbody>
	  <g:each in="${bpmnExecInstList}" status="i" var="bpmnExecInst">
	    <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
	      <td><g:link action="show" id="${bpmnExecInst.uuid}">${fieldValue(bean: bpmnExecInst, field: "label")}</g:link></td>
	      <td>${fieldValue(bean: bpmnExecInst, field: "flowElementName")}</td>
	      <g:set var="imgfile"><g:disabled flag="${bpmnExecInst.suspended}"/></g:set>
	      <td><g:img dir="images/silk" file="${imgfile}"/></td>
	      <g:set var="imgfile"><g:disabled flag="${bpmnExecInst.ended}"/></g:set>
	      <td><g:img dir="images/silk" file="${imgfile}"/></td>
	    </tr>
	  </g:each>
	</tbody>
      </table>
    </div>
    <fieldset class="buttons">
      <g:hiddenField name="id" value="${procInst?.uuid}"/>
      <g:link class="show" controller="Procinst" action="show" id="${procInst?.processInstanceId}"><g:message code="default.button.list.label" default="Process Instance"/></g:link>
    </fieldset>
  </body>
</html>
