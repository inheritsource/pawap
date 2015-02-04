<%@ page import="org.motrice.coordinatrice.CrdFormMap" %>
<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main">
      <g:set var="entityName" value="${message(code: 'crdFormMap.label', default: 'CrdFormMap')}" />
      <title><g:message code="default.show.label" args="[entityName]" /></title>
  </head>
  <body>
    <a href="#show-crdFormMap" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div id="show-crdFormMap" class="content scaffold-show" role="main">
      <h1><g:message code="crdFormMap.label"/>: ${formDefTitle}</h1>
      <g:if test="${flash.message}">
	<div class="message" role="status">${flash.message}</div>
      </g:if>
	<fieldset class="form">
	  <table>
	    <thead>
	      <tr>
		<th><g:message code="crdFormMap.section.label"/></th><th><g:message code="crdFormMap.control.label"/></th><th><g:message code="crdFormMap.variable.label"/></th>
	      </tr>
	    </thead>
	    <tbody>
	      <g:each in="${crdFormMapList}" status="i" var="tup">
		<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
		  <td>${tup.section}</td><td>${tup.control}</td><td><g:nullval value="${tup.variable}"/></td>
		</tr>
	      </g:each>
	    </tbody>
	  </table>
	</fieldset>
      <g:form>
	<fieldset class="buttons">
	  <g:hiddenField name="id" value="${crdFormMapInst?.id}" />
	  <g:link class="edit" action="createlist" id="${crdFormMapInst?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
	  <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
	  <g:link class="show" controller="pxdFormdefVer" action="show" id="${crdFormMapInst?.id}"><g:message code="crdFormMap.show.form.label"/></g:link>
	</fieldset>
      </g:form>
    </div>
  </body>
</html>
