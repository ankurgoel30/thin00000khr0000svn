package com.thinkhr.external.api.services;

import static com.thinkhr.external.api.ApplicationConstants.DEFAULT_SORT_BY_COMPANY_NAME;
import static com.thinkhr.external.api.ApplicationConstants.MAX_RECORDS_COMPANY_CSV_IMPORT;
import static com.thinkhr.external.api.ApplicationConstants.REQUIRED_HEADERS_COMPANY_CSV_IMPORT;
import static com.thinkhr.external.api.ApplicationConstants.SPACE;
import static com.thinkhr.external.api.ApplicationConstants.VALID_FILE_EXTENSION_IMPORT;
import static com.thinkhr.external.api.services.utils.EntitySearchUtil.getEntitySearchSpecification;
import static com.thinkhr.external.api.services.utils.EntitySearchUtil.getPageable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.web.multipart.MultipartFile;

import com.thinkhr.external.api.db.entities.Company;
import com.thinkhr.external.api.exception.APIErrorCodes;
import com.thinkhr.external.api.exception.ApplicationException;
import com.thinkhr.external.api.repositories.CompanyRepository;
import com.thinkhr.external.api.repositories.FileDataRepository;
import com.thinkhr.external.api.services.utils.FileImportUtil;

/**
*
* Provides a collection of all services related with Company
* database object
*
* @author Surabhi Bhawsar
* @Since 2017-11-04
*
* 
*/

@Service
public class CompanyService  extends CommonService {
	
	private Logger logger = LoggerFactory.getLogger(CompanyService.class);
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private FileDataRepository fileDataRepository;

    /**
     * To fetch companies records. Based on given parameters companies records will be filtered out.
     * 
     * @param Integer offset First record index from database after sorting. Default value is 0
     * @param Integer limit Number of records to be fetched. Default value is 50
     * @param String sortField Field on which records needs to be sorted
     * @param String searchSpec Search string for filtering results
     * @param Map<String, String>
     * @return List<Company> object 
     * @throws ApplicationException 
     */
    public List<Company> getAllCompany(Integer offset, 
    		Integer limit,
    		String sortField, 
    		String searchSpec, 
    		Map<String, String> requestParameters) throws ApplicationException {
    	
    	List<Company> companies = new ArrayList<Company>();

    	Pageable pageable = getPageable(offset, limit, sortField, getDefaultSortField());
    	
		if(logger.isDebugEnabled()) {
			logger.debug("Request parameters to filter, size and paginate records ");
			requestParameters.entrySet().stream().forEach(entry -> { logger.debug(entry.getKey() + ":: " + entry.getValue()); });
		}

    	
    	Specification<Company> spec = getEntitySearchSpecification(searchSpec, requestParameters, Company.class, new Company());

    	Page<Company> companyList  = (Page<Company>) companyRepository.findAll(spec, pageable);

    	companyList.getContent().forEach(c -> companies.add(c));
    	
    	return companies;
    }

    
	/**
     * Fetch specific company from database
     * 
     * @param companyId
     * @return Company object 
     */
    public Company getCompany(Integer companyId) {
    	return companyRepository.findOne(companyId);
    }
    
    /**
     * Add a company in database
     * 
     * @param company object
     */
    public Company addCompany(Company company)  {
    	return companyRepository.save(company);
    }
    
    /**
     * Update a company in database
     * 
     * @param company object
     * @throws ApplicationException 
     */
    public Company updateCompany(Company company) throws ApplicationException  {
    	Integer companyId = company.getCompanyId();
    	
		if (null == companyRepository.findOne(companyId)) {
    		throw ApplicationException.createEntityNotFoundError(APIErrorCodes.ENTITY_NOT_FOUND, "company", "companyId="+companyId);
    	}
		
    	return companyRepository.save(company);

	}

	/**
	 * Delete specific company from database
	 * 
	 * @param companyId
	 */
	public int deleteCompany(int companyId) throws ApplicationException {
		try {
			companyRepository.delete(companyId);
		} catch (EmptyResultDataAccessException ex ) {
			throw ApplicationException.createEntityNotFoundError(APIErrorCodes.ENTITY_NOT_FOUND, "company", "companyId="+companyId);
		}
		return companyId;
	} 


