<%@ page import="org.motrice.open311.O311ServiceGroup" %>
<div class="fieldcontain ${hasErrors(bean: o311ServiceGroupInst, field: 'code', 'error')} ">
  <label for="code">
    <g:message code="o311ServiceGroup.code.label" default="Code" />
  </label>
  <g:textField name="code" maxlength="64" pattern="${o311ServiceGroupInst.constraints.code.matches}" value="${o311ServiceGroupInst?.code}"/>
</div>
<div class="fieldcontain ${hasErrors(bean: o311ServiceGroupInst, field: 'displayName', 'error')} ">
  <label for="displayName">
    <g:message code="o311ServiceGroup.displayName.label" default="Display Name" />
  </label>
  <g:textField name="displayName" maxlength="120" value="${o311ServiceGroupInst?.displayName}"/>
</div>
<div class="fieldcontain ${hasErrors(bean: o311ServiceGroupInst, field: 'jurisdiction', 'error')} required">
  <label for="jurisdiction">
    <g:message code="o311ServiceGroup.jurisdiction.label" default="Jurisdiction" />
    <span class="required-indicator">*</span>
  </label>
  <g:select id="jurisdiction" name="jurisdiction.id" from="${org.motrice.open311.O311Jurisdiction.list()}" optionKey="id" required="" value="${o311ServiceGroupInst?.jurisdiction?.id}" class="many-to-one"/>
</div>
