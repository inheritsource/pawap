<%@ page import="org.motrice.coordinatrice.PastProcinst" %>
<div class="fieldcontain">
  <label for="finishedState">
    <g:message code="pastProcinst.finished.state.label" default="Finished"/>
  </label>
  <g:set var="finishedAny"><g:message code="pastProcinst.finished.state.any.label"/></g:set>
  <g:set var="finishedYes"><g:message code="pastProcinst.finished.state.finished.label"/></g:set>
  <g:set var="finishedNo"><g:message code="pastProcinst.finished.state.unfinished.label"/></g:set>
  <g:radioGroup name="finishedState" values="[0,1,4]" value="1" labels="[finishedAny,finishedYes,finishedNo]">
    ${it.radio}&nbsp;${it.label}&nbsp;&nbsp;
  </g:radioGroup>
</div>
<div class="fieldcontain">
  <label for="startedBefore">
    <g:message code="pastProcinst.started.before.label" default="Start Time"/>
  </label>
  <g:checkBox name="startedBeforeFlag"/>
  <g:datePicker name="startedBefore" precision="minute" value="${new Date()}" />
</div>
<div class="fieldcontain">
  <label for="startedAfter">
    <g:message code="pastProcinst.started.after.label" default="Start Time"/>
  </label>
  <g:checkBox name="startedAfterFlag"/>
  <g:datePicker name="startedAfter" precision="minute" value="${new Date() - 1}"/>
</div>
<div class="fieldcontain">
  <label for="startedBy">
    <g:message code="pastProcinst.startUserId.label" default="Starting user"/>
  </label>
  <g:textField name="startedBy"/>
</div>
<div class="fieldcontain">
  <label for="finishedBefore">
    <g:message code="pastProcinst.finished.before.label" default="Start Time"/>
  </label>
  <g:checkBox name="finishedBeforeFlag"/>
  <g:datePicker name="finishedBefore" precision="minute" value="${new Date()}" />
</div>
<div class="fieldcontain">
  <label for="finishedAfter">
    <g:message code="pastProcinst.finished.after.label" default="Start Time"/>
  </label>
  <g:checkBox name="finishedAfterFlag"/>
  <g:datePicker name="finishedAfter" precision="minute" value="${new Date() - 1}"/>
</div>
<div class="fieldcontain">
  <label for="procdefById">
    <g:message code="pastProcinst.procdef.label" default="Procdef"/>
  </label>
  <g:select name="procdefId" from="${procdefNameList}" keys="${procdefIdList}" value="${selectedProcdef}"
	    noSelection="${['':' ']}"/>
</div>
<div class="fieldcontain">
  <label for="procdefExclude">
    <g:message code="pastProcinst.exclude.procdef.keys.label" default="Not Keys"/>
  </label>
  <g:select name="procdefExcludeKey" from="${procdefKeyList}" multiple="true"/>
</div>
<div class="fieldcontain">
  <label for="variableLike">
    <g:message code="pastProcinst.process.variable.label" default="Variable"/>
  </label>
  <g:textField name="variableName"/>
  <g:message code="pastProcinst.value.like.label" default="Like"/>
  <g:textField name="variablePattern"/>
</div>
<div class="fieldcontain">
  <label for="orderBy">
    <g:message code="pastProcinst.order.by.label" default="Order By"/>
  </label>
  <g:set var="orderStartTime"><g:message code="pastProcinst.order.by.start.time.label"/></g:set>
  <g:set var="orderEndTime"><g:message code="pastProcinst.order.by.end.time.label"/></g:set>
  <g:set var="orderProcDef"><g:message code="pastProcinst.order.by.process.definition.label"/></g:set>
  <g:radioGroup name="orderByProperty" values="['startTime','endTime','procDef']" value="startTime"
		labels="[orderStartTime,orderEndTime,orderProcDef]">
    ${it.radio}&nbsp;${it.label}&nbsp;&nbsp;
  </g:radioGroup>
</div>
<div class="fieldcontain">
  <label for="orderDir">
    <g:message code="pastProcinst.order.label" default="Order"/>
  </label>
  <g:set var="orderAsc"><g:message code="pastProcinst.order.ascending.label"/></g:set>
  <g:set var="orderDesc"><g:message code="pastProcinst.order.descending.label"/></g:set>
  <g:radioGroup name="orderDirection" values="['orderAsc','orderDesc']" value="orderAsc"
		labels="[orderAsc,orderDesc]">
    ${it.radio}&nbsp;${it.label}&nbsp;&nbsp;
  </g:radioGroup>
</div>
<div class="fieldcontain">
  <label for="ended">
    <g:message code="pastProcinst.exclude.subprocesses.label" default="Ended"/>
  </label>
  <g:checkBox name="excludeSubprocesses"/>
</div>
