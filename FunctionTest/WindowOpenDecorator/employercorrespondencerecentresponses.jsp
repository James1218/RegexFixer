<%@ page language="java"%>
<%@ taglib uri="/taglib/struts-html" prefix="html"%>
<%@ taglib uri="/taglib/struts-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/taglib/accesstag" prefix="access"%>
<%@ taglib uri="/taglib/displaytag" prefix="display"%>


<%--  form area & content area --%>

<%--CIF_01127 - Non-Functional Requirements/ Error: end tag for "input" omitted, but OMITTAG NO was specified--%><html:xhtml/><%--CIF_01127 end --%><html:form action="employercorrespondencerecentresponses" method="post">
<table style="display:none"><tr><td><input type="hidden" name="encKey" value="<c:out value="${encKeyEncrypted}"/>" id="encKeyId"/></td></tr></table>

	<html:errors />
	<div class="pageheader">
		<bean:message
			key="access.nmon.employer.correspondence.recent.page.title" />
	</div>
	<br/>
	<table  class="tablefields">
	<%--CIF_P2_00281 - Start - Commenting out code as per requirements--%>
		<!--<tr>
			<td  class="tdnumber"><bean:message
					key="access.nmon.correspondence.emplr.corrsearchtype.number" />.</td>
			<td  class="tdmandatory">*</td>
			<access:error property="selectedCorrType">
				<td id="corrsearchtypetitle" class="textlabel"><label for="selectedCorrType"><bean:message
						key="access.nmon.correspondence.emplr.corrsearchtype.title" /></label></td>
			</access:error>
			<td  class="textdata" colspan="3"><html:select styleId="selectedCorrType" 
					property="selectedCorrType">
					<html:option value="">
						<bean:message key="access.select" />
					</html:option>
					<c:forEach var="corrSearchTypeEnum"
						items="${CorrespondenceSearchTypeEnum}">
						<html:option value="${corrSearchTypeEnum.value.name}">
							<c:out value="${corrSearchTypeEnum.value.description}" />
						</html:option>
					</c:forEach>
				</html:select></td>
		</tr>
	-->
	<%--CIF_P2_00281 - End  --%>
		<tr>
			<td  class="tdnumber"><bean:message
					key="access.nmon.correspondence.emplr.ssn.number" />.</td>
			<td  class="tdmandatory"></td>
			<access:error property="ssnBean">
				<td id="ssntitle" class="textlabel"><label for="ssnBeanId.ssn1"><bean:message
						key="access.nmon.correspondence.emplr.ssn.title" /></label></td>
			</access:error>
			<td  class="textdata"><access:ssn styleId="ssnBeanId.ssn1"  property="ssnBean"
					readonly="false" /></td>
		</tr>

		<tr>
			<td  class="tdnumber"><bean:message
					key="access.nmon.corr.emplr.sentdate.number" />.</td>
			<td  class="tdmandatory">*</td>
			<%--CIF_P2_00281 - Code added for UI changes --%>
			<td id="sentdate" class="textlabel" colspan="2"><bean:message
					key="access.nmon.corr.sent.date" /></td>
		</tr>

		<tr>
			<%--   			<td  colspan="4">--%>
			<%--          	<table >--%>
			<%--	          	<tr>--%>
			<td  class="tdnumber"></td>
			<td  class="tdnumber"><bean:message
					key="access.nmon.corr.emplr.sentdate.from.number" />.</td>
			<access:error property="corrSentDateFrom">
				<td id="fromdate" class="textlabel"><label for="corrSentDateFromId.month"><bean:message
						key="access.nmon.corr.sent.fromdate" /></label></td>
				<td  class="textdata"><access:calendar styleId="corrSentDateFromId.month" 
						property="corrSentDateFrom" readonly="false" /></td>
			</access:error>
		</tr>
		<tr>
			<td  class="tdnumber"></td>
			<td  class="tdnumber"><bean:message
					key="access.nmon.corr.emplr.sentdate.to.number" />.</td>
			<access:error property="corrSentDateTo">
				<td id="todate" class="textlabel"><label for="corrSentDateToId.month"><bean:message
						key="access.nmon.corr.sent.todate" /></label></td>
				<td  class="textdata"><access:calendar styleId="corrSentDateToId.month"  property="corrSentDateTo"
						readonly="false" /></td>
			</access:error>
			<%--		  		</tr>--%>
			<%--		  	</table>--%>
			<%--		  </td>--%>
		</tr>
		<%--CIF_P2_00281 VIEW CORRESPONDENCES Start - Modified code--%>
		<tr>
			
			<td  class="textlabel" colspan="4"><label for="unreadId"><bean:message
					key="access.nmon.corr.emplr.daterange"/></label></td>
							
		</tr>
		
		
		<tr>
			<td class="textdata"><html:checkbox styleId="unreadId"   property="unread"/></td>
			<td class="textlabel" colspan="3"><bean:message
					key="access.nmon.corr.emplr.unread" /></td>
			
		</tr>
		
			
		<tr>
			<td id="space" colspan="4">&nbsp;</td>
		</tr>
		<%--CIF_P2_00281 END  --%>
		<tr>
		<%--CIF_P2_00281 Start - Modified existing code  --%>		
			<td id="search" align="center" colspan="4">
				<access:nonvalidatesubmit property="method" styleClass="formbutton"
					style="visibility:hidden"
					onmouseover="this.className='formbutton formbuttonhover'"
					onmouseout="this.className='formbutton'">
					<bean:message key="access.popup" />
				</access:nonvalidatesubmit>
				<html:submit property="method"
					styleClass="formbutton"
					onmouseover="this.className='formbutton formbuttonhover'"
					onmouseout="this.className='formbutton'">
					<bean:message key="access.search" />
				</html:submit></td>
		</tr>
	</table>