	public void importFile(MultipartFile fileToImport) throws ApplicationException {
		String fileName = fileToImport.getOriginalFilename();
		
		// Validate if file has valid extension
		if(!FileImportUtil.hasValidExtension(fileName,VALID_FILE_EXTENSION_IMPORT))  {
			throw ApplicationException.createFileImportError(APIErrorCodes.INVALID_FILE_EXTENTION,fileName,VALID_FILE_EXTENSION_IMPORT);
		}

		if (fileToImport.isEmpty()) {
			throw ApplicationException.createFileImportError(APIErrorCodes.NO_RECORDS_FOUND_FOR_IMPORT,fileName);
		} 

		// Read all lines from file
		List<String> result = FileImportUtil.readFileContent(fileToImport);
		
		// Validate for missing headers
		String[] headers = result.get(0).split(",");
		String[] missingHeadersIfAny = FileImportUtil.getMissingHeaders(headers, REQUIRED_HEADERS_COMPANY_CSV_IMPORT);
		if(missingHeadersIfAny.length !=0 ) {
			String requiredHeaders = String.join(",", REQUIRED_HEADERS_COMPANY_CSV_IMPORT)  ;
			String missingHeaders = String.join(",", missingHeadersIfAny)  ;
			throw ApplicationException.createFileImportError(APIErrorCodes.MISSING_REQUIRED_HEADERS,fileName, missingHeaders ,requiredHeaders);
		}
		
		// If we are here then file is a valid csv file with all the required headers. 
		// Now  validate if it has records and number of records not exceed max allowed records
		int numOfRecords = result.size() - 1 ; // as first line is for header
		if(numOfRecords == 0 ) {
			throw ApplicationException.createFileImportError(APIErrorCodes.NO_RECORDS_FOUND_FOR_IMPORT,fileName);
		}
		if (numOfRecords > MAX_RECORDS_COMPANY_CSV_IMPORT) {
			throw ApplicationException.createFileImportError(APIErrorCodes.MAX_RECORD_EXCEEDED,String.valueOf(MAX_RECORDS_COMPANY_CSV_IMPORT));
		}
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Map<String,Company> fileRecordToObjMap =  new LinkedHashMap<String,Company>(result.size());
		List<Company> companies = new ArrayList<Company>();
		for (String record : result.subList(1, result.size())) {
			try { 
				Company company =  getCompanyFromFileRecord( headers , record);
				fileRecordToObjMap.put(record, company);
				companies.add(company);
			} catch  (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ex) { // Ideally these exceptions should not occur
				fileRecordToObjMap.put(record, null);
			}
		}
		
		List<String> duplicateSkippedRecords = new ArrayList<String>(); // List to maintain duplicate records on the basis of companyName
		int numOfSuccessRecords = 0;
		int numOfFailedRecords = 0;
		//TODO : Uncomment this code
		/*for(String record : fileRecordToObjMap.keySet()) {
			Set<String> companyNames = new HashSet<String>() ;
			Company company = fileRecordToObjMap.get(record);
			if (companyNames.contains(company.getCompanyName())) {
				// skip the record for save and add it to duplicateReocords list
				duplicateSkippedRecords.add(record);
				numOfFailedRecords++;
			} else {
				try { 
					this.addCompany(company);
					companyNames.add(company.getCompanyName());
					numOfSuccessRecords++;
				} catch (Exception ex ) {
					//TODO : KEEP track of insertion failure for this record with reason
					//ex.printStackTrace();
					numOfFailedRecords++;
				}
				
			}
 		}*/
		
		//TODO : Remove this code. This is just to get performance stats
		for(Company company : companies) {
			try { 
				this.addCompany(company);
				numOfSuccessRecords++;
			} catch (Exception ex ) {
				//TODO : KEEP track of insertion failure for this record with reason
				//ex.printStackTrace();
				numOfFailedRecords++;
			}
 		}
		
		stopWatch.stop();
		double totalCompletionTimeInSec = stopWatch.getTotalTimeSeconds();
		System.out.println(totalCompletionTimeInSec);
		System.out.println(numOfSuccessRecords);
		System.out.println(numOfFailedRecords);
	}
	
	private Company getCompanyFromFileRecord(String[] headers, String recordInCSV) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		// Key is field of the class and value is list of headers mapped to a field.As multiple header may be mapped to a single field
		Map<String,List<String>> fieldsToHeaderMap =  FileImportUtil.getFieldsToHeaderMapForCompanyCSV(); 
		
		Map<String,Integer> headerIndexMap =  new HashMap<String,Integer>();
		for(int i=0; i< headers.length;i++) {
			headerIndexMap.put(headers[i], i);
		}
		
		String[] splitRecord = recordInCSV.split(",");
		Company company = new Company();
		for(String fieldName : fieldsToHeaderMap.keySet()) {
			List<String> mappedHeaders = fieldsToHeaderMap.get(fieldName) ;
			Class<Company> aClass = Company.class;
			Field field =  aClass.getDeclaredField(fieldName);
			StringBuffer fieldValue = new StringBuffer();
			
			// For each header that is mapped to a field in class get its value from the csv record and concat it by space
			for(String headerInCsv : mappedHeaders) { 
				int index = headerIndexMap.get(headerInCsv);
				try {
					fieldValue.append(splitRecord[index].trim());
				} catch (ArrayIndexOutOfBoundsException ex) {// this can occur if record in csv does not have value corresponding to a column
					break;
				}
				fieldValue.append(SPACE);// concat multiple headers values mapped to a single field by a space
			}
			field.setAccessible(true);
			field.set(company, fieldValue.toString().trim());
		}
		
		//Set dummy values for not null fields not available in csv for now
		// TODO : to get update from businees on what to do for these fields
		company.setCompanyType("unknown");
		company.setSearchHelp("unknown");
		company.setSpecialNote("unknown");
		company.setCompanySince(new Date());
 		return company;
	}
	
	
	/**
	 * Return default sort field for company service
	 * 
	 * @return String 
	 */
	@Override
	public String getDefaultSortField()  {
		return DEFAULT_SORT_BY_COMPANY_NAME;
	}
}