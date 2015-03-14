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
  <g:textField name="fullName" maxlength="160" required="" value="${o311JurisdictionInst?.fullName}"/>
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
  <g:textField name="serviceNotice" maxlength="240" value="${o311JurisdictionInst?.serviceNotice}"/>
</div>
<div class="fieldcontain ${hasErrors(bean: o311JurisdictionInst, field: 'procdefUuid', 'error')} ">
  <label for="procdefUuid">
    <g:message code="o311Jurisdiction.procdefUuid.label" default="Procdef Uuid" />
  </label>
  <g:textField name="procdefUuid" maxlength="64" value="${o311JurisdictionInst?.procdefUuid}"/>
</div>
<div class="fieldcontain ${hasErrors(bean: o311JurisdictionInst, field: 'serviceCnx', 'error')} ">
  <label for="serviceCnx">
    <g:message code="o311Jurisdiction.serviceCnx.label" default="Service Cnx" />
  </label>
  <ul class="one-to-many">
    <g:each in="${o311JurisdictionInst?.serviceCnx?}" var="s">
      <li><g:link controller="o311ServiceInJurisd" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
    </g:each>
    <li class="add">
      <g:link controller="o311ServiceInJurisd" action="create" params="['o311Jurisdiction.id': o311JurisdictionInst?.id]">${message(code: 'default.add.label', args: [message(code: 'o311ServiceInJurisd.label', default: 'O311ServiceInJurisd')])}</g:link>
    </li>
  </ul>
</div>
<div class="fieldcontain ${hasErrors(bean: o311JurisdictionInst, field: 'serviceGroups', 'error')} ">
  <label for="serviceGroups">
    <g:message code="o311Jurisdiction.serviceGroups.label" default="Service Groups" />
  </label>
  <ul class="one-to-many">
    <g:each in="${o311JurisdictionInst?.serviceGroups?}" var="s">
      <li><g:link controller="o311ServiceGroup" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
    </g:each>
    <li class="add">
      <g:link controller="o311ServiceGroup" action="create" params="['o311Jurisdiction.id': o311JurisdictionInst?.id]">${message(code: 'default.add.label', args: [message(code: 'o311ServiceGroup.label', default: 'O311ServiceGroup')])}</g:link>
    </li>
  </ul>
</div>
<div class="fieldcontain ${hasErrors(bean: o311JurisdictionInst, field: 'tenantCnx', 'error')} ">
  <label for="tenantCnx">
    <g:message code="o311Jurisdiction.tenantCnx.label" default="Tenant Cnx" />
  </label>
  <ul class="one-to-many">
    <g:each in="${o311JurisdictionInst?.tenantCnx?}" var="t">
      <li><g:link controller="o311TenantInJurisd" action="show" id="${t.id}">${t?.encodeAsHTML()}</g:link></li>
    </g:each>
    <li class="add">
      <g:link controller="o311TenantInJurisd" action="create" params="['o311Jurisdiction.id': o311JurisdictionInst?.id]">${message(code: 'default.add.label', args: [message(code: 'o311TenantInJurisd.label', default: 'O311TenantInJurisd')])}</g:link>
    </li>
  </ul>
</div>
