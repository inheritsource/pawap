<%@ page import="org.motrice.open311.O311Tenant" %>
<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'o311Jurisdiction.label', default: 'O311Jurisdiction')}" />
    <title><g:message code="default.list.label" args="[entityName]"/></title>
  </head>
  <body>
    <a href="#list-o311Jurisdiction" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <g:form>
      <div id="list-o311Jurisdiction" class="content scaffold-list" role="main">
	<h1><g:message code="o311Tenant.jurisdictions.label" args="[tenantInst]" /></h1>
	<g:if test="${flash.message}">
	  <div class="message" role="status">${flash.message}</div>
	</g:if>
	<table>
	  <thead>
	    <tr>
	      <th><g:message code="o311Tenant.allow.label"/></th>
	      <g:sortableColumn property="jurisdictionId" title="${message(code: 'o311Jurisdiction.jurisdictionId.label', default: 'Jurisdiction Id')}" />
	      <g:sortableColumn property="fullName" title="${message(code: 'o311Jurisdiction.fullName.label', default: 'Full Name')}" />
	      <g:sortableColumn property="enabledFlag" title="${message(code: 'o311Jurisdiction.enabledFlag.label', default: 'Enabled Flag')}" />
	      <g:sortableColumn property="procdefUuid" title="${message(code: 'o311Jurisdiction.procdefUuid.label')}" />
	    </tr>
	  </thead>
	  <tbody>
	    <g:each in="${jurisdictionSelectionList}" status="i" var="selectionObj">
	      <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
		<g:set var="jurisdictionInst" value="${selectionObj.jurisdiction}"/>
		<td><g:checkBox name="${checkboxPrefix}${jurisdictionInst.id}" value="${selectionObj.allowed}"/></td>
		<td><g:link action="show" id="${selectionObj.id}">${fieldValue(bean: jurisdictionInst, field: "jurisdictionId")}</g:link></td>
		<td>${fieldValue(bean: jurisdictionInst, field: "fullName")}</td>
		<g:set var="iconimg"><g:enabled flag="${jurisdictionInst.enabledFlag}"/></g:set>
		<td><g:img dir="images/silk" file="${iconimg}"/></td>
		<td>${fieldValue(bean: jurisdictionInst, field: "procdefDisplay")}</td>
	      </tr>
	    </g:each>
	  </tbody>
	</table>
	<fieldset class="buttons">
	  <g:hiddenField name="id" value="${tenantInst?.id}" />
	  <g:actionSubmit class="save" action="updatejurisd" value="${message(code: 'default.button.update.label', default: 'Update')}" />
	  <g:link class="show" action="show" id="${tenantInst?.id}">${tenantInst?.encodeAsHTML()}</g:link>
	</fieldset>
      </div>
    </g:form>
  </body>
</html>
