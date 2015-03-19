<%@ page import="org.motrice.open311.O311ServiceGroup" %>
<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'o311ServiceGroup.label', default: 'O311ServiceGroup')}" />
    <g:set var="jurisdName">${jurisdictionInst?.fullName}</g:set>
    <title><g:message code="default.list.label" args="[entityName]" /></title>
  </head>
  <body>
    <a href="#list-o311ServiceGroup" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div class="nav" role="navigation">
      <ul>
	<li><g:link class="list" controller="o311Jurisdiction" action="show" id="${jurisdictionInst?.id}">${jurisdName}</g:link></li>
	<li><g:link class="create" action="create" id="${jurisdictionInst?.id}"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
      </ul>
    </div>
    <div id="list-o311ServiceGroup" class="content scaffold-list" role="main">
      <h1><g:message code="o311ServiceGroup.list.label" args="[jurisdName]" /></h1>
      <g:if test="${flash.message}">
	<div class="message" role="status">${flash.message}</div>
      </g:if>
      <table>
	<thead>
	  <tr>
	    <g:sortableColumn property="displayName" title="${message(code: 'o311ServiceGroup.displayName.label', default: 'Display Name')}" />
	    <g:sortableColumn property="code" title="${message(code: 'o311ServiceGroup.code.label', default: 'Code')}" />
	    <g:sortableColumn property="dateCreated" title="${message(code: 'o311ServiceGroup.dateCreated.label', default: 'Date Created')}" />
	    <g:sortableColumn property="lastUpdated" title="${message(code: 'o311ServiceGroup.lastUpdated.label', default: 'Last Updated')}" />
	  </tr>
	</thead>
	<tbody>
	  <g:each in="${serviceGroupInstList}" status="i" var="serviceGroupInst">
	    <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
	      <td><g:link action="show" id="${serviceGroupInst.id}">${fieldValue(bean: serviceGroupInst, field: "displayName")}</g:link></td>
	      <td>${fieldValue(bean: serviceGroupInst, field: "code")}</td>
	      <td>${fieldValue(bean: serviceGroupInst, field: "dateCreated")}</td>
	      <td><g:formatDate date="${serviceGroupInst.lastUpdated}" /></td>
	    </tr>
	  </g:each>
	</tbody>
      </table>
      <div class="pagination">
	<g:paginate total="${serviceGroupInstTotal}"/>
      </div>
    </div>
  </body>
</html>
