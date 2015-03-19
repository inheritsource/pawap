<%@ page import="org.motrice.open311.O311ServiceGroup" %>
<div class="fieldcontain ${hasErrors(bean: serviceGroupInst, field: 'code', 'error')} ">
  <label for="code">
    <g:message code="o311ServiceGroup.code.label" default="Code" />
  </label>
  <g:textField name="code" maxlength="64" pattern="${serviceGroupInst.constraints.code.matches}" value="${serviceGroupInst?.code}"/>
</div>
<div class="fieldcontain ${hasErrors(bean: serviceGroupInst, field: 'displayName', 'error')} ">
  <label for="displayName">
    <g:message code="o311ServiceGroup.displayName.label" default="Display Name" />
  </label>
  <g:textField name="displayName" class="wide" maxlength="120" value="${serviceGroupInst?.displayName}"/>
</div>
