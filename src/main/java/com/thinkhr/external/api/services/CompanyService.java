package com.thinkhr.external.api.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.thinkhr.external.api.db.entities.Company;
import com.thinkhr.external.api.db.entities.User;
import com.thinkhr.external.api.repositories.CompanyRepository;
import com.thinkhr.external.api.repositories.UserRepository;

/*
* The UserService2 class provides a collection of all
* services related with users
*
* 
*/

@Service
public class CompanyService  {
	
    @Autowired
    private CompanyRepository companyRepository;

    /**
     * Fetch all companies from system
     * @return List<Company> object 
     */
    public List<Company> getAllCompany() {
		List<Company> companies = new ArrayList<Company>();
		companyRepository.findAll().forEach(c -> companies.add(c));
		return companies;
	}
    
    /**
     * Fetch specific company from system
     * @param clientId
     * @return Company object 
     */
    public Company getCompany(long clientId) {
    	Company company = null;
    	company = companyRepository.findOne(clientId);
    	return company;
    }
    
    /**
     * Add a company in system
     * @param Company object
     */
    public void addCompany(Company company)  {
    	companyRepository.save(company);
    }
    
    /**
     * Update a company in system
     * @param Company object
     */
    public void updateCompany(Company company)  {
    	companyRepository.save(company);
    }
    
    /**
     * Delete specific company from system
     * @param clientId
     */
    public void deleteCompany(long clientId)  {
    	companyRepository.delete(clientId);
    }
}