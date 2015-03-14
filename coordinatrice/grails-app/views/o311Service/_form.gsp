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
  <g:textField name="name" maxlength="120" required="" value="${o311ServiceInst?.name}"/>
</div>
<div class="fieldcontain ${hasErrors(bean: o311ServiceInst, field: 'description', 'error')} ">
  <label for="description">
    <g:message code="o311Service.description.label" default="Description" />
  </label>
  <g:textField name="description" maxlength="240" value="${o311ServiceInst?.description}"/>
</div>
<div class="fieldcontain ${hasErrors(bean: o311ServiceInst, field: 'jurisdCnx', 'error')} ">
  <label for="jurisdCnx">
    <g:message code="o311Service.jurisdCnx.label" default="Jurisd Cnx" />
  </label>
  <ul class="one-to-many">
    <g:each in="${o311ServiceInst?.jurisdCnx?}" var="j">
      <li><g:link controller="o311ServiceInJurisd" action="show" id="${j.id}">${j?.encodeAsHTML()}</g:link></li>
    </g:each>
    <li class="add">
      <g:link controller="o311ServiceInJurisd" action="create" params="['o311Service.id': o311ServiceInst?.id]">${message(code: 'default.add.label', args: [message(code: 'o311ServiceInJurisd.label', default: 'O311ServiceInJurisd')])}</g:link>
    </li>
  </ul>
</div>
