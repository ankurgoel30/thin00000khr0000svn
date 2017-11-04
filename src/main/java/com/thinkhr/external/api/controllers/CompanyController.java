package com.thinkhr.external.api.controllers;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.thinkhr.external.api.db.entities.Company;
import com.thinkhr.external.api.model.CompanyModel;
import com.thinkhr.external.api.services.CompanyService;


/**
 * Company Controller for performing operations
 * related with Company object.
 * 
 */
@RestController
@RequestMapping(path="/v1/companies")
public class CompanyController {
	
    @Autowired
    CompanyService companyService;

    /**
     * Get all clients from repository
     * @return List<Company>
     * 
     */
    @RequestMapping(method=RequestMethod.GET)
    List<CompanyModel> getAllCompany() {
        return companyService.getAllCompany();
    }
    
    /**
     * Get client with given id from repository
     * @param id clientId
     * @return Company object
     * 
     */
    @RequestMapping(method=RequestMethod.GET,value="/{companyId}")
    public Company getById(@PathVariable(name="companyId",value = "companyId") Long companyId) {
        Company company = companyService.getCompany(companyId);
        if (company == null) {
        	throw new EntityNotFoundException();
        }
        return company;
    }
    
    
    /**
     * Delete specific company from system
     * @param companyId
     */
    @RequestMapping(method=RequestMethod.DELETE,value="/{companyId}")
    public void deleteCompany(@PathVariable(name="companyId",value = "companyId") Long companyId) {
    	companyService.deleteCompany(companyId);
    }
    
    
    /**
     * Update a company in system
     * @param CompanyModel object
     */
    @RequestMapping(method=RequestMethod.PUT)
	public void updateCompany(@RequestBody Company company) {
    	companyService.updateCompany(company);
	}
    
    
    /**
     * Add a company in system
     * @param Company object
     */
    @RequestMapping(method=RequestMethod.POST)
   	public void addCompany(@RequestBody Company Company) {
    	companyService.addCompany(Company);
   	}
}

