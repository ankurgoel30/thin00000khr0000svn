package com.thinkhr.external.api.utils;

import javax.xml.bind.JAXBException;
import javax.xml.bind.PropertyException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thinkhr.external.api.db.entities.Company;
import com.thinkhr.external.api.model.CompanyModel;

/**
 * Utility class to keep all utilities required for Junits
 * @author Surabhi Bhawsar
 * @Since 2017-11-06
 *
 */
public class ApiTestDataUtil {

	public static final String COMPANY_API_BASE_PATH = "/v1/companies/";

	
	/**
	 * Convert object into Json String
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
	 * @param companyId
	 * @param companyName
	 * @param companyType
	 * @param displayName
	 * @return company
	 */
	public static Company createCompany(Integer companyId, String companyName, String companyType, String displayName) {
		Company company = new Company();
		company.setCompanyId(companyId);
		company.setCompanyName(companyName);
		company.setCompanyType(companyType);
		company.setDisplayName(displayName);
		return company;
	}
	
	/**
	 * @param company
	 * @param httpStatus
	 * @return
	 */
	public static ResponseEntity<CompanyModel> createCompanyResponseEntity(CompanyModel company, HttpStatus httpStatus) {
		return new ResponseEntity<CompanyModel>(company, httpStatus);
	}
	
	/**
	 * @param companyId
	 * @param httpStatus
	 * @return
	 */
	public static ResponseEntity<Integer> createCompanyIdResponseEntity(Integer companyId, HttpStatus httpStatus) {
		return new ResponseEntity<Integer>(companyId, httpStatus);
	}

	/**
	 * @return
	 */
	public static CompanyModel createCompanyModel() {
		CompanyModel company = new CompanyModel();
		company.setCompanyId(1);
		company.setCompanyName("Pepcus");
		company.setCompanyType("Software");
		company.setDisplayName("PEP");
		return company;
	}

	/**
	 * @param companyId
	 * @param companyName
	 * @param companyType
	 * @param displayName
	 * @return company
	 */
	public static CompanyModel createCompanyModel(Integer companyId, String companyName, String companyType, String displayName) {
		CompanyModel company = new CompanyModel();
		company.setCompanyId(companyId);
		company.setCompanyName(companyName);
		company.setCompanyType(companyType);
		company.setDisplayName(displayName);
		return company;
	}


}
