<%@ page import="org.motrice.open311.O311Service" %>
<div class="fieldcontain ${hasErrors(bean: o311ServiceInst, field: 'code', 'error')} required">
  <label for="code">
    <g:message code="o311Service.code.label" default="Code" />
    <span class="required-indicator">*</span>
  </label>
  <g:textField name="code" maxlength="64" pattern="${o311ServiceInst.constraints.code.matches}" required="" value="${o311ServiceInst?.code}"/>
</div>
<div class="fieldcontain ${hasErrors(bean: o311ServiceInst, field: 'name', 'error')} required">
  <label for="name">
    <g:message code="o311Service.name.label" default="Name" />
    <span class="required-indicator">*</span>
  </label>
  <g:textField name="name" class="wide" maxlength="120" required="" value="${o311ServiceInst?.name}"/>
</div>
<div class="fieldcontain ${hasErrors(bean: o311ServiceInst, field: 'description', 'error')} ">
  <label for="description">
    <g:message code="o311Service.description.label" default="Description" />
  </label>
  <g:textArea name="description" class="wide" maxlength="240" value="${o311ServiceInst?.description}"/>
</div>
