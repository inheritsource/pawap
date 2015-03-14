<%@ page import="org.motrice.open311.O311ServiceGroup" %>
<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'o311ServiceGroup.label', default: 'O311ServiceGroup')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
  </head>
  <body>
    <a href="#list-o311ServiceGroup" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div id="list-o311ServiceGroup" class="content scaffold-list" role="main">
      <h1><g:message code="default.list.label" args="[entityName]" /></h1>
      <g:if test="${flash.message}">
	<div class="message" role="status">${flash.message}</div>
      </g:if>
      <table>
	<thead>
	  <tr>
	    <g:sortableColumn property="dateCreated" title="${message(code: 'o311ServiceGroup.dateCreated.label', default: 'Date Created')}" />
	    <g:sortableColumn property="lastUpdated" title="${message(code: 'o311ServiceGroup.lastUpdated.label', default: 'Last Updated')}" />
	    <g:sortableColumn property="code" title="${message(code: 'o311ServiceGroup.code.label', default: 'Code')}" />
	    <g:sortableColumn property="displayName" title="${message(code: 'o311ServiceGroup.displayName.label', default: 'Display Name')}" />
	    <th><g:message code="o311ServiceGroup.jurisdiction.label" default="Jurisdiction" /></th>
	  </tr>
	</thead>
	<tbody>
	  <g:each in="${o311ServiceGroupInstList}" status="i" var="o311ServiceGroupInst">
	    <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
	      <td><g:link action="show" id="${o311ServiceGroupInst.id}">${fieldValue(bean: o311ServiceGroupInst, field: "dateCreated")}</g:link></td>
	      <td><g:formatDate date="${o311ServiceGroupInst.lastUpdated}" /></td>
	      <td>${fieldValue(bean: o311ServiceGroupInst, field: "code")}</td>
	      <td>${fieldValue(bean: o311ServiceGroupInst, field: "displayName")}</td>
	      <td>${fieldValue(bean: o311ServiceGroupInst, field: "jurisdiction")}</td>
	    </tr>
	  </g:each>
	</tbody>
      </table>
      <div class="pagination">
	<g:paginate total="${o311ServiceGroupInstTotal}" />
      </div>
    </div>
  </body>
</html>
