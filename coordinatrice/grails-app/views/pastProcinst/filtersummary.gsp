<%@ page import="org.motrice.coordinatrice.PastProcinst" %>
<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'pastProcinst.label')}" />
    <title><g:message code="pastProcinst.summary.label"/></title>
  </head>
  <body>
    <a href="#list-pastProcinst" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div id="list-pastProcinst" class="content scaffold-list" role="main">
      <h1><g:message code="pastProcinst.summary.label"/></h1>
      <g:if test="${flash.message}">
	<div class="message" role="status">${flash.message}</div>
      </g:if>
      <table>
	<thead>
	  <tr>
	    <th><g:message code="pastProcinst.procdef.label" default="Procdef" /></th>
	    <th><g:message code="pastProcinst.finished.count.label" default="Finished Count" /></th>
	    <th><g:message code="pastProcinst.durationFmt.average.label" default="Duration" /></th>
	    <th><g:message code="pastProcinst.unfinished.count.label" default="Bus. Key" /></th>
	  </tr>
	</thead>
	<tbody>
	  <g:each in="${pastProcInstList}" status="i" var="pastProcInst">
	    <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
	      <td><g:link controller="procdef" action="show" id="${pastProcInst?.procdef?.uuid}">${fieldValue(bean: pastProcInst, field: "procdef")}</g:link></td>
	      <td>${fieldValue(bean: pastProcInst, field: "finishedCount")}</td>
	      <td>${fieldValue(bean: pastProcInst, field: "durationFmt")}</td>
	      <td>${fieldValue(bean: pastProcInst, field: "unfinishedCount")}</td>
	    </tr>
	  </g:each>
	</tbody>
      </table>
      <g:form action="filterlist">
	<fieldset class="buttons">
	  <g:hiddenField name="filterId" value="${filterId}" />
	  <g:submitButton name="details" class="show" value="${message(code: 'pastProcinst.list.details.label', default: 'List')}" />
	  <g:link class="edit" action="filterdef"><g:message code="pastProcinst.query.menu"/></g:link>
	</fieldset>
      </g:form>
    </div>
  </body>
</html>
