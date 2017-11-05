package com.thinkhr.external.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thinkhr.external.api.db.entities.Company;
import com.thinkhr.external.api.model.CompanyModel;
import com.thinkhr.external.api.repositories.CompanyRepository;

/*
* The UserService2 class provides a collection of all
* services related with users
*
* 
*/

@Service
public class CompanyService  extends CommonService {
	
    @Autowired
    private CompanyRepository companyRepository;

    /**
     * Fetch all companies from system
     * @return List<Company> object 
     */
    public List<CompanyModel> getAllCompany() {
		List<Company> companies = new ArrayList<Company>();
		companyRepository.findAll().forEach(c -> companies.add(c));
		return companies.stream().map(c -> { return (CompanyModel) convert(c, CompanyModel.class); }).collect(Collectors.toList());
	}
    
    /**
     * Fetch specific company from system
     * @param clientId
     * @return Company object 
     */
    public Company getCompany(int clientId) {
    	Company company = null;
    	company = companyRepository.findOne(clientId);
    	return company;
    }
    
    /**
     * Add a company in system
     * @param CompanyModel object
     */
    public Integer addCompany(CompanyModel companyModel)  {
		Company company = (Company)convert(companyModel, Company.class);
    	companyRepository.save(company);
    	return company.getClientId();
    }
    
    /**
     * Update a company in system
     * @param CompanyModel object
     */
    public void updateCompany(Company company)  {
    	companyRepository.save(company);
    }
    
    /**
     * Delete specific company from system
     * @param clientId
     */
    public void deleteCompany(int clientId)  {
    	companyRepository.delete(clientId);
    }
}