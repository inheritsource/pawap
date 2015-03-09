<%@ page import="org.motrice.coordinatrice.PastProcinst" %>
<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'pastProcinst.label', default: 'PastProcinst')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
  </head>
  <body>
    <a href="#list-pastProcinst" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div class="nav" role="navigation">
      <ul>
	<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
	<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
      </ul>
    </div>
    <div id="list-pastProcinst" class="content scaffold-list" role="main">
      <h1><g:message code="default.list.label" args="[entityName]" /></h1>
      <g:if test="${flash.message}">
	<div class="message" role="status">${flash.message}</div>
      </g:if>
      <table>
	<thead>
	  <tr>
	    <th><g:message code="pastProcinst.procdef.label" default="Procdef" /></th>
	    <g:sortableColumn property="businessKey" title="${message(code: 'pastProcinst.businessKey.label', default: 'Business Key')}" />
	    <g:sortableColumn property="deleteReason" title="${message(code: 'pastProcinst.deleteReason.label', default: 'Delete Reason')}" />
	    <g:sortableColumn property="endTime" title="${message(code: 'pastProcinst.endTime.label', default: 'End Time')}" />
	    <g:sortableColumn property="startActivityId" title="${message(code: 'pastProcinst.startActivityId.label', default: 'Start Activity Id')}" />
	    <g:sortableColumn property="startTime" title="${message(code: 'pastProcinst.startTime.label', default: 'Start Time')}" />
	  </tr>
	</thead>
	<tbody>
	  <g:each in="${pastProcInstList}" status="i" var="pastProcInst">
	    <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
	      <td><g:link action="show" id="${pastProcInst.id}">${fieldValue(bean: pastProcInst, field: "procdef")}</g:link></td>
	      <td>${fieldValue(bean: pastProcInst, field: "businessKey")}</td>
	      <td>${fieldValue(bean: pastProcInst, field: "deleteReason")}</td>
	      <td><g:formatDate date="${pastProcInst.endTime}" /></td>
	      <td>${fieldValue(bean: pastProcInst, field: "startActivityId")}</td>
	      <td><g:formatDate date="${pastProcInst.startTime}" /></td>
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
