<%-- == Motrice Copyright Notice ==

  Motrice Service Platform

  Copyright (C) 2011-2014 Motrice AB

  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU Affero General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
  GNU Affero General Public License for more details.

  You should have received a copy of the GNU Affero General Public License
  along with this program. If not, see <http://www.gnu.org/licenses/>.

  e-mail: info _at_ motrice.se
  mail: Motrice AB, Långsjövägen 8, SE-131 33 NACKA, SWEDEN
  phone: +46 8 641 64 14

--%>
<%@ page import="org.motrice.coordinatrice.Procdef" %>
<%@ page import="org.motrice.coordinatrice.pxd.PxdFormdefVer" %>
<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
      <g:set var="entityName" value="${message(code: 'procdef.label', default: 'Procdef')}" />
      <title><g:message code="default.show.label" args="[entityName]" /></title>
  </head>
  <body>
    <a href="#show-procdef" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div id="show-procdef" class="content scaffold-show" role="main">
      <h1><g:message code="default.show.label" args="[entityName]" /></h1>
      <g:if test="${flash.message}">
	<div class="message" role="status">${flash.message}</div>
      </g:if>
      <ol class="property-list procdef">
	<li class="fieldcontain">
	  <span id="name-label" class="property-label"><g:message code="procdef.name.label" default="Name" /></span>
	  <span class="property-value" aria-labelledby="name-label">
	    <g:link action="listname" id="${procdefInst?.key}"><g:fieldValue bean="${procdefInst}" field="nameOrKey"/></g:link>
	    &nbsp;&nbsp;(<g:fieldValue bean="${procdefInst}" field="uuid"/>)
	  </span>
	</li>
	<g:if test="${procdefInst?.vno}">
	  <li class="fieldcontain">
	    <span id="vno-label" class="property-label"><g:message code="procdef.vno.label" default="Vno" /></span>
	    <span class="property-value" aria-labelledby="vno-label"><g:fieldValue bean="${procdefInst}" field="vno"/></span>
	  </li>
	</g:if>
	<g:if test="${procdefInst?.state}">
	  <li class="fieldcontain">
	    <span id="state-label" class="property-label"><g:message code="procdef.state.label" default="State" /></span>
	    <span class="property-value" aria-labelledby="state-label"><g:pdefstate state="${procdefInst?.state}"/></span>
	  </li>
	</g:if>
	<g:if test="${procdefInst?.category}">
	  <li class="fieldcontain">
	    <span id="category-label" class="property-label"><g:message code="procdef.category.label" default="Categ"/></span>
	    <span class="property-value" aria-labelledby="category-label">
	      <g:link controller="crdProcCategory" action="show" id="${procdefInst?.category?.id}"><g:fieldValue bean="${procdefInst}" field="category"/></g:link>
	    </span>
	  </li>
	</g:if>
	<g:if test="${procdefInst?.deployment}">
	  <li class="fieldcontain">
	    <span id="deployedTime-label" class="property-label"><g:message code="procdef.deployedTime.label" default="Deployed" /></span>
	    <span class="property-value" aria-labelledby="deployedTime-label">
	      <g:link action="listdepl" id="${procdefInst?.deployment?.id}">
	      <g:fieldValue bean="${procdefInst}" field="deployedTimeStr"/>
	      </g:link>
	    </span>
	  </li>
	</g:if>
	<li class="fieldcontain">
	  <span id="category-label" class="property-label"><g:message code="procdef.downloads.label" default="Downloads"/></span>
	  <span class="property-value" aria-labelledby="category-label">
	    <g:link action="diagramDownload" id="${procdefInst.uuid}"><g:img uri="/images/silk/camera.png" title="${message(code: 'procdef.diagram.label', default: 'Diagram')}"/></g:link>&nbsp;&nbsp;
	    <g:link action="xmlDownload" id="${procdefInst.uuid}"><g:img uri="/images/silk/map_edit.png" title="${message(code: 'procdef.xml.label', default: 'BPMN')}"/></g:link>
	  </span>
	</li>

	<li class="fieldcontain">
	  <span class="property-label"><g:message code="startform.selection.label"/></span>
	  <g:if test="${startForms}">
	    <g:each in="${startForms}" var="startFormView">
	      <span class="property-value">
		<g:set var="pfv" value="${startFormView.formdefId}"/>
		<g:if test="${pfv}">
		  <g:link controller="pxdFormdefVer" action="show" id="${pfv}">${startFormView?.formConnectionKey?.encodeAsHTML()}</g:link>
		</g:if>
		<g:else>
		  <g:set var="linktitle"><g:message code="startform.selection.invalid.link"/></g:set>
		  <g:img uri="/images/silk/exclamation.png" title="${linktitle}"/> ${startFormView?.formConnectionKey?.encodeAsHTML()}
		</g:else>
		<g:if test="${procState?.startFormChangeAllowed}">
		  <g:link class="edit" action="edit" id="${procdefInst?.uuid}"><g:img uri="/images/silk/cog_edit.png" title="${message(code: 'default.button.configure.label')}"/></g:link>
		  <g:if test="${procState?.testStartable}">&nbsp;&nbsp;
		    <g:set var="formdef" value="${PxdFormdefVer.get(startFormView.formdefId)}"/>
		    <a class="create" href="${formRunnerBaseUri}/${formdef?.path}/new" target="_"><g:img uri="/images/silk/application_form_edit.png" title="${message(code: 'pxdFormdefVer.fill.out.label')}"/></a>&nbsp;&nbsp;
		    <g:link class="edit" controller="procinst" action="create" id="${formdef.id}"><g:img uri="/images/silk/lightning.png" title="${message(code: 'procdef.start.process.label')}"/></g:link>
		  </g:if>
		</g:if>
	      </span>
	    </g:each>
	  </g:if>
	  <g:else>
	    <g:set var="statetitle"><g:message code="startform.selection.unselected"/></g:set>
	    <span class="property-value"><g:img uri="/images/silk/exclamation.png" title="${statetitle}"/>
	      <g:link class="edit" action="edit" id="${procdefInst?.uuid}">
		<g:img uri="/images/silk/application_form_add.png" title="${message(code: 'procdef.add.start.forms.label')}"/>
	      </g:link>
	    </span>
	  </g:else>
	</li>

	<g:if test="${procdefInst?.activities}">
	  <g:set var="procId" value="${procdefInst?.uuid}"/>
	  <li class="fieldcontain">
	    <table>
	    <span id="activities-label" class="label"><g:message code="procdef.act.cnx.label" default="Activities" /></span>
	    <g:each in="${procdefInst.activities}" var="a">
	      <g:set var="formdef" value="${a?.activityFormdef}"/>
	      <g:set var="actId" value="${a?.uuid}"/>
	      <g:set var="cnx" value="${new org.motrice.coordinatrice.TaskFormSpec(a, formdef)}"/>
	      <span class="property-value" aria-labelledby="activities-label"><tr>
		  <td><g:link controller="actDef" action="show" id="${a?.fullId}">${a?.encodeAsHTML()}</g:link></td>
		  <g:render template="/activityConnection" model="[connection:cnx, actDefInst:a, formdef:formdef, procState:procState]"/>
	      </tr></span>
	    </g:each>
	    </table>
	  </li>
	</g:if>
      </ol>
      <g:form>
	<fieldset class="buttons">
	  <g:hiddenField name="uuid" value="${procdefInst?.uuid}" />
	  <g:link class="edit" action="createcopy" id="${procdefInst?.uuid}"><g:message code="procdef.edit.label" default="Edit" /></g:link>
	  <g:link class="edit" action="createnewversion" id="${procdefInst?.uuid}"><g:message code="procdef.upload.version.label" default="Upload" /></g:link>
	  <g:if test="${procState?.stateChangeAllowed}">
	    <g:link class="edit" action="editstate" id="${procdefInst?.uuid}"><g:message code="procdef.edit.state.label" default="Edit" /></g:link>
	  </g:if>
	</fieldset>
      </g:form>
    </div>
  </body>
</html>
