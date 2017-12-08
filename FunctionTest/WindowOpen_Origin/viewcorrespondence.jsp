<%-- CIF_00918 --%>
<%--
 *
 * This class is JSP that corresponds to the Add Overpayment screen.
 * jsp: 			viewcorrespondence.jsp
 * Action: 			ViewCorrespondenceAction.java
 * Form: 			ViewCorrespondenceForm.java
 * tiles-defs.xml: 	.viewcorrespondence (acccessms/WEB-INF/jsp)
 * 
 * @author Tata Consultancy Services
 * 
 * @version 1.0 
 * 
--%>

<%@ page language="java"%>
<%@ taglib uri="/taglib/struts-html" prefix="html"%>
<%@ taglib uri="/taglib/struts-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/taglib/accesstag" prefix="access"%>

<%--CIF_01127 - Non-Functional Requirements/ Error: end tag for "input" omitted, but OMITTAG NO was specified--%><html:xhtml/><%--CIF_01127 end --%><%--  form area & content area --%>
<html:form action="viewcorrespondence" method="post">
<table style="display:none"><tr><td><input type="hidden" name="encKey" value="<c:out value="${encKeyEncrypted}"/>" id="encKeyId"/></td></tr></table>

	<br />
	<html:errors />
	<div class="pageheader">
		<bean:message key="access.inquiry.overpayment.list.viewcorrespondence" />
	</div>
	<bean:message key="access.requiredfield" />
	<br />
	<br />



	<c:if test="${empty viewcorrespondenceform.correspondenceList}">
		<table  width="100%">
			<tr>
				<td class="textdata" align="center" colspan="5"><bean:message
						key="error.access.bpc.no.correspondence.ssn" /></td>
			</tr>
		</table>
	</c:if>

	<c:if test="${not empty viewcorrespondenceform.correspondenceList}">





<%-- CIF_01362 Adjusted the alignment --%>
<%--CIF_01638 - Mozilla Firefox Bug/ Cellspacing attribute added to table--%>&nbsp;<div align="center">&nbsp;<table   class="tabledata" cellspacing="0" ><%--CIF_01638 end--%>
		<c:forEach var="correspondanceBean"
			items="${(viewcorrespondenceform.correspondenceList)}">
			<tr>
				<td>
					<c:url var="ui501View" value="viewcorrespondence.do?method=Generate Report">
<c:param name="${access:encryptParams('encKey',encKey)}" value="${access:encryptParams(encKey,encKey)}" />
						<c:param name="${access:encryptParams('documentId',encKey)}" value="${access:encryptParams(correspondanceBean.docId,encKey)}" />
						<c:param name="${access:encryptParams('documentPath',encKey)}" value="${access:encryptParams(correspondanceBean.docPath,encKey)}" />
					</c:url>
					<html:link href="#" onclick="window.open('${ui501View}','_blank','left=0,top=0,resizable=yes,scrollbars=yes,status=yes')">
							<html:hidden name="correspondanceBean" property="corrCode" indexed="true" write="true" />
					</html:link></td>
				<td class="textdata" align="left"><html:hidden
						name="correspondanceBean" property="timeStamp" indexed="true"
						write="true" /></td>
			</tr>
		</c:forEach>
</table></div>

	</c:if>
	<br/>
	<br/>
</html:form>

