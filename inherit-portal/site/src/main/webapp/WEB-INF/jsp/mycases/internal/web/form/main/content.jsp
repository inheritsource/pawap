<%@ include file="/WEB-INF/jspf/htmlTags.jspf" %>
<%--@elvariable id="document" type="se.inherit.portal.beans.NewsDocument"--%>

<c:choose>
  <c:when test="${empty document}">
    <tag:pagenotfound/>
  </c:when>
  <c:otherwise>
    <c:if test="${not empty document.title}">
      <hst:element var="headTitle" name="title">
        <c:out value="${activity.activityLabel}--${processInstanceDetails.processLabel}"/>
      </hst:element>
      <hst:headContribution keyHint="headTitle" element="${headTitle}"/>
    </c:if>

	
	<c:if test="${not empty activity}">
    	<script type="text/javascript" charset="utf-8">
		        jQuery.noConflict();
		        var $j = jQuery;
		        $j(document).ready(function () {
		             $j("#xform").load("<fmt:message key="orbeonbase.portal.url"/>${activity.formUrl}", function(data) {
		                if (typeof ORBEON != "undefined") { 
		                    if (!document.all) {
		                        ORBEON.xforms.Init.document(); 
		                    } 
		                } 
		        	    }); 
					});
		</script>
    </c:if>
    
    <h1><fmt:message key="mycases.activity.column.lbl"/></h1>
    <p> ${activity.activityLabel} i ${processInstanceDetails.processLabel}</p>
	<!--  activity form (ajax load after page is loaded) -->
	<div class="row-fluid">
		<div class="span12">
    		<div id="xform">Loading form...please wait...</div>
		</div>
	</div>    
    
    
    
  </c:otherwise>  
</c:choose>
	
<p></p>
<h1><fmt:message key="mycases.processInstanceDetails.lbl"/></h1>

<c:choose>
  <c:when test="${empty processInstanceDetails}">
  	<fmt:message key="mycases.noProcessInstanceDetails.lbl"/>
  </c:when>
  <c:otherwise> 
 	<h2><fmt:message key="mycases.pendingActivities.lbl"/></h2>   
 	<p></p>
	 <table class="display dataTable">
		<thead>
			<tr>
			   <th><fmt:message key="mycases.activity.column.lbl"/></th>
			   <th><fmt:message key="mycases.startDate.column.lbl"/></th>
			   <th><fmt:message key="mycases.expectedEndDate.column.lbl"/></th>
			   <th><fmt:message key="mycases.candidates.column.lbl"/></th>
			   <th><fmt:message key="mycases.assignedto.column.lbl"/></th>			   
			</tr>
		</thead>
		<tbody>
	    <c:if test="${not empty processInstanceDetails.pending}">
			<c:forEach var="pendingTask" items="${processInstanceDetails.pending}">
				<tr>
			 	  	<td>${pendingTask.activityLabel}</td>
			 	  	<!--  TODO check start date vs lastStateUpdate -->
			 	  	<td><fmt:formatDate value="${pendingTask.lastStateUpdate}" type="Both" /></td>
			 	  	<td><fmt:formatDate value="${pendingTask.expectedEndDate}" type="Date" /></td> 
			 	  	<td>
			 	  	<c:choose>
				 	  	<c:when test="${empty pendingTask.candidates}}">
				 	  		
				 	  	</c:when>
				 	  	<c:otherwise>
				 	  		<c:forEach var="candidate" items="${pendingTask.candidates}">
				 	  			${candidate}<br>
				 	  		</c:forEach>
				 	  	</c:otherwise>
			 	  	</c:choose>
			 	  	</td>
					<td>${pendingTask.assignedUserId}</td>
				</tr>
			</c:forEach>
		</c:if>
		</tbody>
	</table>
	<p></p> 
	
  	    
  	 <h2><fmt:message key="mycases.timeline.lbl"/></h2>
  	 <p></p>
  	 <c:if test="${not empty timelineByDay}">
		<c:forEach var="dayEntry" items="${timelineByDay}">
			<h3><fmt:formatDate value="${dayEntry.key}" type="Date"/></h3>
			
			<ul class="toggle-view timeline">
				<c:forEach var="logItem" items="${dayEntry.value}">
					<li>
					  <h4><fmt:formatDate value="${logItem.timestamp}" type="Both"/>&nbsp;${logItem.briefDescription}&nbsp;(${logItem.userId})</h4>
					  <span class="exp">+ visa mer...</span>
					  <div class="panel">
						<c:choose>
							<c:when test="${not empty logItem.description}">
								<p>${logItem.description}</p>
							</c:when>
							<c:when test="${not empty logItem.viewUrl}">
								<p><fmt:message key="mycases.loading"/></p>
							</c:when>
							<c:otherwise>
								<p><fmt:message key="mycases.nomoredetails"/></p>
							</c:otherwise>
						</c:choose>
					  </div>
					  <c:if test="${not empty logItem.viewUrl}">
					  	<a class="view-url" href="<fmt:message key="orbeonbase.portal.url"/>${logItem.viewUrl}"></a>
					  </c:if>
					</li>
				</c:forEach>
			</ul>
		</c:forEach>
	 </c:if>
    
  </c:otherwise>
 </c:choose>