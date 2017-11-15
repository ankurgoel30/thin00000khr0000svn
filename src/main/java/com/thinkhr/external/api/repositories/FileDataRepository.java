package com.thinkhr.external.api.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class FileDataRepository {
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Value("${com.test.batch.size}")
	int batchSize;

	public void save(List<String> result) {
		List<Object[]> companyRecordsToSave = new ArrayList<Object[]>();
		for(String record : result.subList(1, result.size()) ) {
			String[] values = record.split(",");
			String[] companyRecord = new String[7];
			companyRecord[0] = values[0].trim();
			companyRecord[1] = values[1].trim();
			companyRecord[2] = values[2].trim();
			companyRecord[3] = values[3].trim() ;//+ " " + values[4].trim() + " " + values[5].trim() + " "  + values[6].trim() ;//+ " " + values[7].trim() ;
			companyRecord[4] = values[8].trim();
			companyRecord[5] = values[9].trim();
			companyRecord[6] = values[10].trim();
			companyRecordsToSave.add(companyRecord);
		}

		int iteration = 0;
		try {
			for (int j = 0; j < companyRecordsToSave.size(); j += batchSize) {
				final List<Object[]> batchList = companyRecordsToSave.subList(j, j + batchSize > companyRecordsToSave.size() ? companyRecordsToSave.size() : j + batchSize);
				jdbcTemplate.batchUpdate("INSERT INTO clients(client_name,display_name, client_phone,officeLocation,industry,companysize,producer,search_help,client_type,client_since,special_note) VALUES (?,?,?,?,?,?,?,'dummy','dummy type','2017-10-09','dummy text')", batchList);
				iteration = iteration + 1;
			}
		} catch (Exception  ex) {
			ex.printStackTrace();
		}

	}
}
