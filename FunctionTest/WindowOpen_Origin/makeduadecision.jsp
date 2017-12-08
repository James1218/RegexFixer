<%--  processduaproofofwages.jsp													--%>
<%-- This jsp displays the dua proof of wages information for adjudicator   	--%>
<%-- jsp            : makeduadecision.jsp									--%>
<%-- Action         : MakeDuaDecisionAction									--%>
<%-- Form           : MakeDuaDecisionForm									--%>
<%-- tiles-defs.xml : .makeduadecision(accessms/WEB-INF/jsp/nmon)			--%>


<%@ page language="java"%>
<%@ taglib uri="/taglib/struts-html" prefix="html"%>
<%@ taglib uri="/taglib/struts-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/taglib/accesstag" prefix="access"%>

<%-- form area & content area --%>
<%--CIF_01127 - Non-Functional Requirements/ Error: end tag for "input" omitted, but OMITTAG NO was specified--%><html:xhtml/><%--CIF_01127 end --%><%--CIF_01127 - Non-Functional Requirements/ Error: required attribute "type" not specified--%><script type="text/javascript" language=JavaScript>
//<![CDATA[
<%--CIF_01127 end --%>
		var Alow = '${DecisionCodeEnum.ALLOWED.name}';
		var Deny ='${DecisionCodeEnum.DENY.name}';
		var Terminate = '${DecisionCodeEnum.TERMINATE.name}';

//]]>
</script>
<script type="text/javascript" src="javascript/nonmonetary.js">
//<![CDATA[

//]]>
</script>
<%--CIF_01554 Start --%>
<%--SIT Defect_792 --%>
<script type="text/javascript" language="JavaScript">
//<![CDATA[

	function enableOtherTextfield(reason) {
		if(reason == 'OTHR'){
			document.getElementsByName('remarksOfDenyOther')[0].disabled = false;
		}
		else {
			document.getElementsByName('remarksOfDenyOther')[0].value = '';
			document.getElementsByName('remarksOfDenyOther')[0].disabled = true;
		}
	}

//]]>
</script>
<%--CIF_01554 End --%>
<html:form action="makeduadecision" method="post">
<table style="display:none"><tr><td><input type="hidden" name="encKey" value="<c:out value="${encKeyEncrypted}"/>" id="encKeyId"/></td></tr></table>

	<html:errors />

	<div class="pageheader">
		<bean:message key="access.nmon.adjudication.dua.decision.page.title" />
	</div>

	<bean:message key="access.requiredfield" />
	<br/>

	<html:hidden property="claimantProvidedListSize" />
	<html:hidden property="duaEffectiveStartDate" />
	<html:hidden property="ui501CorrId" />
	<br/>
	<table  class="tablefieldslables" style="width: 100%" border="0">
		<tr>
			<td class="textlabel"><bean:message
					key="access.nmon.adjudication.dua.decision.ssn" /></td>
			<td class="textdata">${access:fmtssn(makeduadecisionform.ssn)}<html:hidden
					property="ssn" /></td>
			<td width="15%"></td>
			<td class="textlabel"><bean:message
					key="access.nmon.adjudication.dua.decision.name" /></td>
			<td class="textdata"><html:hidden property="claimantName"
					write="true" /></td>
		</tr>
		<tr>
			<td class="textlabel" nowrap="nowrap"><bean:message
					key="access.nmon.adjudication.dua.decision.reasonforuiineligiblity" /></td>
			<%-- CIF_02217	Defect_3085 - Spacing Issue - Start --%>
			<td class="textdata" width="25%"><html:hidden
					property="reasonForUiIneligibility" write="true" /></td>
			<%-- CIF_02217	Defect_3085 - Spacing Issue - End --%>
			<td></td>
			<td class="textlabel"><bean:message
					key="access.nmon.adjudication.dua.decision.lastdayworked" /></td>
			<td class="textdata"><html:hidden property="lastDayWorked"
					write="true" /></td>
		</tr>
		<tr>
			<td class="textlabel"><bean:message
					key="access.nmon.adjudication.dua.decision.employmentcounty" /></td>
			<td class="textdata"><html:hidden property="employmentCounty" />
			    <%--  CIF_00206--%> 
                <%-- table name has been changed From t_mst_ms_county to t_mst_county --%>
				<access:cachedisplay property="employmentCounty"
					cache="T_MST_COUNTY" /></td>
			<td></td>
			<td class="textlabel"><bean:message
					key="access.nmon.adjudication.dua.decision.residencecounty" /></td>
			<td class="textdata"><html:hidden property="residenceCounty" />
			<%--  CIF_00206--%> 
            <%-- table name has been changed From t_mst_ms_county to t_mst_county --%>
				<access:cachedisplay property="residenceCounty"
					cache="T_MST_COUNTY" /></td>
		</tr>
		<tr>
			<td class="textlabel"><bean:message
					key="access.nmon.adjudication.dua.decision.dusstartdate" /></td>
			<td class="textdata"><html:hidden property="duaStartDate"
					write="true" /></td>
			<td></td>
			<td class="textlabel"><bean:message
					key="access.nmon.adjudication.dua.decision.duaenddate" /></td>
			<td class="textdata"><html:hidden property="duaEndDate"
					write="true" /></td>
		</tr>
		<tr>
			<td class="textlabel"><bean:message
					key="access.nmon.adjudication.dua.decision.duaname" /></td>
			<td class="textdata"><html:hidden property="disasterName"
					write="true" /></td>
			<td></td>
			<td class="textlabel" nowrap="nowrap"><bean:message
					key="access.nmon.adjudication.dua.decision.res.county.during.disaster" /></td>
			<td class="textdata"><html:hidden
					property="resCountyDuringDisaster" />
				<%--  CIF_00206--%> 
                <%-- table name has been changed From t_mst_ms_county to t_mst_county --%>
				<access:cachedisplay property="resCountyDuringDisaster"
					cache="T_MST_COUNTY" /></td>
		</tr>
		<c:if test="${makeduadecisionform.claimDtlsPresent}">
			<tr>
				<td class="textlabel"><bean:message
						key="access.nmon.adjudication.dua.decision.regclaim.startdate" /></td>
				<td class="textdata"><html:hidden property="regClaimStartDate"
						write="true" /></td>
				<td></td>
				<td class="textlabel"><bean:message
						key="access.nmon.adjudication.dua.decision.regclaim.enddate" /></td>
				<td class="textdata"><html:hidden property="regClaimEndDate"
						write="true" /></td>
			</tr>
			<tr>
				<td class="textlabel"><bean:message
						key="access.nmon.adjudication.dua.decision.wba" /></td>
				<td class="textdata">$<html:hidden property="wba" write="true" /></td>
			</tr>
		</c:if>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td></td>
			<td class="textdata" colspan="2" align="right"><%-- <c:choose>
					<c:when test='${not empty makeduadecisionform.dua1CorrId}'> --%>
