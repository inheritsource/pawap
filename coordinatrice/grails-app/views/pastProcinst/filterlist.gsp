<%@ page import="org.motrice.coordinatrice.PastProcinst" %>
<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'pastProcinst.label')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
  </head>
  <body>
    <a href="#list-pastProcinst" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div id="list-pastProcinst" class="content scaffold-list" role="main">
      <h1><g:message code="default.list.label" args="[entityName]" /></h1>
      <g:if test="${flash.message}">
	<div class="message" role="status">${flash.message}</div>
      </g:if>
      <table>
	<thead>
	  <tr>
	    <th><g:message code="pastProcinst.id.label" default="Id" /></th>
	    <th><g:message code="pastProcinst.procdef.label" default="Procdef" /></th>
	    <th><g:message code="pastProcinst.startTime.label" default="Started" /></th>
	    <th><g:message code="pastProcinst.durationFmt.label" default="Duration" /></th>
	    <th><g:message code="pastProcinst.businessKey.label" default="Bus. Key" /></th>
	    <th><g:message code="pastProcinst.startUserId.label" default="User" /></th>
	  </tr>
	</thead>
	<tbody>
	  <g:each in="${pastProcInstList}" status="i" var="pastProcInst">
	    <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
	      <td><g:link action="show" id="${pastProcInst?.uuid}">${fieldValue(bean: pastProcInst, field: "uuid")}</g:link></td>
	      <td><g:link controller="procdef" action="show" id="${pastProcInst?.procdef?.uuid}">${fieldValue(bean: pastProcInst, field: "procdef")}</g:link></td>
	      <td><g:formatDate date="${pastProcInst.startTime}" /></td>
	      <td>${fieldValue(bean: pastProcInst, field: "durationFmt")}</td>
	      <td>${fieldValue(bean: pastProcInst, field: "businessKey")}</td>
	      <td>${fieldValue(bean: pastProcInst, field: "startUserId")}</td>
	    </tr>
	  </g:each>
	</tbody>
      </table>
      <div class="pagination">
	<g:paginate total="${pastProcInstTotal}" />
      </div>
    </div>
  </body>
</html>
