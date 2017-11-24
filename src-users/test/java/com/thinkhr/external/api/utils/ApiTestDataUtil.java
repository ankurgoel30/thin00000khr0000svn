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
import com.thinkhr.external.api.db.entities.User;

/**
 * Utility class to keep all utilities required for Junits
 * 
 * @author Surabhi Bhawsar
 * @Since 2017-11-06
 *
 */
public class ApiTestDataUtil {

	public static final String API_BASE_PATH = "/v1/";
	public static final String COMPANY_API_BASE_PATH = "/v1/companies/";
	public static final String USER_API_BASE_PATH = "/v1/users/";
	public static final String COMPANY_API_REQUEST_PARAM_OFFSET = "offset";
	public static final String COMPANY_API_REQUEST_PARAM_LIMIT = "limit";
	public static final String COMPANY_API_REQUEST_PARAM_SORT = "sort";
	public static final String COMPANY_API_REQUEST_PARAM_SEARCH_SPEC = "searchSpec";
	
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
	 * @param companySince
	 * @param specialNotes
	 * @return
	 */
	public static Company createCompany(Integer companyId, String companyName,
			String companyType, String displayName, 
			Date companySince, String specialNotes, String searchHelp) {
		Company company = new Company();
		if (companyId != null) {
			company.setCompanyId(companyId);
		}
		company.setCompanyName(companyName);
		company.setCompanyType(companyType);
		company.setDisplayName(displayName);
		company.setCompanySince(companySince);
		company.setSpecialNote(specialNotes);
		company.setSearchHelp(searchHelp); 
		company.setIsActive(1);
		return company;
	}
	
	/**
	 * Create a User entity for given inputs
	 * 
	 * @param contactId
	 * @param firstName
	 * @param lastName
	 * @param searchHelp
	 * @return
	 */
	public static User createUser(Integer userId, String firstName, String lastName,
			String searchHelp, String userName, Integer blockedAccount, String mkdate, 
			String codevalid, String updatePassword) {
		User user = new User();
		if (userId != null) {
			user.setUserId(userId);
		}
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setSearchHelp(searchHelp);
		user.setUserName(userName);
		user.setBlockedAccount(blockedAccount);
		user.setMkdate(mkdate);
		user.setCodevalid(codevalid);
		user.setUpdatePassword(updatePassword); 
		return user;
	}
	
	/**
	 * 
	 * @return
	 */
	public static User createUser() {
		return createUser(1, "Surabhi", "Bhawsar", "Software Developer", "sbhawsar", 1, "2017-08-08", "y", "y");
	}
	
	/**
	 * Creates a user response object
	 * 
	 * @param user
	 * @param httpStatus
	 * @return
	 */
	public static ResponseEntity<User> createUserResponseEntity(User user, HttpStatus httpStatus) {
		return new ResponseEntity<User>(user, httpStatus);
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
	 * createContactIdResponseEntity
	 * 
	 * @param contactId
	 * @param httpStatus
	 * @return
	 */
	public static ResponseEntity<Integer> createUserIdResponseEntity(Integer userId, HttpStatus httpStatus) {
		return new ResponseEntity<Integer>(userId, httpStatus);
	}

	/**
	 * Create Model object for Company
	 * 
	 * @return
	 */
	public static Company createCompany() {
		return createCompany(1, "Pepcus", "Software", "PEP", new Date(), "Special", "This is search help");
	}

	/**
	 * Create List for Company objects
	 * 
	 * @return
	 */
	public static List<Company> createCompanies() {
		List<Company> companies = new ArrayList<Company>();
		companies.add(createCompany(1, "Pepcus", "IT", "PEP", new Date(), "IT comp at Indore", "This is search help1"));
		companies.add(createCompany(2, "Google", "IT", "PEP", new Date(), "IT comp at Indore", "This is search help2"));
		companies.add(createCompany(3, "Facebook", "IT", "PEP", new Date(), "IT comp at Indore", "This is search help3"));
		companies.add(createCompany(4, "Suzuki", "IT", "PEP", new Date(), "IT comp at Indore", "This is search help4"));
		companies.add(createCompany(5, "General Motors", "IT", "PEP", new Date(), "IT comp at Indore", "This is search help5"));
		companies.add(createCompany(6, "L & T", "IT", "PEP", new Date(), "IT comp at Indore", "This is search help6"));
		companies.add(createCompany(7, "General Electric", "IT", "PEP", new Date(), "IT comp at Indore", "This is search help7"));
		companies.add(createCompany(8, "Oracle", "IT", "PEP", new Date(), "IT comp at Indore", "This is search help8"));
		companies.add(createCompany(9, "Microsoft", "IT", "PEP", new Date(), "IT comp at Indore", "This is search help9"));
		companies.add(createCompany(10, "Thinkhr", "IT", "PEP", new Date(), "IT comp at Indore", "This is search help10"));
		return companies;

	}
	
	/**
	 * Create List for User objects
	 * 
	 * @return
	 */
	public static List<User> createUserList() {
		List<User> users = new ArrayList<User>();
		
		users.add(createUser(1, "Isha", "Khandelwal", "ishaa", "ishaa", 1, "2017-08-08", "dummyCode", "updated"));
		users.add(createUser(2, "Sharmila", "Tagore", "stagore", "stagore", 1, "2017-08-08", "dummyCode", "updated"));
		users.add(createUser(3, "Surabhi", "Bhawsar", "sbhawsar", "sbhawsar", 1, "2017-08-08", "dummyCode", "updated"));
		users.add(createUser(4, "Shubham", "Solanki", "ssolanki", "ssolanki", 1, "2017-08-08", "dummyCode", "updated"));
		users.add(createUser(5, "Ajay", "Jain", "ajain", "ajain", 1, "dummyDate", "2017-08-08", "updated"));
		users.add(createUser(6, "Sandeep", "Vishwakarma", "svishwakarma", "svishwakarma", 1, "2017-08-08", "dummyCode", "updated"));
		users.add(createUser(7, "Sushil", "Mahajan", "smahajan", "smahajan", 1, "2017-08-08", "dummyCode", "updated"));
		users.add(createUser(8, "Sumedha", "Wani", "swani", "swani", 1, "2017-08-08", "dummyCode", "updated"));
		users.add(createUser(9, "Mohit", "Jain", "mjain", "mjain", 1, "2017-08-08", "dummyCode", "updated"));
		users.add(createUser(10, "Avi", "Jain", "ajain", "ajain", 1, "2017-08-08", "dummyCode", "updated"));
		
		return users;
	}

}
