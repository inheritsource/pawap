
<%@ page import="org.motrice.coordinatrice.Procinst" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'procinst.label', default: 'Procinst')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-procinst" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-procinst" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list procinst">
			
				<g:if test="${procinstInst?.procdef}">
				<li class="fieldcontain">
					<span id="procdef-label" class="property-label"><g:message code="procinst.procdef.label" default="Procdef" /></span>
					
						<span class="property-value" aria-labelledby="procdef-label"><g:link controller="procdef" action="show" id="${procinstInst?.procdef?.id}">${procinstInst?.procdef?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${procinstInst?.formdef}">
				<li class="fieldcontain">
					<span id="formdef-label" class="property-label"><g:message code="procinst.formdef.label" default="Formdef" /></span>
					
						<span class="property-value" aria-labelledby="formdef-label"><g:link controller="pxdFormdefVer" action="show" id="${procinstInst?.formdef?.id}">${procinstInst?.formdef?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${procinstInst?.activityId}">
				<li class="fieldcontain">
					<span id="activityId-label" class="property-label"><g:message code="procinst.activityId.label" default="Activity Id" /></span>
					
						<span class="property-value" aria-labelledby="activityId-label"><g:fieldValue bean="${procinstInst}" field="activityId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${procinstInst?.assignee}">
				<li class="fieldcontain">
					<span id="assignee-label" class="property-label"><g:message code="procinst.assignee.label" default="Assignee" /></span>
					
						<span class="property-value" aria-labelledby="assignee-label"><g:fieldValue bean="${procinstInst}" field="assignee"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${procinstInst?.businessKey}">
				<li class="fieldcontain">
					<span id="businessKey-label" class="property-label"><g:message code="procinst.businessKey.label" default="Business Key" /></span>
					
						<span class="property-value" aria-labelledby="businessKey-label"><g:fieldValue bean="${procinstInst}" field="businessKey"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${procinstInst?.ended}">
				<li class="fieldcontain">
					<span id="ended-label" class="property-label"><g:message code="procinst.ended.label" default="Ended" /></span>
					
						<span class="property-value" aria-labelledby="ended-label"><g:formatBoolean boolean="${procinstInst?.ended}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${procinstInst?.forminst}">
				<li class="fieldcontain">
					<span id="forminst-label" class="property-label"><g:message code="procinst.forminst.label" default="Forminst" /></span>
					
						<span class="property-value" aria-labelledby="forminst-label"><g:link controller="pxdItem" action="show" id="${procinstInst?.forminst?.id}">${procinstInst?.forminst?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${procinstInst?.msfd}">
				<li class="fieldcontain">
					<span id="msfd-label" class="property-label"><g:message code="procinst.msfd.label" default="Msfd" /></span>
					
						<span class="property-value" aria-labelledby="msfd-label"><g:link controller="mtfStartFormDefinition" action="show" id="${procinstInst?.msfd?.id}">${procinstInst?.msfd?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${procinstInst?.processInstanceId}">
				<li class="fieldcontain">
					<span id="processInstanceId-label" class="property-label"><g:message code="procinst.processInstanceId.label" default="Process Instance Id" /></span>
					
						<span class="property-value" aria-labelledby="processInstanceId-label"><g:fieldValue bean="${procinstInst}" field="processInstanceId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${procinstInst?.suspended}">
				<li class="fieldcontain">
					<span id="suspended-label" class="property-label"><g:message code="procinst.suspended.label" default="Suspended" /></span>
					
						<span class="property-value" aria-labelledby="suspended-label"><g:formatBoolean boolean="${procinstInst?.suspended}" /></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${procinstInst?.id}" />
					<g:link class="edit" action="edit" id="${procinstInst?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
