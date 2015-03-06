<%@ page import="org.motrice.coordinatrice.Procinst" %>
<div class="fieldcontain ${hasErrors(bean: procInst, field: 'procdef', 'error')} required">
  <label for="procdef">
    <g:message code="procinst.procdef.label" default="Procdef" />
  </label>
  <g:link controller="procdef" action="listname" id="${procInst?.procdef?.key}">${procInst?.procdef?.nameOrKey}</g:link>
  <label for="procdef">
    <g:message code="procdef.vno.label"/>
  </label>
  ${procInst?.procdef?.vno}
</div>
<div class="fieldcontain ${hasErrors(bean: procInst, field: 'formdef', 'error')} ">
  <label for="formdef">
    <g:message code="procinst.formdef.label" default="Formdef" />
  </label>
  <g:link controller="pxdFormdefVer" action="show" id="${procInst?.formdef?.id}">${procInst?.formdef?.path}</g:link>
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
<div class="fieldcontain">
  <label for="commentText">
    <g:message code="procinst.add.comment.label"/>
  </label>
  <g:textArea name="commentText" class="wide" maxlength="1200"><g:message code="procinst.started.by.coordinatrice"/></g:textArea>
</div>
<div class="fieldcontain ${hasErrors(bean: procInst, field: 'msfd', 'error')}">
  <label class="narrow" for="motriceStartFormTypeId">motriceStartFormTypeId</label>
  ${procInst?.msfd?.formHandlerId}
</div>
<div class="fieldcontain">
  <label class="narrow" for="motriceStartFormDefinitionKey">motriceStartFormDefinitionKey</label>
  ${procInst?.msfd?.formConnectionKey}
</div>
