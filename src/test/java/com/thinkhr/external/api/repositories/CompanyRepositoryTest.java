package com.thinkhr.external.api.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static com.thinkhr.external.api.services.utils.EntitySearchUtil.getPageable;
import static com.thinkhr.external.api.utils.ApiTestDataUtil.createCompanies;
import static com.thinkhr.external.api.utils.ApiTestDataUtil.createCompany;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import com.thinkhr.external.api.db.entities.Company;
import com.thinkhr.external.api.services.EntitySearchSpecification;

/**
 * Junit to verify methods of CompanyRepository with use of H2 database
 * @author Surabhi Bhawsar
 * @since 2017-11-06
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.AUTO_CONFIGURED)
public class CompanyRepositoryTest {

	@Autowired
	private CompanyRepository companyRepository;
	
	private Integer savedCompanyId;  
	
	private String defaultSortField = "+companyName";

	/**
	 * To test save method
	 */
	@Test
	public void testSave() {
		Company company = createCompany(null, "HDFC", "Banking", "HHH");
		company.setSearchHelp("Test");
		company.setCompanySince(new Date());
		company.setSpecialNote("111");
		
		Company companySaved = companyRepository.save(company);
		
		savedCompanyId = company.getCompanyId();
		assertNotNull(companySaved);
		assertNotNull(companySaved.getCompanyId());// As company is saved successfully.
		assertEquals(companySaved.getSearchHelp(), company.getSearchHelp());
		assertEquals(companySaved.getCompanySince(), company.getCompanySince());
		assertEquals(companySaved.getSpecialNote(), company.getSpecialNote());
		assertEquals(companySaved.getCompanyName(), company.getCompanyName());
		
	}
	
	/**
	 * To test findAll method
	 */
	@Test
	public void testFindAll() {
		Company company1 = createCompany(null, "HDFC", "Banking", "HHH");
		company1.setSearchHelp("Test");
		company1.setCompanySince(new Date());
		company1.setSpecialNote("111");
		
		//SAVE a Company
		companyRepository.save(company1);

		Company company2 = createCompany(null, "PEPCUS", "IT", "PEP");
		company2.setSearchHelp("PEPTEST");
		company2.setCompanySince(new Date());
		company2.setSpecialNote("22");

		//SAVE second Company
		companyRepository.save(company2);

		List<Company> companyList = (List<Company>) companyRepository.findAll();
		  
		assertNotNull(companyList);
		assertEquals(companyList.size(), 2);
	}

	/**
	 * To test findOne method
	 */
	@Test
	public void testFindOne() {
		Company company1 = createCompany(null, "HDFC", "Banking", "HHH");
		company1.setSearchHelp("Test");
		company1.setCompanySince(new Date());
		company1.setSpecialNote("111");
		
		//SAVE a Company
		Company savedCompany = companyRepository.save(company1);

		Company findCompany = (Company) companyRepository.findOne(savedCompany.getCompanyId());
		assertNotNull(findCompany);
		assertEquals(findCompany.getSearchHelp(), "Test");
		assertEquals(findCompany.getCompanyName(), "HDFC");
	}
	
	/**
	 * To test delete method
	 */
	@Test
	public void testDelete() {
		Company company1 = createCompany(null, "HDFC", "Banking", "HHH");
		company1.setSearchHelp("Test");
		company1.setCompanySince(new Date());
		company1.setSpecialNote("111");
		
		//SAVE a Company
		Company savedCompany = companyRepository.save(company1);

		//DELETING record here.
		companyRepository.delete(savedCompany);
		
		//FIND saved company with find and it should not  return
		Company findCompany = (Company) companyRepository.findOne(savedCompany.getCompanyId());
		assertEquals(null, findCompany);
	}
	
	/**
	 * Test to verify get all companies when no parameters are provided 
	 * i.e., all parameters are default provided.  
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAllCompaniesWithDefault() throws Exception {
		
		saveCompaniesToH2DB();
		
		String searchSpec = null;
		Pageable pageable = getPageable(null, null, null, defaultSortField);
    	Specification<Company> spec = null;
    	if(StringUtils.isNotBlank(searchSpec)) {
    		spec = new EntitySearchSpecification<Company>(searchSpec, new Company());
    	}
    	Page<Company> companies  = (Page<Company>) companyRepository.findAll(spec, pageable);
    	
    	assertNotNull(companies.getContent());
    	assertEquals(companies.getContent().size(), 10);
	}

	/**
	 * Test to verify get all companies when searchSpec is default and all other 
	 * parameters are provided (sort is ascending)  
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAllCompaniesWithParamsAndSearchSpecNull() throws Exception {
		
		saveCompaniesToH2DB();
		String searchSpec = null;
		Pageable pageable = getPageable(3, 3, "+companyType", defaultSortField);
    	Specification<Company> spec = null;
    	if(StringUtils.isNotBlank(searchSpec)) {
    		spec = new EntitySearchSpecification<Company>(searchSpec, new Company());
    	}
    	Page<Company> companies  = (Page<Company>) companyRepository.findAll(spec, pageable);
    	
    	assertNotNull(companies.getContent());
    	assertEquals(companies.getContent().size(), 3);
	}
	
	/**
	 * Test to verify get all companies searchSpec is provided and other parameters are default.  
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAllCompaniesWithParamsAndPageableNull() throws Exception {
		
		saveCompaniesToH2DB();
		String searchSpec = "fifth";
		Pageable pageable = getPageable(null, null, null, defaultSortField);
    	Specification<Company> spec = null;
    	if(StringUtils.isNotBlank(searchSpec)) {
    		spec = new EntitySearchSpecification<Company>(searchSpec, new Company());
    	}
    	Page<Company> companies  = (Page<Company>) companyRepository.findAll(spec, pageable);
    	
    	assertNotNull(companies.getContent());
    	assertEquals(companies.getContent().size(), 1);
	}
	
	/**
	 * Test to verify get all companies when all parameters are provided 
	 * and sort is ascending   
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAllCompaniesWithParamsAndAscSort() throws Exception {
		
		saveCompaniesToH2DB();
		
		String searchSpec = "General";
		Pageable pageable = getPageable(0, null, "+companyType", defaultSortField);
    	Specification<Company> spec = null;
    	if(StringUtils.isNotBlank(searchSpec)) {
    		spec = new EntitySearchSpecification<Company>(searchSpec, new Company());
    	}
    	Page<Company> companies  = (Page<Company>) companyRepository.findAll(spec, pageable);
    	
    	assertNotNull(companies.getContent());
    	assertEquals(companies.getContent().size(), 2);
	}
	
	/**
	 * Test to verify get all companies when all parameters are provided
	 * and sort is descending.  
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAllCompaniesWithParamsAndDescSort() throws Exception {
		
		saveCompaniesToH2DB();
		String searchSpec = "Suzuki";
		Pageable pageable = getPageable(null, null, "-companyType", defaultSortField);
    	Specification<Company> spec = null;
    	if(StringUtils.isNotBlank(searchSpec)) {
    		spec = new EntitySearchSpecification<Company>(searchSpec, new Company());
    	}
    	Page<Company> companies  = (Page<Company>) companyRepository.findAll(spec, pageable);
    	
    	
    	assertNotNull(companies.getContent());
    	assertEquals(companies.getContent().size(), 1);
	}
	
	/**
	 * Save Companies to test database
	 */
	private List<Company> saveCompaniesToH2DB() {
		List<Company> companyList = createCompanies();

		for (Company company : companyList) {
			companyRepository.save(company);
		}
		return companyList;
	}
	
}