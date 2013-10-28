<%@ page import="org.motrice.coordinatrice.pxd.PxdFormdef" %>
<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
      <g:set var="entityName" value="${message(code: 'pxdFormdef.label', default: 'PxdFormdef')}" />
      <title><g:message code="default.list.label" args="[entityName]" /></title>
  </head>
  <body>
    <a href="#list-pxdFormdef" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div class="nav" role="navigation">
      <ul>
	<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
      </ul>
    </div>
    <div id="list-pxdFormdef" class="content scaffold-list" role="main">
      <h1><g:message code="default.list.label" args="[entityName]" /></h1>
      <g:if test="${flash.message}">
	<div class="message" role="status">${flash.message}</div>
      </g:if>
      <table>
	<thead>
	  <tr>
	    <g:sortableColumn property="path" title="${message(code: 'pxdFormdef.path.label', default: 'Path')}" />
	    <g:sortableColumn property="appName" title="${message(code: 'pxdFormdef.appName.label', default: 'App Name')}" />
	    <g:sortableColumn property="formName" title="${message(code: 'pxdFormdef.formName.label', default: 'Form Name')}" />
	    <g:sortableColumn property="uuid" title="${message(code: 'pxdFormdef.uuid.label', default: 'Uuid')}" />
	  </tr>
	</thead>
	<tbody>
	  <g:each in="${pxdFormdefInstList}" status="i" var="pxdFormdefInst">
	    <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
	      <td><g:link action="show" id="${pxdFormdefInst.id}">${fieldValue(bean: pxdFormdefInst, field: "path")}</g:link></td>
	      <td>${fieldValue(bean: pxdFormdefInst, field: "appName")}</td>
	      <td>${fieldValue(bean: pxdFormdefInst, field: "formName")}</td>
	      <td><g:abbr text="${pxdFormdefInst?.uuid}"/></td>
	    </tr>
	  </g:each>
	</tbody>
      </table>
      <div class="pagination">
	<g:paginate total="${pxdFormdefInstTotal}" />
      </div>
    </div>
  </body>
</html>
