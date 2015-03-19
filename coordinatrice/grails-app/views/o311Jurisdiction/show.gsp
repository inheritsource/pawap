<%@ page import="org.motrice.open311.O311Jurisdiction" %>
<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'o311Jurisdiction.label', default: 'O311Jurisdiction')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
  </head>
  <body>
    <a href="#show-o311Jurisdiction" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div id="show-o311Jurisdiction" class="content scaffold-show" role="main">
      <h1><g:message code="default.show.label" args="[entityName]" /></h1>
      <g:if test="${flash.message}">
	<div class="message" role="status">${flash.message}</div>
      </g:if>
      <ol class="property-list o311Jurisdiction">
	<g:if test="${o311JurisdictionInst?.jurisdictionId}">
	  <li class="fieldcontain">
	    <span id="jurisdictionId-label" class="property-label"><g:message code="o311Jurisdiction.jurisdictionId.label" default="Jurisdiction Id" /></span>
	    <span class="property-value" aria-labelledby="jurisdictionId-label"><g:fieldValue bean="${o311JurisdictionInst}" field="jurisdictionId"/></span>
	  </li>
	</g:if>
	<g:if test="${o311JurisdictionInst?.fullName}">
	  <li class="fieldcontain">
	    <span id="fullName-label" class="property-label"><g:message code="o311Jurisdiction.fullName.label" default="Full Name" /></span>
	    <span class="property-value" aria-labelledby="fullName-label"><g:fieldValue bean="${o311JurisdictionInst}" field="fullName"/></span>
	  </li>
	</g:if>
	<li class="fieldcontain">
	  <span id="enabledFlag-label" class="property-label"><g:message code="o311Jurisdiction.enabledFlag.label" default="Enabled Flag" /></span>
	  <g:set var="iconimg"><g:enabled flag="${o311JurisdictionInst.enabledFlag}"/></g:set>
	  <span class="property-value" aria-labelledby="enabledFlag-label"><g:img dir="images/silk" file="${iconimg}"/></span>
	</li>
	<g:if test="${o311JurisdictionInst?.dateCreated}">
	  <li class="fieldcontain">
	    <span id="dateCreated-label" class="property-label"><g:message code="o311.created.updated.label" default="Date Created"/></span>
	    <span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${o311JurisdictionInst?.dateCreated}"/>&nbsp;/&nbsp;<g:formatDate date="${o311JurisdictionInst?.lastUpdated}"/></span>
	  </li>
	</g:if>
	<g:if test="${o311JurisdictionInst?.serviceNotice}">
	  <li class="fieldcontain">
	    <span id="serviceNotice-label" class="property-label"><g:message code="o311Jurisdiction.serviceNotice.label" default="Service Notice" /></span>
	    <span class="property-value" aria-labelledby="serviceNotice-label"><g:fieldValue bean="${o311JurisdictionInst}" field="serviceNotice"/></span>
	  </li>
	</g:if>
	<g:if test="${o311JurisdictionInst?.procdefUuid}">
	  <li class="fieldcontain">
	    <span id="procdefUuid-label" class="property-label"><g:message code="o311Jurisdiction.procdefUuid.label" default="Procdef Uuid" /></span>
	    <span class="property-value" aria-labelledby="procdefUuid-label"><g:link controller="procdef" action="show" id="${o311JurisdictionInst?.procdefUuid}"><g:fieldValue bean="${o311JurisdictionInst}" field="procdefDisplay"/></g:link></span>
	  </li>
	</g:if>
	<g:if test="${o311JurisdictionInst?.serviceGroups}">
	  <li class="fieldcontain">
	    <span id="serviceGroups-label" class="property-label"><g:message code="o311Jurisdiction.serviceGroups.label" default="Service Groups" /></span>
	    <g:each in="${o311JurisdictionInst.serviceGroups}" var="s">
	      <span class="property-value" aria-labelledby="serviceGroups-label"><g:link controller="o311ServiceGroup" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></span>
	    </g:each>
	  </li>
	</g:if>
	<g:if test="${tenantList}">
	  <li class="fieldcontain">
	    <span id="tenant-label" class="property-label"><g:message code="o311Jurisdiction.tenants.label" default="Tenants" /></span>
	    <g:each in="${tenantList}" var="t">
	      <span class="property-value" aria-labelledby="tenant-label"><g:link controller="o311Tenant" action="show" id="${t.id}">${t?.encodeAsHTML()}</g:link></span>
	    </g:each>
	  </li>
	</g:if>
      </ol>
      <g:form>
	<fieldset class="buttons">
	  <g:hiddenField name="id" value="${o311JurisdictionInst?.id}" />
	  <g:link class="edit" action="edit" id="${o311JurisdictionInst?.id}"><g:message code="o311Jurisdiction.edit.label" default="Edit" /></g:link>
	  <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
	  <g:link class="show" controller="o311ServiceGroup" action="list" id="${o311JurisdictionInst?.id}"><g:message code="o311ServiceGroup.list.menu" default="List" /></g:link>
	  <g:link class="show" action="listservices" id="${o311JurisdictionInst?.id}"><g:message code="o311Jurisdiction.configure.services.label" default="Services" /></g:link>
	</fieldset>
      </g:form>
    </div>
  </body>
</html>
