<%@ page import="org.motrice.coordinatrice.Procinst" %>
<div class="fieldcontain ${hasErrors(bean: procinstInst, field: 'procdef', 'error')} required">
  <label for="procdef">
    <g:message code="procinst.procdef.label" default="Procdef" />
  </label>
  ${procInst?.procdef?.nameOrKey}
  <label for="procdef">
    <g:message code="procdef.vno.label"/>
  </label>
  ${procInst?.procdef?.vno}
</div>
<div class="fieldcontain ${hasErrors(bean: procinstInst, field: 'formdef', 'error')} ">
  <label for="formdef">
    <g:message code="procinst.formdef.label" default="Formdef" />
  </label>
  ${procInst?.formdef?.path}
</div>
<div class="fieldcontain">
  <label for="formdef">
    <g:message code="procinst.form.data.label"/>
  </label>
  <g:textField name="formDataPath" class="wide" maxlength="200"/>
</div>
<div class="fieldcontain">
  <label class="narrow" for="motriceStartFormAssignee">motriceStartFormAssignee</label>
  <g:textField name="startFormAssignee" class="wide" maxlength="200"/>
</div>
<div class="fieldcontain ${hasErrors(bean: procinstInst, field: 'msfd', 'error')}">
  <label class="narrow" for="motriceStartFormTypeId">motriceStartFormTypeId</label>
  ${procInst?.msfd?.formHandlerId}
</div>
<div class="fieldcontain">
  <label class="narrow" for="motriceStartFormDefinitionKey">motriceStartFormDefinitionKey</label>
  ${procInst?.msfd?.formConnectionKey}
</div>
