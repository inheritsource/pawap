<%@ page import="org.motrice.coordinatrice.PastProcinst" %>
<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'pastProcinst.label', default: 'PastProcinst')}" />
    <g:set var="procId" value="${pastProcInst?.uuid}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
    <g:javascript library="jquery"/>
    <g:javascript library="jquery-ui"/>
    <g:javascript>
      $("#processVariablesDisplay").dialog({autoOpen: false, width: 790});
      function processVariableDialog() {
        $("#processVariablesDisplay").dialog("open").css({height:"380px", overflow:"auto"});
      }
    </g:javascript>
  </head>
  <body>
    <a href="#show-pastProcinst" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div id="show-pastProcinst" class="content scaffold-show" role="main">
      <h1><g:message code="pastProcinst.label" default="Past Process Instance"/></h1>
      <g:if test="${flash.message}">
	<div id="flashMessage" class="message" role="status">${flash.message}</div>
      </g:if>
      <ol class="property-list pastProcinst">
	<g:if test="${pastProcInst?.uuid}">
	  <li class="fieldcontain">
	    <span id="uuid-label" class="property-label"><g:message code="pastProcinst.uuid.label" default="Uuid" /></span>
	    <span class="property-value" aria-labelledby="uuid-label"><g:fieldValue bean="${pastProcInst}" field="uuid"/></span>
	  </li>
	</g:if>
	<g:if test="${pastProcInst?.superProcessInstanceId}">
	  <li class="fieldcontain">
	    <span id="superProcessInstanceId-label" class="property-label"><g:message code="pastProcinst.superProcessInstanceId.label" default="Super Process Instance Id" /></span>
	    <span class="property-value" aria-labelledby="superProcessInstanceId-label"><g:fieldValue bean="${pastProcInst}" field="superProcessInstanceId"/></span>
	  </li>
	</g:if>
	<g:if test="${pastProcInst?.procdef}">
	  <li class="fieldcontain">
	    <span id="procdef-label" class="property-label"><g:message code="pastProcinst.procdef.label" default="Procdef" /></span>
	    <span class="property-value" aria-labelledby="procdef-label"><g:link controller="procdef" action="show" id="${pastProcInst?.procdef?.uuid}">${pastProcInst?.procdef?.encodeAsHTML()}</g:link></span>
	  </li>
	</g:if>
	<g:if test="${pastProcInst?.businessKey}">
	  <li class="fieldcontain">
	    <span id="businessKey-label" class="property-label"><g:message code="pastProcinst.businessKey.label" default="Business Key" /></span>
	    <span class="property-value" aria-labelledby="businessKey-label"><g:fieldValue bean="${pastProcInst}" field="businessKey"/></span>
	  </li>
	</g:if>
	  <li class="fieldcontain">
	    <span id="startTime-label" class="property-label"><g:message code="pastProcinst.start.end.time.label" default="Start Time"/></span>
	    <span class="property-value" aria-labelledby="startTime-label"><g:formatDate date="${pastProcInst?.startTime}"/>&nbsp;/&nbsp;<g:formatDate date="${pastProcInst?.endTime}"/></span>
	  </li>
	  <li class="fieldcontain">
	    <span id="duration-label" class="property-label"><g:message code="pastProcinst.durationFmt.label" default="Duration" /></span>
	    <span class="property-value" aria-labelledby="duration-label">${pastProcInst?.durationFmt?.encodeAsHTML()}</span>
	  </li>
	<g:if test="${pastProcInst?.startActivityId}">
	  <li class="fieldcontain">
	    <span id="startActivityId-label" class="property-label"><g:message code="pastProcinst.startActivityId.label" default="Start Activity Id" /></span>
	    <span class="property-value" aria-labelledby="startActivityId-label"><g:fieldValue bean="${pastProcInst}" field="startActivityId"/></span>
	  </li>
	</g:if>
	<g:if test="${pastProcInst?.deleteReason}">
	  <li class="fieldcontain">
	    <span id="deleteReason-label" class="property-label"><g:message code="pastProcinst.deleteReason.label" default="Delete Reason" /></span>
	    <span class="property-value" aria-labelledby="deleteReason-label"><g:fieldValue bean="${pastProcInst}" field="deleteReason"/></span>
	  </li>
	</g:if>
	<g:if test="${pastProcInst?.startUserId}">
	  <li class="fieldcontain">
	    <span id="startUserId-label" class="property-label"><g:message code="pastProcinst.startUserId.label" default="Start User Id" /></span>
	    <span class="property-value" aria-labelledby="startUserId-label"><g:fieldValue bean="${pastProcInst}" field="startUserId"/></span>
	  </li>
	</g:if>
	<li class="fieldcontain">
	  <span id="startUserId-label" class="property-label"><g:message code="pastProcinst.finished.label" default="Finished" /></span>
	  <g:set var="imgfile"><g:disabled flag="${pastProcInst?.finished}"/></g:set>
	  <span class="property-value" aria-labelledby="startUserId-label"><g:img dir="images/silk" file="${imgfile}"/></span>
	</li>
      </ol>
      <div id="processVariablesDisplay" title="${message(code: 'pastProcinst.var.title.label', args: [procId])}">Process Variables</div>
      <g:form>
	<fieldset class="buttons">
	  <g:hiddenField name="id" value="${procId}"/>
	  <g:remoteLink class="show" action="showProcessVariables" id="${procId}"
			update="[success:'processVariablesDisplay', failure:'flashMessage']"
			onComplete="processVariableDialog()"><g:message code="pastProcinst.var.button.label"/></g:remoteLink>
	</fieldset>
      </g:form>
    </div>
  </body>
</html>