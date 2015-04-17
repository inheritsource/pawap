<table class="compact">
  <tr><th><g:message code="pastProcinst.var.name.label"/></th><th><g:message code="pastProcinst.var.value.label"/></th></tr>
  <g:each in="${procVarMap}" status="i" var="procVar">
    <tr class="${(i % 2) == 0 ? 'even' : 'odd'}"><td>${procVar.key}</td><td>${procVar.value?.encodeAsHTML()}</td></tr>
  </g:each>
</table>