<%-- CIF_00350 Start --%>
<%-- Removed code --%>					
						<%-- <c:url var="dua1View"
							value="opendocument.do?method=Generate Report">
<c:param name="${access:encryptParams('encKey',encKey)}" value="${access:encryptParams(encKey,encKey)}" /> --%>
<%-- CIF_00350 End. --%>
<%-- CIF_00350 Start --%>
<%-- Added path for flow. --%>							
						<c:url var="dua1View" value= "/nonmonlinkaction.do">
<c:param name="${access:encryptParams('encKey',encKey)}" value="${access:encryptParams(encKey,encKey)}" />	
							<c:param name="${access:encryptParams('forwardName',encKey)}" value="${access:encryptParams('additionalduainfoviewonly',encKey)}" />
<%-- CIF_00350 End. --%>							
							<%-- <c:param name="${access:encryptParams('corrId',encKey)}" value="${access:encryptParams(makeduadecisionform.dua1CorrId,encKey)}" />
							<c:param name="${access:encryptParams('ssn',encKey)}" value="${access:encryptParams(makeduadecisionform.ssn,encKey)}" /> --%>
						</c:url>
						<html:link href="#"
							onclick="window.open('${dua1View}','_blank','left=0,top=0,resizable=yes,scrollbars=yes,status=yes')">
							<bean:message key="access.nmon.dua.decision.additionalinfo" />
						</html:link>
					<%-- </c:when>
					<c:otherwise>
						<bean:message key="access.nmon.dua.decision.dua1.na" />
					</c:otherwise>
				</c:choose> <c:choose>
					<c:when test='${not empty makeduadecisionform.dua12CorrId}'> --%>
<%-- CIF_00350 Start--%>
<%-- modified new link and also condition --%>
				<c:choose>
					<c:when	test="${makeduadecisionform.employmentType == 'SELF'}">
						<c:url var="dua12View" value="/nonmonlinkaction.do">
<c:param name="${access:encryptParams('encKey',encKey)}" value="${access:encryptParams(encKey,encKey)}" />
							<c:param name="${access:encryptParams('forwardName',encKey)}" value="${access:encryptParams('duaselfemploymentviewonly',encKey)}" />
<%--CIF_00350 End. --%>		
<%-- CIF_00350 Start --%>
<%-- Removed code --%>				
						<%-- <c:url var="dua12View"
							value="opendocument.do?method=Generate Report">
<c:param name="${access:encryptParams('encKey',encKey)}" value="${access:encryptParams(encKey,encKey)}" /> --%>
<%-- CIF_00350 End. --%>
							<%-- <c:param name="${access:encryptParams('corrId',encKey)}" value="${access:encryptParams(makeduadecisionform.dua12CorrId,encKey)}" />
							<c:param name="${access:encryptParams('ssn',encKey)}" value="${access:encryptParams(makeduadecisionform.ssn,encKey)}" /> --%>
						</c:url>
