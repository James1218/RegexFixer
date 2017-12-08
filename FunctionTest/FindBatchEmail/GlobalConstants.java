package gov.state.uim;

import java.math.BigDecimal;

/**
 * This class is having all the common constants.
 * 
 * @author esc005
 * 
 */
public interface GlobalConstants {
	// total base period wages for Wyoming Specific
	int WBA_MBA_WY_RATIO = 26;
	Double MBA_TOTAL_WAGE_WY_RATIO = 0.3;
	// date time related constants
	String DATE_TIME_FORMAT_AM_PM = "MM/dd/yyyy hh:mm:ss aaa";
	String DATE_TIME_FORMAT = "MM/dd/yyyy hh:mm:ss";
	String DATE_FORMAT = "MM/dd/yyyy";
	// CIF_00687
	String HPEXTREME_DATE_FORMAT = "MM-dd-yyyy";
	String TIME_FORMAT = "HH:mm:ss";
	String SHORT_TIME_FORMAT = "HH:mm";
	String DISPLAY_TIME_FORMAT = "h:mm a";
	String DB_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	String DB_DATE_FORMAT = "yyyy-MM-dd";
	String DB_TIME_FORMAT = "HH:mm:ss";
	String SSA_DATE_FORMAT = "yyMMdd";
	String SSA_TIME_FORMAT = "HHmmss";
	String SSA_DOB_FORMAT = "MMddyyyy";
	String WIC_DATE_FORMAT = "MMddyy";
	String WIC_TIME_FORMAT = "HHmmss";
	String BIZ_CONSTANT_DATE_PRIOR_TO_INSTALLATION = "2007-06-24";
	// CIF_01044 Start
	String RJS_TIME_FORMAT = "EEEE, MMM d";
	// CIF_01044 Start
	// deprecated
	String IB5_MAINFRAME_DATE_FORMAT = "MMddyy";
	// deprecated
	String IB4_MAINFRAME_DATE_FORMAT = "MMddyy";
	// deprecated
	String FCCC_MAINFRAME_DATE_FORMAT = "yyyyMMdd";

	String MAINFRAME_DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
	String MAINFRAME_DATE_FORMAT_MMDDYYYY = "MMddyyyy";
	String MAINFRAME_DATE_FORMAT_MMDDYY = "MMddyy";
	// CIF_01389
	String TEXT_MONTH_FORMAT_MMM = "MMM";

	String MIDNIGHT = "00:00:00";
	
	//Sonar Fix Start
	String Ipaddress = "127.0.0.1";
	//Sonar Fix End
	// global constant for session listener
	String TRANSIENT_SESSION_CONSTANTS_LIST = "_TRANSIENT_SESSION_CONSTANTS_LIST";

	String SESSION_CONSTANTS_LIST = "_SESSION_CONSTANTS_LIST";

	String JAVASCRIPT_PARAMETER_NAME = "jsenabled";

	// date time related formats other than the above one
	String LONG_MONTH_DATE_FORMAT = "MMMM  d,  yyyy";
	String SHORT_MONTH_DATE_FORMAT = "MMM d, yyyy";
	String MONTH_DATE_ONLY_FORMAT = "MM/dd";
	String USER_CHECK_SESSION_ATTRIBUTE = "CLAIMFILEDBY";
	String BIZ_CONSTANT_CACHE_KEY = "BIZCONSTANT";

	String USER_LOCKED = "1";
	String USER_UNLOCKED = "0";
	String PASSWORD_CHANGE_REQUIRED = "1";
	String PASSWORD_CHANGE_NOT_REQUIRED = "0";

	String RESUME_STATUS_INCOMPLETE = "ICOM";
	String RESUME_STATUS_COMPLETE = "COMP";
	String SESSION_JOBORDER_ID = "JOBORDERID";
	String SESSION_EMPLOYER_NAME = "EMPLOYERNAME";
	String SESSION_WORKSITE_NAME = "WORKSITENAME";
	String SESSION_JOB_TITLE = "JOBTITLE";
	// String SESSION_USER_FIRST_NAME="FIRSTNAME";
	// String SESSION_USER_LAST_NAME="LASTNAME";

	String SCREENFLOW_REGISTER = "SCREENFLOW";
	String SCREENFLOW_IB6 = "SCREENFLOWIB6";

	String MDHS_DATA = "MDHSDATALIST";
	// CIF_INT_00744 || Defect_7627 Added . after message 'SAVE_SUCCESS_MSG'
	String SAVE_SUCCESS_MSG = "Your changes are saved successfully.";
	String PWD_CHANGE_REQD_MSG = "Your current password has expired. You must change your password to continue.";

	// following variables need to be removed later on
	// String SESSION_LOGIN_TYPE="LOGINTYPE";
	// String CLAIMANT_TYPE="CLMT";
	// String CSR_TYPE="CSR";
	// String EMPLOYER_TYPE="EMP";
	// CIF_00720 Generate Adhoc Correspondence
	String ADHOC_CORRESPONDENCE_CONFIRMATION_HEADER = "Adhoc Correspondence Confirmation";
	String ADHOC_CORRESPONDENCE_CONFIRMATION_MESSAGE = "Adhoc correspondence generated successfully.";
	// CIF_INT_02892 || Defect_1162
	String NO_EMPLOYERS_ERROR = "There are no valid records to process.";
	int NUM_SSNS_TO_ADD = 5;
	String LAYOFF_REASON = "LWLO";
	String LOGGED_IN = "LOGGEDIN";
	String MASSLAYOFF_SAVE_SUCCESS_HDNG = "Report Mass Layoff Confirmation";
	String FILEMASSLAYOFF_SAVE_SUCCESS_HDNG = "File Mass Layoff Confirmation";
	String MASSLAYOFF_SAVE_SUCCESS_MSG = "Your mass layoff records are saved successfully.";
	String DUADECLARATION_SAVE_SUCCESS_MSG = "Dua declaration is saved successfully";
	// added for CIF_00033 starts
	String FILE_MASS_LAYOFF_HEAD = "File Mass Layoff Confirmation";
	String FILE_MASS_LAYOFF_SUBMISSION_CONFIRMED = "File Mass Layoff Application Submission";
	String FILE_MASS_LAYOFF_SUBMISSION_MESSAGE = "Your application for a Mass Claim has been submitted with the division of Employment Security for further review. You will receieve a notification within 1-2 business days regarding the status of your application";
	// added for CIF_00141 starts
	String FILE_APPEAL_REFEREE_ACKNOWLEDGEMENT_MESSAGE_TO_APPELLANT = "The Appeals Tribunal has received your appeal.";
	String FILE_APPEAL_REFEREE_ACKNOWLEDGEMENT_MESSAGE_TO_OTHER_PARTY = "An unemployment insurance appeal has been filed which may affect you.";
	// added for CIF_00141 ends
	String MASS_LAYOFF_MANAGER_REVIEW_CONFORMATION = "Mass Layoff Application Confirmation";
	// CIF_01361 Changes in label text as per Defect_867
	String MASS_LAYOFF_MANAGER_REVIEW_MESSAGE = "The Decision regarding the Mass Layoff Application has been recorded.";
	// CIF_00033 ends
	// CIF_00090
	String SHARED_PLAN_CONFIRMATION = "Shared Work Plan Application Confirmation";
	String SHARED_PLAN_MESSAGE = "Your application for Shared Work Plan has been submitted successfully.";
	// for jreport download block size
	int REPORT_DOWNLOAD_BYTE_BLOCK_SIZE = 81920;

	// For Applicant Contact details
	String RESUME_ADVISEMENT = "resumeAdvisement";
	String JOB_ADVISEMENT = "jobAdvisement";
	String SESSION_PREV_SCREEN = "sessionprevScreen";
	String SCREEN_FLOW = "screenFlow";

	// Constants for claim intake flow type
	String CIN_FLOW_TYPE_UI = "UI";
	String CIN_FLOW_TYPE_ES = "ES";

	// CIF_INT_02978 || Defect_143
	int CW_ONLINE_REPORT_FILING_THRESHOLD = 1000;
	String WAGE_REPORT_NOT_FILED = "1";
	// Constants for claim intake employer type
	String[] QUARTER_TEXT = new String[] { "Jan-Mar", "Apr-Jun", "Jul-Sep", "Oct-Dec" };
	String CIN_MISSING_EMPLOYER_TRUE = "1";
	String CIN_MISSING_EMPLOYER_FALSE = "0";
	// constants for ES Hours per week
	// 8 hours a day
	BigDecimal DAILY_HOURS = new BigDecimal("8");
	BigDecimal MIN_QUALIFYING_WAGE = new BigDecimal("1500");
	// 5 days a week
	BigDecimal WEEKLY_HOURS = new BigDecimal(8 * 5);
	// 52 weeks a year
	BigDecimal YEARLY_HOURS = new BigDecimal(8 * 5 * 52);
	// 12 months in a year
	BigDecimal MONTHLY_HOURS = new BigDecimal(
			(GlobalConstants.NUMERIC_EIGHT * GlobalConstants.NUMERIC_FIVE * GlobalConstants.NUMERIC_FIFTY_TWO)
					/ GlobalConstants.NUMERIC_TWELVE);
	// constants for checkboxes' yes and no values
	String CHECKBOX_ON = "on";
	String CHECKBOX_OFF = "";
	String COMMERCIAL_LICENSE = "COMM";
	// CIF_INT_02804 || TAD Changes
	String WAGE_ADJUSTED = "1";
	String WAGE_NOT_ADJUSTED = "0";

	// constant for dummy ssn
	String DUMMY_SSN = "999999999";

	// in miliseconds
	int SSA_WAITING_TIME = 2000;

	// #P1_BCL_003 - changes for add charges in overpayment
	double STATE_PENALTY = 0.05;
	double FEDERAL_PENALTY = 0.15;
	double OVERPAYMENT_INTEREST = 0.1;
	double ADDITIONAL_INTEREST = 0.1;
	int MONTHS_6 = 6;
	// cif_wy(impactNumber="P1-OVP-013, P1-OVP-014, P1-OVP-015",
	// requirementId="FR_1509",
	// designDocName="06 Design Document Overpayments Overpayment Maintenance",
	// designDocSection="1.3.",dcrNo="DCR_MDDR_94", mddrNo="",
	// impactPoint="Start")
	BigDecimal STATE_PENALTY_PERCENT = new BigDecimal("5.00");
	// cif_wy(impactNumber="P1-OVP-013, P1-OVP-014, P1-OVP-015",
	// requirementId="FR_1509",
	// designDocName="06 Design Document Overpayments Overpayment Maintenance",
	// designDocSection="1.3.",dcrNo="DCR_MDDR_94", mddrNo="",
	// impactPoint="End")
	// Constants for questionnaries
	String QUE_PENDING = "PEND";
	String QUE_ANA_MEDICAL = "QUE_ANA_MEDICAL";
	String QUE_ANA_SELF_EMPLOYED = "QUE_ANA_SELF_EMPLOYED";
	String QUE_REFUSAL_TO_WORK = "QUE_REFUSAL_TO_WORK";
	String QUE_ANA_PLANNING_SCHOOL = "QUE_ANA_PLANNING_SCHOOL";
	String QUE_ANA_ATTENDING_SCHOOL = "QUE_ANA_ATTENDING_SCHOOL";
	String QUE_ANA_REASON_FULLTIME = "QUE_ANA_REASON_FULLTIME";

	// Constants for Adding Employers
	String ADD_FED_EMP_FLAG = "ADD_FED_EMP_FLAG";
	String EMPLOYER_TYPE = "EMPLOYER_TYPE";
	String ADD_EMP_FLAG = "ADD_EMP_FLAG";
	String EMP_NOT_FND = "EMP_NOT_FND";

	// Constants for the detection of seasonal employer
	String SEASONAL_EAN_UNIT = "99";

	// constants coming from JORS for login/ldap purposes
	// CIF_00066
	// Incremented to stop failure for No of attempts(This will be again changed
	// as per Requirement)
	// starts from 0
	int FAILED_LOGIN_THRESHHOLD = 3;
	int USER_PASSED = 0;
	int USER_NOT_FOUND = -1;
	int USER_NOT_ACTIVE = -2;
	int USER_PAST_THRESHHOLD = -3;
	int INCREMENT_FAILED_LOGIN_COUNT = 1;
	int RESET_FAILED_LOGIN_COUNT = 0;
	int ATTEMPTS_LOGIN_COUNT = 3;
	int ATTEMPTS_LOGIN_NEW_COUNT = 3;

	// Batch constants
	String FILE_PATH = "C://";
	String FILE_UNDERSCORE = "_";
	String FILE_DOT = ".";
	String FILE_EXT = "txt";
	String FILE_DELIMETER = ", ";
	// CIF_01389
	String PASSWORD_DELIMETER = "@";
	// CIF_01341
	String SQL_GENERIC_VALUE = "%";

	String BATCH_CIN_005 = "BATCH-CIN-005";
	String BATCH_CIN_006 = "BATCH-CIN-006";
	String BATCH_CIN_007 = "BATCH-CIN-007";
	String BATCH_CIN_008 = "BATCH-CIN-008";
	String BATCH_CIN_011 = "BATCH-CIN-011";

	String BATCH_CFM_001 = "BATCH-CFM-001";

	String BATCH_CIN_CAF = "BATCH-CIN-CAF";

	String BATCH_CIN_DTU = "BATCH-CIN-DTU";

	String BATCH_CIN_CHG = "BATCH-CIN-CHG";

	String BATCH_CIN_RPT = "BATCH_CIN_RPT";

	String BATCH_CIN_EBN = "BATCH-CIN-EBN";

	String BATCH_CIN_INS = "BATCH-CIN-INS";

	String BATCH_CIN_SSN = "BATCH-CIN-SSN";

	int BATCH_IB4_RESPONSE_WAIT_PERIOD = 5;
	String BATCH_IB4_RESPONSE_INCOMING_TYPE = "IB4RI";
	String BATCH_IB4_RESPONSE_OUTGOING_TYPE = "IB4RO";
	String BATCH_IB4_REQUEST_OUT = "IB4QO";

	String BATCH_MON_IB4_RESPONSE_IN_001 = "IB4RESPONSEIN_001";
	String BATCH_MON_IB4_RESPONSE_IN_002 = "IB4RESPONSEIN_002";
	String BATCH_MON_IB4_REQUEST_IN_001 = "IB4REQUESTIN_001";

	String BATCH_ES93X_001 = "ES93X_001";

	// fccc batch ids
	String BATCH_FCCC_RESPONSE = "FCCCRESPONSE_001";

	// Claim Status
	// Initial Claim
	String CIN_CLAIM_STATUS_INC = "INC";
	// Active Claim
	String CIN_CLAIM_STATUS_ACC = "ACC";
	// Saved Common Intake Form
	String CIN_CLAIM_STATUS_SCI = "SCI";
	// Additional Initial Claim
	String CIN_CLAIM_STATUS_AIC = "AIC";
	// Reopen Claim
	String CIN_CLAIM_STATUS_ROC = "ROC";

	// // Intervening Employment
	// String CIN_FLOW_TYPE_INT_EMP = "INT";
	// // Active Claim
	// String CIN_FLOW_TYPE_ACC = "ACC";
	// // Completed Claim
	// String CIN_FLOW_TYPE_CMT = "CMT";

	// Back Date Reason
	String T_MST_REASON_BACKDATE0 = "0";
	String T_MST_REASON_BACKDATE1 = "1";
	String T_MST_REASON_BACKDATE2 = "2";

	// Claim Application Type
	// String CIN_CLAIM_APP_TYPE_NEW = "N";
	// String CIN_CLAIM_APP_TYPE_AIC = "A";
	// String CIN_CLAIM_APP_TYPE_ROC = "R";

	// Messages
	// String CIN_GENERAL_MESSAGE = "CIN_GENERAL_MESSAGE";
	// String CIN_MESSAGE_1 = "CIN_MESSAGE_1";
	// String CIN_MESSAGE_2 = "CIN_MESSAGE_2";
	// String CIN_MSG_INT_EMP =
	// "You should go to Weekly Certification.";