<%--CIF_P2_00281 Start - Modified existing code  --%>
	<c:if test='${sessionScope.SEARCH_RESULT_KEY != null}'>
		<br />
		<div align="center"><display:table requestURI="${sessionScope.actionpath}?research=false"
			name="sessionScope.SEARCH_RESULT_KEY"
			pagesize="${ViewConstants_SEARCH_RESULT_PAGE_SIZE}"
			class="disp_result_table"
			decorator="gov.state.uim.web.search.EmployerCorrespondenceRecentResponsesDecorator">
			<display:column class="centered" property="title"
				titleKey="access.nmon.correspondence.search.corrtype.title" />
			<display:column class="textnowrap" property="ssn"
				titleKey="access.ssn" />
			<display:column property="fullName" titleKey="access.name" />
			<display:column class="centered" property="sentdate"
				titleKey="access.nmon.correspondence.search.sentdate" />
				<%--CIF_P2_00281 Start - Modified existing code  --%>
			<display:column class="centered" property="ean"
				titleKey="access.nmon.correspondence.search.ean" />
				<%--CIF_P2_00281 End - Modified existing code  --%>
		</display:table></div>
	</c:if>
<%--CIF_P2_00281 Start - Commented existing code  --%>
<!--  	<c:if
		test='${sessionScope.SEARCH_RESULT_KEY != null && employercorrespondencerecentresponsesform.selectedCorrType == "Appeals"}'>
		<div align="center"><display:table requestURI="${sessionScope.actionpath}?research=false"
			name="sessionScope.SEARCH_RESULT_KEY"
			pagesize="${ViewConstants_SEARCH_RESULT_PAGE_SIZE}"
			class="disp_result_table"
			decorator="gov.state.uim.web.search.EmployerCorrespondenceAppealDecorator">
			<display:column class="centered" property="title"
				titleKey="access.nmon.correspondence.search.corrtype" />
			<display:column class="centered" property="docket"
				titleKey="access.docket" />
			<display:column class="centered" property="sentdate"
				titleKey="access.nmon.correspondence.search.sentdate" />
		</display:table></div>
	</c:if>

-->
<%--CIF_P2_00281 End --%>
	<br/>
	<br/>
	<br/>
	<table  class="buttontable">
		<tr>

			<%--CIF_01127 - Non-Functional Requirements/ Error: end tag for "td" omitted, but OMITTAG NO was specified--%><td class="buttontdleft">
			</td><%--CIF_01127 end --%><td class="buttontdleft"><access:localebuttontag
	property="method" styleClass="formbutton"
	helpFileName="./html/Help/nmon/employer_recent_corresondence_search_two.htm"
	onmouseover="this.className='formbutton formbuttonhover'"
	onmouseout="this.className='formbutton'">
	<bean:message key="access.help" />
</access:localebuttontag> &nbsp;</td>
			<td class="buttontdright"><html:cancel property="method"
					styleClass="formbutton"
					onmouseover="this.className='formbutton formbuttonhover'"
					onmouseout="this.className='formbutton'">
					<bean:message key="access.home" />
				</html:cancel></td>
		</tr>
	</table>
</html:form>
