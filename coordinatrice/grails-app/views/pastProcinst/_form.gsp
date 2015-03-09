<%@ page import="org.motrice.coordinatrice.PastProcinst" %>
<div class="fieldcontain ${hasErrors(bean: pastProcInst, field: 'procdef', 'error')} required">
  <label for="procdef">
    <g:message code="pastProcinst.procdef.label" default="Procdef" />
    <span class="required-indicator">*</span>
  </label>
  <g:select id="procdef" name="procdef.id" from="${org.motrice.coordinatrice.Procdef.list()}" optionKey="id" required="" value="${pastProcInst?.procdef?.id}" class="many-to-one"/>
</div>
<div class="fieldcontain ${hasErrors(bean: pastProcInst, field: 'businessKey', 'error')} ">
  <label for="businessKey">
    <g:message code="pastProcinst.businessKey.label" default="Business Key" />
  </label>
  <g:textField name="businessKey" value="${pastProcInst?.businessKey}"/>
</div>
<div class="fieldcontain ${hasErrors(bean: pastProcInst, field: 'deleteReason', 'error')} ">
  <label for="deleteReason">
    <g:message code="pastProcinst.deleteReason.label" default="Delete Reason" />
  </label>
  <g:textField name="deleteReason" value="${pastProcInst?.deleteReason}"/>
</div>
<div class="fieldcontain ${hasErrors(bean: pastProcInst, field: 'endTime', 'error')} required">
  <label for="endTime">
    <g:message code="pastProcinst.endTime.label" default="End Time" />
    <span class="required-indicator">*</span>
  </label>
  <g:datePicker name="endTime" precision="day"  value="${pastProcInst?.endTime}"  />
</div>
<div class="fieldcontain ${hasErrors(bean: pastProcInst, field: 'startActivityId', 'error')} ">
  <label for="startActivityId">
    <g:message code="pastProcinst.startActivityId.label" default="Start Activity Id" />
  </label>
  <g:textField name="startActivityId" value="${pastProcInst?.startActivityId}"/>
</div>
<div class="fieldcontain ${hasErrors(bean: pastProcInst, field: 'startTime', 'error')} required">
  <label for="startTime">
    <g:message code="pastProcinst.startTime.label" default="Start Time" />
    <span class="required-indicator">*</span>
  </label>
  <g:datePicker name="startTime" precision="day"  value="${pastProcInst?.startTime}"  />
</div>
<div class="fieldcontain ${hasErrors(bean: pastProcInst, field: 'startUserId', 'error')} ">
  <label for="startUserId">
    <g:message code="pastProcinst.startUserId.label" default="Start User Id" />
  </label>
  <g:textField name="startUserId" value="${pastProcInst?.startUserId}"/>
</div>
<div class="fieldcontain ${hasErrors(bean: pastProcInst, field: 'superProcessInstanceId', 'error')} ">
  <label for="superProcessInstanceId">
    <g:message code="pastProcinst.superProcessInstanceId.label" default="Super Process Instance Id" />
  </label>
  <g:textField name="superProcessInstanceId" value="${pastProcInst?.superProcessInstanceId}"/>
</div>
<div class="fieldcontain ${hasErrors(bean: pastProcInst, field: 'uuid', 'error')} ">
  <label for="uuid">
    <g:message code="pastProcinst.uuid.label" default="Uuid" />
  </label>
  <g:textField name="uuid" value="${pastProcInst?.uuid}"/>
</div>