	// for Tax
	String ENTERED_BY_EMPLOYER = "Employer";
	String DUMMY_EMPLOYER_ROLE = "dummyemployer";
	String EMPLOYER_ADMIN_ROLE = "employer";
	String EMPLOYER_BENEFITS_ROLE = "employerbenefitsuser";
	// CIF_INT_00427
	String EMPLOYER_TAX_REPORTING_ROLE = "employerusertaxfunctions";
	String CORPORATE_PARTNER_ROLE = "corporatemaintenance";
	String TAX_EMPLOYER_ACCOUNT_INQUIRY_ROLE = "taxemployeraccountinquiry";
	String TAX_EMPLOYER_REPORT_INQUIRY_ROLE = "taxemployerreportinquiry";
	// CIF_P2_00947 Start
	String EMPLOYER_USER_ROLE = "employer";
	String INCOMPLETE_EMPLOYER_USER_ROLE = "incompleteemployer";
	// CIF_P2_00947 End
	// CIF_INT_04471 TCS NAICS Update , UIM-6324
	String T_MST_PRINCIPLE_ACTIVITY1_TYPE = "T_MST_PRINCIPLE_ACTIVITY1_TYPE";
	String T_MST_PRINCIPLE_ACTIVITY2_TYPE = "T_MST_PRINCIPLE_ACTIVITY2_TYPE";
	String T_MST_PRINCIPLE_ACTIVITY3_TYPE = "T_MST_PRINCIPLE_ACTIVITY3_TYPE";
	String EMPLOYER_USER_TYPE = "EMPL";
	String EMPLOYER_REGISTRATION_ORIGIN = "A";
	String ANSWER_YES = "Y";
	String ANSWER_NO = "N";
	String QUARTER_ONE = "1";
	String QUARTER_TWO = "2";
	String QUARTER_THREE = "3";
	String QUARTER_FOUR = "4";
	String REPORT_EXIST_FLAG = "REPORT_EXIST_FLAG";
	String REPORT_RECIEVED_DATE = "REPORT_RECIEVED_DATE";
	String EMPLOYER_MASTER_USER_TYPE = "M";
	String EMPLOYER_ADDITIONAL_USER_TYPE = "A";
	int MAX_EMPLOYER_USER_COUNT = 5;
	int MAX_EMPLOYER_USER_COUNT_WITH_MASTER_USER = 6;
	String EMPLOYER_FINANCE_TYPE_OPTION_ONE = "1";
	String EMPLOYER_FINANCE_TYPE_OPTION_TWO = "2";
	String EMPLOYER_FINANCE_TYPE_OPTION_THREE = "3";
	char PAD_ZERO = '0';
	char QUESTION_MARK_CHAR = '?';
	int EAN_SECOND_PART_LENGTH = 5;
	String SEARCH_REGISTERED_EMPLOYER_QUERY_NAME = "RegisteredEmployerSearch";
	String SEARCH_EMPLOYER_RESET_PASSWORD_QUERY_NAME = "EmployerResetPasswordSearch";
	String SEARCH_INCOMPLETE_EMPLOYER_REGISTRATION_QUERY_NAME = "IncompleteEmployerRegistrationSeach";
	String SEARCH_INCOMPLETE_EMPLOYER_REGISTRATION_QUERY_NAME_EXTERNAL_USER = "IncompleteEmployerRegistrationSeachForExternalUser";
	String SEARCH_CRITERIA_EMPLOYER_ENTITY_NAME = "entityname";
	String SEARCH_CRITERIA_EMPLOYER_TRADE_NAME = "tradename";
	String SEARCH_CRITERIA_EMPLOYER_EAN = "employerean";
	String SEARCH_CRITERIA_EMPLOYER_FEIN = "fein";
	String SEARCH_CRITERIA_EMPLOYER_USER_ID = "userId";
	String SEARCH_CRITERIA_EMPLOYER_CITY = "employercity";
	String SEARCH_CRITERIA_EMPLOYER_STATUS = "status";
	String SEARCH_CRITERIA_EMPLOYER_FIELD_REP = "fieldRep";
	String SEARCH_CRITERIA_EMPLOYER_STATUS_EFFECTIVE_BEGIN_DATE = "statusEffectiveBeginDate";
	String SEARCH_CRITERIA_EMPLOYER_STATUS_EFFECTIVE_END_DATE = "statusEffectiveEndDate";

	BigDecimal BIGDECIMAL_ZERO = new BigDecimal("0.00");
	String CHECK_BOX_SELECTED = "on";
	String TP_ADDRESS_TYPE = "MAIL";
	String THIRD_PARTY_SEARCH_CRITERIA_THIRD_PARTY_NAME = "thirdpartyname";
	String THIRD_PARTY_SEARCH_CRITERIA_THIRD_PARTY_FEIN = "thirdpartyfein";
	// CIF_P2_00137
	String SEARCH_THIRD_PARTY_QUERY_NAME = "thirdpartysearch";
	String SEARCH_CDSV_QUERY_NAME = "cdsvsearch";
	String SEARCH_PAYROLL_SERVICES_QUERY_NAME = "payrollservicesearch";
	// CIF_P2_00087 - Search for CDS Vendor
	String CDS_VENDOR_SEARCH_CRITERIA_CDS_VENDOR_NAME = "cdsVendorName";
	String CDS_VENDOR_SEARCH_CRITERIA_CDS_VENDOR_FEIN = "cdsVendorFein";
	String SEARCH_CDS_VENDOR_QUERY_NAME = "CDSVendorSearch";
	String EMPLOYER_REGISTRATION_SESSION_BEAN = "EMPLOYER_REGISTRATION_SESSION_BEAN";
	String COTTON_GIN_NAICS_CODE = "115111";
	String UNCLASSIFIED_NAICS_CODE = "999999";
	String EARLIEST_EMPLOYMENT_DATE = "01/01/1925";
	String ZERO_FEIN = "000000000";
	String ATTENTION = "Attention";
	// Escrow account eans
	String[] ESCROW_ACCOUNTS_TO_CHECK = new String[] { "85001000", "85002000" };

	// Business Constants user in Employer Registration
	String BIZ_CONSTANT_BENIFIT_RATIO_LEVEL1 = "BENIFIT_RATIO_LEVEL1";
	String BIZ_CONSTANT_BENIFIT_RATIO_LEVEL2 = "BENIFIT_RATIO_LEVEL2";
	String BIZ_CONSTANT_BENIFIT_RATIO_LEVEL3 = "BENIFIT_RATIO_LEVEL3";
	String BIZ_CONSTANT_BENIFIT_RATIO_LEVEL4 = "BENIFIT_RATIO_LEVEL4";
	String BIZ_CONSTANT_TRAINING_RATE_LEVEL1 = "TRAINING_RATE_LEVEL1";
	String BIZ_CONSTANT_TRAINING_RATE_LEVEL2 = "TRAINING_RATE_LEVEL2";
	String BIZ_CONSTANT_TRAINING_RATE_LEVEL3 = "TRAINING_RATE_LEVEL3";
	String BIZ_CONSTANT_TRAINING_RATE_LEVEL4 = "TRAINING_RATE_LEVEL4";
	String BIZ_CONSTANT_STANDARD_RATE_BY_LAW = "STANDARD_RATE_BY_LAW";
	String BIZ_CONSTANT_UI_RATE_LEVEL1 = "UI_RATE_LEVEL1";
	String BIZ_CONSTANT_UI_RATE_LEVEL2 = "UI_RATE_LEVEL2";
	String BIZ_CONSTANT_UI_RATE_LEVEL3 = "UI_RATE_LEVEL3";
	String BIZ_CONSTANT_UNADJUSTED_RATIO = "UNADJUSTED_RATIO";
	String BIZ_CONSTANT_ADD_TO_UNADJUSTED_RATIO = "ADD_TO_UNADJUSTED_RATIO";
	String BIZ_CONSTANT_DOMESTIC_LIABLE_WAGE = "DOMESTIC_LIABLE_WAGE";
	String BIZ_CONSTANT_AGRICULTURAL_LIABLE_WAGE = "AGRICULTURAL_LIABLE_WAGE";
	String BIZ_CONSTANT_ALL_OTHER_EMPLOYER_LIABLE_WAGE = "ALL_OTHER_EMPLOYER_LIABLE_WAGE";
	String BIZ_CONSTANT_NONP_LIABLE_EMPLOYEE_COUNT = "NONP_LIABLE_EMPLOYEE_COUNT";
	String BIZ_CONSTANT_AGRICULTURAL_LIABLE_EMPLOYEE_COUNT = "AGRICULTURAL_LIABLE_EMPLOYEE_COUNT";
	String BIZ_CONSTANT_ALL_OTHER_EMPLOYER_LIABLE_EMPLOYEE_COUNT = "ALL_OTHER_EMPLOYER_LIABLE_EMPLOYEE_COUNT";
	String BIZ_CONSTANT_LOCAL_GOVRNMENT_RATE_REIMBURSABLE_2 = "LOCAL_GOVRNMENT_RATE_REIMBURSABLE_2";
	String BIZ_CONSTANT_LOCAL_GOVRNMENT_RATE_REIMBURSABLE_5 = "LOCAL_GOVRNMENT_RATE_REIMBURSABLE_5";
	String BIZ_CONSTANT_NEW_EMPLOYER_RATE = "NEW_EMPLOYER_RATE";
	String BIZ_CONSTANT_NEW_EMPLOYER_UI_RATE = "NEW_EMPLOYER_UI_RATE";
	String BIZ_CONSTANT_NEW_EMPLOYER_TRAINING_RATE = "NEW_EMPLOYER_TRAINING_RATE";
	String COLLEGE_UNIVERSITY_HOSPITAL_EAN_FIRST_PART = "93";
	String LOCAL_GOVERNMENT_OPTION_THREE_EAN_FIRST_PART = "89";
	String LOCAL_GOVERNMENT_OPTION_TWO_EAN_FIRST_PART = "92";
	String LOCAL_GOVERNMENT_OPTION_ONE_EAN_FIRST_PART = "87";
	String NON_PROFIT_EAN_FIRST_PART = "90";
	String STATE_GOVERNMENT_EAN_FIRST_PART = "91";
	String DIFFERENT_COUNTY_EAN_FIRST_PART = "83";
	String NO_BUSINESS_LOCATIONS = "84";
	String DEFAULT_FIELD_REPRESENTATIVE_ID = "00000";
	String BIZ_CONSTANT_BASE_PERIOD_QT_FIRST = "5";
	String BIZ_CONSTANT_BASE_PERIOD_QT_LAST = "2";

	String HINDS_COUNTRY_CODE = "025";

	int ADDRESS_VALIDATION_REQUIRED_CONFIDENCE_LEVEL = 90;

	String UPDATE_REPORT_APPROVAL_MODE = "UPDATE_REPORT_APPROVAL_MODE";
	String IS_USER_CSR = "IS_USER_CSR";

	String UI_CONTRIBUTION = "UICO";
	String TRAINING_CONTRIBUTION = "TRCO";
	String INTEREST = "INTR";
	String PENALTY = "PENL";

	String CREDIT_ADJUSTMENT = "CA";
	String TAX_REPORT_ADJUSTMENT = "TA";

	String TOTALWAGEONLY = "TOTALWAGEONLY";
	String ASSESSEDQUARTERYEARLIST = "ASSESSEDQUARTERYEARLIST";

	String DIRECTION_BOTH_CORRESPONDENCE = "ALL";

	String TO = "to";

	// KEY DESCRIPTION UPDATED_TS UPDATED_BY
	//
	// UICO UI Contribution 2006-05-02 15:29:28.008827 SYSTEM
	// TRCO Training Contribution 2006-05-02 15:29:28.020097 SYSTEM
	// INTR Interest 2006-05-02 15:29:28.032143 SYSTEM
	// PENL Penalty 2006-05-02 15:29:28.044107 SYSTEM
	// PROC Processing Cost 2006-05-02 15:29:28.056185 SYSTEM

	String PREVIOUS_PAGE = "PREVIOUS_PAGE";

	// Global constants for roles
	String CLAIMANT_ROLE = "claimant";
	String CLAIMANT_NO_SELF_SERVICE_ROLE = "claimantnoself";
	String THIRDPARTY_ROLE = "thirdparty";
	// Constants used in WBA calculation. WBA is Hight quarter wage divided by
	// 26
	int WBA_HIGH_QUARTER_RATIO = 26;
	// Constants used in MBA calculation. MBA is 26 time WBA or one third of
	// total base period wages.
	int WBA_MBA_RATIO = 20;

	int MBA_TOTAL_WAGE_RATIO = 3;
	// CIF_00278 Start
	// Constants used for WBA and MBA calculation.
	int WBA_FIRST_SECOND_AVG = 2;
	double WBA_QUARTERS_RATIO = 0.04;
	int MBA_MAX_QRT_WAGE = 26;
	double WBA_TOTAL_WAGE_RATIO = 1.5;
	// CIF_00278 END

	// Ratio between wba and total BP wages for monetary eligibility
	// int WBA_TOTAL_WAGE_RATIO = 40;

	// Number of days to be added in effective date to calculate BYE
	int BYE_DAYS = 363;

	// Max amount of wages and charges added in update rate
	String MAX_WAGES = "9999999999.99";
	String MAX_CHARGES = "99999999.99";
	// CIF_P2_00666 Max amount of Contribution for update tax rate -- Start
	String MAX_CONTRIBUTION = "99999999.99";
	// CIF_P2_00666 Max amount of Contribution for update tax rate -- End
	String SYSTEM_USER_ID = "SYSTEM";

	String ACTIVE_ISSUE_FLAG = "1";
	String INACTIVE_ISSUE_FLAG = "0";

	String WAGE_USED_FLAG = "0";
	// CIF_01363 Changed from MO Local Office 0380 to 9999
	String DEFAULT_LOCAL_OFFICE = "9999";
	// issue and stop constants
	String[] ISSUE_CODES_TO_CHECK = new String[] { "313", "314", "315", "322", "321", "300", "400" };
	// correspondence constants
	String EXR_21 = "EXR-21";
	String EXR_8B = "EXR-8B";
	String EXR_8C = "EXR-8C";
	String EXR_8D = "EXR-8D";
	String EXR_8E = "EXR-8E";
	String EXR_8F = "EXR-8F";
	String EXR_8G = "EXR-8G";
	String EXR_8S = "EXR_8S";
	String EXRS = "EXRS";
	String UI_4A = "UI-4A";
	String UI_4 = "UI-4";
	String CAS_002 = "CAS-002";
	String UI_501A = "UI-501A";
	String UI_505 = "UI-505";
	String UI_563 = "UI-563";
	String ES_931A = "ES-931A";
	String EMP_FLAG = "EMPFLAG";
	String CLMT_FLAG = "CLMTFLAG";
	String OTHR_FLAG = "OTHRFLAG";
	String APPL_FLAG = "APPEALFLAG";
	String EEER_FLAG = "EEERFLAG";
	String FEIN_FLAG = "FEINFLAG";
	String SSA_INVALID = "SSA";
	// corresepondence constants for TWR
	String TW_OUT_OF_BALANCE_REPORT = "TWOB";

	// SSN status
	String SSA_STATUS_PENDING = "P";
	String SSA_STATUS_INVALID = "I";
	String SSA_STATUS_VALID = "V";

	String MORE_INFO_FLAG = "MORE_INFO_FLAG";
	String BACK_DIRECTION_FLAG = "BACK_DIRECTION_FLAG";
	String CURRENT_EMP_NO = "CURRENT_EMP_NO";

	// Potential Issue - Applied for Unemployment Insurance to Other State
	String POT_ISSUE_UNEMP_INS = "POT_ISSUE_UNEMP_INS";

	String POT_ISSUE_ID = "POT_ISSUE_ID";

	/***** Tax & Wage Reporting starts ************/

	String CONVENIENCE_FEE_MAP = "ConvenienceFeeMap";
	String TOTAL_WAGE_SAME_AS_TR = "totWageSameAsTR";
	String TAX_REPORT_EXISTS = "taxReportExists";
	String WAGE_REPORT_EXISTS = "wageReportExists";
	String MMREF = "MMRF";
	String NEW_ICESA = "NISA";
	String TIB4 = "TIB4";
	String OLD_ICESA = "OISA";

	String ONLINE_MODE = "ON";
	String FILE_UPLOAD_MODE = "FU";
	String DATA_ENTRY = "DE";
	String FTP = "FT";
	String CREATED_BY_MIGRATION = "MIGRATION";
	String BATCH_ADP = "1111";
	String BATCH_PAYCHECK = "2222";
	String BATCH_ADVANTAGE = "3333";
	String BATCH_OA = "4444";
	String BATCH_MAGNETIC_MEDIA = "5555";
	String OUTSIDE_VENDOR = "7777";
	String MDES_EAN = "9100136001";
	String CHECK21_WAGES = "9999";

