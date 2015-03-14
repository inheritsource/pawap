n<%@ page import="org.motrice.open311.O311Service" %>
<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'o311Service.label', default: 'O311Service')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
  </head>
  <body>
    <a href="#list-o311Service" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div id="list-o311Service" class="content scaffold-list" role="main">
      <h1><g:message code="default.list.label" args="[entityName]" /></h1>
      <g:if test="${flash.message}">
	<div class="message" role="status">${flash.message}</div>
      </g:if>
      <table>
	<thead>
	  <tr>
	    <g:sortableColumn property="dateCreated" title="${message(code: 'o311Service.dateCreated.label', default: 'Date Created')}" />
	    <g:sortableColumn property="lastUpdated" title="${message(code: 'o311Service.lastUpdated.label', default: 'Last Updated')}" />
	    <g:sortableColumn property="code" title="${message(code: 'o311Service.code.label', default: 'Code')}" />
	    <g:sortableColumn property="name" title="${message(code: 'o311Service.name.label', default: 'Name')}" />
	    <g:sortableColumn property="description" title="${message(code: 'o311Service.description.label', default: 'Description')}" />
	  </tr>
	</thead>
	<tbody>
	  <g:each in="${o311ServiceInstList}" status="i" var="o311ServiceInst">
	    <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
	      <td><g:link action="show" id="${o311ServiceInst.id}">${fieldValue(bean: o311ServiceInst, field: "dateCreated")}</g:link></td>
	      <td><g:formatDate date="${o311ServiceInst.lastUpdated}" /></td>
	      <td>${fieldValue(bean: o311ServiceInst, field: "code")}</td>
	      <td>${fieldValue(bean: o311ServiceInst, field: "name")}</td>
	      <td>${fieldValue(bean: o311ServiceInst, field: "description")}</td>
	    </tr>
	  </g:each>
	</tbody>
      </table>
      <div class="pagination">
	<g:paginate total="${o311ServiceInstTotal}" />
      </div>
    </div>
  </body>
</html>
