<%@ page import="org.motrice.coordinatrice.BpmnTask" %>



<div class="fieldcontain ${hasErrors(bean: bpmnTaskInst, field: 'definitionKey', 'error')} ">
	<label for="definitionKey">
		<g:message code="bpmnTask.definitionKey.label" default="Definition Key" />
		
	</label>
	<g:textField name="definitionKey" value="${bpmnTaskInst?.definitionKey}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bpmnTaskInst, field: 'assignee', 'error')} ">
	<label for="assignee">
		<g:message code="bpmnTask.assignee.label" default="Assignee" />
		
	</label>
	<g:textField name="assignee" value="${bpmnTaskInst?.assignee}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bpmnTaskInst, field: 'createdTime', 'error')} required">
	<label for="createdTime">
		<g:message code="bpmnTask.createdTime.label" default="Created Time" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="createdTime" precision="day"  value="${bpmnTaskInst?.createdTime}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: bpmnTaskInst, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="bpmnTask.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${bpmnTaskInst?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bpmnTaskInst, field: 'dueTime', 'error')} required">
	<label for="dueTime">
		<g:message code="bpmnTask.dueTime.label" default="Due Time" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="dueTime" precision="day"  value="${bpmnTaskInst?.dueTime}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: bpmnTaskInst, field: 'executionId', 'error')} ">
	<label for="executionId">
		<g:message code="bpmnTask.executionId.label" default="Execution Id" />
		
	</label>
	<g:textField name="executionId" value="${bpmnTaskInst?.executionId}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bpmnTaskInst, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="bpmnTask.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${bpmnTaskInst?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bpmnTaskInst, field: 'owner', 'error')} ">
	<label for="owner">
		<g:message code="bpmnTask.owner.label" default="Owner" />
		
	</label>
	<g:textField name="owner" value="${bpmnTaskInst?.owner}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bpmnTaskInst, field: 'priority', 'error')} required">
	<label for="priority">
		<g:message code="bpmnTask.priority.label" default="Priority" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="priority" type="number" value="${bpmnTaskInst.priority}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: bpmnTaskInst, field: 'processInstanceId', 'error')} ">
	<label for="processInstanceId">
		<g:message code="bpmnTask.processInstanceId.label" default="Process Instance Id" />
		
	</label>
	<g:textField name="processInstanceId" value="${bpmnTaskInst?.processInstanceId}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bpmnTaskInst, field: 'suspended', 'error')} ">
	<label for="suspended">
		<g:message code="bpmnTask.suspended.label" default="Suspended" />
		
	</label>
	<g:checkBox name="suspended" value="${bpmnTaskInst?.suspended}" />
</div>

<div class="fieldcontain ${hasErrors(bean: bpmnTaskInst, field: 'uuid', 'error')} ">
	<label for="uuid">
		<g:message code="bpmnTask.uuid.label" default="Uuid" />
		
	</label>
	<g:textField name="uuid" value="${bpmnTaskInst?.uuid}"/>
</div>