	/*
	 * String TWR_PREV_SCREEN_VERIFY_WAGE = "tax&WagePreviousScreen"; String
	 * TWR_UPLOAD_WAGE_FILE_ACTION = "twruploadwagefile"; String
	 * TWR_ONLINE_WAGE_ACTION = "twremployeewagesonline"; static final String
	 * TWR_UPDATE_WAGE_REPORT_REQUEST_ACTION = "twrrequestwrforupdatewagesinfo";
	 * String TWR_UPDATE_TAX_REPORT_ACTION = "twrupdatewagesinfo"; String
	 * TWR_VERIFY_TOTAL_WAGE_ACTION = "twrverifywageinfo"; static final String
	 * TWR_WORK_QUEUE_TAX_REPORT_UPDATE_ACTION =
	 * "twrworkqueueapprovetaxreportupdate"; String
	 * TWR_ONLINE_PAYMENT_INFO_SUBMITTED_BY_ACTION =
	 * "twronlinepaymentinfosubmittedby"; String
	 * TWR_CSR_TAXWAGE_REPORT_ENTRY_ACTION = "twrcsrtaxwagereportentry"; public
	 * String TWR_BUSINESS_QUESTIONNAIRE_ACTION = "twrbusinessquestions"; String
	 * TWR_ONLINE_PAYMENT_ACTION = "twronlinepayment"; String
	 * TWR_CONTACT_DETAILS_ACTION = "twrcontactdetails";
	 */
	String DEFAULT_UPDATE_REASON = "Total Wage Discrepency";
	String COMMA_SEPARATOR = ", ";
	String SINGLE_SPACE_SEPARATOR = " ";
	String QUARTER_YEAR_SEPARATOR = "/";
	String EAN_SEPARATOR = "-";
	String PIPE_SEPARATOR = "|";
	String PAYMENT_DUE = "Y";
	String TAX_REPORT_EXIST_ONLY = "1";
	String WAGE_REPORT_EXIST_ONLY = "2";
	String NOTAX_NOWAGE_REPORT_EXIST = "0";
	String TAX_WAGE_REPORT_EXIST = "3";
	String TAX_REPORT_ONLINE_PAYMENT_PAY_CHECKBOX_DEFAULT = "0";
	String DATA_ENTRY_ROWS_NUMBER = "25";
	String SSN_LENGTH = "9";

	String SUCCESS_DATA_ENTRY = "SUCCESS_DATA_ENTRY";
	String SUCCESS_REQUEST_FOR_TAX_REPORT_UPDATE = "SUCCESS_REQUEST_FOR_TAX_REPORT_UPDATE";
	String SUCCESS_TRANSFER_CREDITS = "SUCCESS_TRANSFER_CREDITS";
	String SUCCESS_MAINTAIN_EMPLOYER_CONTACT_PERSON = "SUCCESS_MAINTAIN_EMPLOYER_CONTACT_PERSON";
	String DELETED = "1";
	String SIGN_PLUS = "+";
	String SIGN_MINUS = "-";
	String RADIO_TURNED_OFF_VALUE = "0";
	String RADIO_TURNED_ON_VALUE = "1";
	String EMPTY_STRING = "";
	String DB_EMPTY_STRING_FOR_NULLS = "";
	String DB_SINGLE_SPACE_STRING_FOR_NULLS = " ";
	// New Plcash005 start
	String DB_FOUR_SPACE_STRING_FOR_NULLS = "    ";
	// New Plcash005 end
	String BLANK_SPACE = " ";
	String ZERO = "0";
	String ONE = "1";
	String TWO = "2";
	String WAGE_DELETED = "0";
	String WAGE_NOT_DELETED = "1";
	// CIF_P2_00377 addition of flag for multistate and probationary
	String MULTISTATE_FLAG = "1";
	String NOT_MULTISTATE_FLAG = "0";
	String PROBATIONARY_FLAG = "1";
	String NOT_PROBATIONARY_FLAG = "0";
	// CIF_P2_00377 End
	String DB_ANSWER_YES = "1";
	String DB_ANSWER_NO = "0";
	String DEFAULT_EMPLOYEE_NUMBER = "0";
	int TRANSFER_CREDIT_DEFAULT_ROW_NUMBER = 12;
	String WAGE_REPORT_DATA_MAP = "WageReportList";
	String TAX_ADD_EMPLOYER_CONTACT_PERSON_DATA_ID = "contactPersonId";
	String CURRENT_USER_TYPE = "CURRENT_USER_TYPE";
	String QUARTERS_LIST = "QUARTERS_LIST";
	String DATA_ENTRY_WAGE_REPORT = "Detailed Employee Wages";
	String INVOICE_KEY = "TaxReportPayment";
	String HYPHEN_SEPARATOR = "-";
	String FORWARDSLASH_SEPARATOR = "/";
	String CREDIT_CARD_FOUR_DIGIT_MASK = "****";
	String TOTAL_WAGE_DISCREPENCY_UPDATE_REASON = "Difference in total wages from Tax Report and Wage Report";
	String DETAILED_WAGE_READY_FOR_USE = "1";
	String DETAILED_WAGE_NOT_READY_FOR_USE = "0";
	String WORK_WITH_TEMP_ONLY = "WorkWithTempTablesOnly";
	String TEXT_FILE_EXTENSION = ".txt";
	String ALL = "ALL";
	String VALUES_FROM_TABLE = "valuesFromTable";
	String TWR_ADD_INTEREST_DAMAGE_DATE_ID = "interestDamageID";
	/***** Tax & Wage Reporting ends ************/

	String ES_931A_REQUEST_ID = "ES_931A_REQUEST_ID";
	String ES_931_REQUEST_ID = "ES_931_REQUEST_ID";
	String ES_934_REQUEST_ID = "ES_934_REQUEST_ID";

	String ES_931A_RESPONSE_ID = "ES_931A_RESPONSE_ID";
	String ES_931_RESPONSE_ID = "ES_931_RESPONSE_ID";
	String ES_934_RESPONSE_ID = "ES_934_RESPONSE_ID";
	// first part of the name for the fact finding questionaire
	String FACT_FINDING_QE_NAME = "FACT_FINDING_";
	String FACT_FINDING_MISSING_FLAG = "1";
	String CARRY_OVER_ISSUE_YES = "1";
	String CARRY_OVER_ISSUE_NO = "0";
	String MULTI_CLMT_ISSUE_YES = "1";
	String MULTI_CLMT_ISSUE_NO = "0";
	String INDEFINITE_DATE = "09/09/9999";
	// INDEFINITE_DATE_DB_FORMAT_yyyy_MM_dd
	String INDEFINITE_DATE_DB_FORMAT_YYYY_MM_DD = "9999-09-09";
	String INDEFINITE_DATE_MMDDYYYY = "09099999";
	int MINIMUN_PASSWORD_LENGHT = 4;
	String ISSUE = "I";
	String DECISION = "D";
	String NA = "N/A";
	String MISSISSIPPI_STATE = "Mississippi";
	// global constant for work queue screen
	String WORK_QUEUE_HOLD_DATE = "holdDate";
	String WORK_QUEUE_HOLD_COMMENT = "comments";

	String USER_AVAIL_AVAILABILITY = "accessavailstatus";
	String USER_AVAIL_START_DATE = "accessavailstartdate";
	String USER_AVAIL_END_DATE = "accessavailenddate";
	String USER_AVAIL_UNIT_TYPE = "accessuserunittype";

	//
	String ONLINE_PAYMENT_BY_MAIL_LIST = "ONLINEPAYMENTBYMAILLIST";
	String RETURN_CODE = "RETURN_CODE";

	// Monetary (IB4) related constants
	String BLANK_MESSAGE_ID = "000000";
	String IB14_REQUEST_NOT_PROCESSED = "0";
	String IB14_REQUEST_PROCESSED = "1";
	String IB4_REQUEST_PROCESSED = "1";
	String IB4_REQUEST_NOT_PROCESSED = "0";

	// Monetary Reconsideration Related Constants
	String MIN_WAGE = "0";
	String MAX_WAGE = "99999999.99";
	String WAGES_NOT_AVAILABLE = "NOT AVAILABLE";
	String MON_RECON_ADD_MILITARY = "ADD_MILITARY";
	String MON_RECON_ADD_FEDERAL = "ADD_FEDERAL";
	String MON_RECON_ADD_OTHER_STATE = "ADD_OTHER_STATE";

	// NonMonetary related constants
	String EIGHT_X_MET = "EIGHT-X-MET";
	String UI21A_ACTION = "UI21A-ACTION";
	String RECONSIDERATION_REQUESTED_BY = "RECONSIDERATION-REQUESTED-BY";
	String EMPLOYER = "EMPR";
	String CLAIMANT = "CLMT";
	String DECISION_INDEFINITE_END_DATE = "09/09/9999";
	// Restrict the creation of this default user
	String DEFAULT_PROCESS_START_USER = "default_process_start_user";
	String FACTFINDING_MULTICLAIMANT_FLAG = "FACTFINDING_MULTICLAIMANT_FLAG";

	// CQ : R4UAT00024371. Investigation default user name
	// "default_inv_usr";
	String DEFAULT_INVESTIGATION_USER = "default_investigator";
	String BIZ_CONSTANT_TIMELY_WI_PERCENTAGE_SEP = "TIMELY_WI_PERCENTAGE_SEP";
	String BIZ_CONSTANT_TIMELY_WI_PERCENTAGE_NSEP = "TIMELY_WI_PERCENTAGE_NSEP";
	String BIZ_CONSTANT_TIMELY_PAID_WI_PERCENTAGE_SEP = "TIMELY_PAID_WI_PERCENTAGE_SEP";
	String BIZ_CONSTANT_TIMELY_PAID_WI_PERCENTAGE_NSEP = "TIMELY_PAID_WI_PERCENTAGE_NSEP";
	String BIZ_CONSTANT_NON_TIMELY_PAID_WI_PERCENTAGE_SEP = "NON_TIMELY_PAID_WI_PERCENTAGE_SEP";
	String BIZ_CONSTANT_NON_TIMELY_PAID_WI_PERCENTAGE_NSEP = "NON_TIMELY_PAID_WI_PERCENTAGE_NSEP";
	String BIZ_CONSTANT_INVESTIGATOR_TIMELAPSE_DAY_NSEP = "INVESTIGATOR_TIMELAPSE_DAYS_SEP";
	String BIZ_CONSTANT_ADJUDICATOR_TIMELAPSE_DAY_NSEP = "ADJUDICATOR_TIMELAPSE_DAYS_SEP";
	String BIZ_CONSTANT_INVESTIGATOR_TIMELAPSE_DAY_SEP = "INVESTIGATOR_TIMELAPSE_DAYS_NSEP";
	String BIZ_CONSTANT_ADJUDICATOR_TIMELAPSE_DAY_SEP = "ADJUDICATOR_TIMELAPSE_DAYS_NSEP";

	// Added For Weekly Certification Process
	String CLAIM_DATA = "CLAIM_DATA";
	String CLAIMANT_DATA = "CLAIMANT_DATA";
	String CLAIMANT_DOES_NOT_EXIST = "CLAIMANT_DOES_NOT_EXIST";
	String CURRENT_WEEK = "CURRENT_WEEK";
	String TWO_WEEKS = "TWO_WEEKS";
	String NO_WEEKS = "NO_WEEKS";
	String NO_CLAIM = "NO_CLAIM";
	String NO_SSN_FOUND = "SSN_DOES_NOT_EXISY";
	String MONETARILY_INELIGIBLE = "MONETARILY_INELIGIBLE";
	String ISSUES_PENDING = "ISSUES_PENDING";
	String MORETHAN_TWOWEEKS = "MORETHAN_TWOWEEKS";
	String BENEFITS_EXHAUSTED = "BENEFITS_EXHAUSTED";
	String DISQUALIFICATION_PERIOD = "DISQUALIFICATION_PERIOD";
	String BYE_EXPIRED = "BYE_EXPIRED";
	String NO_PENDING_WEEKS = "NO_PENDING_WEEKS";
	String LIST_CWES_TO_CERTIFY = "LIST_CWES_TO_CERTIFY";
	String CLAIM_EFF_DATE = "CLAIM_EFF_DATE";
	String CURRENT_CWE = "CURRENT_CWE";
	String LAST_CERTIFIED_DATE = "LAST_CERTIFIED_DATE";

	String MESSAGES_LIST = "MESSAGES_LIST";
	String MESSAGES_MAP = "MESSAGES_MAP";

	// Added for the selection of work items for workflow admin role
	String ALL_UNITS = "allunits";

	// Added for Holiday List
	String HOLIDAY_LIST = "T_MST_HOLIDAY_LIST";

	// Added for DD214
	String CLIAMDATA = "CLAIMDATA";
	String BEAN = "BEAN";
	String SSN = "SSN";
	String WRONG_DATE = "WRONG_DATE";
	// added for ES931,ES931A,ES934
	// Defect_272 - changed the FIPS code from 29 to 56.
	String STATE_CODE = ApplicationProperties.TENANT_STATE_FIPS_CODE;
	String WRONG_DATA = "WRONG_DATA";

	// Added for Job fair Set Up Functionality
	String FLOW_FLAG = "FLOW_FLAG";
	String FLOW_FLAG_ADD = "FLOW_FLAG_ADD";
	String FLOW_FLAG_EDIT = "FLOW_FLAG_EDIT";

	// Business constant for monetary reconsideration request
	String BIZ_CONSTANT_NO_OF_DAYS_MON_RECONSIDERATION_REQUEST = "NO_OF_DAYS_MON_RECONSIDERATION_REQUEST";
	// Added for IB4Response
	// String RECV_SEND_USER_ID = "MS.CWC-UNIT"; Changing value after discussion
	// with STATE CIF_00854
	// Changing value after discussion with STATE CIF_INT_03286||Defect_1463
	// @cif_wy(impactNumber="P1-OOS-37",
	// requirementId="FR_1620",designDocName="01 IB4
	// Processing",designDocSection="1.1",
	// dcrNo="", mddrNo="",impactPoint="start")
	String RECV_SEND_USER_ID = "WY.CWC-UNIT";
	String IB4Q_RECV_SEND_USER_ID = "WY.CWC     ";
	// @cif_wy(impactNumber="P1-OOS-37",
	// requirementId="FR_1620",designDocName="01 IB4
	// Processing",designDocSection="1.1",
	// dcrNo="", mddrNo="",impactPoint="end")

	// Changing value after discussion with STATE CIF_INT_03286||Defect_1463

	// String RESP_RECV_SEND_USER_ID = "MO.CWC ";

	String SEND_RECV_USER_ID = "IB4R";
	// String IB4RESPONSE_LOCAL_OFFICE_CODE = "1234";Changing value after
	// discussion with STATE CIF_00854
	String IB4RESPONSE_LOCAL_OFFICE_CODE = "9999";

	String CORRESPONDENCE_SEARCH_FLOW_TYPE = "EMPLOYERSEARCH";
	String CORRESPONDENCE_SEARCH_FLOW = "CORRESPONDENCE_SEARCH_FLOW";

	String IW_CLAIM = "IWCL";
	String ISSUE_ID = "ISSUE_ID";
	String FCCC_TYPE1_RECORD_TYPE = "1";
	String FCCC_TYPE2_RECORD_TYPE = "2";
	String FCCC_TYPE3_RECORD_TYPE = "3";
	String FCCC_TYPE4_RECORD_TYPE = "4";
	String FCCC_TYPE5_RECORD_TYPE = "5";
	String FCCC_TYPE6_RECORD_TYPE = "6";
	// Added for the different flows of correspondence
	String UI_21A_QUICK_LAUNCH = "UI_21A_QUICK_LAUNCH";
	String UI_21A_EMPLOYER_SEARCH = "UI_21A_EMPLOYER_SEARCH";

	// Added for Es931 assiginibility check
	String[] FCCC_MANUAL_PROCESS_MESSAGE_LIST = { "006", "007", "009", "011", "012", "013", "014", "015", "017", "018",
			"024", "025", "027", "028", "029", "030", "031", "034", "035", "036", "037", "038", "039", "040", "041" };
	String[] CONTROL_RECORD_MESSAGE_LIST = { "006", "009", "012", "013", "025" };
	// message 023 was added 11/28/2007 (defect4791) - these do not create
	// work-items
	// CIF_03154
	String[] FCCC_ERROR__MESSAGE_LIST = { "001", "002", " 003", "004", "005", "006", "007", "008", "009", "010", "011",
			"012", "013", "014", "015", "016", "017", "018", "019", "020", "021", "022", "023", "024", "025", "026",
			"027", "028", "029", "030", "031", "032" };
	String[] TYPE2_ACCEPTED_MESSAGE_LIST = { "023", "006" };
	String[] TYPE3_ACCEPTED_MESSAGE_LIST = { "023", "026", "006" };
	String[] TYPE4_ACCEPTED_MESSAGE_LIST = { "023", "019", "006" };
	String[] TYPE5_ACCEPTED_MESSAGE_LIST = { "023", "026", "006" };
	String[] TYPE6_ACCEPTED_MESSAGE_LIST = { "023", "003", "006" };
	String[] TYPE2_DO_NOT_PROCESS_LIST = { "023" };
	String UI_INELIGIBILITY = "UI_INELIGIBILITY";
	String DUA_DENY_OTHER_PARA = "DuaDenyOtherPara";
	String UI_501_CORR_ID = "UI501CorrId";
	String DUA_1_CORR_ID = "DUA1CorrId";
	String DUA_12_CORR_ID = "DUA12CorrId";

