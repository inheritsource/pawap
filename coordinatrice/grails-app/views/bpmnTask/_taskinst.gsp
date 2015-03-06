<%@ page import="org.motrice.coordinatrice.BpmnTask" %>
<div class="fieldcontain ${hasErrors(bean: bpmnTaskInst, field: 'procdef', 'error')} required">
  <label for="nameid">
    <g:message code="bpmnTask.nameid.label" default="Name" />
  </label>
  <g:link action="show" id="${bpmnTaskInst?.uuid}"><g:fieldValue bean="${bpmnTaskInst}" field="name"/>&nbsp;&nbsp;(<g:fieldValue bean="${bpmnTaskInst}" field="uuid"/>)</g:link>
</div>
<div class="fieldcontain ${hasErrors(bean: bpmnTaskInst, field: 'formdef', 'error')} ">
  <label for="formdef">
    <g:message code="bpmnTask.formdef.label" default="Formdef" />
  </label>
  <g:link controller="pxdFormdefVer" action="show" id="${bpmnTaskInst?.formdef?.id}">${bpmnTaskInst?.formdef?.path}</g:link>
</div>
<div class="fieldcontain">
  <label for="formdef">
    <g:message code="bpmnTask.form.data.label"/>
  </label>
  <g:textField name="formDataPath" class="wide" maxlength="200"/>
</div>
<div class="fieldcontain">
  <label for="commentText">
    <g:message code="bpmnTask.add.comment.label"/>
  </label>
  <g:textArea name="commentText" class="wide" maxlength="1200"><g:message code="bpmnTask.completed.by.coordinatrice"/></g:textArea>
</div>
<div class="fieldcontain ${hasErrors(bean: bpmnTaskInst, field: 'mafd', 'error')}">
  <label class="narrow" for="motriceActivityFormTypeId">motriceFormTypeId</label>
  ${bpmnTaskInst?.mafd?.formHandlerType?.id}
</div>
<div class="fieldcontain">
  <label class="narrow" for="motriceActivityFormDefinitionKey">motriceFormDefinitionKey</label>
  ${bpmnTaskInst?.mafd?.formConnectionKey}
</div>
