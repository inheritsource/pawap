<%@ page import="org.motrice.open311.O311Tenant" %>
<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'o311Tenant.label', default: 'O311Tenant')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
  </head>
  <body>
    <a href="#show-o311Tenant" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div id="show-o311Tenant" class="content scaffold-show" role="main">
      <h1><g:message code="default.show.label" args="[entityName]" /></h1>
      <g:if test="${flash.message}">
	<div class="message" role="status">${flash.message}</div>
      </g:if>
      <ol class="property-list o311Tenant">
	<g:if test="${o311TenantInst?.displayName}">
	  <li class="fieldcontain">
	    <span id="displayName-label" class="property-label"><g:message code="o311Tenant.displayName.label" default="Display Name" /></span>
	    <span class="property-value" aria-labelledby="displayName-label"><g:fieldValue bean="${o311TenantInst}" field="displayName"/></span>
	  </li>
	</g:if>
	<g:if test="${o311TenantInst?.organizationName}">
	  <li class="fieldcontain">
	    <span id="organizationName-label" class="property-label"><g:message code="o311Tenant.organizationName.label" default="Organization Name" /></span>
	    <span class="property-value" aria-labelledby="organizationName-label"><g:fieldValue bean="${o311TenantInst}" field="organizationName"/></span>
	  </li>
	</g:if>
	<g:if test="${o311TenantInst?.dateCreated}">
	  <li class="fieldcontain">
	    <span id="dateCreated-label" class="property-label"><g:message code="o311.created.updated.label" default="Date Created"/></span>
	    <span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${o311TenantInst?.dateCreated}"/>&nbsp;/&nbsp;<g:formatDate date="${o311TenantInst?.lastUpdated}"/></span>
	  </li>
	</g:if>
	<g:if test="${o311TenantInst?.firstName}">
	  <li class="fieldcontain">
	    <span id="firstName-label" class="property-label"><g:message code="o311Tenant.firstName.label" default="First Name" /></span>
	    <span class="property-value" aria-labelledby="firstName-label"><g:fieldValue bean="${o311TenantInst}" field="firstName"/></span>
	  </li>
	</g:if>
	<g:if test="${o311TenantInst?.lastName}">
	  <li class="fieldcontain">
	    <span id="lastName-label" class="property-label"><g:message code="o311Tenant.lastName.label" default="Last Name" /></span>
	    <span class="property-value" aria-labelledby="lastName-label"><g:fieldValue bean="${o311TenantInst}" field="lastName"/></span>
	  </li>
	</g:if>
	<g:if test="${o311TenantInst?.email}">
	  <li class="fieldcontain">
	    <span id="email-label" class="property-label"><g:message code="o311Tenant.email.label" default="Email" /></span>
	    <span class="property-value" aria-labelledby="email-label"><g:fieldValue bean="${o311TenantInst}" field="email"/></span>
	  </li>
	</g:if>
	<g:if test="${o311TenantInst?.phone}">
	  <li class="fieldcontain">
	    <span id="phone-label" class="property-label"><g:message code="o311Tenant.phone.label" default="Phone" /></span>
	    <span class="property-value" aria-labelledby="phone-label"><g:fieldValue bean="${o311TenantInst}" field="phone"/></span>
	  </li>
	</g:if>
	<g:if test="${o311TenantInst?.apiKey}">
	  <li class="fieldcontain">
	    <span id="apiKey-label" class="property-label"><g:message code="o311Tenant.apiKey.label" default="Api Key" /></span>
	    <span class="property-value" aria-labelledby="apiKey-label"><g:fieldValue bean="${o311TenantInst}" field="apiKey"/></span>
	  </li>
	</g:if>
	<g:if test="${o311TenantInst?.jurisdictions}">
	  <li class="fieldcontain">
	    <span id="jurisd-label" class="property-label"><g:message code="o311Tenant.admitting.jurisdictions.label" default="Jurisd Cnx" /></span>
	    <g:each in="${o311TenantInst.jurisdictions}" var="j">
	      <g:set var="iconimg"><g:enabled flag="${j.enabledFlag}"/></g:set>
	      <span class="property-value" aria-labelledby="jurisd-label"><g:link controller="o311Jurisdiction" action="show" id="${j.id}">${j?.encodeAsHTML()}</g:link>&nbsp;&nbsp;<g:img dir="images/silk" file="${iconimg}"/></span>
	    </g:each>
	  </li>
	</g:if>
      </ol>
      <g:form>
	<fieldset class="buttons">
	  <g:hiddenField name="id" value="${o311TenantInst?.id}" />
	  <g:link class="edit" action="edit" id="${o311TenantInst?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
	  <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
	  <g:link class="show" action="listjurisd" id="${o311TenantInst?.id}"><g:message code="o311Tenant.configure.jurisdictions.label" default="Configure"/></g:link>
	</fieldset>
      </g:form>
    </div>
  </body>
</html>