	String FIRST_DAY_OF_MONTH = "01";
	String FIRST_MONTH_OF_YEAR = "01";
	String LAST_DAY_OF_MONTH = "31";
	String LAST_MONTH_OF_YEAR = "12";

	// Non mon constants for Affirm / Reverse for Between Term changes
	String AFFIRMED = "AFFIRMED";
	String REVERSED = "REVERSED";

	// following string constants are used in Dua Decision Letter generation
	// process.
	String DUA_DECISION_ALLOW_PRIORTAX = "allowpriortax";
	String DUA_DECISION_ALLOW = "allow";
	String DUA_DECISION_DENY_OTHER = "DOTR";
	String DUA_DECISION_DENY = "deny";
	String DUA_PRIOR_TAX_YEAR = "priorTaxYear";
	String DUA_CURRENT_WEEKLY_PAYMENT = "duaCurrentWeeklyPayment";
	String DUA_DISASTER_DATE = "disasterDate";
	String DUA_EFFECTEIVE_DATE = "duaEffectiveDate";
	String DUA_ANNOUNCEMENT_DATE_1 = "announcementDate1";
	String DUA_ANNOUNCEMENT_DATE_2 = "announcementDate2";
	String DUA_FILING_END_DATE = "duaFileEndDate";
	String DUA_FILING_DATE = "duaFileDate";
	String DUA_NAME_OF_EMPLOYER = "nameOfEmployer";
	String DUA_TERMINATE_DATE = "duaTerminateDate";
	// DUMMY VARIABLE.. NEED
	String YEAR = "priorTaxYear";
	// TO REMOVE
	String DUA_NOT_TIMELY_PARA_ANDT12FEDT = "anDt12FeDt";
	String DUA_NOT_TIMELY_PARA_ANDT1FEDT = "anDt1FeDt";
	String DUA_NOT_TIMELY_PARA_NODATE = "nodate";
	String DUA_DECISION_ALLOW_PROOF_OF_EMPLOYMENT = "allowPfOfEmpl";
	String DUA_21_DAYS_FROM_CLAIM_FILE_DATE = "21DaysFromFileDate";

	// added for dd214 response,members mentioned here cannot be allowed for
	// filling up dd214
	/*
	 * commented not in use String[] dd214mem = { "MEM5", "MEM4" };
	 */

	String[] DD214MEMALLOWEDPENDINGFCCC = { "MEM5", "MEM4" };
	// dd214charOfService
	String[] DD214_CHAR_OF_SERVICE = { "HO", "UH", "NA", "GS", "DR", "VE", "SE" };
	// dd214pendingmessage
	String[] DD214_PENDING_MESSAGE = { "002" };

	String ISSUE_INDEFINITE_DATE = "9999-09-09";
	// Added for verifyEanForMissingEmployer Constants
	String CONTAINS_VERIFY_EAN_ACCESS_FUNCTION = "YES";
	String GET_VERIFY_EAN_ACCESS_FUNCTION = "VERIFY_EAN";
	String ACTIVITY_LOCATEEAN = "LocateEAN";

	String DMS_SEARCH_RESULT = "DmsSearchResult";
	String TFR_COMMENT_FOR_EAN_VERIFICATION = "Could not verify EAN";
	// CIF_P2_00164 - Added for Blocked Claim Screen
	String CSR_COMMENT_FOR_EAN_VERIFICATION = "Could not verify EAN";
	String NUMBER_WEEK_ENDING_DATES_ENTER_OVERPAYMENT = "26";
	String BRANCH_OF_SERVICE = "BranchofService";
	String NEW_ER_SCHEDULE = "NEW_ER_SCHEDULE";
	String NOT_APPLICABLE = "N/A";
	String CLAIM_EFFECTIVE_DATE = "effectiveDate";
	// code added for throwing message in email for rejected ssn from fccc
	// response batch
	String TRANSFERRING_STATE = "IB4 Request cannot be generated as transferring state for this ssn is null.The SSN is:";

	String MDES_CALL_CENTER_LOC_OFF_ID = "0380";

	String BETWEEN_TERMS_START_DATE = "5/1/2000";
	String BETWEEN_TERMS_END_DATE = "8/31/2000";
	String SEASON_START_DATE = "09/1/2000";
	String SEASON_END_DATE = "12/31/2000";

	String BETWEEN_TERMS_SEPARATION = "1/1/2000";

	String CLAIMANT_LOGIN_COMMENT_APPENDER = " - (Self Service).";

	String INCORRECT_WAGE_DECISION = "Incorrect Wage Resolved";
	String MISSING_EMPLOYER_DECISION = "Missing Employer Resolved";

	String ADDRESS_ISSUE = "ADDRESS_ISSUE";

	// CSS Classes for the Workflow search results
	String DISPLAY_CURRENT_DUE_DATE_COLOR = "disp_current_due_date";
	String DISPLAY_PAST_DUE_DATE_COLOR = "disp_past_due_dates";
	String DISPLAY_NEAR_FUTURE_DUE_DATE_COLOR = "disp_near_future_due_date";

	// BOR Access Level constants
	String BOR = "BOR";
	String TAX_APPEAL_BOR = "Tax_Appeal_BOR";
	String TAX_INTERCEPT_BOR = "Tax_Intercept_BOR";
	String BOR_ACCESS_LEVEL = "Level";

	// Constants defined for Appellant and Opponent
	String APPELLANT = "APNT";
	String OPPONENT = "OPNT";
	// Constants for Interpreter in Appeals
	String INTERPRETER = "NOIP";
	String MDES_PARTY_TYPE = "DES";

	String REPROCESS_CHARGES_ALLOW_DECISION = "REPROCESS_CHARGES_ALLOW_DECISION";
	String REASON_FOR_REDE = "Reason_For_Redetermination";
	String PROCEED_FURTHER = "Proceed_further";
	String REPROCESS_FLAG = "reprocess_flag";
	// CIF_00238 starts
	// added for Add Resolution Search
	String SEARCH_RESOLUTION_AND_PROBLEM_SUMMARY_BY_KEYWORD = "SearchResolutionByKeyword";
	String SEARCH_RESOLUTION_AND_PROBLEM_SUMMARY_BY_PROBLEM_SUMMARY = "SearchByProblemSummary";
	// added for CIF_00238 end
	String SEARCH_HEARING_BY_DOCKET_NUMBER_QUERY_NAME = "SearchHearingByDocketNumber";
	String SEARCH_HEARING_BY_EAN_QUERY_NAME = "SearchHearingByEAN";
	String SEARCH_HEARING_BY_SSN_QUERY_NAME = "SearchHearingBySSN";
	String SEARCH_HEARING_BY_ALJ_HEARING_DATE_QUERY_NAME = "SearchHearingByALJAndHearingDate";
	String SEARCH_BOR_MEMBER_DOCKET_QUERY_NAME = "SearchBORMemberDocket";

	/**********************
	 * Added to enable Back flow Navigation from multiple screens--Reetesh
	 ********************/
	String FLOW_PARENT_ENTEREANFORTAXAPPEAL = "entereanfortaxappealBack";
	String FLOW_PARENT_LISTCOMPLETEDALJLEVELTAXAPPEAL = "listcompletedaljleveltaxappealBack";
	String FLOW_PARENT_FILETAXAPPEAL = "filetaxappealBack";
	String FLOW_PARENT_FILETAXAPPEALBOR = "filetaxappealborBack";
	/**********************
	 * Back flow Navigation from multiple screens Ends--Reetesh
	 ********************/

	String NAME_MISMATCH = "NameMissmatch";

	/***************** TRA/ATAA/TAA starts *********************/
	int DB2_UNIQUE_CONSTRAINT_VIOLATION_CODE = -803;
	String SEARCH_EMPLOYER_FOR_ADD_PETITION_QUERY_NAME = "EmployerSearchForAddPetition";
	String SEARCH_PETITION_QUERY_NAME = "SearchPetition";
	// -- Added by Battu for Search Facility
	String SEARCH_FACILITY = "TrainingFacilitySearch";
	String SEARCH_T_CLAIM_BY_TRA = "searchTraClaimFromClaim";
	// --Till here
	// added for Training Waiver Status
	String WAIVER_STATUS_ISSUED = "ISSD";
	String WAIVER_STATUS_EXTENDED = "EXTD";
	String WAIVER_STATUS_REVOKED = "RVKE";
	String WAIVER_STATUS_DENY = "DENY";
	// Till here
	/***************** TRA/ATAA/TAA ends *********************/
	String MESSAGE_FLAG = "1";
	/* added for external hearing transcription */
	String DOC_FILE_EXTENSION = ".doc";

	String ISSUE_DECISIONS = "Issue Decisions";
	String RECALL_APPEAL_DECISION = "Recall Appeal Decision";
	String NONE = "None";
	String INCORRECT_WAGE = "Resolved Incorrect Wage";
	String MISSING_EMPLOYER = "Resolved Missing Employer";
	String DUA_UNANNOUNCED_COUNTY_LIST = "UnAnnouncedCountyList";
	int DUA_FILABLE_DAYS_FROM_FIRST_ANNOUNCEMENT = 30;

	String MONDAY = "Monday";

	String TUESDAY = "Tuesday";

	String WEDNESDAY = "Wednesday";

	String THURSDAY = "Thursday";

	String FRIDAY = "Friday";

	String SSN_PARAMETER = "ssn";
	String EAN_PARAMETER = "ean";
	// CIF_00606:START
	String DOCUMENT_ID_PARAMETER = "documentId";
	String DOCUMENT_PATH_PARAMETER = "documentPath";
	String DOCUMENT_IMAGE_INDEX = "documentImageIndex";
	// CIF_00606:END
	String FEIN_PARAMETER = "_fein";
	String EEER_PARAMETER = "investigationID";
	String DOCKET_NUMBER_PARAMETER = "docketno";
	String CORR_ID_PARAMETER = "corrId";
	String MISSING_EMP_TFR_ASSIGNMENT_ZIP_CODE = "tfrassignmentzipcode";

	String MON_RECON_INCORRECT_WAGES_OTHER_STATE = "ADD_OTHER_STATE";

	// TO USE DIFFERENT FTP SERVER FOR DIFFERENT BATCHES
	String FTP_SERVER_PREFIX = "FTP_HOST_";
	String FTP_UNAME_PREFIX = "FTP_USER_";
	String FTP_PASSWD_PREFIX = "FTP_PASSWORD_";

	// TO USE DIFFERENT SSH SERVER FOR DIFFERENT BATCHES
	String SSH_SERVER_PREFIX = "SSH_HOST_";
	String SSH_UNAME_PREFIX = "SSH_USER_";
	String SSH_PASSWD_PREFIX = "SSH_PASSWORD_";
	String SSH_PORT_PREFIX = "SSH_PORT_";

	String RQRQ_CLAIM = "PODD";

	String EXECUTE_MULTIPLE_SEARCH_QUERIES = "EXECUTE_MULTIPLE_SEARCH_QUERIES";

	// Btq
	String BTQ_VALIDATE_INTERVAL = "Interval";
	String BTQ_VALIDATE_NONMONETARY_SAMPLE = "Nonmonetary Quality Sample";
	String BTQ_VALIDATE_APPEAL_SAMPLE = "Lower Authority Appeals Quality Sample";
	String PARALLEL_CHARGE_LIST_OF_EMPLOYERS = "ListOfEmployers";

	// Wave 5 - BAM starts here

	String APPOINTMENT_TRACKING = "appointmenttracking";

	String GENERATE_CORRESPONDENCE = "generatecorrespondence";

	String SUMMARY_PAID = "summarypaid";

	String SUMMARY_MONETARY = "summarymonetary";

	String SUMMARY_NONMONETARY = "summarynonmonetary";

	String SUMMARY_SEPARATION = "summaryseparation";

	String DCI_BAM = "dcibam";

	String DCI_DCA = "dcidca";

	String REOPEN_CASE = "reopencase";

	// CIF_00921 Start
	String INITIAL_CORRESPONDENCE = "INITIAL";

	String FINAL_CORRESPONDENCE = "FINAL";
	// CIF_00921 End

	// Wave 5 - BAM ends here
	// wave 4 starts here
	String BIZCONSTANT_OFFSET_PREFIX = "OFFSET";
	String BIZCONSTANT_OFFSET_SEPARATOR = "-";
	String DECIMAL_ZERO = "0.00";
	// BCL_149 : Initiative test
	String DECIMAL_ZERO_ONE_PLACE = "0.0";

	// wave 4 ends here
	String CHECK_REPRINT_REQUEST = "RREQ";
	String CHECK_REPRINT_APPROVED = "RAPR";
	String CHECK_REPRINTED = "REPR";
	String PROCCESSED_FLAG_SAVE = "0";
	String PROCCESSED_FLAG_LOAD = "1";
	String DEFAULT_RESIDENCE_COUNTY = "999";

	int EB_CLAIM_NO_OF_WEEKS = 13;
	int TRA_WAGE_BEAN_LIST_CONSTANT = 8;
	// CIF_01519
	int TRA_WAGE_BEAN_LIST_CONSTANT1 = 5;
	int TRA_LIST_SIZE_CONSTANT = 5;

	String SESSION_CHECK_NOS = "SESSION_CHECK_NOS";

	String TYPE_CLAIMANT_PAYMENT = "TypeClaimantPayment";
	// CIF_03387 Start
	String TYPE_ATAA_PAYMENT = "AtaaPaymentType";
	String TYPE_TAA_PAYMENT = "TaaPaymentType";
	// CIF_03387 End
	String TYPE_IRORA_PAYMENT = "TypeIroraPayment";
	String CHECK_REPRINT_REQUESTOR = "requestor";
	String CHECK_REPRINT_APPROVER = "approver";

	String HCTC_TRAPAYMENT_ELIGIBLE = "HctcTraPaymentEligible";
	String HCTC_BEAN = "Hctcinfobean";

	String QUALIFYING_PERIOD_SEPARATOR_STRING = " to ";

	// TRA Training Status values
	String TRAINING_IN_PROGRESS = "Training in progress";
	String NO_TRAINING = "No Training";
	String TRAINING_COMPLETED = "Training Completed";

	String BIZ_CONSTANT_BACK_DATE_DAYS = "DAYS_TO_WAIT_FOR_CLAIM_BACK_DATE";
	String STRING_TWO = "2";
	String STRING_THREE = "3";
	String STRING_FOUR = "4";
	String STRING_ZERO = "0";
	String STRING_ONE = "1";
	String START_CWE_DATE = "06/25/2007";

	String MDES_CALL_CENTER_LOC_OFF_PHONE = "";
	String CONTACT_NOTICE_BY_TELEPHONE = "1";
	String CONTACT_NOTICE_IN_PERSON = "2";

	String IBIQ_MORE_DATA_AVAILABLE = "More Data Exists";
	String IBWI_RESPONSE_AVAILABLE = "IBWIRESPONSE";
	BigDecimal NOT_APPLICABLE_NUMBER_DASHBOARD = new BigDecimal("-999999");

	String EXCEL_FILE_EXTENSION = ".xls";
	String WORD_FILE_EXTENSION = ".doc";
	String PDF_FILE_EXTENSION = ".pdf";

	String TEUC_BYE = "05/23/2010";

	// Tax Adjustment Batch
	String CREDIT_MISSMATCH = "CREDIT_MISSMATCH";
	String TRANSACTION_IN_AUDIT = "TRANSACTION_IN_AUDIT";
	String NO_MATCHING_TRANSACTION = "NO_MATCHING_TRANSACTION";
	String MATCHING_CREDIT = "MATCHING_CREDIT";
	String NO_RATE_RECORD = "NO_RATE_RECORD";
	String CREDIT = "CREDIT";
	String DEBIT = "DEBIT";
	double ROUNDING_PRECISION = 0;
	double ROUNDING_PRECISION_ONE = 1.00;
	int REFUND_REQUEST_MAX_ROWS = 20;

	String DOL_TIMELAPSE = "DOL_TIMELAPSE_INV_ADJUDICATION";
	String DOL_TIMELAPSE_NUMBER_OF_DAYS = "18";

