<%@ page import="org.motrice.coordinatrice.Procinst" %>
<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
      <g:set var="entityName" value="${message(code: 'procinst.label', default: 'Procinst')}" />
      <title><g:message code="default.list.label" args="[entityName]" /></title>
  </head>
  <body>
    <a href="#list-procinst" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div id="list-procinst" class="content scaffold-list" role="main">
      <h1><g:message code="procinst.list.title"/></h1>
      <g:if test="${flash.message}">
	<div class="message" role="status">${flash.message}</div>
      </g:if>
      <table>
	<thead>
	  <tr>
	    <g:sortableColumn property="processInstanceId" title="${message(code: 'procinst.processInstanceId.label')}" />
	    <th><g:message code="procinst.procdef.label" default="Procdef" /></th>
	    <g:sortableColumn property="activityId" title="${message(code: 'procinst.activityId.label')}" />
	    <g:sortableColumn property="businessKey" title="${message(code: 'procinst.businessKey.label', default: 'Business Key')}" />
	    <g:sortableColumn property="suspended" title="${message(code: 'procinst.suspended.label')}" />
	    <g:sortableColumn property="ended" title="${message(code: 'procinst.ended.label', default: 'Cont')}" />
	  </tr>
	</thead>
	<tbody>
	  <g:each in="${procInstList}" status="i" var="procInst">
	    <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
	      <td><g:link action="show" id="${procInst?.processInstanceId}">${fieldValue(bean: procInst, field: "processInstanceId")}</g:link></td>
	      <td><g:link controller="procdef" action="show" id="${procInst?.procdef?.uuid}">${fieldValue(bean: procInst, field: "procdef")}</g:link></td>
	      <td>${procInst?.flowElementName}</td>
	      <td>${fieldValue(bean: procInst, field: "businessKey")}</td>
	      <g:set var="imgfile"><g:disabled flag="${procInst.suspended}"/></g:set>
	      <td><g:img dir="images/silk" file="${imgfile}"/></td>
	      <g:set var="imgfile"><g:disabled flag="${procInst.ended}"/></g:set>
	      <td><g:img dir="images/silk" file="${imgfile}"/></td>
	    </tr>
	  </g:each>
	</tbody>
      </table>
    </div>
  </body>
</html>
