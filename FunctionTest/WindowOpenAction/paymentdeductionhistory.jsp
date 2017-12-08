<%-- paymentdeductionhistory.jsp 										--%>

<%-- This jsp displays claimant payment deduction history				--%>

<%-- jsp			: paymentdeductionhistory.jsp 							--%>

<%-- Action			: PaymentDeductionHistoryAction.java 					--%>

<%-- Form			: CinInqform.java 			--%>

<%-- tiles-defs.xml	: .paymentdeductionhistory (acccessms/WEB-INF/jsp) 	--%>


<%@ page language="java"%>
<%@ taglib uri="/taglib/struts-html" prefix="html"%>
<%@ taglib uri="/taglib/struts-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/taglib/accesstag" prefix="access"%>

<%--  form area & content area --%>

<%--CIF_01127 - Non-Functional Requirements/ Error: end tag for "input" omitted, but OMITTAG NO was specified--%><html:xhtml/><%--CIF_01127 end --%><html:form action="paymentdeductionhistory" method="post">
<table style="display:none"><tr><td><input type="hidden" name="encKey" value="<c:out value="${encKeyEncrypted}"/>" id="encKeyId"/></td></tr></table>


	<html:errors />

	<html:hidden property="claimantId" />
	<html:hidden property="ssn" />

	<div class="pageheader">
		<bean:message key="access.cin.inq.paymentdeductionhistory.page.title" />
	</div>
	<br/>
	<br/>

	<c:set var="claimant" value="${cininqform.details[0].values}" />

	<table  class="tablefields" align="center" style="margin: auto">
		<tr>
			<td width="15%">&nbsp;</td>
			<%--CIF_01127 - Non-Functional Requirements/ Error: value of attribute "nowrap" cannot be "true"; must be one of "nowrap"--%><td class="textlabel" width="1%" nowrap="nowrap"><bean:message
					key="access.cin.inq.claimant.ssn" /></td><%--CIF_01127 end --%>
			<%--CIF_01127 - Non-Functional Requirements/ Error: value of attribute "nowrap" cannot be "true"; must be one of "nowrap"--%><td class="textdata" nowrap="nowrap">${access:fmtssn(claimant.ssn)}</td><%--CIF_01127 end --%>
			<%--CIF_01127 - Non-Functional Requirements/ Error: value of attribute "nowrap" cannot be "true"; must be one of "nowrap"--%><td class="textlabel" width="1%" nowrap="nowrap"><bean:message
					key="access.cin.inq.claimant.name" /></td><%--CIF_01127 end --%>
			<%--CIF_01127 - Non-Functional Requirements/ Error: value of attribute "nowrap" cannot be "true"; must be one of "nowrap"--%><td class="textdata" nowrap="nowrap"><c:out
					value="${cininqform.fullNameWithMiddleInitial}" /></td><%--CIF_01127 end --%>
		</tr>
	</table>
	<br/>
	<br/>

	<c:if test="${not empty cininqform.details[1]}">
		<%--CIF_01638 - Mozilla Firefox Bug/ Cellspacing attribute added to table--%><table  class="tabledatainq" cellspacing="0"  style="margin: auto"><%--CIF_01638 end--%>
			<tr class="firefoxspecial">
				<td class="columnHeader"><bean:message
						key="access.cin.inq.paymentdeductionhistory.lastmodifieddate" /></td>
				<td class="columnHeader"><bean:message
						key="access.cin.inq.paymentdeductionhistory.lastmodifiedby" /></td>
				<td class="columnHeader"><bean:message
						key="access.cin.inq.paymentdeductionhistory.federaltaxwithheld" /></td>
						<%-- As per MO this field is not required --%>
						<%-- CIF_01758 Federal Tax doc number--%>
						<td class="columnHeader"><bean:message
						key="access.cin.inq.paymentdeductionhistory.federaltaxwithhelddoc" /></td>
				<!-- 		
				<td class="columnHeader"><bean:message
						key="access.cin.inq.paymentdeductionhistory.statetaxwithheld" /></td>
						 -->
				<!-- 
			<td class="columnHeader"><bean:message key="access.cin.inq.paymentdeductionhistory.childsupport" /></td>
			 -->
			</tr>
			<c:forEach var="payment" items="${cininqform.details[1]}" begin="0">
				<tr>
					<td class="textdata" valign="top" align="center"><c:out
							value="${payment.values.lastmodifieddate}" /></td>
					<c:choose>
						<c:when test="${!empty claimant.userid}">
							<c:choose>
								<c:when
									test="${claimant.userid == payment.values.lastmodifiedby}">
									<%--CIF_01127 - Non-Functional Requirements/ Error: value of attribute "nowrap" cannot be "true"; must be one of "nowrap"--%><td class="textdata" nowrap="nowrap"><c:out
											value="${applicationScope.ViewConstants.SELF_SERVE}" /></td><%--CIF_01127 end --%>
								</c:when>
								<c:otherwise>
									<%--CIF_01127 - Non-Functional Requirements/ Error: value of attribute "nowrap" cannot be "true"; must be one of "nowrap"--%><td class="textdata" nowrap="nowrap"><c:out
											value="${payment.values.lastmodifiedby}" /></td><%--CIF_01127 end --%>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<%--CIF_01127 - Non-Functional Requirements/ Error: value of attribute "nowrap" cannot be "true"; must be one of "nowrap"--%><td class="textdata" nowrap="nowrap"><c:out
									value="${payment.values.lastmodifiedby}" /></td><%--CIF_01127 end --%>
						</c:otherwise>
					</c:choose>
					<c:if test="${empty  payment.values.federaltaxstatus}">
					<td class="textdata" nowrap="nowrap"><bean:message
								key="access.na" /></td><%--CIF_01127 end --%>
					</c:if> 
					
					<c:if
						test="${(payment.values.federaltaxstatus == YesNoTypeEnum.NumericYes.name) or (claimInformation.federaltaxstatus == YesNoTypeEnum.Yes.name)}">
						<%--CIF_01127 - Non-Functional Requirements/ Error: value of attribute "nowrap" cannot be "true"; must be one of "nowrap"--%><td class="textdata" nowrap="nowrap"><c:out
								value="${YesNoTypeEnum.NumericYes.description}" /></td><%--CIF_01127 end --%>			
					</c:if>

					<c:if
						test="${(payment.values.federaltaxstatus == YesNoTypeEnum.NumericNo.name) or (claimInformation.federaltaxstatus == YesNoTypeEnum.No.name)}">
						<%--CIF_01127 - Non-Functional Requirements/ Error: value of attribute "nowrap" cannot be "true"; must be one of "nowrap"--%><td class="textdata" nowrap="nowrap"><c:out
								value="${YesNoTypeEnum.NumericNo.description}" /></td><%--CIF_01127 end --%>							
						</c:if>
					<c:choose>
					<c:when test="${not empty payment.values.authwithholddate}">
						<td class="textdata" nowrap="nowrap">
						<c:url var="viewfedtaxdoc" 
 								value="/opendocuments.do?method=Generate Report">