	String RETURNED_TO_WORK = "Returned to Work/Job Attached";
	String MOVED_OUT_OF_STATE = "Moved(Out of State)";
	String IN_APPROVED_TRAINING = "In Approved Training";
	String ALREADY_PROFILED = "Already Profiled";
	String REFUSED_TO_BE_PROFILED = "Refused to be Profiled";
	int TAX_CREDIT_TRANSFER_CUTOFF_START_YEAR = 2006;
	String TAX_REFUND_BEGINING_CHECK_NUMBER = "TAX_REFUND_BEGINING_CHECK_NUMBER";
	String EMPLOYER_TAX_REFUND_SEQUENCE = "EREF";

	String CREATEDBYUSER = "createdByUser";

	String EMPLOYER_USER_ID = "user";

	String A_AND_A_ISSUE = "Did not report A&A";
	String PRODUCTION_ROLL_OUT_DATE = "03/21/2009";

	String TEUC_REACHBACK_TYPE = "TEUC_REACHBACK_TYPE";

	String WARANT_RENEWAL_OVERPAYMENT_BAL_AMT = "WR_REN_OVP_BAL_AMT";

	String CLAIM_AUTO_CANCELLED = "CLAIM_AUTO_CANCELLED";
	String CLAIM_MANUAL_CANCELLED = "CLAIM_MANUAL_CANCELLED";
	String CLAIM_TYPE_AMENDED_IB4 = "3";
	String MDES_TAX_DEPT = "MDES TAX DEPT";
	String LDAP_FUNC_TYPE_TAX_CACHE_KEY = "TAX_FUNCTIONS";
	String LDAP_FUNC_TYPE_BENEFITS_CACHE_KEY = "BENEFITS_FUNCTIONS";
	String GARNISHMENT_LIST_PROCESSED_CWE_CHECK_DAYS = "GARNISHMENT_LIST_PROCESSED_CWE_CHECK_DAYS";
	String SHOW_EMAIL_IN_CORRESPONDENCE_MODE = "SHOW_EMAIL_IN_CORRESPONDENCE_MODE";
	String QUESTION_MARK = "?";
	String EQUALS_TO = "=";
	String EMAIL_VERIFICATION_MESSAGE = "EMAIL_VERIFICATION_MESSAGE";
	String EMAIL_BROADCAST_GROUP_QUERY_APPENDER = "_EMAIL_BROADCAST";
	String REPORTCONFIG_CORRESPONDENCE_MODE_PARAMETER = "corresmodeparam";
	String EMAIL_CORRESPONDENCE_MODE_PARAMETER = "1";

	String QTR_TAG_YR_ALL = "ALL";
	String EMAIL_ADDRESS_UPDATED = "EMAIL_ADDRESS_UPDATED";

	Integer MAX_NUM_QTR_FOR_UI23 = 12;

	String USER_WORK_ITEMS = "USER_WORK_ITEMS";
	String APP_WORK_ITEM_LIST = "APP_WORK_ITEM_LIST";

	String APPEAL_AUTO_SCHEDULE_MAX_DAYS = "APPEAL_AUTO_SCHEDULE_MAX_DAYS";
	String APPEAL_AUTO_SCHEDULE_MIN_DAYS = "APPEAL_AUTO_SCHEDULE_MIN_DAYS";
	// CIF_P2_00813 Start
	// 5MB size limit for attachment
	Integer QUICK_RESPONSE_ATTACHMENT_LIMIT = 1024 * 5120;
	String[] QUICK_ACCESS_SUPPORTED_FILE_FORMATS = { "txt", "pdf", "docx", "jpeg", "jpg" };
	// CIF_P2_00813 End
	String ADDITIONAL_WEEKS_FOR_QUICK_ACCESS = "OVPMT_INVE_ADDITIONAL_WEEK";

	String[] UPLOAD_DOCUMENTS_DMS_SUPPORTED_FILE_FORMATS = { "txt", "pdf", "doc", "docx", "xls", "xlsx", "tiff", "tif",
			"jpg", "jpe", "jfif", "jpeg", "TXT", "PDF", "DOC", "DOCX", "XLS", "XLSX", "TIFF", "JPEG", "JPG", "JPE",
			"JFIF", "TIF" };
	String TIF_FILE_EXTENSION = ".tif";
	String JPEG_FILE_EXTENSION = ".jpeg";
	String TIFF_FILE_EXTENSION = ".tiff";
	String XLSX_FILE_EXTENSION = ".xlsx";
	String WORD_DOCX_FILE_EXTENSION = ".docx";
	String JPG_FILE_EXTENSION = ".jpg";
	String JPE_FILE_EXTENSION = ".jpe";
	String JFIF_FILE_EXTENSION = ".jfif";
	// CIF_P2_01300
	String[] UPLOAD_DOCUMENTS_LIRC_DECISION = { "PDF", "pdf" };
	Integer UPLOAD_DOCUMENTS_DMS_LIMIT = 1024 * 1024;

	String FIC_HOMELAND_SECURITY = "574";

	// CIF_00220
	// Added for the ResetClaimantUserPassword Heading
	String RESETCLAIMANTUSERPASSWORD_SAVE_SUCCESS_HDNG = "Reset Account Confirmation";

	// CIF_00400
	Integer MIN_CERTIFICATE_REQUEST_DAYS = 35;
	BigDecimal MIN_TOTAL_FRAD_OVERPAYMENT_AND_PENALTYBAL_AMT = new BigDecimal("250.00");
	// secondsInADay
	long SECONDS_IN_A_DAY = 24 * 60 * 60 * 1000;

	// CIF_00140
	String WEEKLY_CERT_ONLINE = "Online";
	Integer WEEKLY_CERT_ZERO = 0;
	String NPND = "NPND";
	String NSEP = "NSEP";
	String NAAP = "NAAP";

	// CIF_00473
	String CERTIFICATE_CANCELLATION_REASON = "AGER";

	// CIF_00589
	BigDecimal MIN_OVP_DEBT = new BigDecimal("250.00");
	String ASSESSMENTSERVICE = "ASSESSMENT SERVICE";
	String COMMENT_UPDATE_ASSESSMENT = "Assessment is remailed as regular";

	// CIF_00550 start
	// A discharge will have 6x WBA and a quit or work refusal will have 10x WBA
	// @CIF_WY(impactNumber="P1-BRD-002", requirementId="FR_731",
	// designDocName="01 Process Documents",
	// designDocSection="2.2.4.2.1", dcrNo=" ",
	// mddrNo=" ", impactPoint="Start")
	BigDecimal DISCHARGE_WBA_MULTIPLIER = new BigDecimal("12.00");
	BigDecimal QUIT_WORK_REFUSAL_WBA_MULTIPLIER = new BigDecimal("8.00");
	// @CIF_WY(impactNumber="P1-BRD-002", requirementId="FR_731",
	// designDocName="01 Process Documents",
	// designDocSection="2.2.4.2.1", dcrNo=" ",
	// mddrNo=" ", impactPoint="End")
	// CIF_00550 end

	// CIF_00691
	int GARNISHMENT_ACTIVE_DAYS = 180;
	// CIF_02223 - Defect_2713 - Percentage value - Start
	String GARNISHMENT_PERCENTAGE = "25%";
	// CIF_02223 - Defect_2713 - Percentage value - End
	int CHILD_SUPPORT_THRESHOLD_GARNISHMENT_PERCENTAGE = 25;

	// CIF_00691 Start
	// New Constants
	String WAGEGARNISHMENTSOURCEWAGE_WAGEKEY = "WAGE";
	String WAGEGARNISHMENTSOURCEWAGE_WAGEVALUE = "WAGE";
	String WAGEGARNISHMENTSOURCEWAGE_BANKKEY = "Bank Account";
	String WAGEGARNISHMENTSOURCEWAGE_BENAKVALUE = "BANK";
	// CIF_00691 End

	// CIF_00671
	// to set dummy data
	String DUMMY = "dummy";

	// CIF_00114 start
	// Intrastate crossmatch criteria hits
	String WEEKS = "3";
	String WAGES = "1000";
	String PROCESSED_FLAG = "0";
	int IS_SUBMITTED = 1;
	// CIF_00114 end

	// CIF_00651 start
	String YES = "YES";
	String NO = "NO";
	// CIF_00651 end

	// added to suppress check style 'magic number' warnings
	// CIF_01389
	int NUMERIC_MINUS_THREE = -3;
	int NUMERIC_ZERO = 0;
	int NUMERIC_ONE = 1;
	int NUMERIC_MINUS_TWO = -2;
	int NUMERIC_TWO = 2;
	int NUMERIC_THREE = 3;
	int NUMERIC_FOUR = 4;
	int NUMERIC_FIVE = 5;
	double NUMERIC_POINT_ONE = 0.1;
	// CIF_01214
	// aaded numeric six
	int NUMERIC_SIX = 6;
	int NUMERIC_SEVEN = 7;
	int NUMERIC_EIGHT = 8;
	int NUMERIC_NINE = 9;
	// CIF-879 - Starts
	int NUMERIC_TEN = 10;
	int NUMERIC_ELEVEN = 11;
	int NUMERIC_TWELVE = 12;
	int NUMERIC_THIRTEEN = 13;
	int NUMERIC_FOURTEEN = 14;
	int NUMERIC_FIFTEEN = 15;
	int NUMERIC_SIXTEEN = 16;
	int NUMERIC_SEVENTEEN = 17;
	int NUMERIC_EIGHTEEN = 18;
	int NUMERIC_NINTEEN = 19;
	int NUMERIC_TWENTY_ONE = 21;
	int NUMERIC_TWENTY_TWO = 22;
	int NUMERIC_TWENTY_THREE = 23;
	int NUMERIC_TWENTY_FOUR = 24;
	int NUMERIC_TWENTY_FIVE = 25;
	int NUMERIC_TWENTY_SIX = 26;
	int NUMERIC_TWENTY_SEVEN = 27;
	int NUMERIC_TWENTY_EIGHT = 28;
	int NUMERIC_TWENTY_NINE = 29;
	int NUMERIC_THIRTY = 30;

	int NUMERIC_THIRTY_ONE = 31;
	int NUMERIC_THIRTY_TWO = 32;
	int NUMERIC_THIRTY_THREE = 33;
	int NUMERIC_THIRTY_FOUR = 34;
	int NUMERIC_THIRTY_FIVE = 35;
	int NUMERIC_THIRTY_SIX = 36;
	int NUMERIC_THIRTY_SEVEN = 37;
	int NUMERIC_THIRTY_EIGHT = 38;
	int NUMERIC_THIRTY_NINE = 39;
	int NUMERIC_FOURTY = 40;
	int NUMERIC_FOURTY_ONE = 41;
	int NUMERIC_FOURTY_TWO = 42;
	int NUMERIC_FOURTY_THREE = 43;
	int NUMERIC_FOURTY_FOUR = 44;
	int NUMERIC_FOURTY_FIVE = 45;
	int NUMERIC_FOURTY_SIX = 46;
	int NUMERIC_FOURTY_SEVEN = 47;
	int NUMERIC_FOURTY_EIGHT = 48;
	int NUMERIC_FOURTY_NINE = 49;
	// @cif_wy(impactNumber = "P1-BCL-034,P1-BCL-035,P1-BCL-036", requirementId
	// = "FR_1516", designDocName =
	// "10 Overpayment Waiver Handling Bad Addresses.docx", designDocSection =
	// "2.2", dcrNo = " ", mddrNo = " ", impactPoint = "Start")
	int NUMERIC_FIFTY = 50;
	// @cif_wy(impactNumber = "P1-BCL-034,P1-BCL-035,P1-BCL-036", requirementId
	// = "FR_1516", designDocName =
	// "10 Overpayment Waiver Handling Bad Addresses.docx", designDocSection =
	// "2.2", dcrNo = " ", mddrNo = " ", impactPoint = "End")
	int NUMERIC_FIFTY_NINE = 48;
	int NUMERIC_FIFTY_TWO = 52;
	// CIF_02689
	int NUMERIC_FIFTY_SIX = 56;
	int NUMERIC_SIXTY = 60;
	// CIF_01213
	int NUMERIC_NINETY = 90;
	// CIF_P2_00137
	int NUMERIC_HUNDRED = 100;
	int NUMERIC_THOUSAND = 1000;
	int NUMERIC_FOUR_THOUSAND = 4000;// CIF_INT_02317 || Defect_740
	int NUMERIC_TEN_THOUSAND = 10000;
	int NUMERIC_MINUS_ONE = -1;
	int NUMERIC_TWO_THOUSAND_FIVE_HUNDRED = 2500;
	int NUMERIC_FIVE_HUNDRED_TWELVE = 512;
	int NUMERIC_TWENTY = 20;
	int NUMERIC_ONE_HUNDRED_EIGHTY = 180;
	int NUMERIC_TWO_HUNDRED = 200;
	int NUMERIC_NINETY_NINE = 99;
	// CIF_01211

	int NUMERIC_THIRTYFIVE = 35;
	// CIF-595 - Starts
	BigDecimal NUMBER_OF_QUARTERS = new BigDecimal(4);
	String TERMINATION_PAY_AMOUNT = "terminationPayAmount";
	String QUARTER_TERMINATION_PAY_AMOUNT = "quarterTerminationPayAmount";
	// CIF-595 - Ends

	// CIF_00672
	String COMMA = ",";
	// CIF_00117
	Integer DAYS_FROM_DUA_FILE_DATE = 21;

	// CIF_00704
	Integer CERTIFIED_MAIL_DIGITS_WITHOUT_CHECKDIGIT = 19;
	Integer NUMBER_TEN = 10;

	// CIF_00795 START B-238
	// Interstates
	// Missouri
	String MO = "MO";
	// Delaware
	String DE = "DE";
	// Virgin Islands
	String VI = "VI";
	// CIF_00795 End
	String NAME = "NAME";

	// CIF_00082 START: Add Transaction ID type
	String TRANS_INSERT = "1";
	String TRANS_DELETE = "2";
	// CIF_00082 END
	// CIF_00215 Start : Defining trade act year related variable
	String TRADE_ACT_YEAR_2009 = "2009";
	String TRADE_ACT_YEAR_2011 = "2011";
	// CIF_00215 end
	// CIF_INT_04303 || Defect_544
	String TRADE_ACT_YEAR_2015 = "2015";
	// CIF_00437
	String UNIT_ID = "0121";

	// CIF_00343 Start
	// constants defined for re-send packet data in update appeal
	String APPEAL_PARTY_EMPLOYER = "Employer";
	String APPEAL_PARTY_CLAIMANT = "Claimant";
	String APPEAL_PARTY_SPOUSE = "Spouse";
	String DIVISION_WITNESS = "Division Witness";
	String PARTY = "Party";
	String PARTY_1 = PARTY + SINGLE_SPACE_SEPARATOR + NUMERIC_ONE;
	String PARTY_2 = PARTY + SINGLE_SPACE_SEPARATOR + NUMERIC_TWO;
	String PARTY_3 = PARTY + SINGLE_SPACE_SEPARATOR + NUMERIC_THREE;
	String PARTY_4 = PARTY + SINGLE_SPACE_SEPARATOR + NUMERIC_FOUR;
	String PARTY_5 = PARTY + SINGLE_SPACE_SEPARATOR + NUMERIC_FIVE;
	// CIF_00343 End

	// CIF_01078 Start
	String COLE_COUNTY = "051";
	// CIF_01078 End
	// CIF_01081 Start
	String INDEX_VALUE = "INDEX_VALUE";
	// CIF_01042
	BigDecimal BIGDECIMAL_ONE = new BigDecimal("1.00");

	// CIF_00758
	String WI_MAP_VALUES = "wimapvalues";

	// CIF_00829
	String STLOUIS_WARRANT_COUNTY = "189";

	// CIF_01088 Start
	String SHOW_MONTH = "MM";
	String SHOW_DAY = "DD";
	String SHOW_YEAR = "YYYY";
	// CIF_01099 End

	// CIF_00563
	int DUA_END_DATE_WEEK = 26;
	int DAYS_IN_YEAR = 365;

	// CIF_00511 Start
	BigDecimal MIN_WAGE_PER_QUARTER_FOR_GARN_5000 = new BigDecimal("5000.00");
	BigDecimal MIN_WAGE_PER_QUARTER_FOR_GARN_6000 = new BigDecimal("6000.00");
	int DAYS_SINCE_EWI_LETTER_SENT = 180;
	String WAGESOURCE = "WAGE";
	String BATCH_GEN_GARN_INV = "BatchGenerateGarnishmentInvest";
	// CIF_00511 End
	// CIF_01278 - Starts
	String ISBACK = "isBack";
	// CIF_01278 - Ends

