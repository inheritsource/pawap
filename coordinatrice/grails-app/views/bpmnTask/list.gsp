
<%@ page import="org.motrice.coordinatrice.BpmnTask" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
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
					
						<g:sortableColumn property="definitionKey" title="${message(code: 'bpmnTask.definitionKey.label', default: 'Definition Key')}" />
					
						<g:sortableColumn property="assignee" title="${message(code: 'bpmnTask.assignee.label', default: 'Assignee')}" />
					
						<g:sortableColumn property="createdTime" title="${message(code: 'bpmnTask.createdTime.label', default: 'Created Time')}" />
					
						<g:sortableColumn property="description" title="${message(code: 'bpmnTask.description.label', default: 'Description')}" />
					
						<g:sortableColumn property="dueTime" title="${message(code: 'bpmnTask.dueTime.label', default: 'Due Time')}" />
					
						<g:sortableColumn property="executionId" title="${message(code: 'bpmnTask.executionId.label', default: 'Execution Id')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${bpmnTaskInstList}" status="i" var="bpmnTaskInst">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${bpmnTaskInst.id}">${fieldValue(bean: bpmnTaskInst, field: "definitionKey")}</g:link></td>
					
						<td>${fieldValue(bean: bpmnTaskInst, field: "assignee")}</td>
					
						<td><g:formatDate date="${bpmnTaskInst.createdTime}" /></td>
					
						<td>${fieldValue(bean: bpmnTaskInst, field: "description")}</td>
					
						<td><g:formatDate date="${bpmnTaskInst.dueTime}" /></td>
					
						<td>${fieldValue(bean: bpmnTaskInst, field: "executionId")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${bpmnTaskInstTotal}" />
			</div>
		</div>
	</body>
</html>
