package com.thinkhr.external.api.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.thinkhr.external.api.db.entities.Company;
import com.thinkhr.external.api.exception.APIErrorCodes;
import com.thinkhr.external.api.exception.ApplicationException;
import com.thinkhr.external.api.repositories.CompanyRepository;

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
	
    @Autowired
    private CompanyRepository companyRepository;
    public static final String DEFAULT_SORT_BY = "companyName";

    /**
     * Fetch all companies from database. To avoid huge number of records, we are limiting the records only upto 10 numbers.
     * 
     * @return List<Company> object 
     */
    public List<Company> getAllCompany(Integer offset,Integer limit ,String sortField) {
    	List<Company> companies = new ArrayList<Company>();

    	Pageable pageable = getPageable(offset, limit, sortField);
    	Page<Company> companyList  = (Page<Company>) companyRepository.findAll(pageable);

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
    /**
     * Return default sort field for company service
     * 
     * @return String 
     */
    @Override
    public String getDefaultSortField()  {
    	return null;
    }
}