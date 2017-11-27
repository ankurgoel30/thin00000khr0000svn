package com.thinkhr.external.api.services;

import static com.thinkhr.external.api.services.utils.EntitySearchUtil.getPageable;
import static com.thinkhr.external.api.utils.ApiTestDataUtil.COMPANY_SORT_BY;
import static com.thinkhr.external.api.utils.ApiTestDataUtil.LIMIT;
import static com.thinkhr.external.api.utils.ApiTestDataUtil.OFFSET;
import static com.thinkhr.external.api.utils.ApiTestDataUtil.SEARCH_SPEC;
import static com.thinkhr.external.api.utils.ApiTestDataUtil.createCompany;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.DataTruncation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import com.thinkhr.external.api.db.entities.Company;
import com.thinkhr.external.api.exception.ApplicationException;
import com.thinkhr.external.api.exception.MessageResourceHandler;
import com.thinkhr.external.api.model.FileImportResult;
import com.thinkhr.external.api.repositories.CompanyRepository;
import com.thinkhr.external.api.repositories.FileDataRepository;

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
    @Mock
    private FileDataRepository fileDataRepository;
    @Mock
    private MessageResourceHandler resourceHandler;
	
	@InjectMocks
	private CompanyService companyService;
	
	private String defaultSortField = "+companyName";
	
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
		Pageable pageable = getPageable(null, null, null, defaultSortField);
		
		when(companyRepository.findAll(null, pageable)).thenReturn(new PageImpl<Company>(companyList, pageable, companyList.size()));

		try {
			List<Company> result =  companyService.getAllCompany(null, null, null, null, null);
			assertEquals(3, result.size());
		} catch (ApplicationException ex) {
			fail("Not expected exception");
		}
		
		//TODO: ADD MORE test cases to verify limit, offset, sort and other search parameters.
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
		Pageable pageable = getPageable(OFFSET, LIMIT, COMPANY_SORT_BY, defaultSortField);
		Specification<Company> spec = null;
    	if(SEARCH_SPEC != null && SEARCH_SPEC.trim() != "") {
    		spec = new EntitySearchSpecification(SEARCH_SPEC, new Company());
    	}
		when(companyRepository.findAll(spec, pageable)).thenReturn(new PageImpl<Company>(companyList, pageable, companyList.size()));

		List<Company> result;
		try {
			result = companyService.getAllCompany(OFFSET, LIMIT, COMPANY_SORT_BY, SEARCH_SPEC, null);
			assertEquals(3, result.size());
		} catch (ApplicationException e) {
			fail("Not expecting application exception for a valid test case");
		}

	}
	
	/**
	 * To verify createCompany method
	 * 
	 */
	@Test
	public void testGetCompany() {
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
	 * To verify createCompany method
	 * 
	 */
	@Test
	public void testGetCompanyNotExists() {
		Integer companyId = 16;
		when(companyRepository.findOne(companyId)).thenReturn(null);
		Company result = companyService.getCompany(companyId);
		assertNull("companyId " + companyId + " does not exist", result);
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
			fail("Not expecting application exception for a valid test case");
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

    /**
     * To verify isValidBrokerId
     * At least one company record exists for given brokerId
     * 
     */
    @Test
    public void testIsValidBrokerId_1() {
        int brokerId = 12345;
        Map<String, String> filterParameters = new HashMap<String, String>(1);
        filterParameters.put("companyId", String.valueOf(brokerId));

        when(companyRepository.count(Matchers.any(Specification.class))).thenReturn(10L);
        assertTrue(companyService.isValidBrokerId(brokerId));
    }

    /**
     * To verify isValidBrokerId
     * No company record exists for given brokerId
     * 
     */
    @Test
    public void testIsValidBrokerId_2() {
        int brokerId = 12345;
        Map<String, String> filterParameters = new HashMap<String, String>(1);
        filterParameters.put("companyId", String.valueOf(brokerId));

        when(companyRepository.count(Matchers.any(Specification.class))).thenReturn(0L);
        assertFalse(companyService.isValidBrokerId(brokerId));
    }

    /**
     * To verify getCustomFieldsMap
     * When fileDataRepository.getCustomFields(id) return map with some entries
     * 
     */
    @Test
    public void tesGetCustomFieldsMap_1() {
        int brokerId = 12345;
        Map<String, String> customFieldsMap = new HashMap<String, String>(1);
        customFieldsMap.put("BUSINESS_ID", "1");
        customFieldsMap.put("CLIENT_TYPE", "4");

        when(fileDataRepository.getCustomFields(brokerId)).thenReturn(customFieldsMap);
        Map<String, String> customFieldsToHeaderMap = companyService.getCustomFieldsMap(brokerId);

        assertEquals("BUSINESS_ID", customFieldsToHeaderMap.get("custom1"));
        assertEquals("CLIENT_TYPE", customFieldsToHeaderMap.get("custom4"));
    }

    /**
     * To verify getCustomFieldsMap
     * When fileDataRepository.getCustomFields(id) return map with no entries
     * 
     */
    @Test
    public void tesGetCustomFieldsMap_2() {
        int brokerId = 12345;
        Map<String, String> customFieldsMap = new HashMap<String, String>(1);

        when(fileDataRepository.getCustomFields(brokerId)).thenReturn(customFieldsMap);
        Map<String, String> customFieldsToHeaderMap = companyService.getCustomFieldsMap(brokerId);

        assertEquals(0, customFieldsToHeaderMap.size());
    }

    /**
     * To verify saveByNativeQuery
     * records having blank elements
     * 
     */
    @Test
    public void testSaveByNativeQuery_Blank() {
        String[] headersInCsv = new String[] { "CLIENT_NAME", "DISPLAY_NAME", "PRODUCER", "INDUSTRY", "BUSINESS_ID", "ADDRESS" };
        List<String> records = new ArrayList<String>();
        FileImportResult fileImportResult = new FileImportResult();
        String rec1 = "";
        String rec2 = "";
        records.add(rec1);
        records.add(rec2);
        int brokerId = 187624;

        Map<String, String> customColumnToHeaderMap = new HashMap<String, String>();
        customColumnToHeaderMap.put("BUSINESS_ID", "1");

        when(fileDataRepository.getCustomFields(brokerId)).thenReturn(customColumnToHeaderMap);
        companyService.saveByNativeQuery(headersInCsv, records, fileImportResult, brokerId);

        assertEquals(2, fileImportResult.getTotalRecords());
        assertEquals(0, fileImportResult.getNumSuccessRecords());
        assertEquals(2, fileImportResult.getNumFailedRecords());
        assertEquals(2, fileImportResult.getFailedRecords().size());
    }

    /**
     * To verify saveByNativeQuery
     * records having 5 elements for all good records
     * 
     */
    @Test
    public void testSaveByNativeQuery_5_Good_Records() {
        String[] headersInCsv = new String[] { "CLIENT_NAME", "DISPLAY_NAME", "PRODUCER", "INDUSTRY", "BUSINESS_ID", "ADDRESS" };
        List<String> records = new ArrayList<String>();
        FileImportResult fileImportResult = new FileImportResult();
        String rec1 = "Pepcus,PEP,true,Software,PEP123,Indore";
        String rec2 = "Suzuki,SUZ,false,Automobile,SUZ123,Ahemdabad";
        String rec3 = "GOOGLE,GLE,true,Software,GLE125,US";
        String rec4 = "Patanjali,PAT,true,FMCG,PAT145,Haridwar";
        String rec5 = "Facebook,FB,false,Software,FB12234,USA";
        records.add(rec1);
        records.add(rec2);
        records.add(rec3);
        records.add(rec4);
        records.add(rec5);
        int brokerId = 187624;

        Map<String, String> customColumnToHeaderMap = new HashMap<String, String>();
        customColumnToHeaderMap.put("BUSINESS_ID", "1");

        when(fileDataRepository.getCustomFields(brokerId)).thenReturn(customColumnToHeaderMap);
        companyService.saveByNativeQuery(headersInCsv, records, fileImportResult, brokerId);

        assertEquals(5, fileImportResult.getTotalRecords());
        assertEquals(5, fileImportResult.getNumSuccessRecords());
        assertEquals(0, fileImportResult.getNumFailedRecords());
        assertEquals(0, fileImportResult.getFailedRecords().size());
    }

    /**
     * To verify saveByNativeQuery
     * records having elements with some missing fields
     * 
     */
    @Test
    public void testSaveByNativeQuery_MissingFields() {
        String[] headersInCsv = new String[] { "CLIENT_NAME", "DISPLAY_NAME", "PRODUCER", "INDUSTRY", "BUSINESS_ID", "ADDRESS" };
        List<String> records = new ArrayList<String>();
        FileImportResult fileImportResult = new FileImportResult();
        String rec1 = "Pepcus,PEP,true,Software,PEP123,Indore";
        String rec2 = "Suzuki,SUZ,false,Automobile,SUZ123,Ahemdabad";
        String rec3 = "GOOGLE,GLE,true,Software";
        String rec4 = "Patanjali,PAT,true,FMCG,PAT145,Haridwar";
        String rec5 = "Facebook,FB,false,Software,FB12234,USA";
        records.add(rec1);
        records.add(rec2);
        records.add(rec3);
        records.add(rec4);
        records.add(rec5);
        int brokerId = 187624;

        Map<String, String> customColumnToHeaderMap = new HashMap<String, String>();
        customColumnToHeaderMap.put("BUSINESS_ID", "1");

        when(fileDataRepository.getCustomFields(brokerId)).thenReturn(customColumnToHeaderMap);
        companyService.saveByNativeQuery(headersInCsv, records, fileImportResult, brokerId);

        assertEquals(5, fileImportResult.getTotalRecords());
        assertEquals(4, fileImportResult.getNumSuccessRecords());
        assertEquals(1, fileImportResult.getNumFailedRecords());
        assertEquals(1, fileImportResult.getFailedRecords().size());
    }

    /**
     * To verify saveByNativeQuery
     * record with a duplicate company name
     * 
     */
    @Test
    public void testSaveByNativeQuery_DuplicateCompanyName() {
        String[] headersInCsv = new String[] { "CLIENT_NAME", "DISPLAY_NAME", "PRODUCER", "INDUSTRY", "BUSINESS_ID", "ADDRESS" };
        List<String> records = new ArrayList<String>();
        FileImportResult fileImportResult = new FileImportResult();
        String rec1 = "Pepcus,PEP,true,Software,PEP123,Indore";
        String rec2 = "Suzuki,SUZ,false,Automobile,SUZ123,Ahemdabad";
        String rec3 = "Pepcus,GLE,true,Software"; // record with duplicate company name with rec1
        String rec4 = "Patanjali,PAT,true,FMCG,PAT145,Haridwar";
        String rec5 = "Patanjali,FB,false,Software,FB12234,USA";//// record with duplicate company name with rec4
        records.add(rec1);
        records.add(rec2);
        records.add(rec3);
        records.add(rec4);
        records.add(rec5);
        int brokerId = 187624;

        Map<String, String> customColumnToHeaderMap = new HashMap<String, String>();
        customColumnToHeaderMap.put("BUSINESS_ID", "1");

        when(fileDataRepository.getCustomFields(brokerId)).thenReturn(customColumnToHeaderMap);
        companyService.saveByNativeQuery(headersInCsv, records, fileImportResult, brokerId);

        assertEquals(5, fileImportResult.getTotalRecords());
        assertEquals(3, fileImportResult.getNumSuccessRecords());
        assertEquals(2, fileImportResult.getNumFailedRecords());
        assertEquals(2, fileImportResult.getFailedRecords().size());
    }

    /**
     * To verify saveByNativeQuery
     * fileDataRepository.saveCompanyRecord throws runtime exception
     * 
     */
    @Test
    public void testSaveByNativeQuery_ThrowException() {
        String[] headersInCsv = new String[] { "CLIENT_NAME", "DISPLAY_NAME", "PRODUCER", "INDUSTRY", "BUSINESS_ID", "ADDRESS" };
        List<String> records = new ArrayList<String>();
        FileImportResult fileImportResult = new FileImportResult();
        String rec1 = "Pepcus,PEP,true,Software,PEP123,Indore";
        String rec2 = "Suzuki,SUZ,false,Automobile,SUZ123,Ahemdabad";
        String rec3 = "GOOGLE,GLE,true,Software";
        String rec4 = "Patanjali,PAT,true,FMCG,PAT145,Haridwar";
        String rec5 = "Facebook,FB,false,Software,FB12234,USA";
        records.add(rec1);
        records.add(rec2);
        records.add(rec3);
        records.add(rec4);
        records.add(rec5);
        int brokerId = 187624;

        Map<String, String> customColumnToHeaderMap = new HashMap<String, String>();
        customColumnToHeaderMap.put("BUSINESS_ID", "1");

        when(fileDataRepository.getCustomFields(brokerId)).thenReturn(customColumnToHeaderMap);
        doThrow(new RuntimeException()).when(fileDataRepository).saveCompanyRecord(any(), any(), any(), any());
        companyService.saveByNativeQuery(headersInCsv, records, fileImportResult, brokerId);

        assertEquals(5, fileImportResult.getTotalRecords());
        assertEquals(0, fileImportResult.getNumSuccessRecords());
        assertEquals(5, fileImportResult.getNumFailedRecords());
        assertEquals(5, fileImportResult.getFailedRecords().size());
    }

    /**
     * To verify saveByNativeQuery
     * fileDataRepository.saveCompanyRecord throws wrapped DataTruncation exception
     * 
     */
    @Test
    public void testSaveByNativeQuery_ThrowDatatruncException() {
        String[] headersInCsv = new String[] { "CLIENT_NAME", "DISPLAY_NAME", "PRODUCER", "INDUSTRY", "BUSINESS_ID", "ADDRESS" };
        List<String> records = new ArrayList<String>();
        FileImportResult fileImportResult = new FileImportResult();
        String rec1 = "Pepcus,PEP,true,Software,PEP123,Indore";
        String rec2 = "Suzuki,SUZ,false,Automobile,SUZ123,Ahemdabad";
        String rec3 = "GOOGLE,GLE,true,Software";
        String rec4 = "Patanjali,PAT,true,FMCG,PAT145,Haridwar";
        String rec5 = "Facebook,FB,false,Software,FB12234,USA";
        records.add(rec1);
        records.add(rec2);
        records.add(rec3);
        records.add(rec4);
        records.add(rec5);
        int brokerId = 187624;

        Map<String, String> customColumnToHeaderMap = new HashMap<String, String>();
        customColumnToHeaderMap.put("BUSINESS_ID", "1");

        when(fileDataRepository.getCustomFields(brokerId)).thenReturn(customColumnToHeaderMap);
        RuntimeException ex = new RuntimeException();
        ex.initCause(new DataTruncation(0, true, true, 12, 13));
        doThrow(ex).when(fileDataRepository).saveCompanyRecord(any(), any(), any(), any());
        companyService.saveByNativeQuery(headersInCsv, records, fileImportResult, brokerId);

        assertEquals(5, fileImportResult.getTotalRecords());
        assertEquals(0, fileImportResult.getNumSuccessRecords());
        assertEquals(5, fileImportResult.getNumFailedRecords());
        assertEquals(5, fileImportResult.getFailedRecords().size());
    }
}
