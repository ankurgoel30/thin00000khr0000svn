package com.thinkhr.external.api;

/**
 * Class to keep all the constants used by application
 * @author Surabhi Bhawsar
 * @since 2017-11-09
 *
 */
public class ApplicationConstants {
    public static final int DEFAULT_OFFSET = 0;
    public static final int DEFAULT_LIMIT = 50;
    public static final String DESENDING = "-";
    public static final String ASCENDING = "+";
    public static final String DEFAULT_SORT_BY_COMPANY_NAME = "+companyName";
    public static final String DEFAULT_SORT_BY_USER_NAME = "+userName";
    public static final String SUCCESS_DELETED = "SUCCESSFULLY_DELETED";
    public static final String STRING_CLASS_NAME = "java.lang.String";
    public static final String TOTAL_RECORDS = "totalRecords";
    public static final String LIMIT_PARAM = "limit";
    public static final String OFFSET_PARAM = "offset";
    public static final String SORT_PARAM = "sort";
    public static final String VALID_FILE_EXTENSION_IMPORT = "csv";
    public static final String[] REQUIRED_HEADERS_COMPANY_CSV_IMPORT = {"CLIENT_NAME", "DISPLAY_NAME","PHONE","ADDRESS","ADDRESS2",
    																	"CITY","STATE","ZIP","INDUSTRY", "COMPANY_SIZE", "PRODUCER"};
    public static final int MAX_RECORDS_COMPANY_CSV_IMPORT = 11000;
    public static final String SPACE = " ";

}
