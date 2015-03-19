<%@ page import="org.motrice.open311.O311Jurisdiction" %>
<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'o311Jurisdiction.label', default: 'O311Jurisdiction')}" />
    <title><g:message code="o311Service.list.menu"/></title>
  </head>
  <body>
    <a href="#list-o311Jurisdiction" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <g:form>
      <div id="list-o311Jurisdiction" class="content scaffold-list" role="main">
	<h1><g:message code="o311Jurisdiction.services.label" args="[jurisdictionInst]" /></h1>
	<g:if test="${flash.message}">
	  <div class="message" role="status">${flash.message}</div>
	</g:if>
	<table>
	  <thead>
	      <th><g:message code="o311Jurisdiction.include.service.label" default="Service"/></th>
	      <th><g:message code="o311Jurisdiction.in.service.group.label" default="Service Group"/></th>
	  </thead>
	  <tbody>
	    <g:set var="noselect"><g:message code="o311ServiceGroup.no.selection.label" default="No Selection"/></g:set>
	    <g:each in="${serviceList}" status="i" var="serviceMap">
	      <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
		<g:set var="service" value="${serviceMap.service}"/>
		<g:set var="checkboxName" value="${servicePrefix}${service?.id}"/>
		<g:set var="dropdownName" value="${serviceGroupPrefix}${service?.id}"/>
		<td><g:checkBox name="${checkboxName}" value="${serviceMap.included}"/>&nbsp;${serviceMap.service}</td>
		<td><g:select name="${dropdownName}" from="${serviceGroupList}" optionKey="id" value="${serviceMap.serviceGroup?.id}"
			      noSelection="[null:noselect]"/></td>
	      </tr>
	    </g:each>
	  </tbody>
	</table>
	<fieldset class="buttons">
	  <g:hiddenField name="id" value="${jurisdictionInst?.id}" />
	  <g:actionSubmit class="save" action="updateservices" value="${message(code: 'default.button.update.label', default: 'Update')}" />
	  <g:link class="show" action="show" id="${jurisdictionInst?.id}">${jurisdictionInst?.encodeAsHTML()}</g:link>
	  <g:link class="show" controller="o311ServiceGroup" action="list" id="${jurisdictionInst?.id}"><g:message code="o311ServiceGroup.list.menu" default="List" /></g:link>
	</fieldset>
      </div>
    </g:form>
  </body>
</html>
