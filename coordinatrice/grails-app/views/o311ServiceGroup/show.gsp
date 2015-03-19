<%@ page import="org.motrice.open311.O311ServiceGroup" %>
<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'o311ServiceGroup.label', default: 'O311ServiceGroup')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
  </head>
  <body>
    <a href="#show-o311ServiceGroup" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div id="show-o311ServiceGroup" class="content scaffold-show" role="main">
      <g:set var="jurisdDisplay">${serviceGroupInst?.jurisdiction}</g:set>
      <h1><g:message code="o311ServiceGroup.show.label" args="[jurisdDisplay]" /></h1>
      <g:if test="${flash.message}">
	<div class="message" role="status">${flash.message}</div>
      </g:if>
      <ol class="property-list o311ServiceGroup">
	<g:if test="${serviceGroupInst?.code}">
	  <li class="fieldcontain">
	    <span id="code-label" class="property-label"><g:message code="o311ServiceGroup.code.label" default="Code" /></span>
	    <span class="property-value" aria-labelledby="code-label"><g:fieldValue bean="${serviceGroupInst}" field="code"/></span>
	  </li>
	</g:if>
	<g:if test="${serviceGroupInst?.displayName}">
	  <li class="fieldcontain">
	    <span id="displayName-label" class="property-label"><g:message code="o311ServiceGroup.displayName.label" default="Display Name" /></span>
	    <span class="property-value" aria-labelledby="displayName-label"><g:fieldValue bean="${serviceGroupInst}" field="displayName"/></span>
	  </li>
	</g:if>
	<g:if test="${serviceGroupInst?.jurisdiction}">
	  <li class="fieldcontain">
	    <span id="jurisdiction-label" class="property-label"><g:message code="o311ServiceGroup.jurisdiction.label" default="Jurisdiction" /></span>
	    <span class="property-value" aria-labelledby="jurisdiction-label"><g:link controller="o311Jurisdiction" action="show" id="${serviceGroupInst?.jurisdiction?.id}">${jurisdDisplay}</g:link></span>
	  </li>
	</g:if>
	<g:if test="${serviceGroupInst?.dateCreated}">
	  <li class="fieldcontain">
	    <span id="dateCreated-label" class="property-label"><g:message code="o311.created.updated.label" default="Date Created"/></span>
	    <span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${serviceGroupInst?.dateCreated}"/>&nbsp;/&nbsp;<g:formatDate date="${serviceGroupInst?.lastUpdated}"/></span>
	  </li>
	</g:if>
	<g:if test="${serviceList}">
	  <li class="fieldcontain">
	    <span id="services-label" class="property-label"><g:message code="o311ServiceGroup.services.label" default="Services"/></span>
	    <g:each in="${serviceList}" var="s">
	      <span class="property-value" aria-labelledby="services-label"><g:link controller="o311Service" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></span>
	    </g:each>
	  </li>
	</g:if>
      </ol>
      <g:form>
	<fieldset class="buttons">
	  <g:hiddenField name="id" value="${serviceGroupInst?.id}" />
	  <g:link class="edit" action="edit" id="${serviceGroupInst?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
	  <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
	  <g:link class="show" action="list" id="${serviceGroupInst?.jurisdiction?.id}"><g:message code="o311ServiceGroup.list.menu" default="List" /></g:link>
	</fieldset>
      </g:form>
    </div>
  </body>
</html>