	// CIF_00105- START-Payment Defect FIX
	String NONEPAYMENTS = "NONE";
	String DISCHARGED = "DIS";
	String SUSPENDED = "SUSP";
	String QUIT = "QUIT";
	String REFUSED = "REF";
	String FLOW_TYPE = "flowType";
	BigDecimal BIGDECIMAL_400_01 = new BigDecimal("400.01");
	// CIF_00105- END
	// CIF_01221 - Starts
	String PRENOTE = "PREN";
	String DELETE = "DEL";
	String CASE5 = "Payment mode changed to EPC with Preactive status";
	String CASE9 = "Payment mode changed to DD with Prenote/Reactivate status";
	String CASE12 = "DD status changed to Delete";
	String CASE10 = "DD status changed to Active after serving prenote period";
	// @cif_wy(impactNumber="P1-BAD-024", requirementId="FR_1621",
	// designDocName="01 Update Claimant Profile review approve",
	// designDocSection="2.1", dcrNo="", mddrNo="", impactPoint="Start")
	String CASE11 = "Payment mode changed to Check with Prenote/Reactivate status";
	// @cif_wy(impactNumber="P1-BAD-024", requirementId="FR_1621",
	// designDocName="01 Update Claimant Profile review approve",
	// designDocSection="2.1", dcrNo="", mddrNo="", impactPoint="End")
	String REACTIVE = "REAC";
	// CIF_01221 - Ends

	// payment defect_41 fix
	String FROM_WEEKLY_CERT = "fromweeklycert";

	// CIF_01329 start
	String SKIPPED_WEEK = "skippedweek";
	String SKIPPED_WEEK_AIC_SAVED = "skippedweekaicsaved";
	String SKIPPED_WEEK_IN_BETWEEN = "skippedweekinbetween";
	String FROM_SKIPPED_WEEK_WCMAIN = "fromskippedweekwcmain";
	// CIF_01329 end
	// Added GlobalConstants for set aside
	String SET_ASIDE_1 = "SETASIDE1";
	String SET_ASIDE_1_PARA_1 = "Set Aside 1 Paragraph 1.";
	String SET_ASIDE_1_PARA_2 = "Set Aside 1 Paragraph 2.";
	String SET_ASIDE_2 = "SETASIDE2";
	String SET_ASIDE_2_PARA_1 = "Set Aside 2 Paragraph 1.";
	String SET_ASIDE_2_PARA_2 = "Set Aside 2 Paragraph 2.";
	String SET_ASIDE_3 = "SETASIDE3";
	String SET_ASIDE_3_PARA_1 = "Set Aside 3 Paragraph 1.";
	String SET_ASIDE_3_PARA_2 = "Set Aside 3 Paragraph 2.";

	String DUA_CLAIM = "DUA";
	String TRA_CLAIM = "TRA";
	String REG_CLAIM = "REG";

	String DEFAULT_PIN = "1111";
	// CIF_01375
	String APPEAL_CONFIRM_SCREEN = "appealconfirmscreen";

	String BANK_ACCOUNT_NUMBER_ZERO = "0000000000000000";
	// CIF_01451
	String RECOVERY_UNIT_HEAD_ROLE = "recoveryUnitHead";

	// CIF_01395 Veracode Change
	String KEY_ALGORITHM = "PBEWithMD5AndDES";
	int ENCRYPTION_DECRYPTION_KEY_COUNT_VALUE = 20;
	int CRYPTO_SECURITY_ENCRYPTION_VALUE = 20;
	// CIF_01467
	int NUMBER_FIFTY_FIVE = 55;

	// CIF_01478 || I2 Determination
	int NUMBER_FIFTY_ONE = 51;

	// CIF_01465 start
	// Changes for password policy
	// Defect_426 Start
	String ITSD = "ITSD";
	String BUSINESS_USER = "DWS Staff";
	String DES_STAFF = "DWS Staff";
	String IT_STAFF = "IT Staff";
	String BUSINESS_DWS_USER = "DWS Staff";
	String EXTERNAL_STATE_STAFF = "External State Staff";
	String ETS_STAFF = "ETS Staff";
	// Defect_426 End
	
	// CIF_01465 end
	// CIF_01150
	String PDF_EXTENSION_OSCA = ".pdf";
	String PROTECTED_CONFIG_CHARSET = "UTF-8";
	String BASE_PERIOD_WF_PRIORITY = "BSPD";
	// CIF_01629 Start
	int NUMERIC_FIVE_HUNDRED = 500;
	// CIF_01629 End
	// CIF_00109 Start
	// Information Request Upload document limit
	Integer IR_UPLOAD_DOCUMENTS_LIMIT = 2048 * 1024;
	// CIF_00109 End
	// CIF_01684 - Starts
	String FRAUD = "FRAD";
	String AGENCY = "AGCY";
	String EMPLOYEROP = "EMPL";
	String NONFRAUD = "NFRD";
	String IDENTHEFT = "IDTH";
	int DEFAULT_YEAR = 9999;
	// CIF_01684 - Ends

	// CIF_01659
	String FROM_WC_EMPLOYMENT_DETAIL = "fromwcemploymentdetail";
	String ENTITLEMENTTYPEREGULAR = "REG";
	String FACC = "FACC";
	String FACOP = "FACOP";
	String REGOP = "REGOP";
	String SOURCE = "Overpayment Source";
	String CREATEDBY = "Created By";
	String MODES1660 = "ProcessMODES1660Overpayment";
	String MODES4396 = "ProcessMODES4396Overpayment";
	String MODESB162 = "ProcessB162Overpayment";
	String MODES4396_3 = "ProcessMODES43963Overpayment";

	// CIF_01736 start
	String METHOD = "method";
	String EDIT = "edit";
	// CIF_01736 end

	// CIF_01753
	String DELETE_STRING = "delete";
	// CIF_INT_00338
	String ADD = "add";
	String SUCCESS_MESSAGE_KEY = "successmessagekey";
	String FROM_DETERMINATION_WORKER_MISC = "determinationWorkerMisc";
	String ADD_MORE_WAGES = "addMoreWages";
	// CIF_INT_00338

	// CIF_01759 - Starts
	String PAID_WEEK = "3";
	// CIF_01759 - Ends
	// CIF_INT_02857 || Global Constants Hundred Change
	String HUNDRED = "100.0000";

	// CIF_P2_00061 Start
	String SEARCH_FOR_RELATED_CORPORATION = "searchforrelatedcorporation";
	// CIF_P2_00061 End
	// LEAS-010 start
	String SEARCH_FOR_CLIENT = "searchforclient";
	// LEAS-010 end
	// CIF_P2_00120 START
	String SEARCH_CRITERIA_EMPLOYER_FIRST_WORKER_DATE = "firstworkerdate";
	String SEARCH_CRITERIA_EMPLOYER_FIRST_PAYROLL_DATE = "firstpayrolldate";
	// CIF_P2_00120 END

	// CIF_01796 - Starts
	BigDecimal BIGDECIMAL_ZERO_POINT_TWOTHREE = new BigDecimal("0.23");
	StringBuilder DECIMAL_VALUE = new StringBuilder("0.");
	// CIF_01796 - Ends

	// CIF_01787
	String WORK_SRCH_CNTCT_MAP = "worksearchcontactmap";
	BigDecimal HUNDRED_BIGDECIMAL = new BigDecimal(100);
	// CIF_01807
	char BATCH_CONSTANT_ONE_CHAR = '1';
	char BATCH_CONSTANT_THREE_CHAR = '3';

	// CIF_01810
	String CLAIM_DATA_FOR_APPEAl = "claimdataforappeal";

	// CIF_P2_00135 - Employer Maintenance - EM_095/ partnerid global constant
	// added
	String TAX_ADD_EMPLOYER_PARTNER_DATA_ID = "partnerId";
	// CIF_P2_00135 end
	// CIF_01623
	String MISSOURI_STATE = "Missouri";
	// CIF_00126 start
	// Changes for reporting service signup
	String CDS_VENDOR_ROLE = "cdsVendor";
	String PAYROLL_ROLE = "payrollservice";
	// CIF_00126 end
	// CIF_P2_00167
	String FEDERAL_INTEREST_ASSESSMENT_YEAR = "2011";

	// CIF_P2_00208:Start
	BigDecimal BIG_DECIMAL_HUNDRED = new BigDecimal("100");
	String STRING_DUMMY_QUARTER_ID_NUMBER_FOR_PRIOR_VALUE = "999";
	// CIF_P2_00208:End

	// CIF_INT_03676:start
	BigDecimal BIG_DECIMAL_THOUSAND = new BigDecimal("1000");
	// CIF_INT_03676:End

	// CIF_P2_00232
	String BATCH_GEN_UNIVERSE_FOR__STATUS_DETERMINATION_NEW_QUARTER = "1";
	// CIF_P2_00140 START
	int HIGHEST_SEQ_FOR_EAN = 99999;
	// CIF_P2_00140 End
	// CIF_P2_00236 - Starts
	String CREDIT_PAYMENT = "Credit";
	String SUSPENDED_PAYMENT = "Suspended Payment";
	String TAX_CREDIT = "Credit Payment";
	String CHARGE_CREDIT = "Charge Payment";
	String CREDIT_AVAILABLE = "CRDT";
	// CIF_P2_00236 - Ends
	// CIF_P2_00244
	String BATCH_GEN_UNIVERSE_FOR__STATUS_DETERMINATION_SUCCESSOR_QUARTER = "1";
	// CIF_P2_00250
	String BATCH_GEN_UNIVERSE_FOR__STATUS_DETERMINATION_TERMINATED_QUARTER = "1";
	// CIF_01691 - IB-13 - Start
	String IB_13_READ_INDICATOR_YES = "Y";
	String IB_13_READ_INDICATOR_NO = "N";
	String IB_13_OUTGOING = "IB13O";
	String IB_13_INCOMING = "IB13I";
	// CIF_01691 - IB-13 - End
	String MAKE_WK_PAYABLE = "MAKE_WK_PAYABLE";
	// CIF_P2_00247 - Starts
	String CDS_VENDOR = "CRDT";
	String THIRD_PARTY_VENDOR = "TPA";
	String TYPE_EMPLOYER = "EMPL";
	String CDS_ASSOCIATION_TYPE = "CDSV";
	String THIRD_PARTY_ASSOCIATION_TYPE = "THDP";

	// CIF_02332
	String ADJ_NON_SEP_NO_CALL_WI = "ADJ_NON_SEP_NO_CALL_WI";
	// CIF_INT_01250
	// @cif_wy(impactNumber="P1-DM-002, Defect_1700, Defect_1663",
	// requirementId="FR_1637",designDocName="Appendix E- Document Management -
	// Batch Programs",designDocSection="10", dcrNo="",
	// mddrNo="",impactPoint="start")
	String FOOTER_IMAGE = "/images/WyomingDepartmentOfWorkforcesServices.jpg";
	String HEADER_IMAGE = "/images/newHeader1.jpg";
	// @cif_wy(impactNumber="P1-DM-002, Defect_1700, Defect_1663",
	// requirementId="FR_1637",designDocName="Appendix E- Document Management -
	// Batch Programs",designDocSection="10", dcrNo="",
	// mddrNo="",impactPoint="end")

	// //CIF_02414 IRS Activity
	String INTERNAL_IP_SEGMENT_FIRST_TWO = "127.0";
	String DEFAULT_OSCA_CODE = "99999999";
	// CIF_02639
	String LOGOUT_LANGUAGE = "logoutLanguage";
	// CIF_02643
	String FORM_DATA = "formData";
	double DEFAULT_ZERO = 0.0;
	// CIF_P2_00247 - Ends
	// CIF_P2_00261 - Starts
	long MIN_ELIG_CREDIT_AMOUNT = 10;
	// CIF_P2_00261 - Ends
	// CIF_P2_00278 -Starts
	int NUMBER_THIRTY = 30;
	// CIF_P2_00278 - Ends
	// CIF_P2_00315 - Starts
	String BATCH_UPDATED = "Batch";
	String CSR = "CSR";
	// CIF_P2_00315 - Ends
	// CIF_P2_00223||TRackChargeabilityChange Business Rules Implementation-
	// START
	BigDecimal MIN_WAGES_ALLOWED_FOR_CHARGING_BIGDECIMAL = new BigDecimal(400);
	String NO_POOL = "NO POOL";
	// CIF_P2_00223||TRackChargeabilityChange Business Rules Implementation- END
	// CIF_P2_00297 Starts
	String APPROVE_CLIENT_LIST_PROCESS_NAME = "ApproveClientList";
	// CIF_P2_00297 Ends
	String MAX_CHECK_NUMBER = "MAX_CHECK_NUMBER";
	// CIF_P2_00117 Start
	String BIZ_CONSTANT_501C3_EMPLOYEE_COUNT = "501C3_EMPLOYER_EMPLOYEE_COUNT";
	// CIF_P2_00117 End
	// CIF_P2_00327
	String SEARCH_REPORT_DUE_BY_EAN = "getReportDueByEAN";
	// CIF_P2_00351 Starts
	String DEFAULT_BANK_NUMBER = "001";
	String CHECK_ACCOUNT_NUMBER = "CHECK_ACCOUNT_NUMBER";
	String AMOUNT_FORMAT = "#000000000.00";
	String WBA_FORMAT = "#000.00";
	// CIF_P2_00351 - Ends
	// CIF_P2_00356 Starts
	String CHECK_STATUS_CASHED = "C";
	// CIF_P2_00356 Ends
	// CIF_P2_00123 start - adding No and Yes constants with only first letter
	// as capital
	String YES_FIRST_CAPITAL = "Yes";
	String NO_FIRST_CAPITAL = "No";
	// CIF_P2_00123 end
	// CIF_P2_00351 Starts
	String CHECK_STATUS_CANCEL = "V";
	// CIF_P2_00351 Ends
	// CIF_P2_00544 Starts
	String FILE_HEADER_IDENTIFIER = "F";
	String BATCH_HEADER_IDENTIFIER = "B";
	String TRAILER1_HEADER_IDENTIFIER = "T";
	String TRAILER2_HEADER_IDENTIFIER = "W";
	String TRAILER1_REPORT_CATEGORY = "ORG";
	BigDecimal BIGDECIMAL_PERCENT = BigDecimal.ONE;
	// CIF_P2_00544 Ends
	/* CIF_P2_00490 Starts */
	String IC_DETERMINATION_FOLLOWUP = "icdeterminationfollowup";
	/* CIF_P2_00490 Ends */
	// CIF_P2_00417 Start
	String errorsMessages = "errorsMessages";
	String warningMessages = "warningMessages";
	String wageUploadReportBeans = "wageUploadReportBeans";
	String uploadWageFileSummary = "uploadWageFileSummary";
	// CIF_P2_00417 End
	// CIF_P2_00525 start - batch update assessment service constant for
	// comments
	String TAXASSESSMENTSERVICE = "TAX ASSESSMENT SERVICE";
	String COMMENT_UPDATE_TAX_ASSESSMENT = "Tax Assessment is remailed as regular";
	// CIF_P2_00525 end
	// CIF_P2_00450 START
	String AND_LOGICAL_OPERATOR = " AND ";
	String LESS_THN_EQUAL_TO_OPERATOR = "<=";
	String GREATER_THN_EQUAL_TO_OPERATOR = ">=";
	String SINGLE_QUOTE = "'";
	String SINGLE_QUOTE_COMMA_SINGLE_QUOTE = "','";
	// CIF_P2_00450 END
	// CIF_P2_00547 Starts
	int[] EAN_SEVEN_DIGITS = { 0, 7 };
	// CIF_P2_00547 Ends
	// CIF_P2_00619 Start
	String RPS_TRAN_ADJUSTMENT = "ADJU";
	// CIF_P2_00619 Ends
	// CIF_P2_00501 Start
	String FULLICESA_RECORD_A = "RECORD_A";
	String FULLICESA_RECORD_E = "RECORD_E";
	String FULLICESA_RECORD_S = "RECORD_S";
	String FULLICESA_RECORD_T = "RECORD_T";
	String FULLICESA_RECORD_F = "RECORD_F";
	// CIF_P2_00501 End

	// /CIF_02656 - Veracode Fix
	String DYNAMIC_CHARACTER = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	// CIF_P2_00610||Batch Generate Billing And Charge Statement- START
	String FORM_TYPE = "QB";
	String PAY_STATE = "MO";
	String NEXT_LINE = "\n";
	// CIF_P2_00610||Batch Generate Billing And Charge Statement- END
	// CIF_P2_00685 Starts
	String OTHER_PAYMENT = "Credit";
	// CIF_P2_00685 Ends

