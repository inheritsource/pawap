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
      <h1><g:message code="default.show.label" args="[entityName]" /></h1>
      <g:if test="${flash.message}">
	<div class="message" role="status">${flash.message}</div>
      </g:if>
      <ol class="property-list o311ServiceGroup">
	<g:if test="${o311ServiceGroupInst?.dateCreated}">
	  <li class="fieldcontain">
	    <span id="dateCreated-label" class="property-label"><g:message code="o311ServiceGroup.dateCreated.label" default="Date Created" /></span>
	    <span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${o311ServiceGroupInst?.dateCreated}" /></span>
	  </li>
	</g:if>
	<g:if test="${o311ServiceGroupInst?.lastUpdated}">
	  <li class="fieldcontain">
	    <span id="lastUpdated-label" class="property-label"><g:message code="o311ServiceGroup.lastUpdated.label" default="Last Updated" /></span>
	    <span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${o311ServiceGroupInst?.lastUpdated}" /></span>
	  </li>
	</g:if>
	<g:if test="${o311ServiceGroupInst?.code}">
	  <li class="fieldcontain">
	    <span id="code-label" class="property-label"><g:message code="o311ServiceGroup.code.label" default="Code" /></span>
	    <span class="property-value" aria-labelledby="code-label"><g:fieldValue bean="${o311ServiceGroupInst}" field="code"/></span>
	  </li>
	</g:if>
	<g:if test="${o311ServiceGroupInst?.displayName}">
	  <li class="fieldcontain">
	    <span id="displayName-label" class="property-label"><g:message code="o311ServiceGroup.displayName.label" default="Display Name" /></span>
	    <span class="property-value" aria-labelledby="displayName-label"><g:fieldValue bean="${o311ServiceGroupInst}" field="displayName"/></span>
	  </li>
	</g:if>
	<g:if test="${o311ServiceGroupInst?.jurisdiction}">
	  <li class="fieldcontain">
	    <span id="jurisdiction-label" class="property-label"><g:message code="o311ServiceGroup.jurisdiction.label" default="Jurisdiction" /></span>
	    <span class="property-value" aria-labelledby="jurisdiction-label"><g:link controller="o311Jurisdiction" action="show" id="${o311ServiceGroupInst?.jurisdiction?.id}">${o311ServiceGroupInst?.jurisdiction?.encodeAsHTML()}</g:link></span>
	  </li>
	</g:if>
      </ol>
      <g:form>
	<fieldset class="buttons">
	  <g:hiddenField name="id" value="${o311ServiceGroupInst?.id}" />
	  <g:link class="edit" action="edit" id="${o311ServiceGroupInst?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
	  <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
	</fieldset>
      </g:form>
    </div>
  </body>
</html>
