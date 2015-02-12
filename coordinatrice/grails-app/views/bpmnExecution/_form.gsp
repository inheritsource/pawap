<%@ page import="org.motrice.coordinatrice.BpmnExecution" %>



<div class="fieldcontain ${hasErrors(bean: bpmnExecutionInst, field: 'uuid', 'error')} ">
	<label for="uuid">
		<g:message code="bpmnExecution.uuid.label" default="Uuid" />
		
	</label>
	<g:textField name="uuid" value="${bpmnExecutionInst?.uuid}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bpmnExecutionInst, field: 'activityId', 'error')} ">
	<label for="activityId">
		<g:message code="bpmnExecution.activityId.label" default="Activity Id" />
		
	</label>
	<g:textField name="activityId" value="${bpmnExecutionInst?.activityId}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bpmnExecutionInst, field: 'ended', 'error')} ">
	<label for="ended">
		<g:message code="bpmnExecution.ended.label" default="Ended" />
		
	</label>
	<g:checkBox name="ended" value="${bpmnExecutionInst?.ended}" />
</div>

<div class="fieldcontain ${hasErrors(bean: bpmnExecutionInst, field: 'parentId', 'error')} ">
	<label for="parentId">
		<g:message code="bpmnExecution.parentId.label" default="Parent Id" />
		
	</label>
	<g:textField name="parentId" value="${bpmnExecutionInst?.parentId}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bpmnExecutionInst, field: 'processInstanceId', 'error')} ">
	<label for="processInstanceId">
		<g:message code="bpmnExecution.processInstanceId.label" default="Process Instance Id" />
		
	</label>
	<g:textField name="processInstanceId" value="${bpmnExecutionInst?.processInstanceId}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bpmnExecutionInst, field: 'suspended', 'error')} ">
	<label for="suspended">
		<g:message code="bpmnExecution.suspended.label" default="Suspended" />
		
	</label>
	<g:checkBox name="suspended" value="${bpmnExecutionInst?.suspended}" />
</div>

