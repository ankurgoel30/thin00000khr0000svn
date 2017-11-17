package com.thinkhr.external.api.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class FileDataRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Value("${com.test.batch.size}")
    int batchSize;

    public void save(List<String> result) {
        List<Object[]> companyRecordsToSave = new ArrayList<Object[]>();
        for (String record : result.subList(1, result.size())) {
            String[] values = record.split(",");
            String[] companyRecord = new String[7];
            companyRecord[0] = values[0].trim();
            companyRecord[1] = values[1].trim();
            companyRecord[2] = values[2].trim();
            companyRecord[3] = values[3].trim();//+ " " + values[4].trim() + " " + values[5].trim() + " "  + values[6].trim() ;//+ " " + values[7].trim() ;
            companyRecord[4] = values[8].trim();
            companyRecord[5] = values[9].trim();
            companyRecord[6] = values[10].trim();
            companyRecordsToSave.add(companyRecord);
        }

        int iteration = 0;
        try {
            for (int j = 0; j < companyRecordsToSave.size(); j += batchSize) {
                final List<Object[]> batchList = companyRecordsToSave.subList(j,
                        j + batchSize > companyRecordsToSave.size() ? companyRecordsToSave.size() : j + batchSize);
                jdbcTemplate.batchUpdate(
                        "INSERT INTO clients(client_name,display_name, client_phone,officeLocation,industry,companysize,producer,search_help,client_type,client_since,special_note) VALUES (?,?,?,?,?,?,?,'dummy','dummy type','2017-10-09','dummy text')",
                        batchList);
                iteration = iteration + 1;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void saveCompanyRecord(String[] companyColumns, Object[] companyColumnsValues, String[] locationColumns,
            Object[] locationColumnValues) {
        // INSERT INTO clients(client_name,display_name, client_phone,industry,companysize,producer,custom1,custom2,custom3,custom4,
        // search_help,client_type,client_since,special_note) " + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)
        StringBuffer insertClientSql = new StringBuffer();
        insertClientSql.append("INSERT into clients(");
        insertClientSql.append(StringUtils.join(companyColumns, ","));
        insertClientSql.append(",search_help,client_type,special_note,client_since)");
        insertClientSql.append(" Values(");
        for (int i = 0; i < companyColumns.length; i++) {
            insertClientSql.append(" ?,");
        }
        insertClientSql.append("?,?,?,?)");

        //INSERT INTO locations(address,address2,city,state,zip,client_id) values(?,?,?,?,?,?);
        StringBuffer insertLocationSql = new StringBuffer();
        insertLocationSql.append("INSERT INTO locations(");
        insertLocationSql.append(StringUtils.join(locationColumns, ","));
        insertLocationSql.append(",client_id");
        insertLocationSql.append(")");
        insertLocationSql.append(" Values(");
        for (int i = 0; i < locationColumns.length; i++) {
            insertLocationSql.append(" ?,");
        }
        insertLocationSql.append("?)");

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(insertClientSql.toString(), Statement.RETURN_GENERATED_KEYS);
                int i;
                for (i = 0; i < companyColumnsValues.length; i++) {
                    statement.setString(i + 1, (String) companyColumnsValues[i]);
                }
                statement.setString(++i, "");
                statement.setString(++i, "");
                statement.setString(++i, "");
                Date now = new Date();
                statement.setString(++i, now.getYear() + "-" + now.getMonth() + "-" + now.getDate());
                return statement;
            }
        }, keyHolder);

        int clientId = keyHolder.getKey().intValue();

        try {
            //jdbcTemplate.update(insertLocationSql.toString(), locationColumnValues, clientId);
            jdbcTemplate.update(new PreparedStatementCreator() {

                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(insertLocationSql.toString());
                    int i;
                    for (i = 0; i < locationColumnValues.length; i++) {
                        statement.setString(i + 1, (String) locationColumnValues[i]);
                    }
                    statement.setInt(++i, clientId);
                    return statement;
                }
            });
        } catch (Exception ex) {
            //rollback client table  insert if location table insert fails
            String deleteSql = "Delete from clients where clientId=?";
            jdbcTemplate.update(deleteSql, clientId);
            throw ex;
        }
    }

    public Map<String, String> getCustomFields(int id) {
        String customFieldsLookupQuery = "Select customFieldDisplayLabel,customFieldColumnName from app_throne_custom_fields  where companyId = ?";

        return jdbcTemplate.query(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(customFieldsLookupQuery);
                statement.setInt(1, id);
                return statement;
            }
        }, new ResultSetExtractor<Map<String, String>>() {
            @Override
            public Map<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
                Map<String, String> customFieldsMap = new HashMap<String, String>();
                while (rs.next()) {
                    String key = rs.getString(1);
                    String value = rs.getString(2);
                    customFieldsMap.put(key, value);
                }
                return customFieldsMap;
            }
        });
    }
}
