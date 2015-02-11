<%@ page import="org.motrice.coordinatrice.Procinst" %>



<div class="fieldcontain ${hasErrors(bean: procinstInst, field: 'procdef', 'error')} required">
	<label for="procdef">
		<g:message code="procinst.procdef.label" default="Procdef" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="procdef" name="procdef.id" from="${org.motrice.coordinatrice.Procdef.list()}" optionKey="id" required="" value="${procinstInst?.procdef?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: procinstInst, field: 'formdef', 'error')} ">
	<label for="formdef">
		<g:message code="procinst.formdef.label" default="Formdef" />
		
	</label>
	<g:select id="formdef" name="formdef.id" from="${org.motrice.coordinatrice.pxd.PxdFormdefVer.list()}" optionKey="id" value="${procinstInst?.formdef?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: procinstInst, field: 'activityId', 'error')} ">
	<label for="activityId">
		<g:message code="procinst.activityId.label" default="Activity Id" />
		
	</label>
	<g:textField name="activityId" value="${procinstInst?.activityId}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: procinstInst, field: 'assignee', 'error')} ">
	<label for="assignee">
		<g:message code="procinst.assignee.label" default="Assignee" />
		
	</label>
	<g:textField name="assignee" value="${procinstInst?.assignee}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: procinstInst, field: 'businessKey', 'error')} ">
	<label for="businessKey">
		<g:message code="procinst.businessKey.label" default="Business Key" />
		
	</label>
	<g:textField name="businessKey" value="${procinstInst?.businessKey}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: procinstInst, field: 'ended', 'error')} ">
	<label for="ended">
		<g:message code="procinst.ended.label" default="Ended" />
		
	</label>
	<g:checkBox name="ended" value="${procinstInst?.ended}" />
</div>

<div class="fieldcontain ${hasErrors(bean: procinstInst, field: 'forminst', 'error')} required">
	<label for="forminst">
		<g:message code="procinst.forminst.label" default="Forminst" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="forminst" name="forminst.id" from="${org.motrice.coordinatrice.pxd.PxdItem.list()}" optionKey="id" required="" value="${procinstInst?.forminst?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: procinstInst, field: 'msfd', 'error')} required">
	<label for="msfd">
		<g:message code="procinst.msfd.label" default="Msfd" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="msfd" name="msfd.id" from="${org.motrice.coordinatrice.MtfStartFormDefinition.list()}" optionKey="id" required="" value="${procinstInst?.msfd?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: procinstInst, field: 'processInstanceId', 'error')} ">
	<label for="processInstanceId">
		<g:message code="procinst.processInstanceId.label" default="Process Instance Id" />
		
	</label>
	<g:textField name="processInstanceId" value="${procinstInst?.processInstanceId}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: procinstInst, field: 'suspended', 'error')} ">
	<label for="suspended">
		<g:message code="procinst.suspended.label" default="Suspended" />
		
	</label>
	<g:checkBox name="suspended" value="${procinstInst?.suspended}" />
</div>

