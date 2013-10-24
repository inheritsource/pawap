
<%@ page import="org.motrice.coordinatrice.bonita.BnProcDef" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'bnProcDef.label', default: 'BnProcDef')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-bnProcDef" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-bnProcDef" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="uuid" title="${message(code: 'bnProcDef.uuid.label', default: 'Uuid')}" />
					
						<g:sortableColumn property="state" title="${message(code: 'bnProcDef.state.label', default: 'State')}" />
					
						<g:sortableColumn property="name" title="${message(code: 'bnProcDef.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="label" title="${message(code: 'bnProcDef.label.label', default: 'Label')}" />
					
						<g:sortableColumn property="vno" title="${message(code: 'bnProcDef.vno.label', default: 'Vno')}" />
					
						<g:sortableColumn property="type" title="${message(code: 'bnProcDef.type.label', default: 'Type')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${bnProcDefInstList}" status="i" var="bnProcDefInst">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${bnProcDefInst.id}">${fieldValue(bean: bnProcDefInst, field: "uuid")}</g:link></td>
					
						<td>${fieldValue(bean: bnProcDefInst, field: "state")}</td>
					
						<td>${fieldValue(bean: bnProcDefInst, field: "name")}</td>
					
						<td>${fieldValue(bean: bnProcDefInst, field: "label")}</td>
					
						<td>${fieldValue(bean: bnProcDefInst, field: "vno")}</td>
					
						<td>${fieldValue(bean: bnProcDefInst, field: "type")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${bnProcDefInstTotal}" />
			</div>
		</div>
	</body>
</html>