<%-- CIF_00350 Start--%>
<%-- modified new link and also condition --%>	
							<html:link href="#"
								onclick="window.open('${dua12View}','_blank','left=0,top=0,resizable=yes,scrollbars=yes,status=yes')">
								<bean:message key="access.nmon.dua.decision.selfemployment" />
							</html:link>					
						</c:when>						
						<c:when	test="${makeduadecisionform.employmentType == 'FARM'}">
						<c:url var="dua12View" value="/nonmonlinkaction.do">
<c:param name="${access:encryptParams('encKey',encKey)}" value="${access:encryptParams(encKey,encKey)}" />
							<c:param name="${access:encryptParams('forwardName',encKey)}" value="${access:encryptParams('duafarmerviewonly',encKey)}" />
							<%-- <c:param name="${access:encryptParams('corrId',encKey)}" value="${access:encryptParams(makeduadecisionform.dua12CorrId,encKey)}" />
							<c:param name="${access:encryptParams('ssn',encKey)}" value="${access:encryptParams(makeduadecisionform.ssn,encKey)}" /> --%>

						</c:url>				
<%--CIF_00350 End. --%>						
						<html:link href="#"
							onclick="window.open('${dua12View}','_blank','left=0,top=0,resizable=yes,scrollbars=yes,status=yes')">
							<bean:message key="access.nmon.dua.decision.selfemployment" />
						</html:link>
					</c:when>
					<%-- <c:otherwise>
						<bean:message key="access.nmon.dua.decision.dua12.na" />
					</c:otherwise> --%>
				</c:choose>  <c:choose>
					<c:when test='${not empty makeduadecisionform.ui501CorrId}'> 
						<c:url var="initialclaimforbenefits" value="/opendocuments.do?method=Generate Report">
