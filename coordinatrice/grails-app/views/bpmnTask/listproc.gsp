<%@ page import="org.motrice.coordinatrice.BpmnTask" %>
<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'bpmnTask.label', default: 'BpmnTask')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
  </head>
  <body>
    <a href="#list-bpmnTask" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div class="nav" role="navigation">
      <ul>
	<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
	<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
      </ul>
    </div>
    <div id="list-bpmnTask" class="content scaffold-list" role="main">
      <h1><g:message code="default.list.label" args="[entityName]" /></h1>
      <g:if test="${flash.message}">
	<div class="message" role="status">${flash.message}</div>
      </g:if>
      <table>
	<thead>
	  <tr>
	    <g:sortableColumn property="name" title="${message(code: 'bpmnTask.name.label', default: 'Name')}" />
	    <g:sortableColumn property="owner" title="${message(code: 'bpmnTask.owner.label', default: 'Owner/Assignee')}" />
	    <g:sortableColumn property="priority" title="${message(code: 'bpmnTask.priority.label', default: 'Priority')}" />
	    <g:sortableColumn property="dueTime" title="${message(code: 'bpmnTask.dueTime.label', default: 'Due Time')}" />
	    <g:sortableColumn property="createdTime" title="${message(code: 'bpmnTask.createdTime.label', default: 'Created Time')}" />
	    <g:sortableColumn property="suspended" title="${message(code: 'bpmnTask.suspended.label', default: 'Active')}" />
	  </tr>
	</thead>
	<tbody>
	  <g:each in="${bpmnTaskInstList}" status="i" var="bpmnTaskInst">
	    <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
	      <td><g:link action="show" id="${bpmnTaskInst.uuid}">${fieldValue(bean: bpmnTaskInst, field: "name")}</g:link></td>
	      <td><g:ownerassignee owner="${bpmnTaskInst?.owner}" assignee="${bpmnTaskInst?.assignee}"/></td>
	      <td>${fieldValue(bean: bpmnTaskInst, field: "priority")}</td>
	      <td><g:formatDate date="${bpmnTaskInst.dueTime}" /></td>
	      <td><g:formatDate date="${bpmnTaskInst.createdTime}" /></td>
	      <g:set var="imgfile"><g:disabled flag="${bpmnTaskInst.suspended}"/></g:set>
	      <td><g:img dir="images/silk" file="${imgfile}"/></td>
	    </tr>
	  </g:each>
	</tbody>
      </table>
      <fieldset class="buttons">
	<g:hiddenField name="id" value="${procInst?.uuid}"/>
	<g:link class="show" controller="Procinst" action="show" id="${procInst?.processInstanceId}"><g:message code="default.button.list.label" default="Process Instance"/></g:link>
      </fieldset>
    </div>
  </body>
</html>
