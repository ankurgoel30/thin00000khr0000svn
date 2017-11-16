package com.thinkhr.external.api.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(rollbackFor = Throwable.class)
    public void saveCompanyRecord(String record) {
        String insertClientSQL = "INSERT INTO clients(client_name,display_name, client_phone,industry,companysize,producer,custom1,custom2,custom3,custom4,"
                + "search_help,client_type,client_since,special_note) " + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        String insertLocationSQL = "INSERT INTO locations(address,address2,city,state,zip,client_id) values(?,?,?,?,?,?)";
        String[] values = record.split(",");

        String[] companyRecord = new String[14];
        String[] locationRecord = new String[6];
        companyRecord[0] = values[0].trim(); // companyName
        companyRecord[1] = values[1].trim(); // displayName
        companyRecord[2] = values[2].trim(); // companyPhone
        companyRecord[3] = values[8].trim(); // industry
        companyRecord[4] = values[9].trim(); // companySize
        companyRecord[5] = values[10].trim();// producer

        companyRecord[6] = values[11].trim();// custom1
        companyRecord[7] = values[12].trim();// custom2
        companyRecord[8] = values[13].trim();// custom3
        companyRecord[9] = values[14].trim();// custom4

        //Not null fields. Setting dummy values
        companyRecord[10] = "unknown";
        companyRecord[11] = "unknown";
        companyRecord[12] = "2017-10-10";
        companyRecord[13] = "unknown";

        // Fields to be inserted in location table
        locationRecord[0] = values[3].trim(); //Adderess
        locationRecord[1] = values[4].trim(); //Addresss2
        locationRecord[2] = values[5].trim(); //City
        locationRecord[3] = values[6].trim(); //State
        locationRecord[4] = values[7].trim(); //Zip

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(insertClientSQL, Statement.RETURN_GENERATED_KEYS);
                for (int i = 0; i < companyRecord.length; i++) {
                    statement.setString(i + 1, companyRecord[i]);
                }
                return statement;
            }
        }, keyHolder);

        int clientId = keyHolder.getKey().intValue();

        locationRecord[5] = String.valueOf(clientId);
        jdbcTemplate.update(insertLocationSQL, locationRecord);
    }
}