<c:param name="${access:encryptParams('encKey',encKey)}" value="${access:encryptParams(encKey,encKey)}" />
							<%-- <c:param name="${access:encryptParams('forwardName',encKey)}" value="${access:encryptParams('openDocument',encKey)}" /> --%>
							 <c:param name="${access:encryptParams('documentId',encKey)}"
								value="${access:encryptParams(makeduadecisionform.ui501CorrId,encKey)}" />
							<c:param name="${access:encryptParams('documentPath',encKey)}" value="${access:encryptParams(makeduadecisionform.documentPath,encKey)}" /> 
							<%-- <c:param name="${access:encryptParams('flow',encKey)}" value="${access:encryptParams('flow',encKey)}" />  --%>
						</c:url>
						<html:link href="#"
							onclick="window.open('${initialclaimforbenefits}','_blank','left=0,top=0,resizable=yes,scrollbars=yes,status=yes')">
							<bean:message key="access.nmon.dua.decision.ui" />
						</html:link>
					 </c:when>
					<%-- <c:otherwise>
						<bean:message key="access.nmon.dua.decision.ui.na" />
					</c:otherwise> --%>
				</c:choose> </td>
		</tr>
	</table>
	<br/>
	<html:hidden property="onlyForEffectiveDate" />
	<c:if test="${!makeduadecisionform.onlyForEffectiveDate}">
		<table width="100%">
			<tr>
				<td align="left" class="textlabelbold" colspan="4"><bean:message
						key="access.nmon.adjudication.dua.decision.baseperiodwages" /></td>
			</tr>
			<c:choose>
				<c:when test='${not empty makeduadecisionform.basePeriodWageList}'>
					<tr>
						<td colspan="4">
							<%--CIF_01638 - Mozilla Firefox Bug/ Cellspacing attribute added to table--%>&nbsp;<div align="center">&nbsp;<table  class="tabledata" cellspacing="0"  border="1" align="left" ><%--CIF_01638 end--%>
								<thead>
									<tr>
										<th scope="col" valign="middle" align="center"><bean:message
												key="access.nmon.adjudication.dua.decision.baseperiodwages.employername" /></th>
										<c:set var="basePeriodWage"
											value="${makeduadecisionform.basePeriodWageList}" />
										<th scope="col" class="textlabel"><c:out
												value="${basePeriodWage[0].labelQtr1}" /><br/>(${ViewConstants.DOLLAR_SYMBOL})</th>
										<th scope="col" class="textlabel"><c:out
												value="${basePeriodWage[0].labelQtr2}" /><br/>(${ViewConstants.DOLLAR_SYMBOL})</th>
										<th scope="col" class="textlabel"><c:out
												value="${basePeriodWage[0].labelQtr3}" /><br/>(${ViewConstants.DOLLAR_SYMBOL})</th>
										<th scope="col" class="textlabel"><c:out
												value="${basePeriodWage[0].labelQtr4}" /><br/>(${ViewConstants.DOLLAR_SYMBOL})</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="basePeriodWageList"
										items="${makeduadecisionform.basePeriodWageList}">
										<tr>
											<td   class="textdata" align="left"><html:hidden
													name="basePeriodWageList" property="employer"
													indexed="true" write="true" /></td>
											<td   align="right" class="textdata"><access:fmtnumber
													name="basePeriodWageList" property="wageQtr1" /></td>
											<td   align="right" class="textdata"><access:fmtnumber
													name="basePeriodWageList" property="wageQtr2" /></td>
											<td   align="right" class="textdata"><access:fmtnumber
													name="basePeriodWageList" property="wageQtr3" /></td>
											<td   align="right" class="textdata"><access:fmtnumber
													name="basePeriodWageList" property="wageQtr4" /></td>
										</tr>
									</c:forEach>
									<c:forEach var="basePeriodWageListTotal"
										items="${makeduadecisionform.basePeriodWageList}" begin="0"
										end="0">
										<tr>
											<td   class="textlabelbold"><bean:message
													key="access.nmon.adjudication.dua.decision.baseperiodwages.total" /></td>
											<td   align="right" class="textdata"><access:fmtnumber
													name="basePeriodWageListTotal" property="totalQtr1" /></td>
											<td   align="right" class="textdata"><access:fmtnumber
													name="basePeriodWageListTotal" property="totalQtr2" /></td>
											<td   align="right" class="textdata"><access:fmtnumber
													name="basePeriodWageListTotal" property="totalQtr3" /></td>
											<td   align="right" class="textdata"><access:fmtnumber
													name="basePeriodWageListTotal" property="totalQtr4" /></td>
										</tr>
									</c:forEach>
								</tbody>
							</table></div>
						</td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="4">No wages available.</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</table>
		<br />
		<table width="100%">
			<td align="left" class="textlabelbold"><bean:message
					key="access.nmon.adjudication.dua.decision.claimantprovidedinfo" /></td>
			<c:choose>
				<c:when
					test='${not empty makeduadecisionform.claimantProvidedWageList}'>
					<tr>
						<td id="space" colspan="4">
							<%--CIF_01638 - Mozilla Firefox Bug/ Cellspacing attribute added to table--%>&nbsp;<div align="center">&nbsp;<table  class="tabledata" cellspacing="0"  border="1" align="left" ><%--CIF_01638 end--%>
								<thead>
									<tr>
										<th scope="col" id="usewages" width="1%" align="center" valign="middle"><label for="useClaimantProvidedWageFlag1"><bean:message
												key="access.nmon.adjudication.dua.decision.claimantprovidedinfo.usewages" /></label></th>
	<c:choose>
		<c:when test='${not empty claimantProvidedWage.addedThruClaimsIntake}'>		
										<th scope="col" id="employername" align="center" valign="middle"><bean:message
												key="access.nmon.adjudication.dua.decision.claimantprovidedinfo.employername" /></th>
		</c:when>
		<c:otherwise>								
										<th scope="col" id="employername.1" align="center" valign="middle"><label for="employer1"><bean:message
												key="access.nmon.adjudication.dua.decision.claimantprovidedinfo.employername" /></label></th>
		</c:otherwise>
	</c:choose>
										<c:set var="claimantWage"
											value="${makeduadecisionform.claimantProvidedWageList}" />
										<th scope="col"  class="textlabel"><c:out
												value="${claimantWage[0].labelQtr1}" /><br/>(${ViewConstants.DOLLAR_SYMBOL})</th>
										<th scope="col"  class="textlabel"><c:out
												value="${claimantWage[0].labelQtr2}" /><br/>(${ViewConstants.DOLLAR_SYMBOL})</th>
										<th scope="col"  class="textlabel"><c:out
												value="${claimantWage[0].labelQtr3}" /><br/>(${ViewConstants.DOLLAR_SYMBOL})</th>
										<th scope="col"  class="textlabel"><c:out
												value="${claimantWage[0].labelQtr4}" /><br/>(${ViewConstants.DOLLAR_SYMBOL})</th>
										<th scope="col" id="prooftype" align="center" valign="middle"><bean:message
												key="access.nmon.adjudication.dua.decision.claimantprovidedinfo.prooftype" /></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="claimantProvidedWage"
										items="${makeduadecisionform.claimantProvidedWageList}"
										varStatus="wages">
										<access:error property="proofTypeSubmitted">
											<tr>
												
	<c:choose>
		<c:when test="${wages.count == 1}">
			<td   id="useClaimantProvidedWageFlag" align="center" class="textdata"><html:multibox  
														property="useClaimantProvidedWageFlag" styleId="useClaimantProvidedWageFlag${wages.count}"  >
														<bean:write name="claimantProvidedWage"
															property="duaAppEmpId" />
													</html:multibox></td>
		</c:when>
		<c:otherwise>
			<td   id="useClaimantProvidedWageFlag.1" align="center" class="textdata"><html:multibox  
														property="useClaimantProvidedWageFlag" title="useClaimantProvidedWageFlag${wages.count}"  >
														<bean:write name="claimantProvidedWage"
															property="duaAppEmpId" />
													</html:multibox></td>
		</c:otherwise>
	</c:choose>
												<c:choose>
													<c:when
														test='${not empty claimantProvidedWage.addedThruClaimsIntake}'>
														<td   id="employer" class="textdata" align="left"><html:hidden
																name="claimantProvidedWage" property="employer"
																indexed="true" write="true" /></td>
													</c:when>
													<c:otherwise>
	<c:choose>
		<c:when test="${wages.count == 1}">
														<td   id="employer.1" class="textdata" align="left"><html:textarea  styleId="employer${wages.count}" 
																name="claimantProvidedWage" property="employer" rows="2"
																cols="15" indexed="true" /></td>
		</c:when>
		<c:otherwise>
														<td   id="employer.2" class="textdata" align="left"><html:textarea  title="employer${wages.count}" 
																name="claimantProvidedWage" property="employer" rows="2"
																cols="15" indexed="true" /></td>
		</c:otherwise>
	</c:choose>
													</c:otherwise>
												</c:choose>
												<%--CIF_01127 - Non-Functional Requirements/ Error: required attribute "type" not specified--%>
												<%--<script type="text/javascript" language=Javascript>