	// CIF_P2_00666 Added constants for adding the value in base rate for suta
	// dumping -- Start
	BigDecimal SUTA_BASE_RATE = new BigDecimal("2.00");
	BigDecimal MIN_SUTA_BASE_RATE = new BigDecimal("6.00");
	// CIF_P2_00666 Added constants for adding the value in base rate for suta
	// dumping -- End
	// CIF_P2_00619 starts
	String COMMENTS = "RPS";
	// CIF_P2_00619 Ends
	// CIF_P2_00755 Starts
	String REFUND_AMOUNT_FORMAT = "#00.00";
	// CIF_P2_00755 Ends
	// CIF_P2_00699 Start
	String ACHCREDIT_IMPORT_LIST = "ACHCREDIT_IMPORT_LIST";
	String PSEUDO_EAN_PROCESS_PAYMENTS = "9999999999";
	Long PSEUDO_PKID_PROCESS_PAYMENTS = Long.valueOf("130556");
	// CIF_P2_00699 End

	// CIF_P2_00501
	String FULL_ICESA_WAGE_FILE_DATA = "fullICESAData";

	String CHECK21_EMAIL_SUBJECT = "Reg: Errors while importing Check21 file";
	String CHECK21_EMAIL_FROM = "noreply@des.ms.gov";
	String CHECK21_EMAIL_FROM_ALIAS = "BatchSupport";
	String CHECK21_FILE_HEADER_BACTH_COUNT1 = "Total number of Trailer 1 records in Batch header  : ";
	String CHECK21_FILE_HEADER_BACTH_COUNT2 = "is not matching with total number of Trailer 1 record count";

	String CHECK21_BATCH_HEADER_TRAILER_COUNT1 = "Total number of Batch records in file header : ";
	String CHECK21_BATCH_HEADER_TRAILER_COUNT2 = ",is not matching with total number of Batch record count: ";

	String CHECK21_TRAILER1_HEADER_TRAILER2_COUNT1 = "Total number of Trailer 2 records in Trailer 1 header : ";
	String CHECK21_TRAILER1_HEADER_TRAILER2_COUNT2 = ",is not matching with total number of Trailer 2 record count";

	String CHECK21_SEQUENCE_MISMATCH_ERROR1 = "Total payment amount : ";
	String CHECK21_SEQUENCE_MISMATCH_ERROR2 = ",is not matching with sum of payment amounts : ";
	String CHECK21_SEQUENCE_MISMATCH_ERROR3 = ",in trailer 1 records for EAN: ";

	String CHECK21_TRAILER1_HEADER_TRAILER2_COUNT2_NULL = "Total number of Trailer 2 records in Trailer 1 header is not available. But Trailer 2 record(s) exist.";
	// CIF_P2_00417 Start
	String SEMICOLON_DELIMITTER = ";";
	String TAB_DELIMITTER = "\\t";
	// CIF_P2_00417 End
	String DATE_FORMAT_CHECK21 = "yyyy/MM/dd";
	// CIF_P2_00994 Starts
	String CHECK21_PREMATCH_EMAIL_SUBJECT = "Reg: Errors while running Check21 Prematch batch";
	// CIF_P2_00994 Ends
	// CIF_INT_05922 | LRAMT-191
	String AMT_CWREPORTS_EMAIL_SUBJECT = "Reg: Errors while running AMT CW Reports processing batch";

	// CIF_P2_00869 Start
	String FROMQUARTER = "FROMQUARTER";
	String FROMYEAR = "FROMYEAR";
	String TOQUARTER = "TOQUARTER";
	String TOYEAR = "TOYEAR";
	String DASHBOARD_START_DATE = "DASHBOARD_START_DATE";
	String DASHBOARD_END_DATE = "DASHBOARD_END_DATE";
	String SEARCH_QUERY_DATE1 = "SEARCH_QUERY_DATE1";
	String SEARCH_QUERY_DATE2 = "SEARCH_QUERY_DATE2";
	String SEARCH_QUERY_DATE3 = "SEARCH_QUERY_DATE3";
	String SEARCH_QUERY_DATE4 = "SEARCH_QUERY_DATE4";
	String SEARCHNAME = "searchName";
	String SEARCHFROMQUARTER = "searchFromQuarter";
	String SEARCHFROMYEAR = "searchFromYear";
	String SEARCHTOQUARTER = "searchToQuarter";
	String SEARCHTOYEAR = "searchToYear";
	String SEARCH_QUERY_KEY = "SEARCH_QUERY_KEY";
	String SEARCHDATE1 = "searchDate1";
	String SEARCHDATE2 = "searchDate2";
	String SEARCHDATE3 = "searchDate3";
	String SEARCHDATE4 = "searchDate4";
	String BACK = "back";
	String NEXT = "next";
	String CHARTSWF = "swf";
	String CHARTHEIGHT = "chartHeight";
	String CHARTWIDTH = "chartWidth";
	String CHARTID = "chartId";
	String CHARTXML = "chartXML";
	String DEBUGMODE = "debugMode";

	String LONG_MONTH_SHART_YEAR_FORMAT = "MMM-yy";
	String TABLEONLYSEARCHNAME = "tableOnlySearchName";
	String SEARCHGRAPHLISTNAME = "searchGraphListName";

	String TAX = "TAX";

	// CIF_P2_00869 End
	// CIF_P2_00779 Start
	String[] UPLOAD_DOCUMENTS_DES_REPORT_REQUEST_SUPPORTED_FILE_FORMATS = { "txt", "pdf", "docx", "TXT", "PDF",
			"DOCX" };
	// 5MB size limit for attachment
	Integer UPLOAD_DOCUMENTS_DES_REPORT_REQUEST_ATTACHMENT_LIMIT = 1024 * 5120;
	// CIF_P2_00779 End
	// CIF_P2_01072 Start
	String EMPLOYER_TAX_USER_ROLE = "employertaxuser";
	// CIF_P2_01072 End
	// CIF_P2_00775 Start
	String TYPE_TPS_202_BEAN = "TYPE_TPS_202_BEAN";
	String AREA_NAME_BEAN = "AREA_NAME_BEAN";
	// CIF_P2_00775 End
	// CIF_P2_00776 Start
	String MONTH_NUM_1 = "1";
	String MONTH_NUM_2 = "2";
	String MONTH_NUM_3 = "3";
	String MONTH_NUM_4 = "4";
	String MONTH_NUM_5 = "5";
	String MONTH_NUM_6 = "6";
	String MONTH_NUM_7 = "7";
	String MONTH_NUM_8 = "8";
	String MONTH_NUM_9 = "9";
	String MONTH_NUM_10 = "10";
	String MONTH_NUM_11 = "11";
	String MONTH_NUM_12 = "12";
	// CIF_P2_00776 End
	// CIF_03017 UIM-3155 : Start
	String KOFAX_BOTH_YES_NO = "2";
	String KOFAX_NO_ANSWER = "3";
	// CIF_03017 UIM-3155 : End
	// CIF_00780 Start
	String COLON_SEPRATOR = ":";
	// CIF_00780 End
	String CERT_PASSWORD = "Testing$01";
	String SSO_ALIAS = "jeffcitydev";
	String FTI_IRS_DATA_ACCESS_CLASS = "gov.state.uim.mwds.service.IRSInquiryService";
	String FTI_IRS_DATA_ACCESS_FUNTION = "saveIRSInformationForLogging";
	String FTI_EMPLOYER_REFUND_BATCH = "BatchProcessTaxTopRefund";
	String LAW_FLAG = "LawFlag";
	String USED_DESCRIPTION = "Used";
	String UNUSED_DESCRIPTION = "Unused";

	// CIF_P2_01217
	String STAR_SYMBOL = "*";
	// CIF_03370
	int NUMERIC_NEGATIVE_SIX = -6;
	String MANUAL_REDETERMINATIN_PERFORMED = "Manual Redetermination performaed";
	// CIF_P2_01643 Defect_6634 Fix
	String WORKFLOW_APP_ENV_BENEFIT = "BEN";
	String WORKFLOW_APP_ENV_TAX = "TAX";
	String WORKFLOW_APP_ENV_COM = "COM";
	String OPEN_BRACKET = "(";
	String CLOSED_BRAKCET = ")";

	// CIF_03426 start
	String BUSINESS_KEY_DATA = "BUSINESS_KEY_DATA";
	String CLOSE_WORKITEM = "CLOSE_WORKITEM";
	// CIF_INT_00674
	BigDecimal MAX_SEV_PAY = new BigDecimal("9999999.99");
	// CIF_03426 end
	String CANCEL_B9 = "cancl";
	String QUICK_ACCESS = "Quick Access";

	// CIF_INT_00709||MSDESFileToUpload layout Changes
	String MSDES_PERCENATGE = "000000000";
	// CIF_INT_00823 start
	String ALREADY_INITIATED_IC_DET_FOLLOWUP = "INC";
	String TAX_AUDIT_FOLLOWUP = "TAF";
	String INVESTIGATION_FOLLOWUP = "IVF";
	String INVESTIGATION_TOKEN = "Investigation";
	String SUCCESS_PAGE_HEADING_KEY = "successpageheadingkey";
	String IS_TFR = "isTfr";
	String IS_MDES_USER = "isMdesUser";
	String IS_AUDITOR = "isAuditor";
	String IS_SUPPRESS_CORRESPONDENCE = "issuppresscorrespondence";
	// CIF_INT_00823 end
	// CIF_INT_00791 || FTI Data Activity
	String EMPLOYER_ACCOUNT_REVIEW = "Employer Account Review";
	String CLAIMANT_REVIEW = "Claimant Review";
	String FORWARD_NAME = "forwardName";
	// CIF_INT_01345 || Defect_8258
	String COMMON_INTERNAL_ROLE = "descommoninternal";
	// CIF_INT_01785
	String TOTAL_OVERPAYMENT_LIST = "totalOverpaymentList";
	// CIF_INT_01834
	Integer NUMERIC_NINETEEN_FIFTY = 1950;
	// CIF_INT_01904 || Defect_143
	// CIF_INT_04609
	int TOTAL_RECORDS_TO_SHOW_ON_SCREEN = 200;
	int TOTAL_RECORDS_TO_SHOW_ON_ADJ_SCREEN = 200;
	/* CIF_INT_02183 || Defect_654 */
	String DOLLAR_SYMBOL = "$";
	// Changes For Story P1-CM-008
	double WBA_TOTAL_WAGE_WY_RATIO = 1.4;
	// CIF_INT_01533 || UIM-5345 || LRFOCUS Implementation Start
	String IRS_PROCESSING_SEARCH_QUERY = "getFtiIRSLogInformaionRequestList";
	String FTI_IRS_LOG_INFORMATION_ID = "ftiIRSLogInformationId";
	String SEARCH_IRS_LOGS_REQUEST_TYPE_PARAM = "type";
	String IS_FROM_SEARCH_IRS_LOG_SCREEN_PARAM = "isFromSearchIRSLogScreen";

	String[] IRS_PROCESSING_UPLOAD_DOCUMENTS_SUPPORTED_FILE_FORMATS = { "txt", "pdf", "doc", "docx", "xls", "xlsx",
			"TXT", "PDF", "DOC", "DOCX", "XLS", "XLSX" };
	// 5MB size limit for attachment
	Integer IRS_PROCESSING_UPLOAD_DOCUMENTS_ATTACHMENT_LIMIT = 1024 * 5120;
	String IRS_PROCESSING_UPLOAD_DOCUMENT_DATA = "IRS_PROCESSING_UPLOAD_DOCUMENT_DATA";
	String ARCHIVE_DATE_FORMAT = "yyyyMMdd_HH_mm";
	// CIF_INT_01533 || UIM-5345 || LRFOCUS Implementation End
	String UINTERACT_EMAIL = "DWS-WYCAN-Alerts@wyo.gov";
	String NO_REPLY_EMAIL = "DWS-WYCAN-Alerts@wyo.gov";
	String UINTERACT_BATCH_EMAIL = "DWS-WYCAN-Alerts@wyo.gov";
	String UINTERACT_INBOX_EMAIL = "DWS-WYCAN-Alerts@wyo.gov";
	String DEFAULT_DEBIT_CARD_NUMBER = "0000000000000000";

	String OTHER_STATE_COUNTY_FOR_BUS_LOC = "999";

	/* CIF_INT_02847 || Defect_1099, Defect_1202 Start */
	String[] UPLOAD_DOCUMENTS_INFORMATION_REPORT_REQUEST_SUPPORTED_FILE_FORMATS = { "txt", "pdf", "docx", "TXT", "PDF",
			"DOCX" };

	Integer UPLOAD_DOCUMENTS_INFORMATION_REPORT_REQUEST_ATTACHMENT_LIMIT = 1024 * 5120;
	/* CIF_INT_02847 || Defect_1099 End */
	String FAILED_BIRT_REQUEST = "F";
	String SUCCESS_BIRT_REQUEST = "S";
	String PROCESSING_BIRT_REQUEST = "P";
	String UNPROCESSED_BIRT_REQUEST = "U";
	Integer BIRT_BATCH_SLEEP_TIME = 120000;
	Integer BIRT_APPLICATION_SLEEP_TIME = 2000;

	String IS_NEW_COREG_FLAG = "IS_NEW_COREG";

	/* CIF_INT_03653 Start */
	String ENGLISH_LANGUAGE_LOCALE = "English";
	String SPANISH_LANGUAGE_LOCALE = "_SPANISH";
	/* CIF_INT_03653 End */

	String[] APPROVE_REGISTRATION_CSR_ROLES = { "liabilityregistrationspecialist",
			"investigationsblockedclaimspecialist", "auditor", "auditorsupervisor" };
	String[] EMPLOYER_REGISTRATION_SPECIALIST = { "liabilityregistrationspecialist",
			"investigationsblockedclaimspecialist", "auditor", "liabilityexaminer", "auditorsupervisor" };
	String[] EMPLOYER_REGISTRATION_EXTERNAL_USER = { "thirdparty", "cdsVendor" }; // CIF_INT_04603
	// CIF_INT_05493
	String CHARGE_STATEMENT_MAIL_DATE = "chargestatementmaildate";

	// @CIF_WY(impactNumber="P1-BAD-006,P1-BAD-007", requirementId="FR_1621",
	// designDocName="02 Claim Adjustments", designDocSection="3.2.4.2.1",
	// dcrNo="", mddrNo="", impactPoint="Start")
	String SPECIAL_PROGRAM_SPECIALIST = "specialProgramsSpecialist";
	String SPECIAL_PROGRAM_SPECIALIST_SUPERVISOR = "specialProgramsSpecialistSupervisor";
	// @CIF_WY(impactNumber="P1-BAD-006,P1-BAD-007", requirementId="FR_1621",
	// designDocName="02 Claim Adjustments", designDocSection="3.2.4.2.1",
	// dcrNo="", mddrNo="", impactPoint="End")

	// @cif_wy(impactNumber="P1-BRD-007",
	// requirementId="FR_728",designDocName="03 Adjudication",
	// designDocSection="1.19.3, 1.19.4.1.5", dcrNo=" ", mddrNo=" ",
	// impactPoint="Start")
	int FRAUD_INELIGIBILITY = 52;
	int MINUS_ONE = -1;
	// @cif_wy(impactNumber="P1-BRD-007",
	// requirementId="FR_728",designDocName="03 Adjudication",
	// designDocSection="1.19.3, 1.19.4.1.5", dcrNo=" ", mddrNo=" ",
	// impactPoint="End")
	// @cif_wy(impactNumber = "P1-USD-014,P1-USD-015", requirementId =
	// "FR_1636",
	// designDocName = "02 Benefits Timeliness Quality Audit", designDocSection
	// = "3.6", dcrNo = "", mddrNo = "", impactPoint = "Start")
	String BTQ_NMON_DCI_DATA_LIST = "BtqNmonDciDataList";
	String SELECTED_BTQ_AUDIT_BEAN = "selectedBtqAuditbean";
	String UCFE = "UCFE";
	String UCX = "UCX";
	String T_MST_DECISION_CODE = "T_MST_DECISION_CODE";
	String DESCRIPTION = "description";
	String GET_DETECTION_DATE_FOR_BTQ_AUDIT = "getDetectionDateForBTQAudit";
	String FIRST_WAGE_RESPONSE_DATE = "first_wage_response_date";
	String DCI_TYPE = "dciType";
	String STRING_BTQ_FLAG = "stringBtqFlag";
	String FSRV = "FSRV";
	String SCRV = "SCRV";
	String KEY = "key";
	String PG_011 = "011";
	String PG_010 = "010";
	String PG_001 = "001";
	String T_MST_BTQ_NMON_DCI_CLAIMANT = "T_MST_BTQ_NMON_DCI_CLAIMANT";
	String T_MST_BTQ_NMON_DCI_EMPLOYER = "T_MST_BTQ_NMON_DCI_EMPLOYER";
	String T_MST_BTQ_NMON_DCI_OTHER = "T_MST_BTQ_NMON_DCI_OTHER";
	String T_MST_BTQ_NMON_DCI_LAW = "T_MST_BTQ_NMON_DCI_LAW";
	String T_MST_BTQ_NMON_DCI_DETERMINATION = "T_MST_BTQ_NMON_DCI_DETERMINATION";
	String INTERFACE_CODE = "interfaceCode";
	String DCI_DATA = "dciData";
	String AUDIT_CASE_SCORE = "auditCaseScore";
	String GET_INTRA_ST_FOR_BTQ_AUDIT = "getIntraStForBTQAudit";
	// @cif_wy(impactNumber = "P1-USD-014,P1-USD-015", requirementId =
	// "FR_1636",
	// designDocName = "02 Benefits Timeliness Quality Audit", designDocSection
	// = "3.6", dcrNo = "", mddrNo = "", impactPoint = "End")

