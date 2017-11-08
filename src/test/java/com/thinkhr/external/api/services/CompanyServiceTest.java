package com.thinkhr.external.api.services;

import static com.thinkhr.external.api.utils.ApiTestDataUtil.createCompany;
import static com.thinkhr.external.api.utils.ApiTestDataUtil.OFFSET;
import static com.thinkhr.external.api.utils.ApiTestDataUtil.LIMIT;
import static com.thinkhr.external.api.utils.ApiTestDataUtil.SORT_BY;
import static com.thinkhr.external.api.utils.ApiTestDataUtil.SEARCH_SPEC;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import com.thinkhr.external.api.db.entities.Company;
import com.thinkhr.external.api.exception.ApplicationException;
import com.thinkhr.external.api.repositories.CompanyRepository;

/**
 * Junit to test all the methods of CompanyService.
 * 
 * @author Surabhi Bhawsar
 * @since 2017-11-06
 *
 */

@RunWith(SpringRunner.class)
public class CompanyServiceTest {
	
	@Mock
	private CompanyRepository companyRepository;
	
	@InjectMocks
	private CompanyService companyService;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * To verify getAllCompany method when no specific (Default) method arguments are provided 
	 * 
	 */
	@Test
	public void testGetAllCompany(){
		List<Company> companyList = new ArrayList<Company>();
		companyList.add(createCompany(1, "Pepcus", "Software", "PEP"));
		companyList.add(createCompany(2, "ThinkHR", "Service Provider", "THR"));
		companyList.add(createCompany(3, "ICICI", "Banking", "ICICI"));
		Pageable pageable = companyService.getPageable(null, null, null);
		
		when(companyRepository.findAll(null, pageable)).thenReturn(new PageImpl<Company>(companyList, pageable, companyList.size()));

		List<Company> result = companyService.getAllCompany(null, null, null, null);
		assertEquals(3, result.size());
	}
	
	/**
	 * To verify getAllCompany method when specific method arguments are provided
	 * 
	 */
	@Test
	public void testGetAllCompanyForParams(){
		List<Company> companyList = new ArrayList<Company>();
		companyList.add(createCompany(1, "Pepcus", "Software", "PEP"));
		companyList.add(createCompany(2, "ThinkHR", "Service Provider", "THR"));
		companyList.add(createCompany(3, "ICICI", "Banking", "ICICI"));
		Pageable pageable = companyService.getPageable(OFFSET, LIMIT, SORT_BY);
		Specification<Company> spec = null;
    	if(SEARCH_SPEC != null && SEARCH_SPEC.trim() != "") {
    		spec = new CompanySearchSpecification(SEARCH_SPEC);
    	}
		when(companyRepository.findAll(spec, pageable)).thenReturn(new PageImpl<Company>(companyList, pageable, companyList.size()));

		List<Company> result = companyService.getAllCompany(OFFSET, LIMIT, SORT_BY, SEARCH_SPEC);
		assertEquals(3, result.size());
	}
	
	/**
	 * To verify createCompany method
	 * 
	 */
	@Test
	public void testGetCompany(){
		Integer companyId = 1;
		Company company = createCompany(companyId, "Pepcus", "Software", "PEP");
		when(companyRepository.findOne(companyId)).thenReturn(company);
		Company result = companyService.getCompany(companyId);
		assertEquals(companyId, result.getCompanyId());
		assertEquals("Pepcus", result.getCompanyName());
		assertEquals("Software", result.getCompanyType());
		assertEquals("PEP", result.getDisplayName());
	}
	
	/**
	 * To verify addCompany method
	 * 
	 */
	@Test
	public void testAddCompany(){
		Integer companyId = 1;
		Company company = createCompany(1, "Pepcus", "Software", "PEP");
		when(companyRepository.save(company)).thenReturn(company);
		Company result = companyService.addCompany(company);
		assertEquals(companyId, result.getCompanyId());
		assertEquals("Pepcus", result.getCompanyName());
		assertEquals("Software", result.getCompanyType());
		assertEquals("PEP", result.getDisplayName());
	}

	/**
	 * To verify updateCompany method
	 * 
	 */
	
	@Test
	public void testUpdateCompany(){
		Integer companyId = 1;
		Company company = createCompany(1, "Pepcus", "Software", "PEP");
		when(companyRepository.save(company)).thenReturn(company);
		when(companyRepository.findOne(companyId)).thenReturn(company);
		Company result = null;
		try {
			result = companyService.updateCompany(company);
		} catch (ApplicationException e) {
		}
		assertEquals(companyId, result.getCompanyId());
		assertEquals("Pepcus", result.getCompanyName());
		assertEquals("Software", result.getCompanyType());
		assertEquals("PEP", result.getDisplayName());
	}
	
	/**
	 * To verify deleteCompany method
	 * 
	 */
	@Test
	public void testDeleteCompany() {
		Integer companyId = 1;
		try {
			companyService.deleteCompany(companyId);
		} catch (ApplicationException e) {
		}
        verify(companyRepository, times(1)).delete(companyId);
	}


}
