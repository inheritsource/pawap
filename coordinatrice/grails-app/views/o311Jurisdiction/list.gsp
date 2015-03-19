<%@ page import="org.motrice.open311.O311Jurisdiction" %>
<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'o311Jurisdiction.label', default: 'O311Jurisdiction')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
  </head>
  <body>
    <a href="#list-o311Jurisdiction" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div id="list-o311Jurisdiction" class="content scaffold-list" role="main">
      <h1><g:message code="default.list.label" args="[entityName]" /></h1>
      <g:if test="${flash.message}">
	<div class="message" role="status">${flash.message}</div>
      </g:if>
      <table>
	<thead>
	  <tr>
	    <g:sortableColumn property="jurisdictionId" title="${message(code: 'o311Jurisdiction.jurisdictionId.label', default: 'Jurisdiction Id')}" />
	    <g:sortableColumn property="fullName" title="${message(code: 'o311Jurisdiction.fullName.label', default: 'Full Name')}" />
	    <g:sortableColumn property="enabledFlag" title="${message(code: 'o311Jurisdiction.enabledFlag.label', default: 'Enabled Flag')}" />
	    <g:sortableColumn property="procdefUuid" title="${message(code: 'o311Jurisdiction.procdefUuid.label')}" />
	    <g:sortableColumn property="lastUpdated" title="${message(code: 'o311.lastUpdated.label', default: 'Last Updated')}" />
	  </tr>
	</thead>
	<tbody>
	  <g:each in="${o311JurisdictionInstList}" status="i" var="o311JurisdictionInst">
	    <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
	      <td><g:link action="show" id="${o311JurisdictionInst.id}">${fieldValue(bean: o311JurisdictionInst, field: "jurisdictionId")}</g:link></td>
	      <td>${fieldValue(bean: o311JurisdictionInst, field: "fullName")}</td>
	      <g:set var="iconimg"><g:enabled flag="${o311JurisdictionInst.enabledFlag}"/></g:set>
	      <td><g:img dir="images/silk" file="${iconimg}"/></td>
	      <td>${fieldValue(bean: o311JurisdictionInst, field: "procdefDisplay")}</td>
	      <td><g:formatDate date="${o311JurisdictionInst.lastUpdated}" /></td>
	    </tr>
	  </g:each>
	</tbody>
      </table>
      <div class="pagination">
	<g:paginate total="${o311JurisdictionInstTotal}" />
      </div>
    </div>
  </body>
</html>
