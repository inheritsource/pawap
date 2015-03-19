<%@ page import="org.motrice.open311.O311Tenant" %>
<div class="fieldcontain ${hasErrors(bean: o311TenantInst, field: 'displayName', 'error')} required">
  <label for="displayName">
    <g:message code="o311Tenant.displayName.label" default="Display Name" />
    <span class="required-indicator">*</span>
  </label>
  <g:textField name="displayName" maxlength="48" required="" value="${o311TenantInst?.displayName}"/>
</div>
<div class="fieldcontain ${hasErrors(bean: o311TenantInst, field: 'organizationName', 'error')} ">
  <label for="organizationName">
    <g:message code="o311Tenant.organizationName.label" default="Organization Name" />
  </label>
  <g:textField name="organizationName" class="wide" maxlength="120" value="${o311TenantInst?.organizationName}"/>
</div>
<div class="fieldcontain ${hasErrors(bean: o311TenantInst, field: 'firstName', 'error')} ">
  <label for="firstName">
    <g:message code="o311Tenant.firstName.label" default="First Name" />
  </label>
  <g:textField name="firstName" value="${o311TenantInst?.firstName}"/>
</div>
<div class="fieldcontain ${hasErrors(bean: o311TenantInst, field: 'lastName', 'error')} ">
  <label for="lastName">
    <g:message code="o311Tenant.lastName.label" default="Last Name" />
  </label>
  <g:textField name="lastName" class="wide" maxlength="120" value="${o311TenantInst?.lastName}"/>
</div>
<div class="fieldcontain ${hasErrors(bean: o311TenantInst, field: 'email', 'error')} required">
  <label for="email">
    <g:message code="o311Tenant.email.label" default="Email" />
    <span class="required-indicator">*</span>
  </label>
  <g:textField name="email" class="wide" maxlength="240" required="" value="${o311TenantInst?.email}"/>
</div>
<div class="fieldcontain ${hasErrors(bean: o311TenantInst, field: 'phone', 'error')} required">
  <label for="phone">
    <g:message code="o311Tenant.phone.label" default="Phone" />
    <span class="required-indicator">*</span>
  </label>
  <g:textField name="phone" maxlength="60" required="" value="${o311TenantInst?.phone}"/>
</div>