<c:param name="${access:encryptParams('encKey',encKey)}" value="${access:encryptParams(encKey,encKey)}" />
 								<c:param name="${access:encryptParams('documentId',encKey)}" value="${access:encryptParams(payment.values.federaltaxdocumentid,encKey)}" />
								<c:param name="${access:encryptParams('documentPath',encKey)}" value="${access:encryptParams(payment.values.federaltaxdocumentpath,encKey)}" />
							</c:url> <html:link href="#" 
								onclick="window.open('${viewfedtaxdoc}','_blank','left=0,top=0,resizable=yes,scrollbars=yes,status=yes')">
								<c:out value="${CorrespondenceCodeEnum.MODES_4347.name}" />
							</html:link>
						</td>
					</c:when>
					<c:otherwise>
					<td class="textdata" nowrap="nowrap"><bean:message
								key="access.na" /></td><%--CIF_01127 end --%>
					</c:otherwise>
					</c:choose>
					<!-- CIF_01873 -->

					
					<!-- 
					<c:if
						test="${payment.values.statetaxstatus == YesNoTypeEnum.NumericYes.name}">
						<%--CIF_01127 - Non-Functional Requirements/ Error: value of attribute "nowrap" cannot be "true"; must be one of "nowrap"--%><td class="textdata" nowrap="nowrap"><c:out
								value="${YesNoTypeEnum.NumericYes.description}" /></td><%--CIF_01127 end --%>
					</c:if>

					<c:if
						test="${payment.values.statetaxstatus == YesNoTypeEnum.NumericNo.name}">
						<%--CIF_01127 - Non-Functional Requirements/ Error: value of attribute "nowrap" cannot be "true"; must be one of "nowrap"--%><td class="textdata" nowrap="nowrap"><c:out
								value="${YesNoTypeEnum.NumericNo.description}" /></td><%--CIF_01127 end --%>
					</c:if>  
					<c:if
						test="${(payment.values.statetaxstatus != YesNoTypeEnum.NumericYes.name) and (claimInformation.statetaxstatus != YesNoTypeEnum.NumericNo.name)}">
						<%--CIF_01127 - Non-Functional Requirements/ Error: value of attribute "nowrap" cannot be "true"; must be one of "nowrap"--%><td class="textdata" nowrap="nowrap"><bean:message
								key="access.na" /></td><%--CIF_01127 end --%>
					</c:if> 
					-->
					<%--CIF_01127 - Non-Functional Requirements/ Error: value of attribute "nowrap" cannot be "true"; must be one of "nowrap"--%>
					<!-- 
			<c:if test="${payment.values.childsupport == YesNoTypeEnum.NumericYes.name}">
			 <td class="textdata" nowrap="nowrap"><c:out value="${YesNoTypeEnum.NumericYes.description}"/></td>
			</c:if>
			<c:if test="${payment.values.childsupport == YesNoTypeEnum.NumericNo.name}">
			<td class="textdata" nowrap="nowrap"><c:out value="${YesNoTypeEnum.NumericNo.description}"/></td>
			</c:if>
			<c:if test="${empty payment.values.childsupport}">
			 <td class="textdata" nowrap="nowrap"><bean:message key="access.na" /></td>
			</c:if>	
			 -->
			 		<%--CIF_01127 end --%>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	<br/>
	<br/>
	<table  class="buttontable">
		<tr>
		<%-- CIF_01758 added new help file --%>
			<td class="buttontdleft"><access:localebuttontag
	property="method" styleClass="formbutton"
	helpFileName="./html/Help/inq/federal_taxwithhold_history.htm"
	onmouseover="this.className='formbutton formbuttonhover'"
	onmouseout="this.className='formbutton'">
	<bean:message key="access.help" />
</access:localebuttontag></td>
			<td class="buttontdright"><html:cancel styleClass="formbutton"
					onmouseover="this.className='formbutton formbuttonhover'"
					onmouseout="this.className='formbutton'">
					<bean:message key="access.back" />
				</html:cancel></td>
		</tr>
	</table>
</html:form>