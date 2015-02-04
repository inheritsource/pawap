<%@ page import="org.motrice.coordinatrice.CrdFormMap" %>
<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'crdFormMap.label', default: 'CrdFormMap')}" />
    <title><g:message code="default.edit.label" args="[entityName]" /></title>
  </head>
  <body>
    <a href="#edit-crdFormMap" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div id="edit-crdFormMap" class="content scaffold-edit" role="main">
      <h1><g:message code="crdFormMap.edit.map.label"/>: ${formDefTitle}</h1>
      <g:if test="${flash.message}">
	<div class="message" role="status">${flash.message}</div>
      </g:if>
      <g:hasErrors bean="${crdFormMapInst}">
	<ul class="errors" role="alert">
	  <g:eachError bean="${crdFormMapInst}" var="error">
	    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
	  </g:eachError>
	</ul>
      </g:hasErrors>
      <g:form method="post" >
	<g:hiddenField name="id" value="${crdFormMapInst?.id}" />
	<g:hiddenField name="version" value="${crdFormMapInst?.version}" />
	<fieldset class="form">
	  <table>
	    <thead>
	      <tr>
		<th><g:message code="crdFormMap.section.label"/></th><th><g:message code="crdFormMap.control.label"/></th><th><g:message code="crdFormMap.variable.label"/></th>
	      </tr>
	    </thead>
	    <tbody>
	      <g:each in="${crdFormMapList}" status="i" var="tup">
		<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
		  <g:if test="${tup.variable}">
		    <g:set var="varval" value="${tup.variable}"/>
		  </g:if>
		  <g:else>
		    <g:set var="varval" value=""/>
		  </g:else>
		  <td>${tup.section}</td><td>${tup.control}</td><td><g:textField name="${tup.id}" value="${varval}" maxlength="48"/></td>
		</tr>
	      </g:each>
	    </tbody>
	  </table>
	</fieldset>
	<fieldset class="buttons">
	  <g:actionSubmit class="save" action="savelist" value="${message(code: 'default.button.update.label', default: 'Update')}" />
	  <g:link class="show" controller="pxdFormdefVer" action="show" id="${crdFormMapInst?.id}"><g:message code="crdFormMap.show.form.label"/></g:link>
	</fieldset>
      </g:form>
    </div>
  </body>
</html>
