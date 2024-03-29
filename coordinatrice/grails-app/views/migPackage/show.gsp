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
<%@ page import="org.motrice.migratrice.MigPackage" %>
<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main">
      <g:set var="entityName" value="${message(code: 'migPackage.label', default: 'MigPackage')}" />
      <title><g:message code="default.show.label" args="[entityName]" /></title>
  </head>
  <body>
    <a href="#show-migPackage" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div id="show-migPackage" class="content scaffold-show" role="main">
      <h1><g:message code="default.show.label" args="[entityName]" /></h1>
      <g:if test="${flash.message}">
	<div class="message" role="status">${flash.message}</div>
      </g:if>
      <ol class="property-list migPackage">
	<g:if test="${migPackageInst?.siteName}">
	  <li class="fieldcontain">
	    <span id="siteName-label" class="property-label"><g:message code="migPackage.siteName.label" default="Site Name" /></span>
	    <span class="property-value" aria-labelledby="siteName-label"><g:fieldValue bean="${migPackageInst}" field="siteName"/></span>
	  </li>
	</g:if>
	<g:if test="${migPackageInst?.packageName}">
	  <li class="fieldcontain">
	    <span id="packageName-label" class="property-label"><g:message code="migPackage.packageName.label" default="Package Name" /></span>
	    <span class="property-value" aria-labelledby="packageName-label"><g:fieldValue bean="${migPackageInst}" field="packageName"/></span>
	  </li>
	</g:if>
	<g:if test="${migPackageInst?.originLocal}">
	  <li class="fieldcontain">
	    <span id="originLocal-label" class="property-label"><g:message code="migPackage.originLocal.label" default="Origin Local" /></span>
	    <span class="property-value" aria-labelledby="originLocal-label"><g:formatOrigin flag="${migPackageInst?.originLocal}"/></span>
	  </li>
	</g:if>
	<g:if test="${migPackageInst?.siteTstamp}">
	  <li class="fieldcontain">
	    <span id="siteTstamp-label" class="property-label"><g:message code="migPackage.siteTstamp.label" default="Site Tstamp" /></span>
	    <span class="property-value" aria-labelledby="siteTstamp-label"><g:tstamp date="${migPackageInst?.siteTstamp}"/></span>
	  </li>
	</g:if>
	<g:if test="${migPackageInst?.dateCreated}">
	  <li class="fieldcontain">
	    <span id="dateCreated-label" class="property-label"><g:message code="migPackage.dateCreated.label" default="Date Created" /></span>
	    <span class="property-value" aria-labelledby="dateCreated-label"><g:tstamp date="${migPackageInst?.dateCreated}"/></span>
	  </li>
	</g:if>
	<g:if test="${migPackageInst?.packageFormat}">
	  <li class="fieldcontain">
	    <span id="packageFormat-label" class="property-label"><g:message code="migPackage.packageFormat.label" default="Package Format" /></span>
	    <span class="property-value" aria-labelledby="packageFormat-label"><g:fieldValue bean="${migPackageInst}" field="packageFormat"/></span>
	  </li>
	</g:if>
	<g:if test="${migPackageInst?.reports}">
	  <li class="fieldcontain">
	    <span id="reports-label" class="property-label"><g:message code="migPackage.reports.label" default="Reports" /></span>
	    <g:each in="${migPackageInst.reports}" var="r">
	      <span class="property-value" aria-labelledby="reports-label"><g:link controller="migReport" action="show" id="${r.id}">${r?.display()?.encodeAsHTML()}</g:link></span>
	    </g:each>
	  </li>
	</g:if>
	<g:if test="${migPackageInst?.formdefs}">
	  <li class="fieldcontain">
	    <span id="formdefs-label" class="property-label"><g:message code="migPackage.formdefs.label" default="Formdefs" /></span>
	    <g:each in="${migPackageInst.formdefs}" var="f">
	      <span class="property-value" aria-labelledby="formdefs-label"><g:link controller="migFormdef" action="show" id="${f.id}">${f?.display()?.encodeAsHTML()}</g:link></span>
	    </g:each>
	  </li>
	</g:if>
      </ol>
      <g:form>
	<fieldset class="buttons">
	  <g:hiddenField name="id" value="${migPackageInst?.id}" />
	  <g:actionSubmit class="export" action="export" value="${message(code: 'migPackage.button.download.label', default: 'Download')}"/>
	  <g:if test="${migPackageInst?.originLocal}">
	  </g:if><g:else>
	    <g:actionSubmit class="install" action="installpre" value="${message(code: 'migPackage.button.label.install', default: 'Install')}"/>
	  </g:else>
	  <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Confirm deletion')}');" />
	</fieldset>
      </g:form>
    </div>
  </body>
</html>
