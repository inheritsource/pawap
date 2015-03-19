<%@ page import="org.motrice.open311.O311Jurisdiction" %>
<div class="fieldcontain ${hasErrors(bean: o311JurisdictionInst, field: 'jurisdictionId', 'error')} required">
  <label for="jurisdictionId">
    <g:message code="o311Jurisdiction.jurisdictionId.label" default="Jurisdiction Id" />
    <span class="required-indicator">*</span>
  </label>
  <g:textField name="jurisdictionId" maxlength="60" pattern="${o311JurisdictionInst.constraints.jurisdictionId.matches}" required="" value="${o311JurisdictionInst?.jurisdictionId}"/>
</div>
<div class="fieldcontain ${hasErrors(bean: o311JurisdictionInst, field: 'fullName', 'error')} required">
  <label for="fullName">
    <g:message code="o311Jurisdiction.fullName.label" default="Full Name" />
    <span class="required-indicator">*</span>
  </label>
  <g:textField name="fullName" class="wide" maxlength="160" required="" value="${o311JurisdictionInst?.fullName}"/>
</div>
<div class="fieldcontain ${hasErrors(bean: o311JurisdictionInst, field: 'enabledFlag', 'error')} ">
  <label for="enabledFlag">
    <g:message code="o311Jurisdiction.enabledFlag.label" default="Enabled Flag" />
  </label>
  <g:checkBox name="enabledFlag" value="${o311JurisdictionInst?.enabledFlag}" />
</div>
<div class="fieldcontain ${hasErrors(bean: o311JurisdictionInst, field: 'serviceNotice', 'error')} ">
  <label for="serviceNotice">
    <g:message code="o311Jurisdiction.serviceNotice.label" default="Service Notice" />
  </label>
  <g:textField name="serviceNotice" class="wide" maxlength="240" value="${o311JurisdictionInst?.serviceNotice}"/>
</div>
<div class="fieldcontain ${hasErrors(bean: o311JurisdictionInst, field: 'procdefUuid', 'error')} ">
  <label for="procdefUuid">
    <g:message code="o311Jurisdiction.procdefUuid.label" default="Procdef Uuid" />
  </label>
  <g:select name="procdefId" from="${procdefNameList}" keys="${procdefIdList}" value="${o311JurisdictionInst?.procdefUuid}"
	    noSelection="${['':' ']}"/>
</div>
