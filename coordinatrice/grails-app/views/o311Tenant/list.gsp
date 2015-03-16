<%@ page import="org.motrice.open311.O311Tenant" %>
<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'o311Tenant.label', default: 'O311Tenant')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
  </head>
  <body>
    <a href="#list-o311Tenant" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div id="list-o311Tenant" class="content scaffold-list" role="main">
      <h1><g:message code="default.list.label" args="[entityName]" /></h1>
      <g:if test="${flash.message}">
	<div class="message" role="status">${flash.message}</div>
      </g:if>
      <table>
	<thead>
	  <tr>
	    <g:sortableColumn property="displayName" title="${message(code: 'o311Tenant.displayName.label', default: 'Display Name')}" />
	    <g:sortableColumn property="dateCreated" title="${message(code: 'o311.dateCreated.label', default: 'Date Created')}" />
	    <g:sortableColumn property="lastUpdated" title="${message(code: 'o311.lastUpdated.label', default: 'Last Updated')}" />
	    <g:sortableColumn property="organizationName" title="${message(code: 'o311Tenant.organizationName.label', default: 'Organization Name')}" />
	  </tr>
	</thead>
	<tbody>
	  <g:each in="${o311TenantInstList}" status="i" var="o311TenantInst">
	    <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
	      <td><g:link action="show" id="${o311TenantInst.id}">${fieldValue(bean: o311TenantInst, field: "displayName")}</g:link></td>
	      <td><g:formatDate date="${o311TenantInst.dateCreated}" /></td>
	      <td><g:formatDate date="${o311TenantInst.lastUpdated}" /></td>
	      <td>${fieldValue(bean: o311TenantInst, field: "organizationName")}</td>
	    </tr>
	  </g:each>
	</tbody>
      </table>
      <div class="pagination">
	<g:paginate total="${o311TenantInstTotal}" />
      </div>
    </div>
  </body>
</html>