//<![CDATA[

													alert(${claimantProvidedWage.proofTypeSubmitted})
												
//]]>
</script>												
												--%>
												<%--CIF_01127 end --%>
												<c:choose>
													<c:when
														test="${'2' ne claimantProvidedWage.addedThruClaimsIntake}">
														<td   id="wageQtr1" class="textdata" align="right"><label for="wageQtr1"><access:fmtnumbertext styleId="wageQtr1" 
																name="claimantProvidedWage" property="wageQtr1"
																indexed="true" size="10"
																onblur="javascript:addWagesToTotal(1)" /></label></td>
														<td   id="wageQtr2" class="textdata" align="right"><access:fmtnumbertext title="wageQtr2" 
																name="claimantProvidedWage" property="wageQtr2"
																indexed="true" size="10"
																onblur="javascript:addWagesToTotal(2)" /></td>
														<td   id="wageQtr3" class="textdata" align="right"><access:fmtnumbertext title="wageQtr3" 
																name="claimantProvidedWage" property="wageQtr3"
																indexed="true" size="10"
																onblur="javascript:addWagesToTotal(3)" /></td>
														<td   id="wageQtr4" class="textdata" align="right"><access:fmtnumbertext title="wageQtr4" 
																name="claimantProvidedWage" property="wageQtr4"
																indexed="true" size="10"
																onblur="javascript:addWagesToTotal(4)" /></td>
														<td   id="proofTypeSubmitted"><html:select title="proofTypeSubmitted"  name="claimantProvidedWage"
																property="proofTypeSubmitted" indexed="true">
																<html:option value="">
																	<bean:message key="access.select" />
																</html:option>
																<c:forEach var="proofType"
																	items="${ProofOfWagesDocumentTypeEnum}">
																	<html:option value="${proofType.value.name}">${proofType.value.description}</html:option>
																</c:forEach>
															</html:select></td>
													</c:when>
													<c:otherwise>
														<td   id="wageQtr1.1" class="textdata" align="right"><label for="wageQtr1"><access:fmtnumbertext styleId="wageQtr1.2" 
																name="claimantProvidedWage" property="wageQtr1"
																indexed="true" size="10" disabled="true" /></label></td>
														<td   id="wageQtr2.1" class="textdata" align="right"><access:fmtnumbertext title="wageQtr2" 
																name="claimantProvidedWage" property="wageQtr2"
																indexed="true" size="10" disabled="true" /></td>
														<td   id="wageQtr3.1" class="textdata" align="right"><access:fmtnumbertext title="wageQtr3" 
																name="claimantProvidedWage" property="wageQtr3"
																indexed="true" size="10" disabled="true" /></td>
														<td   id="wageQtr4.1" class="textdata" align="right"><access:fmtnumbertext title="wageQtr4" 
																name="claimantProvidedWage" property="wageQtr4"
																indexed="true" size="10" disabled="true" /></td>
														<td   id="proofTypeSubmitted.1"><html:select title="proofTypeSubmitted"  name="claimantProvidedWage"
																property="proofTypeSubmitted" indexed="true"
																disabled="true">
																<html:option value="SYSI">"System Identified"</html:option>
																<%--for '2' always display as SYSI --%>
															</html:select></td>
													</c:otherwise>
												</c:choose>

											</tr>
										</access:error>
									</c:forEach>
									<c:forEach var="claimantProvidedWage"
										items="${makeduadecisionform.claimantProvidedWageList}"
										begin="0" end="0">
										<tr>
											<td    class="textdata"></td>
											<td   id="total" class="textlabelbold"><bean:message
													key="access.nmon.adjudication.dua.decision.baseperiodwages.total" /></td>
											<td    align="right" class="textdata"><html:hidden
													name="claimantProvidedWage" property="totalQtr1"
													indexed="true" /><span id="totalQtr1Span"><access:fmtnumber
														name="claimantProvidedWage" property="totalQtr1" /></span></td>
											<td    align="right" class="textdata"><html:hidden
													name="claimantProvidedWage" property="totalQtr2"
													indexed="true" /><span id="totalQtr2Span"><access:fmtnumber
														name="claimantProvidedWage" property="totalQtr2" /></span></td>
											<td    align="right" class="textdata"><html:hidden
													name="claimantProvidedWage" property="totalQtr3"
													indexed="true" /><span id="totalQtr3Span"><access:fmtnumber
														name="claimantProvidedWage" property="totalQtr3" /></span></td>
											<td    align="right" class="textdata"><html:hidden
													name="claimantProvidedWage" property="totalQtr4"
													indexed="true" /><span id="totalQtr4Span"><access:fmtnumber
														name="claimantProvidedWage" property="totalQtr4" /></span></td>
											<td    class="textdata"><html:hidden
													name="claimantProvidedWage" property="duaAppId"
													indexed="true" />
												<html:hidden name="claimantProvidedWage" property="wageYear"
													indexed="true" /></td>
										</tr>
									</c:forEach>
								</tbody>
							</table></div>
						</td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="4">No claimant provided wages available.</td>
					</tr>
				</c:otherwise>
			</c:choose>
			<tr>
				<td colspan="4">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="4"><access:nonvalidatesubmit property="method"
						styleClass="formbutton"
						onmouseover="this.className='formbutton formbuttonhover'"
						onmouseout="this.className='formbutton'">
						<bean:message key="access.addemployer" />
					</access:nonvalidatesubmit></td>
			</tr>
		</table>
	</c:if>
	<br />
	<table  width="100%">
		<!--
		<tr>
			<td class="textlabel"><bean:message key="access.nmon.adjudication.dua.proofofwages.statusofproofofwages.number" /></td>
			<td class="tdmandatory">*</td>
			<access:error property="statusOfProofOfWages">
			<td class="textlabel"><label for="statusOfProofOfWages"><bean:message key="access.nmon.adjudication.dua.proofofwages.statusofproofofwages" /></label></td>
			</access:error>
			<td class="textdata">
		    	<html:select styleId="statusOfProofOfWages"  property="statusOfProofOfWages" >  
				<html:option value=""><bean:message key="access.select"/></html:option>
				<html:optionsCollection name="T_MST_STATUS_OF_PROOF_OF_WAGES" value="key" label="description" />
 				</html:select>	
		    </td>
		</tr>
		<tr>
			<td class="tdnumber"><bean:message key="access.nmon.adjudication.dua.proofofwages.decision.number" />.</td>
			<td class="tdmandatory">*</td>
			<access:error property="decision">
				<td class="textlabel" colspan="2"><label for="decisionId"><bean:message key="access.nmon.adjudication.dua.proofofwages.decision" /></label></td>
			</access:error>
		    <td class="textdata">
 		    <html:radio styleId="decisionId"  property="decision" value="${DecisionCodeEnum.ALLOWED.name}">
				<bean:message key="access.nmon.adjudication.decision.allow"/>
			</html:radio> 
			<html:radio title="decision"  property="decision" value="${DecisionCodeEnum.DENY.name}">
				<bean:message key="access.nmon.adjudication.decision.deny" />
			</html:radio>
			<html:radio title="decision"  property="decision" value="${DecisionCodeEnum.TERMINATE.name}">
				<bean:message key="access.nmon.adjudication.dua.decision.terminate" />
			</html:radio>
			</td>
		</tr>
	-->

		<tr>
			<td class="tdnumber">1.</td>
			<%-- <bean:message key="access.nmon.adjudication.dua.proofofwages.dec.number"/>CIF_01127 - Non-Functional Requirements/ Error: end tag for element "td" which is not open--%><%--CIF_01127 end  --%>
			<td class="tdmandatory">*</td>
			<access:error property="decision">
				<td class="textlabel" colspan="4"><bean:message
						key="access.nmon.adjudication.dua.proofofwages.decision" /></td>
			</access:error>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<%--  For Allow Radion button --%>

		<tr>
			<td class="tdnumber"></td>
			<td class="textdata" colspan="3"><html:radio styleId="decisionId.1"  property="decision"
					value="${DecisionCodeEnum.ALLOWED.name}"
					onclick="javascript:makeDuaDecision(this)">
					&nbsp;&nbsp;<label for="decisionId.1"><bean:message
						key="access.nmon.adjudication.decision.allow" /></label>
				</html:radio></td>
		</tr>
		<tr>
			<td class="tdnumber"></td>
			<td class="tdnumber"></td>
			<td class="tdmandatory">&nbsp;</td>
			<td>
				<table  cellspacing="1">
					<tr>
						<td width="2%">&nbsp;</td>
						<td class="tdnumber"><bean:message
								key="access.nmon.adjudication.dua.decision.duaeffectivedate.number" />.</td>
						<access:error property="allowEffectiveDate">
							<td class="textlabel"><label for="allowEffectiveDateId.month"><bean:message
									key="access.nmon.adjudication.dua.decision.duaeffectivedate" /></label></td>
						</access:error>

						<td class="textlabel"><access:calendar styleId="allowEffectiveDateId.month" 
								property="allowEffectiveDate" readonly="false" /></td>
					</tr>
					<%-- CIF_00350 Start --%>
					<tr>
						<td width="2%">&nbsp;</td>
						<td class="tdnumber"><bean:message
								key="access.nmon.adjudication.dua.decision.duaenddate.number" />.</td>
						<access:error property="allowEndDate">
							<td class="textlabel"><label for="allowEndDateId.month"><bean:message
									key="access.nmon.adjudication.dua.decision.duaenddate" /></label></td>
						</access:error>

						<td class="textlabel"><access:calendar styleId="allowEndDateId.month" 
								property="allowEndDate" readonly="false" /></td>
					</tr>
					<%--  CIF_00350 End. --%>
					<tr>
						<td width="2%">&nbsp;</td>
						<td class="tdnumber"><bean:message
								key="access.nmon.adjudication.dua.proofofwages.statusofproofofwages.number" />.</td>
						<access:error property="statusOfProofOfWages">
							<td class="textlabel"><label for="statusOfProofOfWages.1"><bean:message
									key="access.nmon.adjudication.dua.proofofwages.statusofproofofwages" /></label></td>
						</access:error>

						<td class="textdata"><html:select styleId="statusOfProofOfWages.1" 
								property="statusOfProofOfWages">
								<html:option value="">
									<bean:message key="access.select" />
								</html:option>
								<html:optionsCollection name="T_MST_STATUS_OF_PROOF_OF_WAGES"
									value="key" label="description" />
							</html:select></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<%--  For Deny Radio Button --%>
		<tr>
			<td class="tdnumber">&nbsp;</td>
			<td class="textdata" colspan="3"><html:radio styleId="decisionId.2"  property="decision"
					value="${DecisionCodeEnum.DENY.name}"
					onclick="javascript:makeDuaDecision(this)">
					&nbsp;&nbsp;<label for="decisionId.2"><bean:message
						key="access.nmon.adjudication.decision.deny" /></label>
				</html:radio></td>
		</tr>
		<tr>
			<td class="tdnumber"></td>
			<td class="tdmandatory"></td>
			<td class="tdnumber">&nbsp;</td>
			<td>
				<table  cellspacing="1" border="0">
					<tr>
						<td width="1%" class="tdmandatory">&nbsp;</td>
						<td class="tdnumber"><bean:message
								key="access.nmon.adjudication.dua.decision.duaeffectivedate.number" />.</td>
						<access:error property="denyEffectiveDate">
							<td class="textlabel"><label for="denyEffectiveDateId.month"><bean:message
									key="access.nmon.adjudication.dua.decision.duaeffectivedate" /></label></td>
						</access:error>
						<td class="textlabel" colspan="1"><access:calendar styleId="denyEffectiveDateId.month" 
								property="denyEffectiveDate" readonly="false" /></td>
					</tr>
					<tr>
						<td class="tdmandatory">&nbsp;</td>
						<td class="tdnumber"><bean:message
								key="access.nmon.adjudication.dua.decision.reasongfordeny.number" />.</td>
						<access:error property="reasonForDeny">
							<td class="textlabel" colspan="2"><label for="reasonForDeny"><bean:message
									key="access.nmon.adjudication.dua.decision.reasongfordeny" /></label></td>
						</access:error>
					</tr>
					<tr>
						<td class="tdmandatory">&nbsp;</td>
						<td class="tdnumber"></td>
						<%--CIF_01554 Start --%>
						<%--SIT Defect_792 --%>
						<td class="textdata" colspan="2"><html:select styleId="reasonForDeny" 
								property="reasonForDeny" onchange="enableOtherTextfield(this.value)">
								<html:option value="">
									<bean:message key="access.select" />
								</html:option>
								<html:optionsCollection name="T_MST_DUA_DECISION_DENY"
									value="key" label="description" />
							</html:select></td>
					</tr>
					<%--CIF_01127 - Non-Functional Requirements/ Error: end tag for "tr" which is not finished--%>
					<tr><td style="display:none;"></td></tr>
					<%--CIF_01127 end --%>
					<%--CIF_01127 - Non-Functional Requirements/ Error: end tag for "tr" which is not finished--%>
					<tr><td style="display:none;"></td></tr>
					<%--CIF_01127 end --%>
					<%--CIF_01127 - Non-Functional Requirements/ Error: end tag for "tr" which is not finished--%>
					<tr><td style="display:none;"></td></tr>
					<%--CIF_01127 end --%>
					<%--CIF_01127 - Non-Functional Requirements/ Error: end tag for "tr" which is not finished--%>
					<tr><td style="display:none;"></td></tr>
					<%--CIF_01127 end --%>
					<%--CIF_01127 - Non-Functional Requirements/ Error: end tag for "tr" which is not finished--%>
					<tr><td style="display:none;"></td></tr>
					<%--CIF_01127 end --%>
					<tr>
						<td class="tdnumber"></td>
						<td class="tdmandatory"></td>
						<access:error property="remarksOfDenyOther">
							<td class="textlabel" colspan="2"><label for="remarksOfDenyOtherId"><bean:message
									key="access.nmon.adjudication.dua.decision.remarksofdenyother.number" />.&nbsp;
								<bean:message
									key="access.nmon.adjudication.dua.decision.remarksofdenyother" /><br/>
								&nbsp;&nbsp;&nbsp;<span class="supporttext"> <bean:message
										key="access.nmon.adjudication.dua.decision.remarksofdenyother.helpertext" /></span></label>
							</td>
						</access:error>
					</tr>
					<%--CIF_03139 || Defect_5264 || UAT Start --%>
					<tr>
						<td></td>
						<td class="tdnumber">&nbsp;&nbsp;&nbsp;</td>
						<td class="textdata" colspan="2">&nbsp;&nbsp;&nbsp;<access:spellcheck styleId="remarksOfDenyOtherId" 
								property="remarksOfDenyOther" rows="4" cols="50" disabled="true"  onkeydown="return noCopyKey(event);" onkeypress="return imposeMaxLength(event, this, 249);"/></td>
					</tr>
					<%--CIF_03139 || Defect_5264 || UAT End--%>
				</table>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>


		<!--
		<tr>
			<td class="textlabel"></td>
			<td class="textlabel"><bean:message key="access.nmon.adjudication.dua.decision.reasongfordeny.number" />.</td>
			<access:error property="reasonForDeny">
				<td class="textlabel"><label for="reasonForDeny.1"><bean:message key="access.nmon.adjudication.dua.decision.reasongfordeny" /></label></td>
		    </access:error>
			<td class="textdata">			
		    	<html:select styleId="reasonForDeny.1"  property="reasonForDeny" >  
				<html:option value=""><bean:message key="access.select"/></html:option>
				<html:optionsCollection name="T_MST_DUA_DECISION_DENY" value="key" label="description" />
				</html:select>
		    </td>
		</tr>
		<tr>
			<td class="textlabel"></td>
			<td class="textlabel"></td>
			<access:error property="remarksOfDenyOther">
			<td class="textlabel" colspan="2"><label for="remarksOfDenyOtherId.1"><bean:message key="access.nmon.adjudication.dua.decision.remarksofdenyother.number" />.&nbsp;&nbsp;<bean:message key="access.nmon.adjudication.dua.decision.remarksofdenyother" /><br/>
			<span class="supporttext"><bean:message key="access.nmon.adjudication.dua.decision.remarksofdenyother.helpertext"/></span></label>
			</td>
			</access:error>
		</tr>
		<tr>
			<td class="textlabel"></td>
			<td class="textlabel"></td>
			<td class="textdata" colspan="2"><html:textarea  styleId="remarksOfDenyOtherId.1"  property="remarksOfDenyOther" rows="4" cols="60" /></td>
		</tr>		
-->
	</table>
	<br/>
	<br/>
	<table  class="buttontable">
		<tr>
			<td class="buttontdleft"><access:localebuttontag
	property="method" styleClass="formbutton"
	helpFileName="./html/Help/nmon/make_dua_decision.htm"
	onmouseover="this.className='formbutton formbuttonhover'"
	onmouseout="this.className='formbutton'">
	<bean:message key="access.help" />
</access:localebuttontag></td>
			<td class="buttontdright"><html:cancel styleClass="formbutton"
					onmouseover="this.className='formbutton formbuttonhover'"
					onmouseout="this.className='formbutton'">
					<bean:message key="access.back" />
				</html:cancel> <html:submit property="method" styleClass="formbutton"
					onmouseover="this.className='formbutton formbuttonhover'"
					onmouseout="this.className='formbutton'" styleId="enter">
					<bean:message key="access.submit" />
				</html:submit></td>
		</tr>
	</table>
</html:form>
