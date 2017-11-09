package com.thinkhr.external.api.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.PropertyException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thinkhr.external.api.db.entities.Company;

/**
 * Utility class to keep all utilities required for Junits
 * 
 * @author Surabhi Bhawsar
 * @Since 2017-11-06
 *
 */
public class ApiTestDataUtil {

	public static final String COMPANY_API_BASE_PATH = "/v1/companies/";
	public static final String COMPANY_API_REQUEST_PARAM_OFFSET = "offset";
	public static final String COMPANY_API_REQUEST_PARAM_LIMIT = "limit";
	public static final String COMPANY_API_REQUEST_PARAM_SORT = "sort";
	public static final String COMPANY_API_REQUEST_PARAM_SEARCH_SPEC = "searchSpec";
	public static final Integer OFFSET = 3;
    public static final Integer LIMIT = 3;
    public static final String SORT_BY = "companyType";
    public static final String SEARCH_SPEC = null;

	
	/**
	 * Convert object into Json String
	 * 
	 * @param object
	 * @param kclass
	 * @return
	 * @throws JAXBException 
	 * @throws PropertyException 
	 */
	public static String getJsonString(Object object) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		return mapper.writeValueAsString(object);	
	}
	

	/**
	 * Create a Company Model for given inputs
	 * 
	 * @param companyId
	 * @param companyName
	 * @param companyType
	 * @param displayName
	 * @return company
	 */
	public static Company createCompany(Integer companyId, String companyName, String companyType, String displayName) {
		Company company = new Company();
		if (companyId != null) {
			company.setCompanyId(companyId);
		}
		company.setCompanyName(companyName);
		company.setCompanyType(companyType);
		company.setDisplayName(displayName);
		return company;
	}
	
	/**
	 * Creates a company response object
	 * 
	 * @param company
	 * @param httpStatus
	 * @return
	 */
	public static ResponseEntity<Company> createCompanyResponseEntity(Company company, HttpStatus httpStatus) {
		return new ResponseEntity<Company>(company, httpStatus);
	}
	
	/**
	 * createCompanyIdResponseEntity
	 * 
	 * @param companyId
	 * @param httpStatus
	 * @return
	 */
	public static ResponseEntity<Integer> createCompanyIdResponseEntity(Integer companyId, HttpStatus httpStatus) {
		return new ResponseEntity<Integer>(companyId, httpStatus);
	}

	/**
	 * Create Model object for Company
	 * 
	 * @return
	 */
	public static Company createCompany() {
		Company company = new Company();
		company.setCompanyId(17);
		company.setSearchHelp("HELP"); 
		company.setCompanyName("Pepcus");
		company.setCompanyType("Software");
		company.setDisplayName("PEP");
		company.setCompanySince(new Date());
		company.setSpecialNote("SPECIAL"); 
		return company;
	}
	
	/**
	 * Create List for Company objects
	 * 
	 * @return
	 */
	public static List<Company> createCompanies() {
		List<Company> companies = new ArrayList<Company>();
	
		Company company1 = new Company();
		company1.setCompanyId(1);
		company1.setSearchHelp("This is first HELP"); 
		company1.setCompanyName("Pepcus");
		company1.setCompanyType("Software");
		company1.setDisplayName("PEP");
		company1.setCompanySince(new Date());
		company1.setSpecialNote("SPECIAL"); 
		companies.add(company1);
		
		Company company2 = new Company();
		company2.setCompanyId(2);
		company2.setSearchHelp("This is second HELP"); 
		company2.setCompanyName("Pepcus");
		company2.setCompanyType("Software");
		company2.setDisplayName("PEP");
		company2.setCompanySince(new Date());
		company2.setSpecialNote("SPECIAL"); 
		companies.add(company2);
		
		Company company3 = new Company();
		company3.setCompanyId(3);
		company3.setSearchHelp("This is third HELP"); 
		company3.setCompanyName("Pepcus");
		company3.setCompanyType("Software");
		company3.setDisplayName("PEP");
		company3.setCompanySince(new Date());
		company3.setSpecialNote("SPECIAL"); 
		companies.add(company3);
		
		Company company4 = new Company();
		company4.setCompanyId(4);
		company4.setSearchHelp("This is fourth HELP"); 
		company4.setCompanyName("Pepcus");
		company4.setCompanyType("Software");
		company4.setDisplayName("PEP");
		company4.setCompanySince(new Date());
		company4.setSpecialNote("SPECIAL"); 
		companies.add(company4);
		
		Company company5 = new Company();
		company5.setCompanyId(5);
		company5.setSearchHelp("This is fifth HELP"); 
		company5.setCompanyName("Pepcus");
		company5.setCompanyType("Software");
		company5.setDisplayName("PEP");
		company5.setCompanySince(new Date());
		company5.setSpecialNote("SPECIAL"); 
		companies.add(company5);
		
		Company company6 = new Company();
		company6.setCompanyId(6);
		company6.setSearchHelp("This is sixth HELP"); 
		company6.setCompanyName("Pepcus");
		company6.setCompanyType("Software");
		company6.setDisplayName("PEP");
		company6.setCompanySince(new Date());
		company6.setSpecialNote("SPECIAL"); 
		companies.add(company6);
		
		Company company7 = new Company();
		company7.setCompanyId(7);
		company7.setSearchHelp("This is seventh HELP"); 
		company7.setCompanyName("Pepcus");
		company7.setCompanyType("Software");
		company7.setDisplayName("PEP");
		company7.setCompanySince(new Date());
		company7.setSpecialNote("SPECIAL"); 
		companies.add(company7);
		
		Company company8 = new Company();
		company8.setCompanyId(8);
		company8.setSearchHelp("This is eighth HELP"); 
		company8.setCompanyName("Pepcus");
		company8.setCompanyType("Software");
		company8.setDisplayName("PEP");
		company8.setCompanySince(new Date());
		company8.setSpecialNote("SPECIAL"); 
		companies.add(company8);
		
		Company company9 = new Company();
		company9.setCompanyId(9);
		company9.setSearchHelp("This is nineth HELP"); 
		company9.setCompanyName("Pepcus");
		company9.setCompanyType("Software");
		company9.setDisplayName("PEP");
		company9.setCompanySince(new Date());
		company9.setSpecialNote("SPECIAL"); 
		companies.add(company9);
		
		Company company10 = new Company();
		company10.setCompanyId(10);
		company10.setSearchHelp("This is tenth HELP"); 
		company10.setCompanyName("Pepcus");
		company10.setCompanyType("Software");
		company10.setDisplayName("PEP");
		company10.setCompanySince(new Date());
		company10.setSpecialNote("SPECIAL"); 
		companies.add(company10);

		return companies;
	}
	
	

}
