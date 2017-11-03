package com.thinkhr.external.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.thinkhr.external.api.db.entities.Company;
import com.thinkhr.external.api.services.CompanyService;


/**
 * Company Controller for performing operations
 * related with Company object.
 * 
 */
@RestController
@RequestMapping(path="/v1")
public class CompanyController {
	
    @Autowired
    CompanyService companyService;

    /**
     * Get all clients from repository
     * @return List<Company>
     * 
     */
    @RequestMapping(method=RequestMethod.GET,value="/clients")
    List<Company> getAllCompany() {
        return companyService.getAllCompany();
    }
    
    /**
     * Get client with given id from repository
     * @param id clientId
     * @return Company object
     * 
     */
    @RequestMapping(method=RequestMethod.GET,value="clients/{clientId}")
    public Company getById(@PathVariable(name="clientId",value = "clientId") Long clientId) {
        Company company = companyService.getCompany(clientId);
        return company;
    }
    
    
    /**
     * Delete specific company from system
     * @param clientId
     */
    @RequestMapping(method=RequestMethod.DELETE,value="clients/{clientId}")
    public void deleteCompany(@PathVariable(name="clientId",value = "clientId") Long clientId) {
    	companyService.deleteCompany(clientId);
    }
    
    
    /**
     * Update a company in system
     * @param Company object
     */
    @RequestMapping(method=RequestMethod.PUT,value = "/clients")
	public void updateCompany(@RequestBody Company company) {
    	companyService.updateCompany(company);
	}
    
    
    /**
     * Add a company in system
     * @param Company object
     */
    @RequestMapping(method=RequestMethod.POST,value = "/clients")
   	public void addCompany(@RequestBody Company Company) {
    	companyService.addCompany(Company);
   	}
}

