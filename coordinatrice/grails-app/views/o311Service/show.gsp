<%@ page import="org.motrice.open311.O311Service" %>
<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'o311Service.label', default: 'O311Service')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
  </head>
  <body>
    <a href="#show-o311Service" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div id="show-o311Service" class="content scaffold-show" role="main">
      <h1><g:message code="default.show.label" args="[entityName]" /></h1>
      <g:if test="${flash.message}">
	<div class="message" role="status">${flash.message}</div>
      </g:if>
      <ol class="property-list o311Service">
	<g:if test="${o311ServiceInst?.dateCreated}">
	  <li class="fieldcontain">
	    <span id="dateCreated-label" class="property-label"><g:message code="o311Service.dateCreated.label" default="Date Created" /></span>
	    <span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${o311ServiceInst?.dateCreated}" /></span>
	  </li>
	</g:if>
	<g:if test="${o311ServiceInst?.lastUpdated}">
	  <li class="fieldcontain">
	    <span id="lastUpdated-label" class="property-label"><g:message code="o311Service.lastUpdated.label" default="Last Updated" /></span>
	    <span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${o311ServiceInst?.lastUpdated}" /></span>
	  </li>
	</g:if>
	<g:if test="${o311ServiceInst?.code}">
	  <li class="fieldcontain">
	    <span id="code-label" class="property-label"><g:message code="o311Service.code.label" default="Code" /></span>
	    <span class="property-value" aria-labelledby="code-label"><g:fieldValue bean="${o311ServiceInst}" field="code"/></span>
	  </li>
	</g:if>
	<g:if test="${o311ServiceInst?.name}">
	  <li class="fieldcontain">
	    <span id="name-label" class="property-label"><g:message code="o311Service.name.label" default="Name" /></span>
	    <span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${o311ServiceInst}" field="name"/></span>
	  </li>
	</g:if>
	<g:if test="${o311ServiceInst?.description}">
	  <li class="fieldcontain">
	    <span id="description-label" class="property-label"><g:message code="o311Service.description.label" default="Description" /></span>
	    <span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${o311ServiceInst}" field="description"/></span>
	  </li>
	</g:if>
	<g:if test="${o311ServiceInst?.jurisdCnx}">
	  <li class="fieldcontain">
	    <span id="jurisdCnx-label" class="property-label"><g:message code="o311Service.jurisdCnx.label" default="Jurisd Cnx" /></span>
	    <g:each in="${o311ServiceInst.jurisdCnx}" var="j">
	      <span class="property-value" aria-labelledby="jurisdCnx-label"><g:link controller="o311ServiceInJurisd" action="show" id="${j.id}">${j?.encodeAsHTML()}</g:link></span>
	    </g:each>
	  </li>
	</g:if>
      </ol>
      <g:form>
	<fieldset class="buttons">
	  <g:hiddenField name="id" value="${o311ServiceInst?.id}" />
	  <g:link class="edit" action="edit" id="${o311ServiceInst?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
	  <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
	</fieldset>
      </g:form>
    </div>
  </body>
</html>
