package com.thinkhr.external.api.repositories;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.thinkhr.external.api.ApplicationConstants;
import com.thinkhr.external.api.services.utils.CommonUtil;

/**
 * Query build to build queries
 * 
 * @author Surabhi Bhawsar
 * @since 2017-11-27
 *
 */
public class QueryBuilder {

    private static final String INSERT_COMPANY = "INSERT into clients";
    private static final String INSERT_LOCATION = "INSERT INTO locations";
    private static final String VALUES = "Values";
    private static final String START_BRACES = "(";
    private static final String END_BRACES = ") ";
    public static final String DELETE_COMPANY_QUERY = "Delete from clients where clientId=?";
    
    /**
     *   //INSERT INTO locations(address,address2,city,state,zip,client_id) values(?,?,?,?,?,?);
     * 
     * @param locationColumns
     * @return
     */
    public static String buildLocationInsertQuery(List<String> locationColumns) {
        locationColumns.add("client_id");
        return buildQuery(INSERT_LOCATION, locationColumns);
    }

    /**
     * Build query
     * 
     * @param locationColumns
     * @return
     */
    private static String buildQuery(String insertQueryType, List<String> columns) {
        StringBuffer insertLocationSql = new StringBuffer();
        insertLocationSql.append(insertQueryType)
                .append(START_BRACES).append(StringUtils.join(columns, ApplicationConstants.COMMA_SEPARATOR))
        .append(END_BRACES)
        .append(VALUES)
                .append(START_BRACES).append(getQueryParaSpecifiers(columns.size()))
        .append(END_BRACES);
        return insertLocationSql.toString();
    }

    /**
     *    INSERT INTO clients(client_name,display_name, client_phone,industry,companysize,producer,custom1,custom2,custom3,custom4,
     *    search_help,client_type,client_since,special_note) " + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)
     *
     * @param companyColumns
     * @return
     */
    public static String buildCompanyInsertQuery(List<String> companyColumns) {
        companyColumns.addAll(defaultCompanyColumnsForNewRecord());
        return buildQuery(INSERT_COMPANY, companyColumns);
    }

    /**
     * @return
     */
    public static List<String> defaultCompanyColumnsForNewRecord() {
        List<String> companyColumns = new ArrayList<String>();
        companyColumns.add("search_help");
        companyColumns.add("client_type");
        companyColumns.add("special_note");
        companyColumns.add("client_since");
        companyColumns.add("t1_is_active");
        return companyColumns;
    }

    /**
     * 
     * @return
     */
    public static List<Object> companyDefaultColumnsValuesForNewRecord() {
        List<Object> companyColumnsValues = new ArrayList<Object>();
        companyColumnsValues.add("");
        companyColumnsValues.add("");
        companyColumnsValues.add("");
        companyColumnsValues.add(CommonUtil.getTodayInUTC());
        companyColumnsValues.add("1"); //default all clients are active
        return companyColumnsValues;
    }

    /**
     * Generate string having query parameters for given count
     * 
     * @param locationColumns
     * @return
     */
    public static String getQueryParaSpecifiers(Integer count) {
        if (count == 0) {
            return "";
        }
        StringBuffer params = new StringBuffer();
        for (int i = 0; i < count; i++) {
            if ( i > 0) {
                params.append(ApplicationConstants.COMMA_SEPARATOR);
            }
            params.append(ApplicationConstants.QUERY_SEPARATOR);
        }
        
        return params.toString();
    }
   
}