	// @CIF_WY(impactNumber="P1-BAD-009", requirementId="FR_1621",
	// designDocName="04 Payment Adjustmentsearning",
	// designDocSection="2.5.4.1.2", dcrNo="", mddrNo="", impactPoint="Start")
	BigDecimal BIGDECIMAL_FOUR_POINT_THREE_THREE = new BigDecimal("4.33");
	// @CIF_WY(impactNumber="P1-BAD-009", requirementId="FR_1621",
	// designDocName="04 Payment Adjustmentsearning",
	// designDocSection="2.5.4.1.2", dcrNo="", mddrNo="", impactPoint="End")

	// @cif_wy(impactNumber = "P1-USD-004", requirementId = "FR_1159",
	// designDocName = "Appendix B USDOL Compliance Correspondence",
	// designDocSection = "1", dcrNo = "", mddrNo = "", impactPoint = "Start")
	String SECOND_CORRESPONDENCE = "SECOND";
	String WHICHCORR = "whichCorr";
	String CORRTOSEND = "CorrToSend";
	String CORRMODE = "CorrMode";
	String GET_BAM_INITIAL_CORR_DETAILS = "getBamInitialCorrDetails";
	String MAIL_DATE = "maildate";
	String WHICH_EMPLOYER = "whichEmployer";
	String CORRDATA = "corrData";
	String XML = ".xml";
	String EMPTOSEND = "EmptoSend";
	// @cif_wy(impactNumber = "P1-USD-004", requirementId = "FR_1159",
	// designDocName = "Appendix B USDOL Compliance Correspondence",
	// designDocSection = "1", dcrNo = "", mddrNo = "", impactPoint = "End")

	// @cif_wy(impactNumber="P1-PA-005", requirementId="FR_255",
	// designDocName="01 Payments and Weekly Certifications- Filing Weekly
	// Certification",
	// designDocSection="1.4.5", dcrNo="", mddrNo="", impactPoint="Start")
	int NUMBER_EIGHTY_THREE = 83;
	int NUMBER_EIGHTY_FOUR = 84;
	int NUMBER_SIXTY_EIGHT = 68;
	// @cif_wy(impactNumber="P1-PA-005", requirementId="FR_255",
	// designDocName="01 Payments and Weekly Certifications- Filing Weekly
	// Certification",
	// designDocSection="1.4.5", dcrNo="", mddrNo="", impactPoint="End")

	// @cif_wy(impactNumber = "P1-BAD-015", requirementId = "FR_471",
	// designDocName = "03 - Monetary Redetermination and Reconsideration.docx",
	// designDocSection = "10.2.4.4", dcrNo = "", mddrNo = "", impactPoint =
	// "Start")
	String IS_OS_IB4_REQUEST_SENT = "IS_OS_IB4_REQUEST_SENT";
	String COMPENSATION_CASE_NO = "compCaseNo";
	String DATE_OF_INJURY = "dateOfInjury";
	String FROM_DATE = "FROM_DATE";
	String TO_DATE = "TO_DATE";
	String ESTIMATED_WBA = "ESTIMATED_WBA";
	String ESTIMATED_MBA = "ESTIMATED_MBA";
	// @cif_wy(impactNumber = "P1-BAD-015", requirementId = "FR_471",
	// designDocName = "03 - Monetary Redetermination and Reconsideration.docx",
	// designDocSection = "10.2.4.4", dcrNo = "", mddrNo = "", impactPoint =
	// "End")

	// @cif_wy(impactNumber = "P1-A1-090", requirementId = "FR_319",
	// designDocName = "",
	// designDocSection = "", dcrNo = "", mddrNo = "", impactPoint = "Start")
	BigDecimal BIGDECIMAL_TWO = new BigDecimal(2);
	BigDecimal BIGDECIMAL_350 = new BigDecimal(350);
	BigDecimal BIGDECIMAL_50 = new BigDecimal("50.00");
	BigDecimal BIGDECIMAL_50_PRCT = new BigDecimal(".50");
	BigDecimal BIGDECIMAL_100 = new BigDecimal("100.00");

	// @cif_wy(impactNumber="P1-PA-016",
	// requirementId="FR_245",designDocName="01 Payments and Weekly
	// Certifications- Filing Weekly Certification",designDocSection="1.4.5",
	// dcrNo="", mddrNo="", impactPoint="Start")
	BigDecimal BIGDECIMAL_35 = new BigDecimal("35.00");
	// @cif_wy(impactNumber="P1-PA-016",
	// requirementId="FR_245",designDocName="01 Payments and Weekly
	// Certifications- Filing Weekly Certification",designDocSection="1.4.5",
	// dcrNo="", mddrNo="", impactPoint="End")
	String RECORD_TYPE_T = "T";
	String ACTIVE_CHILDSUPPORT_FLAG = "1";
	String INACTIVE_CHILDSUPPORT_FLAG = "0";
	int SCALE_2 = 2;
	int SCALE_4 = 4;
	int INT_ZERO = 0;
	int INT_ONE = 1;
	String CSE_DATA = "CSEDATALIST";
	String CSE_DATA_FORMAT = "yyyyMMdd";
	String SPACE = " ";
	// @cif_wy(impactNumber = "P1-A1-090", requirementId = "FR_319",
	// designDocName = "",
	// designDocSection = "", dcrNo = "", mddrNo = "", impactPoint = "End")

	// @cif_wy(impactNumber = "P1-CM-012", requirementId = "FR_44",
	// designDocName = "01 Regular UI Claim,EUCC,EB.docx", designDocSection =
	// "1.36.4", dcrNo = "DCR_MDDR_83", mddrNo = "", impactPoint = "Start")
	String BUSINESS_KEY_SEP = "??";
	// @cif_wy(impactNumber = "P1-CM-012", requirementId = "FR_44",
	// designDocName = "01 Regular UI Claim,EUCC,EB.docx", designDocSection =
	// "1.36.4", dcrNo = "DCR_MDDR_83", mddrNo = "", impactPoint = "End")
	// @cif_wy(impactNumber="P1-OOS-44", requirementId="FR_1463",
	// designDocName="08 Work Items OOS", designDocSection="13", dcrNo="",
	// mddrNo="", impactPoint="Start")
	String SYSTEM = "system";
	String RESPONSE = "response";
	String SAVE = "save";
	String FIRST_QUARTER = "Jan-Mar";
	String SECOND_QUARTER = "Apr-Jun";
	String THIRD_QUARTER = "Jul-Sep";
	String FOURTH_QUARTER = "Oct-Dec";
	String FIRST_QUARTER_NO = "1";
	String SECOND_QUARTER_NO = "2";
	String THIRD_QUARTER_NO = "3";
	String FOURTH_QUARTER_NO = "4";
	String BUSINESS_METHOD = "saveWages";
	String IB4_EFFDATE_FORMAT = "yyyy-mm-dd";
	String WAGE_DATA = "wagesearchdata";
	String IB4_DATA = "ib4searchdata";
	String WI_VALUES_NO = "11";
	// @cif_wy(impactNumber="P1-OOS-44", requirementId="FR_1463",
	// designDocName="08 Work Items OOS", designDocSection="13", dcrNo="",
	// mddrNo="", impactPoint="End")

	// @cif_wy(impactNumber = "P1-APP-27" , requirementId = "FR_1632",
	// designDocName = "04 UIC", designDocSection = "4.7", dcrNo = "",
	// mddrNo = "DCR_MDDR_153", impactPoint = "Start")
	String UIC_USER_NAME = "Commission";
	// @cif_wy(impactNumber = "P1-APP-27" , requirementId = "FR_1632",
	// designDocName = "04 UIC", designDocSection = "4.7", dcrNo = "",
	// mddrNo = "DCR_MDDR_153", impactPoint = "End")
	// @cif_wy(impactNumber = "P1-APP-23" , requirementId =
	// "FR_999,FR_1053,FR_1075",
	// designDocName = "03 Conduct Hearing",
	// designDocSection = "1.7", dcrNo = "", mddrNo = "", impactPoint = "Start")
	String TIMELY = "timely";
	// @cif_wy(impactNumber = "P1-APP-23" , requirementId =
	// "FR_999,FR_1053,FR_1075",
	// designDocName = "03 Conduct Hearing",
	// designDocSection = "1.7", dcrNo = "", mddrNo = "", impactPoint = "End")

	// @cif_wy(impactNumber = "P1-CM-007, P1-CM-056", requirementId = "FR_1614",
	// designDocName = "01 Regular UI Claim,EUCC,EB.docx", designDocSection =
	// "1.17.4, 1.20.4, 1.40.4", dcrNo = "DCR_MDDR_158", mddrNo = "",
	// impactPoint = "Start")
	int NO_WEEKS_DIFFERENCE_FOR_LAST_EMPLOYER = 3;
	// @cif_wy(impactNumber = "P1-CM-007, P1-CM-056", requirementId = "FR_1614",
	// designDocName = "01 Regular UI Claim,EUCC,EB.docx", designDocSection =
	// "1.17.4, 1.20.4, 1.40.4", dcrNo = "DCR_MDDR_158", mddrNo = "",
	// impactPoint = "End")
	// @cif_wy(impactNumber = "P1-CASH-002,P1-CASH-003,P1-CASH-004,P1-CASH-005",
	// requirementId = "CR_29,FR_1113", designDocName = "Appendix E Cashiering
	// Batch Programs Design Document", designDocSection = "2", dcrNo = "",
	// mddrNo = "", impactPoint = "Start")
	String CAGN = "CAGN";
	String ECGN = "ECGN";
	String CCGN = "CCGN";
	String CCVW = "CCVW";
	String LOIN = "LOIN";
	String FTIN = "FTIN";
	String STIN = "STIN";
	String MOCH = "MOCH";
	String EMCV = "EMCV";
	String CCRJ = "CCRJ";
	String CARJ = "CARJ";
	String CLMT = "CLMT";
	String GEND = "GEND";
	// @cif_wy(impactNumber = "P1-CASH-002,P1-CASH-003,P1-CASH-004,P1-CASH-005",
	// requirementId = "CR_29,FR_1113", designDocName = "Appendix E Cashiering
	// Batch Programs Design Document", designDocSection = "2", dcrNo = "",
	// mddrNo = "", impactPoint = "End")

	// @cif_wy(impactNumber="P1-PA-057", requirementId="FR_1617",
	// designDocName="01 Design Document - Payments and Weekly Certifications -
	// Filing Weekly Certification", designDocSection="1.11.5.2.1", dcrNo="",
	// mddrNo="", impactPoint="Start")
	String isImmediateRestitution = "T";
	// @cif_wy(impactNumber="P1-PA-057", requirementId="FR_1617",
	// designDocName="01 Design Document - Payments and Weekly Certifications -
	// Filing Weekly Certification", designDocSection="1.11.5.2.1", dcrNo="",
	// mddrNo="", impactPoint="End")
	// cif_wy(impactNumber="P1-PA-032, P1-PA-033,P1-PA-034",
	// requirementId="FR_296", designDocName="01 Design Document - Payments and
	// Weekly Certifications - Filing Weekly Certification",
	// designDocSection="1.4", dcrNo="", mddrNo="", impactPoint="Start")
	String ERRORREQUIRED = "error.required";
	String ERRORNUMBER = "errors.number";

	String MAXAMT = "9999.99";
	String ERRORKEY = "error.access.weekcertification.vacpayamount.invalid";
	String DOLLARMAXAMT = "$9,999.99";
	String CHECK_PATTERN_CONTINUED_CLAIMS = "^[0-9]*\\.?[0-9]*$";
	// cif_wy(impactNumber="P1-PA-032, P1-PA-033,P1-PA-034",
	// requirementId="FR_296", designDocName="01 Design Document - Payments and
	// Weekly Certifications - Filing Weekly Certification",
	// designDocSection="1.4", dcrNo="", mddrNo="", impactPoint="Start")

	// @cif_wy(impactNumber = "P1-BAD-073", requirementId = "FR_1623",
	// designDocName = "D Appendix E BA Batch", designDocSection = "8.3", dcrNo
	// = "", mddrNo = "", impactPoint = "Start")
	String STR_FALSE = "FALSE";
	String STR_TRUE = "TRUE";
	// @cif_wy(impactNumber = "P1-BAD-073", requirementId = "FR_1623",
	// designDocName = "D Appendix E BA Batch", designDocSection = "8.3", dcrNo
	// = "", mddrNo = "", impactPoint = "End")
	
	// @cif_wy(impactNumber = "Defect_551", requirementId = "",
	// designDocName = "", designDocSection = "", dcrNo
	// = "", mddrNo = "", impactPoint = "Start")
	String VIEW_DECISION_DOCUMENT = "Hearing-Officer-Decision-Letter.docx";
	
	// @cif_wy(impactNumber = "Defect_551", requirementId = "",
	// designDocName = "", designDocSection = "", dcrNo
	// = "", mddrNo = "", impactPoint = "End")
	
	//@cif_wy(impactNumber = "Defect_598", requirementId = "", designDocName = "", designDocSection = "", dcrNo = "", mddrNo = "", impactPoint = "Start")
	String LAST_EMPL_REVIEW_WI_EXIST = "LAST_EMPL_REVIEW_WI_EXIST";
	String LAST_EMPL_REVIEW_WI = "LastEmployerReview";
	//@cif_wy(impactNumber = "Defect_598", requirementId = "", designDocName = "", designDocSection = "", dcrNo = "", mddrNo = "", impactPoint = "End")
	//@cif_wy(impactNumber = "Defect_732", requirementId = "", designDocName = "", designDocSection = "", dcrNo = "", mddrNo = "", impactPoint = "Start")
	String STATUS = "Status";
	String SRIP = "SRIP";
	//@cif_wy(impactNumber = "Defect_732", requirementId = "", designDocName = "", designDocSection = "", dcrNo = "", mddrNo = "", impactPoint = "End")
	
	// Sonar String Literal issue for cif_wy comments
	String CIF_NEW = "New";
	String CIF_START = "Start";
	String CIF_END = "End";
	String DWS="DWS";
	String do_not_reply_oa_mo_gov="anshul.kumar@wyo.gov";
	String doliruinteractinfo_labor_mo_gov="anshul.kumar@wyo.gov";
	String do_not_reply_dolir_mo_gov="anshul.kumar@wyo.gov";
	String batchimportdhssmerge_dolir_mo_gov="anshul.kumar@wyo.gov";
	String momail_mo_gov="anshul.kumar@wyo.gov";
	String accessms_des_mo_gov="anshul.kumar@wyo.gov";
	String dolirua_labor_mo_gov="anshul.kumar@wyo.gov";
	String uinteractbatch_dolir_mo_gov="anshul.kumar@wyo.gov";
	String dolirdesbatcherror_dolir_mo_gov="anshul.kumar@wyo.gov";
	String batch_des_mo_gov="anshul.kumar@wyo.gov";
	String admin_oa_mo_gov="anshul.kumar@wyo.gov";
	String DOLIR_DEV_UINTERACT_labor_mo_gov="anshul.kumar@wyo.gov";
	String DOLIR_SIT_UINTERACT_labor_mo_gov="anshul.kumar@wyo.gov";
	String BPCcourt_labor_mo_gov="anshul.kumar@wyo.gov";
	
	// @cif_wy(impactNumber="Defect_1390", requirementId="", designDocName="01 Design Document - Payments and Weekly Certifications - Filing Weekly Certification", designDocSection="1.11.5.2.1", dcrNo="", mddrNo="", impactPoint="Start")
	String DEFAULT_EARLY_DATE = "01/01/1900";
	// @cif_wy(impactNumber="Defect_1390", requirementId="", designDocName="01 Design Document - Payments and Weekly Certifications - Filing Weekly Certification", designDocSection="1.11.5.2.1", dcrNo="", mddrNo="", impactPoint="End")
	
	
};